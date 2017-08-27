package xiaohu.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xiaohu.bean.Page;
import xiaohu.bean.Role;
import xiaohu.dao.iface.RoleDao;
import xiaohu.dao.impl.RoleDaoImpl;
import xiaohu.dbutils.DBConnection;
import xiaohu.dbutils.TransactionManager;
import xiaohu.service.iface.RoleService;

public class RoleServiceImpl implements RoleService {
	private RoleDao roleMsgDao=new RoleDaoImpl();
	@Override
	public List<Role> queryRoleByPageByName(Page page, String name) {
		List<Role> list=null;
		TransactionManager tm=new TransactionManager(DBConnection.getConnection());
		try {
			tm.beginTransaction();
			roleMsgDao.getTotalRowByName(page, name);
			roleMsgDao.getTotalPageByName(page);
			list=roleMsgDao.allRoles(page, name);
			tm.commitAndClose();
		} catch (SQLException e) {
			e.printStackTrace();
			tm.rollbackAndClose();
		}	
		return list;
	}

}
