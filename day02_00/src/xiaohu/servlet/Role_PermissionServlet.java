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

import xiaohu.bean.Permission;
import xiaohu.bean.Role;
import xiaohu.dao.iface.Role_PermissionDao;
import xiaohu.dao.impl.Role_PermissionDaoImpl;

/**
 * Servlet implementation class Role_PermissionServlet
 */
@WebServlet("/role_permission.servlet")
public class Role_PermissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	Role_PermissionDao rolepermissionDao=new Role_PermissionDaoImpl();
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; chaset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		String param=null==request.getParameter("param")?"":request.getParameter("param");
		String id=null==request.getParameter("id")?"":request.getParameter("id");
		String roleId=null==request.getParameter("roleId")?"":request.getParameter("roleId");
		String ids=null==request.getParameter("ids")?"":request.getParameter("ids");
		
		PrintWriter out=response.getWriter();
		Map<String,Object> map=new HashMap<String,Object>();	
		if("rolePermissionManage".equals(param)){
			List<Permission> rolePermission=rolepermissionDao.allRolePermission(id);
			List<Permission> allPermission=rolepermissionDao.allPermission();
			for(Permission permission:allPermission){
				if(rolePermission.contains(permission)){
					permission.setChecked(true);
				}
			}
			map.put("result", "ok");
			map.put("roleId",id);
			map.put("allPermission", allPermission);
		}
		if("updatePermission".equals(param)){
			rolepermissionDao.deleteRolePermission(roleId);
			if(ids!=""){
				rolepermissionDao.updateRolePermission(roleId, ids);
			}
			map.put("result", "ok");
			
			
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
