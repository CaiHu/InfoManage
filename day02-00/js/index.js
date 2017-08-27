function add(){
	let add=document.getElementById("section_add");
	let table=document.getElementById("Table");
	table.hidden=true;
	add.hidden=false;
}
function mod(){
	let t="";
	let count=0;
	let arrays=document.getElementsByName("checks");
		for(item of arrays){
			if(item.checked==true){
				count++;				
			}
		}
		if(count>1){
			alert("一次最多修改一条数据！");
		}else if(count==0){
			alert("请选择数据行！");
		}else{
			alert("现在修改");
		}
}
function del(){
	let t="";
	let arrays=document.getElementsByName("checks");
		for(item of arrays){
			if(item.checked==true){
				if(t==""){
					t+=item.value;
				}else{
					t+=",";
					t+=item.value;
				}
			}
		}
		if(t!=""){
			confirm("确定要删除么？");
		}else{
			alert("必须有选中的删除项");
		}
}
function init(){
	document.getElementById("showTable_delete").addEventListener("click",del,false);
	document.getElementById("showTable_modify").addEventListener("click",mod,false);
	document.getElementById("showTable_add").addEventListener("click",add,false);
	document.getElementById("all").addEventListener("change",()=>{
		let checkValue=document.getElementById("all").checked;
		let arrays=document.getElementsByName("checks");
		for(item of arrays){
			item.checked=checkValue;
		}
	},false);
}

window.addEventListener("load",init,false);