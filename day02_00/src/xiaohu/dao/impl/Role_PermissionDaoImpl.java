package xiaohu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xiaohu.bean.Permission;
import xiaohu.dao.iface.Role_PermissionDao;
import xiaohu.dbutils.DBConnection;

public class Role_PermissionDaoImpl implements Role_PermissionDao {
	//角色所拥有的权限
	@Override
	public List<Permission> allRolePermission(String id) {
		List<Permission> list=new ArrayList<>();
		String sql="select id,name from permission where id in(select permissionid from role_permission where roleid =?)";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, Long.valueOf(id));
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				Permission permission=new Permission(rs.getLong(1),rs.getString(2));
				list.add(permission);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//全部权限
	@Override
	public List<Permission> allPermission() {
		List<Permission> list =new ArrayList<>();
		String sql="select id,name from permission";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				Permission permission=new Permission(rs.getLong(1),rs.getString(2));
				list.add(permission);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//删除角色拥有的权限
	@Override
	public boolean deleteRolePermission(String roleId) {
		boolean flag=false;
		String sql="delete from role_permission where roleid=?";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, Long.valueOf(roleId));
			int count =pstmt.executeUpdate();
			if(count!=0){
				flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	@Override
	public boolean updateRolePermission(String roleId, String ids) {
		boolean flag=false;
		String array[]=ids.split(",");
		String sql="insert into role_permission values(role_permission_id.nextval,?,?)";
		Connection conn=new DBConnection().getConnection();
		int count=0;
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			for(int i=0;i<array.length;i++){
				pstmt.setLong(1, Long.valueOf(roleId));
				pstmt.setLong(2, Long.valueOf(array[i]));
				count=pstmt.executeUpdate();
			}
			if(count==1){
				flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
