package xiaohu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xiaohu.bean.Permission;
import xiaohu.dao.iface.PermissionDao;
import xiaohu.dbutils.DBConnection;

public class PermissionDaoImpl implements PermissionDao {
	
	//初始化查找所有权限
	@Override
	public List<Permission> allPermission() {
		List<Permission> list =new ArrayList<Permission>();
		String sql="select rownum,id,name,url,remark,parentId from permission";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				Permission permission=new Permission(rs.getLong(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getLong(6));
				list.add(permission);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//删除权限
	@Override
	public boolean deletePermission(String id) {
		boolean flag=false;
		return flag;
	}
	//修改权限
	@Override
	public boolean updatePermission(Permission permission) {
		boolean flag=false;
		String sql="update permission set name=?,url=?,remark=?,parentid=? where id=?";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, permission.getPermissionName());
			pstmt.setString(2, permission.getUrl());
			pstmt.setString(3, permission.getRemark());
			pstmt.setLong(4, permission.getParentId());
			pstmt.setLong(5, permission.getId());
			int count=pstmt.executeUpdate();
			if(count!=0){
				flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
