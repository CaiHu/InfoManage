package xiaohu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import xiaohu.bean.Page;
import xiaohu.bean.Permission;
import xiaohu.bean.User;
import xiaohu.dao.iface.UserDao;
import xiaohu.dao.impl.UserDaoImpl;
import xiaohu.service.iface.UserService;
import xiaohu.service.impl.UserServiceImpl;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user.servlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	UserDao userDao=new UserDaoImpl();
	UserService userService=new UserServiceImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		
		String param=request.getParameter("param");
		String id=request.getParameter("id")==null?"":request.getParameter("id");
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String phone=request.getParameter("phone");
		String name=request.getParameter("keyword");
		String currentPage=request.getParameter("currentPage")==null?"":request.getParameter("currentPage");
		String userId=request.getParameter("userId")==null?"":request.getParameter("userId");
		PrintWriter out=response.getWriter();
		Map<String,Object> map=new HashMap<>();
		Page page=new Page();
		
		//获取整个表
		/*if("tableInit".equals(param)){
			if("".equals(currentPage)){
				currentPage="1";
			}
			page.setCurrentPage(Integer.valueOf(currentPage));
			userDao.getTotalRow(page);
			userDao.getTotalPage(page);
			int total=page.getTotalPage();
			List<User> list=userDao.getAllUserByPage(page);
			if(list!=null){
				map.put("result", "ok");
				map.put("list", list);
				map.put("total",total);
			}else{
				map.put("result", "error");
				map.put("msg", "出现错误");
			}
		}*/
		//获得权限
		if("getPermissions".equals(param)){
			List<Permission> list=userDao.getPermissionByUserId(Long.valueOf(userId));
			if(!list.isEmpty()){
				map.put("result", "ok");
				map.put("list", list);
			}else{
				map.put("msg", "没有数据");
			}
		}
		//登录
		if("login".equals(param)){
			long resultUserId=userDao.checkUser(userName, password);
			if(resultUserId!=0){
				map.put("result", "ok");
				map.put("id", resultUserId);
			}else{
				map.put("msg", "没有数据");
			}
		}
		//修改用户
		if("update".equals(param)){
			User user =new User(Long.valueOf(id),userName,password,phone);
			boolean flag=userDao.updateUser(user);
			if(flag){
				map.put("result", "ok");
			}else{
				map.put("msg", "出现错误");
			}
		}
		//删除用户
		if("delete".equals(param)){
			boolean flag=userDao.deleteUser(id);
			if(flag){
				map.put("result", "ok");
			}else{
				map.put("msg", "出现错误");
			}
		}
		//新增用户
		if("add".equals(param)){
			User user=new User(01,userName,password,phone);
			if(userDao.addUser(user)){
				map.put("result", "ok");
			}else{
				map.put("result", "error");
			}
		}
		//按名字查询
		if("query".equals(param)){
			if("".equals(currentPage)){
				currentPage="1";
			}
			page.setCurrentPage(Integer.valueOf(currentPage));
			List<User> list=userService.queryUserByPageByName(page, name);
			int total=page.getTotalPage();
			if(list!=null){
				map.put("result", "ok");
				map.put("list", list);
				map.put("total",total);
			}else{
				map.put("result", "error");
				map.put("msg", "出现错误");
			}
		}
		
		String json = JSON.toJSONString(map);
		out.println(json);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
