/**
 * 
 * Manage JS for createMethodInkTBS.jsp
 * 
 */

function noPopup(idForm){
	$(idForm).target = "";
}

function submitPopup(idForm){
	$(idForm).target = "_blank";
}

function showEl(id) {
	$(id).style.display = 'block';
}
function hideEl(id) {
  $(id).style.display = 'none';
  $(id).value ="";
}

var obselHash = new Hashtable();

function insertObsel(idTarget){
	var indexAttributeType = $('indexAttributeType').value;
	var tr = document.getElementById(indexAttributeType);
	document.getElementById(idTarget).innerHTML = "";
	
	var fatherTr = tr.parentNode;
	var tabTr = fatherTr.childElements();
	
	var currentObsel = null;
	for ( var int2 = 0; int2 < tabTr.length; int2++) {
		var trChild = tabTr[int2];
		var tabTd = trChild.childElements();		
		var celObsel = tabTd[0];
		var attributType = tabTd[2];
		
		currentObsel = celObsel.firstChild.nodeValue;
		var attributes = obselHash.get(currentObsel);
		
		if(!attributes){
			obselHash.put(currentObsel, new Array());
			attributes = new Array();
		}
			
		attributes.push(attributType.firstChild.nodeValue);
		obselHash.put(currentObsel, attributes);
	}
	
	
	//obselHash.remove(obselSelectedValue);
	for (var int3 = 0;int3 < obselHash.size(); int3++) {
		var obsel = obselHash.keys()[int3];
		var option = document.createElement("option");
		option.setAttribute("value", obsel);
		option.appendChild(document.createTextNode(obsel));
		document.getElementById(idTarget).appendChild(option);
	}
	document.getElementById(idTarget).innerHTML = document.getElementById(idTarget).innerHTML;
}

function insertAttribute(idSource, idTarget){
	var obselSelected = $(idSource).value;
	var listAttribute = obselHash.get(obselSelected);
	var target = document.getElementById(idTarget);
	target.innerHTML = "";
	
	for ( var int = 0; int < listAttribute.length; int++) {
		var attribute = listAttribute[int];
		var option = document.createElement("option");
		option.setAttribute("value", attribute);
		option.appendChild(document.createTextNode(attribute));
		target.appendChild(option);
	}
	target.innerHTML = target.innerHTML;
}

function setIndexAttributeType(index){
	$('indexAttributeType').value = index;
}

function deleteCriteria(id){
	$(id).remove();
	$('criterias_'+id+'_idCriteria').remove();
	$('criterias_'+id+'_attributeType').remove();
	$('criterias_'+id+'_obsel').remove();
	$('criterias_'+id+'_obselType').remove();
	$('criterias_'+id+'_operandeType').remove();
	$('criterias_'+id+'_operator').remove();
	$('criterias_'+id+'_texteOperande').remove();
	
}

function deleteConstruct(id){
	$(id).remove();
	$('constructs_'+id+'_idCriteria').remove();
	$('constructs_'+id+'_attributeType').remove();
	$('constructs_'+id+'_obselType').remove();
	$('constructs_'+id+'_operandeType').remove();
	$('constructs_'+id+'_texteOperande').remove();
	
}

function manageSaveWhere(idButton){
	if($('txtOperandeTypeTextValue').value != "" || $('listAttribute').value != "" && $('listAttribute').style.display != "none"){
		showEl(idButton);
	}else{
		hideEl(idButton);
	}
}

function manageSaveConstruct(idButton){
	if(($('obselAttributeInst').value != "" && $('obselAttributeInst').style.display != "none" )|| ($('txtOperandeTypeTextValue').value != "" && $('txtOperandeTypeTextValue').style.display != "none") ){
		showEl(idButton);
	}else{
		hideEl(idButton);
	}
}

function createOperator(selectObjectSource, tableComposeCriteria){
	var options = selectObjectSource.options;
	var tableComposeCriteria = document.getElementById(tableComposeCriteria);	

	for ( var int = 0; int < options.length; int++) {
			if(options[int].selected){
				
				if(!document.getElementById(int)){
				
					var line = document.createElement("tr");
					line.setAttribute("id", int);
					
					
					var cel = document.createElement("td");
					cel.appendChild(document.createTextNode(options[int].value));
					line.appendChild(cel);
					
					cel = document.createElement("td");
					if($('indexAttributeType').value == ''){
						
						
						var select = document.createElement("select");
						select.setAttribute("id", options[int].value + int);
						select.setAttribute("name", "listOperator");
						
						var elOption = document.createElement("option");
						elOption.setAttribute("value", "<");
						elOption.appendChild(document.createTextNode("<"));
						select.appendChild(elOption);
						elOption = document.createElement("option");
						elOption.setAttribute("value", ">");
						elOption.appendChild(document.createTextNode(">"));
						select.appendChild(elOption);
						elOption = document.createElement("option");
						elOption.setAttribute("value", "=");
						elOption.appendChild(document.createTextNode("="));
						select.appendChild(elOption);
						elOption = document.createElement("option");
						elOption.setAttribute("value", "!=");
						elOption.appendChild(document.createTextNode("!="));
						select.appendChild(elOption);
						var elOption = document.createElement("option");
						elOption.setAttribute("value", "<=");
						elOption.appendChild(document.createTextNode("<="));
						select.appendChild(elOption);
						var elOption = document.createElement("option");
						elOption.setAttribute("value", ">=");
						elOption.appendChild(document.createTextNode(">="));
						select.appendChild(elOption);
						
						cel.appendChild(select);
					}
					line.appendChild(cel);
					
					cel = document.createElement("td");
					var selectOperande = document.createElement("select");
					selectOperande.setAttribute("id", "listSecondOperande"+int);
					selectOperande.setAttribute("name", "listSecondOperande");
					
					selectOperande.setAttribute("onchange", "manageOperande('"+int+"')");
					elOption = document.createElement("option");
					elOption.setAttribute("value", "text");
					elOption.appendChild(document.createTextNode("text"));
					selectOperande.appendChild(elOption);
					
					elOption = document.createElement("option");
					elOption.setAttribute("value", "obsel");
					elOption.appendChild(document.createTextNode("obsel"));
					
					
					if($('indexAttributeType').value != ''){
						elOption.setAttribute("selected", "selected");
					}
					selectOperande.appendChild(elOption);
					cel.appendChild(selectOperande);
					line.appendChild(cel);
					
					cel = document.createElement("td");
					var inputOperandeText = document.createElement("input");
					inputOperandeText.setAttribute("id", "listOperandeText"+int);
					inputOperandeText.setAttribute("type", "text");
					inputOperandeText.setAttribute("name", "listOperandeText");
					inputOperandeText.setAttribute("style", "display:block");
					
					if($('indexAttributeType').value != ''){
						if($('criterias_'+$('indexAttributeType').value+'_operandeTypeSelected').value == "obsel"){
							inputOperandeText.setAttribute("style", "display:none");
						}
					}else{
						inputOperandeText.setAttribute("style", "display:block");
						
					}
					cel.appendChild(inputOperandeText);
					line.appendChild(cel);
					
					tableComposeCriteria.appendChild(line);
				}
			}else{
				if(document.getElementById(int)){
					document.getElementById(int).parentNode.removeChild(document.getElementById(int));
				}
			}
			
	}
	tableComposeCriteria.innerHTML = tableComposeCriteria.innerHTML;
	
}
