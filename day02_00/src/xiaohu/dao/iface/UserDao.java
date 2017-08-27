package xiaohu.dao.iface;

import java.util.List;

import xiaohu.bean.Page;
import xiaohu.bean.Permission;
import xiaohu.bean.User;

public interface UserDao {
	public List<User> allUser();
	public boolean addUser(User user);
	public List<User> queryUserByName(String name);
	public boolean deleteUser(String id);
	public boolean updateUser(User user);
	
	public List<User> getAllUserByPage(Page page);
	public void getTotalPage(Page page);
	public void getTotalRow(Page page);
	
	public List<User> queryUserByPageByName(Page page,String name);
	public void getTotalRowByName(Page page,String name);
	public void getTotalPageByName(Page page);
	
	public long checkUser(String userName,String password);
	public List<Permission> getPermissionByUserId(long userId);
}
