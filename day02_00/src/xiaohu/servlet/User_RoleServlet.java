package xiaohu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import xiaohu.bean.Role;
import xiaohu.dao.iface.User_RoleDao;
import xiaohu.dao.impl.User_RoleDaoImpl;
import xiaohu.dbutils.DBConnection;
import xiaohu.dbutils.TransactionManager;

/**
 * Servlet implementation class RoleServlet
 */
@WebServlet("/user_role.servlet")
public class User_RoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	private User_RoleDao roleDao=new User_RoleDaoImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; chaset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		String param=null==request.getParameter("param")?"":request.getParameter("param");
		String id=null==request.getParameter("id")?"":request.getParameter("id");
		String ids=null==request.getParameter("ids")?"":request.getParameter("ids");
		
		
		PrintWriter out=response.getWriter();
		Map<String,Object> map=new HashMap<String,Object>();	
		
		if("updateRole".equals(param)){
			TransactionManager tm=new TransactionManager(DBConnection.getConnection());
			try {
				tm.beginTransaction();
				roleDao.deleteUserRole(id);
				if(ids!=""){
					roleDao.updateUserRole(id, ids);
				}
				tm.commitAndClose();
				map.put("result","ok");
			} catch (SQLException e) {
				tm.rollbackAndClose();
				map.put("result","error");
				e.printStackTrace();
			}
		}
		
		if("roleManage".equals(param)){
			List<Role> allRole=roleDao.getAllRoles();
			List<Role> userRole=roleDao.getRolesByUserId(Long.valueOf(id));
			for(Role role:allRole){
				if(userRole.contains(role)){
					role.setChecked(true);
				}
			}
			map.put("result", "ok");
			map.put("userId",id);
			map.put("allRole", allRole);
		}
		String json=JSON.toJSONString(map);
		out.println(json);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
