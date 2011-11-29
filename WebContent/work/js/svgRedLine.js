/**
 * 
 * @param evt
 */
var svgEspion = null;
var posGObsel = 0;

function getPosGObsel(posX, negativ){
	var a = parseInt(posGObsel);
	var b = Math.abs(posX);
	var c;
	if(negativ){
		c = a + b;
	}else{
		c = a-b;
	}
	return c;
};

function down(evt){
	svgEspion = true;
	//document.getElementById('svgRedLine').setAttributeNS(null, "transform", "translate("+ evt.clientX +")");
};

function move(evt){
	if(svgEspion){
		var posX = parseInt(evt.clientX - 50);
		document.getElementById('svgRedLine').setAttributeNS(null, "transform", "translate("+ posX +")");		
		document.getElementById("gObsel").setAttributeNS(null, "transform", "translate(" + getPosGObsel(posX) +")");
		document.getElementById("time").value = getPosGObsel(posX);
	}
}

function up(evt){
	svgEspion = null;
	//var x= parseInt(evt.clientX - 50);
	
	var newPosX = parseInt(evt.clientX - 50) ;
	if(newPosX < 300 ){
		if((newPosX - 300) > 0){
			newPosX = newPosX -300;
			posGObsel = getPosGObsel("300", true);
		}else{
			newPosX = 0;
			posGObsel= 0;
		}
		document.getElementById('svgRedLine').setAttributeNS(null, "transform", "translate("+ newPosX +")");
	}else{
		if(newPosX > 600){
			newPosX = newPosX +600;
			posGObsel = getPosGObsel("500", false);
		}
		document.getElementById('svgRedLine').setAttributeNS(null, "transform", "translate(0)");
		document.getElementById('svgRedLine').setAttributeNS(null, "transform", "translate(550)");
		
		
		var ajust = 675 - newPosX;
		document.getElementById("gObsel").setAttributeNS(null, "transform", "translate(" + getPosGObsel(ajust, false) +")");
	}
	
}