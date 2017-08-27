package xiaohu.service.iface;

import java.util.List;

import xiaohu.bean.Page;
import xiaohu.bean.Role;

public interface RoleService {
	public List<Role> queryRoleByPageByName(Page page,String name);
}
