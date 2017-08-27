(function(ajax){
	function loginOpration(xhr){
		let json=JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			let name=document.getElementById("userName").value;
			let password=document.getElementById("password").value;
			sessionStorage.setItem("userId",json.id);
			sessionStorage.setItem("name",name);
			sessionStorage.setItem("password",password);
			window.location.href="views/main.html";
		}else{
			window.history.go(0);
		}
	}
	function login(){
		let param="param=login";
		param+="&userName="+document.getElementById("userName").value;
		param+="&password="+document.getElementById("password").value;
		ajax.remotePost("user.servlet",loginOpration,param);
	}
	function init(){
		document.getElementById("btn_login").addEventListener("click",login,false);
	}
	window.addEventListener("load",init,false)
})(window.ajaxOperation);

