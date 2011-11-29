function SelectLigne(obj){
	var oldValue = $('#indexCriteria')[0].value;
 var idLigne=obj.id;
 
 if (obj.className = null || obj.className == "selection"){
    obj.className = "defaut";
    $('#deleteCriteria')[0].style.display = 'none';
    //$('#updateCriteria')[0].style.display = 'none';
 }else{
	 obj.className="selection";
    $('#deleteCriteria')[0].style.display = 'block';
    //$('#updateCriteria')[0].style.display = 'block';
 }
 
 if(oldValue != "" &&  oldValue != idLigne){
	 var tmp = "#"+oldValue;
	 $(tmp)[0].className = "defaut";
 }
 $('#indexCriteria')[0].value = idLigne;
}