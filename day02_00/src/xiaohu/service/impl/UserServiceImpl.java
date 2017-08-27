package xiaohu.service.impl;



import java.sql.SQLException;
import java.util.List;

import xiaohu.bean.Page;
import xiaohu.bean.User;
import xiaohu.dao.iface.UserDao;
import xiaohu.dao.impl.UserDaoImpl;
import xiaohu.dbutils.DBConnection;
import xiaohu.dbutils.TransactionManager;
import xiaohu.service.iface.UserService;

public class UserServiceImpl implements UserService{
	private UserDao userDao=new UserDaoImpl();
	@Override
	public List<User> queryUserByPageByName(Page page, String name) {
		List<User> list=null;
		TransactionManager tm=new TransactionManager(DBConnection.getConnection());
		try {
			tm.beginTransaction();
			userDao.getTotalRowByName(page,name);
			userDao.getTotalPageByName(page);
			list=userDao.queryUserByPageByName(page,name);
			tm.commitAndClose();
		} catch (SQLException e) {
			e.printStackTrace();
			tm.rollbackAndClose();
		}	
		
		return list;
	}

}
