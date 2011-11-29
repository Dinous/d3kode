/**
 * 
 */
function obselInfo(evt){
	var obj = evt.target;
	var id = obj.getAttributeNS(null, 'id');
	
	/*if(document.getElementById("rectObsel_"+id).style.display == "" ||
			document.getElementById("rectObsel_"+id).style.display == "block"){
		document.getElementById("rectObsel_"+id).style.display = "none";
	}else{*/
		document.getElementById("rectObsel_"+id).style.display = "block";
	//}
}

function hide(evt, id){
	document.getElementById(id).style.display = "none";
}

function methodInfo(evt, id){
	document.getElementById(id).style.display = "block";
}