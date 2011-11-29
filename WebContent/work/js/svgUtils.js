/**
 * 
 */

function close(id){
	var tableObsel = document.getElementById(id);
	tableObsel.style.display ="none";
}

function add(item){
	document.getElementsByTagName("body")[0].appendChild(item);
}


function coor(evt){
	alert("X = "+evt.clientX +" Y = "+ evt.clientY);
}
