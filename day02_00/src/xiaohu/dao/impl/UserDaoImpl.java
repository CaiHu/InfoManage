package xiaohu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xiaohu.bean.Page;
import xiaohu.bean.Permission;
import xiaohu.bean.User;
import xiaohu.dao.iface.UserDao;
import xiaohu.dbutils.DBConnection;

public class UserDaoImpl implements UserDao{
	//获取所有用户----已经废弃
	@Override
	public List<User> allUser() {
		List<User> list=new ArrayList<>();
		String sql="select id, rownum, name,password,telephone from users";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				User user=new User(rs.getLong(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//添加用户
	@Override
	public boolean addUser(User user) {
		boolean flag=false;
		String userName=user.getName();
		String password=user.getPassword();
		String phone=user.getPhone();
		String sql="insert into users values(users_id.nextval,?,?,?)";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
			pstmt.setString(3, phone);
			int rs=pstmt.executeUpdate();
			if(rs==1){
				flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	//根据名字查找用户----已经废弃
	@Override
	public List<User> queryUserByName(String name) {
		List<User> list=new ArrayList<>();
		String sql="select id, rownum, name,password,telephone from users where name like ?";
		
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			String newName="%"+name+"%";
			pstmt.setString(1, newName);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				User user=new User(rs.getLong(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//根据分页得到用户----已经废弃
	@Override
	public List<User> getAllUserByPage(Page page) {
		List<User> list=new ArrayList<>();
		int currentPage=page.getCurrentPage();
		int pageNumber=page.getPageNumber();
		String sql="select id, rn,name,password,telephone from (select id,rownum as rn,name,password,telephone from(select id, rownum, name,password,telephone from users)where rownum<=(?)*?) where rn>(?-1)*?";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, currentPage);
			pstmt.setInt(2, pageNumber);
			pstmt.setInt(3, currentPage);
			pstmt.setInt(4, pageNumber);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				User user=new User(rs.getLong(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//总页数----已经废弃
	@Override
	public void getTotalPage(Page page) {
		if(page.getTotalRow()%page.getPageNumber()==0){
			page.setTotalPage(page.getTotalRow()/page.getPageNumber());
			System.out.println("总页数为"+page.getTotalRow()/page.getPageNumber());
		}else{
			page.setTotalPage(page.getTotalRow()/page.getPageNumber()+1);
			System.out.println("总页数为"+page.getTotalRow()/page.getPageNumber()+1);
		}
		
	}
	//总行数----已经废弃
	@Override
	public void getTotalRow(Page page) {
		int rownumber=0;
		String sql="select count(*) from users";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				rownumber=rs.getInt(1);
			}
			page.setTotalRow(rownumber);
			System.out.println("总行数为"+rownumber);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//根据名字查询并进行分页
	@Override
	public List<User> queryUserByPageByName(Page page, String name) {
		List<User> list=new ArrayList<>();
		int currentPage=page.getCurrentPage();
		int pageNumber=page.getPageNumber();
		StringBuffer sb=new StringBuffer("select id,rn,name,password,telephone from(select id,name,password,telephone,rownum as rn from (select id,name,password,telephone from users where 1=1 ");
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
				User user=new User(rs.getLong(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
				list.add(user);
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
			sql="select count(*) from users";
		}else{
			sql="select count(*) from users where name like ?";
			
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
	//用户登录检测
	@Override
	public long checkUser(String userName, String password) {
		long userId = 0l;
		User user=null;
		String sql="select * from users where name=? and password=?";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				 user =new User(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4));
				 userId=user.getId();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}
	@Override
	public List<Permission> getPermissionByUserId(long userId) {
		List<Permission> list=new ArrayList<>();
		
		String sql="select p.id,u.name,p.name as permissionName,p.url,p.remark,p.parentId from users u inner join user_role ur on u.id=ur.userid inner join role_permission rp on ur.roleid=rp.roleid inner join permission p on rp.permissionid=p.id where u.id=?";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, userId);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				Permission p=new Permission(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getLong(6));
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public boolean deleteUser(String id) {
		boolean flag=false;
		String sql="delete from users where id=?";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, Long.valueOf(id));
			int count=pstmt.executeUpdate();
			if(count !=0){
				flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public boolean updateUser(User user) {
		boolean flag=false;
		String sql="update  users set name=?,password=?,telephone=? where id=?";
		Connection conn=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,user.getName());
			pstmt.setString(2,user.getPassword());
			pstmt.setString(3,user.getPhone());
			pstmt.setLong(4, user.getId());
			int count=pstmt.executeUpdate();
			if(count !=0){
				flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}
