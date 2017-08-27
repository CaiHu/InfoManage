(function(ajax){
	let userId;
	function flashMenuOpration(xhr){
		let json=JSON.parse(xhr.responseText);
		let mainMenu=document.getElementById("mainMenu");
		if(json.result=="ok"){
			let ul1=document.createElement("ul");
			for(item of json.list){
				if(item.parentId==0){
					let li1=document.createElement("li");
					li1.innerHTML=item.permissionName;
					ul1.appendChild(li1);
					let ul2=document.createElement("ul");
					ul2.hidden=true;
					for(item0 of json.list){
						if(item0.parentId==item.id){
							let li2 =document.createElement("li");
							let a =document.createElement("a");
							a.href="http://127.0.0.1:8020/day02-00-%E5%B7%B1%E6%96%B9%E8%A1%A8%E6%A0%BC/views/"+item0.url;
							a.target="mainIframe";
							a.innerHTML=item0.permissionName;
							a.style.cssText="text-decoration: none;";
							a.addEventListener("mouseover",()=>{
								a.style.cssText="text-decoration: underline;color: deepskyblue;";
							},false);
							a.addEventListener("mouseout",()=>{
								a.style.cssText="text-decoration: none;";
							},false);
							li2.appendChild(a);
							li2.style.cssText="margin-top: 30px;";
							ul2.appendChild(li2);
						}
					}
					ul1.appendChild(ul2);
					li1.style.cssText="color: red;cursor: pointer;margin-top: 30px;";
					
					li1.addEventListener("click",()=>{
						if(ul2.hidden==true){
							ul2.hidden=false;
						}else{
							ul2.hidden=true;
						}
					},false);
				}
			}
			mainMenu.appendChild(ul1);
		}else{
			mainMenu.innerHTML="没有任何权限";
		}
		
	}
	function flashMenu(){
		let param="&userId="+userId;
		ajax.remoteGet("user.servlet?param=getPermissions",flashMenuOpration,param);
	}
	function init(){
		userId=sessionStorage.getItem("userId");
		flashMenu();
	}
	window.addEventListener("load",init,false);
})(window.ajaxOperation);
