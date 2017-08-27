var ajaxOperation = (function(ajax) {
//	let currentPage=1;
	let totalPage=0;
	let newcurrentPage=1;
	
	//删除用户处理
	function delOpration(xhr){

		let json=JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			alert("删除成功");
			tableInit();
		}else{
			alert("删除失败");
			tableInit();
		}
		
	}
	//修改用户处理
	function updateOpration(xhr){
		let json=JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			alert("更新成功");
			tableInit();
		}else{
			alert("更新失败");
			tableInit();
		}
	}
	//分页处理
	function pageCallback(event){
		if(event.target.id=="btn_firstPage"){
			newcurrentPage=1;
		}
		if(event.target.id=="btn_lastPage"){
			newcurrentPage=totalPage;
		}
		if(event.target.id=="btn_previousPage"){
			newcurrentPage-=1;
		}
		if(event.target.id=="btn_nextPage"){
			newcurrentPage+=1;
		}
		if(event.target.id=="jumpPage"){
			newcurrentPage=Number.parseInt(document.getElementById("jumpPage").value);
		}
		let param="?param=query&keyword=";
		let keyword=document.getElementById("input_span").value;
		param+=keyword;
		param+="&currentPage="+newcurrentPage;
		ajax.remoteGet("user.servlet",flashTable,param);
	}
	//查询用户处理
	function queryOpration(xhr){
		flashTable(xhr);
	}
	//查询用户监听
	function query(event){
		if(event.target.id=="btn_firstPage"){
			newcurrentPage=1;
		}
		if(event.target.id=="btn_lastPage"){
			newcurrentPage=totalPage;
		}
		if(event.target.id=="btn_previousPage"){
			newcurrentPage-=1;
		}
		if(event.target.id=="btn_nextPage"){
			newcurrentPage+=1;
		}
		let param="?param=query&keyword=";
		let keyword=document.getElementById("input_span").value;
		param+=keyword;
		param+="&currentPage="+newcurrentPage;
		ajax.remoteGet("user.servlet",queryOpration,param);
	}
	//添加用户处理
	function addOpration(xhr){
		let json = JSON.parse(xhr.responseText);
		if(json.result == "ok") {
			alert("数据添加成功");

		}else{
			alert("数据添加失败");
		}
		
	}
	//新增用户监听
	function add(){
		let param="";
		param+="&userName="+document.getElementById("userName").value;
		param+="&password="+document.getElementById("password").value;
		param+="&phone="+document.getElementById("phone").value;
		ajax.remotePost("user.servlet?param=add",addOpration,param);
	}
	//页面刷新
	function flashTable(xhr) {
		let table_tbody = document.getElementById("table_tbody");
		let json = JSON.parse(xhr.responseText);
		totalPage=json.total;
		if(table_tbody.rows.length != 1) {
			let len = table_tbody.rows.length;
			for(let i=1;i<len;i++){
				table_tbody.deleteRow(1);
			}
		}
		//分页下拉列表初始化
			let select=document.getElementById("jumpPage");
			select.length=0;
			for(let i=1;i<=totalPage;i++){
				let option=document.createElement("option");
				option.value=i;
				option.innerHTML=i;
				if(i==newcurrentPage){
					option.selected=true;
				}
				select.appendChild(option);
			}
		if(newcurrentPage==1){
			document.getElementById("btn_firstPage").disabled=true;
			document.getElementById("btn_lastPage").disabled=false;
			document.getElementById("btn_previousPage").disabled=true;
			document.getElementById("btn_nextPage").disabled=false;
		}else if(newcurrentPage==totalPage){
			document.getElementById("btn_firstPage").disabled=false;
			document.getElementById("btn_lastPage").disabled=true;
			document.getElementById("btn_previousPage").disabled=false;
			document.getElementById("btn_nextPage").disabled=true;
		}else{
			document.getElementById("btn_firstPage").disabled=false;
			document.getElementById("btn_lastPage").disabled=false;
			document.getElementById("btn_previousPage").disabled=false;
			document.getElementById("btn_nextPage").disabled=false;
		}
		if(json.result == "ok") {
			for(item of json.list) {
				let index = table_tbody.length;
				let tr = table_tbody.insertRow(index);
				tr.id = "row_" + item.id;
				let id=item.id;
				tr.insertCell(0).innerHTML = item.rownum;
				tr.insertCell(1).innerHTML = item.name;
				let oldName=item.name;
				tr.insertCell(2).innerHTML = item.password;
				tr.insertCell(3).innerHTML = item.phone;
				let oldPhone=item.phone;
				tr.insertCell(4).innerHTML = "<input type='button' name='btn_update' value='修改'>";
				tr.insertCell(5).innerHTML = "<input type='button' name='btn_delete' value='删除'>";
				
				//显示页删除按钮
				let delete_btn = tr.lastElementChild;
				delete_btn.addEventListener("click",()=>{
					let param = "&id="+id
					ajax.remoteGet("user.servlet?param=delete",delOpration,param);
				},false);
				//显示页修改按钮
				let update_btn = tr.lastElementChild.previousElementSibling;
				update_btn.addEventListener("click",()=>{
					document.getElementById("div_show").hidden = true;
					document.getElementById("div_add").hidden = false;
					document.getElementById("btn_addUser").innerHTML="修改";
					document.getElementById("userName").value=oldName;
					document.getElementById("phone").value=oldPhone;
					document.getElementById("btn_addUser").addEventListener("click",()=>{
						let param = "&id="+id
						param+="&userName="+document.getElementById("userName").value;
						param+="&password="+document.getElementById("password").value;
						param+="&phone="+document.getElementById("phone").value;
						ajax.remoteGet("user.servlet?param=update",updateOpration,param);
					},false);
					
				},false);
				//显示页用户权限管理
				let Role_btn=tr.firstElementChild.nextElementSibling;
				Role_btn.ondblclick=function(){
					let flag=confirm("点击确定进行用户角色管理");
					if(flag){
						let param="&id="+id;
						ajax.remoteGet("user_role.servlet?param=roleManage",RoleManageOpration,param);
					}
				}
				
			}
		}
	}
	//用户角色展示处理
	function RoleManageOpration(xhr){
		let json=JSON.parse(xhr.responseText);
		document.getElementById("div_show").hidden=true;
		document.getElementById("div_userRole").hidden=false;
		//点击用户权限管理页返回按钮
		document.getElementById("userRole_btn_return").addEventListener("click",()=>{
			document.getElementById("div_show").hidden=false;
			document.getElementById("div_userRole").hidden=true;
			tableInit();
		},false);
		
		let node= document.getElementById("userRoleShow");
		node.innerHTML="";
		if(json.allRole.length!=0){
			for(item of json.allRole){
				let input=document.createElement("input");
				input.id="userRole_"+item.id;
				input.value=item.id;
				input.type="checkbox";
				input.name="userRole";
				if(item.checked==true){
					input.checked=true;
				}else{
					input.checked=false;
				}
				node.appendChild(input);
				let label=document.createElement("label");
				label.innerHTML=item.name;
				label.setAttribute("for","userRole_"+item.id);
				node.appendChild(label);
				let span=document.createElement("span");
				span.innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;";
				node.appendChild(span);
			}
		}
		//修改用户角色监听
		document.getElementById("userRole_btn_update").addEventListener("click",()=>{
			let ids="";
			let roles=document.getElementsByName("userRole");
			for(item of roles){
				if(item.checked){
					if(ids==""){
						ids=item.value;
					}else{
						ids+=",";
						ids+=item.value;
					}
				}
			}
			let param="?param=updateRole";
			param+="&id="+json.userId;
			param+="&ids="+ids;
			ajax.remoteGet("user_role.servlet",updateUserRole,param);
		},false);
		
	}
	//修改用户角色处理
	function updateUserRole(xhr){
		let json=JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			alert("用户角色修改成功");
		}else{
			alert("用户角色修改失败");
		}
	}
	//载入表数据
	function tableInit() {
		let param = "?param=query";
		ajax.remoteGet("user.servlet", flashTable, param);
	}
	function init() {
		//获取数据
		tableInit();
		//显示页新增按钮
		document.getElementById("btn_show").addEventListener("click", () => {
			document.getElementById("div_show").hidden = true;
			document.getElementById("div_add").hidden = false;
			document.getElementById("btn_addUser").innerHTML="添加";
			//添加页添加按钮
			document.getElementById("btn_addUser").addEventListener("click",add,false);
		}, false);
		//添加页返回按钮
		document.getElementById("btn_addReturn").addEventListener("click", () => {
			document.getElementById("div_show").hidden = false;
			document.getElementById("div_add").hidden = true;
			tableInit();
		}, false);
		
		//显示页查询按钮
		document.getElementById("query").addEventListener("click",query,false);
		//显示页分页按钮
		document.getElementById("btn_firstPage").addEventListener("click",pageCallback,false);
		document.getElementById("btn_lastPage").addEventListener("click",pageCallback,false);
		document.getElementById("btn_previousPage").addEventListener("click",pageCallback,false);
		document.getElementById("btn_nextPage").addEventListener("click",pageCallback,false);
		document.getElementById("jumpPage").addEventListener("change",pageCallback,false);
		
	}
	window.addEventListener("load", init, false);
})(window.ajaxOperation);