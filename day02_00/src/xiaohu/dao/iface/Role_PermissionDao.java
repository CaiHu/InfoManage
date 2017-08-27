package xiaohu.dao.iface;

import java.util.List;

import xiaohu.bean.Permission;

public interface Role_PermissionDao {
	public List<Permission> allRolePermission(String id);
	public List<Permission> allPermission();
	
	public boolean deleteRolePermission(String roleId);
	public boolean updateRolePermission(String roleId,String ids);
}
