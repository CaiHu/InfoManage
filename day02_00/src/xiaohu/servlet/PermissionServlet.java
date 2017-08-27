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
import xiaohu.dao.iface.PermissionDao;
import xiaohu.dao.impl.PermissionDaoImpl;

/**
 * Servlet implementation class PermissionServlet
 */
@WebServlet("/permission.servlet")
public class PermissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	PermissionDao permissionDao=new PermissionDaoImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; chaset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		String param=null==request.getParameter("param")?"":request.getParameter("param");
		String id=null==request.getParameter("id")?"":request.getParameter("id");
		String permissionId=null==request.getParameter("permissionId")?"":request.getParameter("permissionId");
		String permissionName=null==request.getParameter("permissionName")?"":request.getParameter("permissionName");
		String url=null==request.getParameter("url")?"":request.getParameter("url");
		String remark=null==request.getParameter("remark")?"":request.getParameter("remark");
		String parentId=null==request.getParameter("parentId")?"":request.getParameter("parentId");
		
		PrintWriter out=response.getWriter();
		Map<String,Object> map=new HashMap<String,Object>();
		
		if("init".equals(param)){
			List<Permission> list=permissionDao.allPermission();
			if(list!=null){
				map.put("result", "ok");
				map.put("list", list);
			}else{
				map.put("msg", "error");
			}
		}
		
		if("delete".equals(param)){
			map.put("result", "error");
		}
		if("update".equals(param)){
			Permission permission=new Permission(Long.valueOf(permissionId),permissionName,url,remark,Long.valueOf(parentId));
			if(permissionDao.updatePermission(permission)){
				map.put("result", "ok");
			}else{
				map.put("msg", "error");
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
