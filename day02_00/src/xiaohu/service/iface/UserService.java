package xiaohu.service.iface;

import java.util.List;

import xiaohu.bean.Page;

public interface UserService<User> {
	public List<User> queryUserByPageByName(Page page,String name);
}
