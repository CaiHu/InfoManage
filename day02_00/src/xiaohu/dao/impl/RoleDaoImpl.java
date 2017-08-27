package xiaohu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xiaohu.bean.Page;
import xiaohu.bean.Role;
import xiaohu.bean.User;
import xiaohu.dao.iface.RoleDao;
import xiaohu.dbutils.DBConnection;

public class RoleDaoImpl implements RoleDao {
	//分页初始化
	@Override
	public List<Role> allRoles(Page page,String name) {
		List<Role> list=new ArrayList<>();
		int currentPage=page.getCurrentPage();
		int pageNumber=page.getPageNumber();
		StringBuffer sb=new StringBuffer("select id,rn,name,remark from(select id,name,remark,rownum as rn from (select id,name,remark from role where 1=1 ");
		if(name!=null){
			sb.append("and name like ?");
		}
		sb.append(") where rownum<=(");
		sb.append("?)*?) where rn>(?-1)*?");
		
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sb.toString());
			if(name!=null){
				String newName="%"+name+"%";
				pstmt.setString(1, newName);
				pstmt.setInt(2, currentPage);
				pstmt.setInt(3, pageNumber);
				pstmt.setInt(4, currentPage);
				pstmt.setInt(5, pageNumber);
			}
			else{
				pstmt.setInt(1, currentPage);
				pstmt.setInt(2, pageNumber);
				pstmt.setInt(3, currentPage);
				pstmt.setInt(4, pageNumber);
			}
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				Role role=new Role(rs.getLong(1),rs.getInt(2),rs.getString(3),rs.getString(4));
				list.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//根据名字查询得到总行数
	@Override
	public void getTotalRowByName(Page page, String name) {
		int rownumber=0;
		String sql;
		if(name==null){
			sql="select count(*) from role";
		}else{
			sql="select count(*) from role where name like ?";
		}
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			if(null!=name){
				String newName="%"+name+"%";
				pstmt.setString(1, newName);
			}
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				rownumber=rs.getInt(1);
			}
			page.setTotalRow(rownumber);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//根据名字查询的到总页数
	@Override
	public void getTotalPageByName(Page page) {

			if(page.getTotalRow()%page.getPageNumber()==0){
				page.setTotalPage(page.getTotalRow()/page.getPageNumber());
			}else{
				page.setTotalPage(page.getTotalRow()/page.getPageNumber()+1);
			}
		}
	//添加角色
	@Override
	public boolean addRole(String roleName, String remark) {
		boolean flag=false;
		String sql="insert into role values(role_id.nextval,?,?)";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, roleName);
			pstmt.setString(2, remark);
			int count=pstmt.executeUpdate();
			if(count==1){
				flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	//删除角色
	@Override
	public boolean deleteRole(String id) {
		boolean flag=false;
		String sql="delete from role where id=?";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, Long.valueOf(id));
			int count=pstmt.executeUpdate();
			if(count!=0){
				flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	//修改角色
	@Override
	public boolean updateRole(String roleName, String remark,String id) {
		boolean flag=false;
		String sql="update role set name=?,remark=? where id=?";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, roleName);
			pstmt.setString(2, remark);
			pstmt.setLong(3, Long.valueOf(id));
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
