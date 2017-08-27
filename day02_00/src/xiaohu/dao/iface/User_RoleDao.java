package xiaohu.dao.iface;

import java.util.List;

import xiaohu.bean.Role;

public interface User_RoleDao {
	public List<Role> getAllRoles();
	public List<Role> getRolesByUserId(long id);
	
	public boolean deleteUserRole(String id);
	public boolean updateUserRole(String id,String ids);
	
	
}
