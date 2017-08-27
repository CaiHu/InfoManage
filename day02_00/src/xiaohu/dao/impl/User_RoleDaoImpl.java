package xiaohu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import xiaohu.bean.Role;
import xiaohu.dao.iface.User_RoleDao;
import xiaohu.dbutils.DBConnection;

public class User_RoleDaoImpl implements User_RoleDao{


	@Override
	public List<Role> getAllRoles() {
		String sql="select * from role";
		List<Role> list=new ArrayList<>();
		Connection conn=new DBConnection().getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				Role role=new Role(rs.getLong(1),rs.getString(2),rs.getString(3));
				list.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	//查找这个id的用户具有哪些权限
	@Override
	public List<Role> getRolesByUserId(long id) {
		String sql="select * from role where id in (select roleid from user_role where userid=?)";
		List<Role> list=new ArrayList<>();
		Connection conn=new DBConnection().getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				Role role=new Role(rs.getLong(1),rs.getString(2),rs.getString(3));
				list.add(role);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	@Override
	public boolean deleteUserRole(String id) {
		boolean flag=false;
		String sql="delete from user_role where userid=?";
		Connection conn=new DBConnection().getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, Long.valueOf(id));
			if(pstmt.executeUpdate()==1){
				flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public boolean updateUserRole(String id, String ids) {
		boolean flag=false;
		String array[]=ids.split(",");
		String sql="insert into user_role values(user_role_id.nextval,?,?)";
		Connection conn=new DBConnection().getConnection();
		
			int count=0;
			try {
				PreparedStatement pstmt=conn.prepareStatement(sql);
				for(int i=0;i<array.length;i++){
					pstmt.setLong(1, Long.valueOf(id));
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
