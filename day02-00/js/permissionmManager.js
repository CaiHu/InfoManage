var ajaxOperation = (function(ajax) {
	function flashTable(xhr) {
		let table_tbody = document.getElementById("table_tbody");
		let json = JSON.parse(xhr.responseText);
		if(table_tbody.rows.length != 1) {
			let len = table_tbody.rows.length;
			for(let i = 1; i < len; i++) {
				table_tbody.deleteRow(1);
			}
		}
		if(json.result == "ok") {
			for(item of json.list) {
				let index = table_tbody.length;
				let tr = table_tbody.insertRow(index);
				tr.id = "row_" + item.id;
				let id = item.id;
				tr.insertCell(0).innerHTML = item.rownum;
				tr.insertCell(1).innerHTML = item.permissionName;
				let oldName = item.permissionName;
				tr.insertCell(2).innerHTML = item.url;
				let oldUrl = item.url;
				tr.insertCell(3).innerHTML = item.remark;
				let oldRemark = item.remark;
				tr.insertCell(4).innerHTML = item.parentId;
				let oldParentId = item.parentId;
				tr.insertCell(5).innerHTML = "<input type='button' name='btn_update' value='修改'>";
				tr.insertCell(6).innerHTML = "<input type='button' name='btn_delete' value='删除'>";
				//点击权限添加页返回按钮
				document.getElementById("btn_addReturn").addEventListener("click", () => {
					document.getElementById("div_show").hidden = false;
					document.getElementById("div_add").hidden = true;
					tableInit();
				}, false);
				//显示页删除按钮
				let delete_btn = tr.lastElementChild;
				delete_btn.addEventListener("click", () => {
					let param = "&id=" + id
					ajax.remoteGet("permission.servlet?param=delete", delOpration, param);
				}, false);
				//显示页修改按钮
				let update_btn = tr.lastElementChild.previousElementSibling;
				update_btn.addEventListener("click", () => {
					document.getElementById("div_show").hidden = true;
					document.getElementById("div_add").hidden = false;
					document.getElementById("btn_addUser").innerHTML = "修改";
					document.getElementById("permissionName").value = oldName;
					document.getElementById("url").value = oldUrl;
					document.getElementById("remark").value = oldRemark;
					document.getElementById("parentId").value = oldParentId;
					document.getElementById("btn_addUser").addEventListener("click", () => {
						let param = "&permissionId=" + id
						param += "&permissionName=" + document.getElementById("permissionName").value;
						param += "&url=" + document.getElementById("url").value;
						param += "&remark=" + document.getElementById("remark").value;
						param += "&parentId=" + document.getElementById("parentId").value;
						console.log(param);
						ajax.remotePost("permission.servlet?param=update", updateOpration, param);
					}, false);

				}, false);

			}
		}
	}

	function updateOpration(xhr) {
		let json = JSON.parse(xhr.responseText);
		if(json.result == "ok") {
			if(confirm("修改成功,点击确定返回")) {
				document.getElementById("div_show").hidden = false;
				document.getElementById("div_add").hidden = true;
				tableInit();
			}
		} else {
			alert("修改失败");
		}
	}

	function delOpration(xhr) {
		let json = JSON.parse(xhr.responseText);
		if(json.result == "error") {
			alert("该权限已经被使用，不能删除，您可以根据需求进行权限增加或者修改");
		}
	}

	function tableInit() {
		let param = "?param=init";
		ajax.remoteGet("permission.servlet", flashTable, param);
	}

	function add() {
		console.log("这里是添加权限页面");
	}

	function init() {
		tableInit();
		//显示页新增按钮
		document.getElementById("btn_show").addEventListener("click", () => {
			document.getElementById("div_show").hidden = true;
			document.getElementById("div_add").hidden = false;
			document.getElementById("btn_addUser").innerHTML = "添加";
			//添加页添加按钮
			document.getElementById("btn_addUser").addEventListener("click", add, false);
		}, false);
	}
	window.addEventListener("load", init, false);
})(window.ajaxOperation);