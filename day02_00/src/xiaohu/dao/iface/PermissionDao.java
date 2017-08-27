package xiaohu.dao.iface;

import java.util.List;

import xiaohu.bean.Permission;

public interface PermissionDao {
	
	public List<Permission> allPermission();
	
	
	public boolean deletePermission(String id);
	public boolean updatePermission(Permission permission);
	
}
