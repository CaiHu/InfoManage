package xiaohu.dao.iface;

import java.util.List;

import xiaohu.bean.Page;
import xiaohu.bean.Role;

public interface RoleDao {
	public List<Role> allRoles(Page page,String name);
	public void getTotalRowByName(Page page,String name);
	public void getTotalPageByName(Page page);
	
	
	
	public boolean addRole(String roleName,String remark);
	public boolean deleteRole(String id);
	public boolean updateRole(String roleName,String remark,String id);
}
