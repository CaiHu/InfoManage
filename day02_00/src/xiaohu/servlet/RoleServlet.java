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
import xiaohu.bean.Role;
import xiaohu.dao.iface.RoleDao;
import xiaohu.dao.impl.RoleDaoImpl;
import xiaohu.service.iface.RoleService;
import xiaohu.service.impl.RoleServiceImpl;



/**
 * Servlet implementation class RoleMsg
 */
@WebServlet("/roleMsg.servlet")
public class RoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoleService roleService=new RoleServiceImpl();
	private RoleDao roleMsgDao=new RoleDaoImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; chaset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		String param=null==request.getParameter("param")?"":request.getParameter("param");
		String currentPage=request.getParameter("currentPage")==null?"":request.getParameter("currentPage");
		String name=null==request.getParameter("name")?"":request.getParameter("name");
		String roleName=null==request.getParameter("roleName")?"":request.getParameter("roleName");
		String remark=null==request.getParameter("remark")?"":request.getParameter("remark");
		String id=null==request.getParameter("id")?"":request.getParameter("id");
		
		PrintWriter out=response.getWriter();
		Map<String,Object> map=new HashMap<String,Object>();	
		Page page=new Page();
		//按角色名字查询
		if("query".equals(param)){
			if("".equals(currentPage)){
				currentPage="1";
			}
			page.setCurrentPage(Integer.valueOf(currentPage));
			List<Role> list=roleService.queryRoleByPageByName(page, name);
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
		//添加角色
		if("add".equals(param)){
			if(roleMsgDao.addRole(roleName, remark)){
				map.put("result", "ok");
			}else{
				map.put("result", "error");
			}
		}
		//删除角色
		if("delete".equals(param)){
			if(roleMsgDao.deleteRole(id)){
				map.put("result", "ok");
			}else{
				map.put("result", "error");
			}
		}
		//修改角色
		if("update".equals(param)){
			if(roleMsgDao.updateRole(roleName, remark, id)){
				map.put("result", "ok");
			}else{
				map.put("result", "error");
			}
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
