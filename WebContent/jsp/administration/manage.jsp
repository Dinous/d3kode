<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix ="sjg" uri="/struts-jquery-grid-tags" %>

<%-- <sj:accordion  collapsible="true">
	<sj:accordionItem title="%{getText('administration.ressources')}"> 
	<table class="border">
		<thead>
			<tr>
				<td>#</td>
				<td><s:property value="getText('administration.type')" /></td>
				<td><s:property value="getText('administration.creation.date')" /></td>			
				<td><s:property value="getText('administration.localName')" /></td>
				<td><s:property value="getText('administration.labels')" /></td>
				<td><s:property value="getText('administration.actions')" /></td>
			</tr>
		</thead>
		<tfoot></tfoot>
		<tbody>
			<s:iterator value="resources" status="resourceStatus">
				<s:if test="typeUri != null">
				<tr>
					<td><s:property value="%{#resourceStatus.index}" /></td>
					<td><s:property value="typeUri.substring(typeUri.indexOf('#'))" /></td>
					<td><s:property value="getCreationDate(localName)" /></td>				
					<td><s:property value="localName" /></td>
					<td><s:property value="getLabel(labels)" /></td>
					<td>
						<s:url var="urlDeleteResource" action="delete_resource" >
							<s:param name="resourceUri" value="uri" />
						</s:url>
						<sj:a 
							href="%{urlDeleteResource}"
						  	cssClass="buttonlink ui-state-default ui-corner-all"
						  	button="true" 
							buttonIcon="ui-icon-trash"
						  	targets="main"
						  	title="%{getText('administration.delete.resource')}"
						  >
						</sj:a>
					</td>
				</tr>
				</s:if>
			</s:iterator>
		</tbody>
	</table>
	</sj:accordionItem> --%>

	

 <%-- <table class="border">
	<thead>
		<tr>
			<td>#</td>
			<td><s:property value="getText('administration.users.login')" /></td>
			<td><s:property value="getText('administration.roles')" /></td>
		</tr>
	</thead>
	<tfoot></tfoot>
	<tbody>
		<s:iterator value="userPojos" status="userPojo">
			<tr>
				<td><s:property value="%{#userPojo.index}" /></td>
				<td><s:property value="%{login}" /></td>
				<td><s:property value="%{roles}" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table> 
</sj:accordion> 
<script>$(".border>tbody>tr").filter(":odd").css("background-color", "#DDDDFF");</script>--%>
<br />
<center>
<%-- <s:form id="frmUri">
	<s:textfield id="uri" name="uri" />
</s:form>

<script>
$.subscribe('rowselect', function(event,data) {
	var grid = event.originalEvent.grid; 
	var sel_id = grid.jqGrid('getGridParam', 'selrow'); 
	var uri = grid.jqGrid('getCell', sel_id, 'uri'); 
	if(uri){
		$("#uri").val(uri);
	}
    });
</script> --%>
	<s:url id="remoteurlKtbs" action="jsontableResourceKtbs"/>
	<s:url id="editurlKtbs" action="editGridEntryKtbs"/>
	<sjg:grid
		id="gridedittableKtbs"
		caption="%{getText('administration.resource.ktbs')}"
		dataType="json"
		href="%{remoteurlKtbs}"
		pager="true"
		navigator="true"
		navigatorSearch="false"
		navigatorAdd="false"
		navigatorEdit="false"
		navigatorView="false"
		navigatorDelete="true"
		navigatorDeleteOptions="{height:280,reloadAfterSubmit:true}"
		gridModel="gridedittableKtbs"
		editurl="%{editurlKtbs}"
		
		rowList="15,30,45"
		rowNum="15"
		multiselect="true"
		onSelectRowTopics="rowselect"
		viewrecords="false"
		
	>
		<sjg:gridColumn  name="type" index="type" title="%{getText('administration.type')}" width="100" sortable="true" search="false" />
		<sjg:gridColumn name="creationDate" index="creationDate" title="%{getText('administration.creation.date')}" width="200"  sortable="true" search="false" />
		<sjg:gridColumn name="localName" index="localName" title="%{getText('administration.localName')}" width="150"  sortable="true" search="false"  />
		<sjg:gridColumn name="label" index="label" title="%{getText('administration.labels')}" width="500"  sortable="true" search="false" />
	</sjg:grid>
	
	<br />

	<s:url id="remoteurl" action="jsontable"/>
	<s:url id="editurl" action="editGridEntry"/>
	<sjg:grid
		id="gridedittable"
		caption="%{getText('administration.users')}"
		dataType="json"
		href="%{remoteurl}"
		pager="true"
		navigator="true"
		navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
		navigatorAddOptions="{height:280,reloadAfterSubmit:true}"
		navigatorEditOptions="{height:280,reloadAfterSubmit:false}"
		navigatorSearch="false"
		navigatorEdit="false"
		navigatorView="false"
		navigatorDelete="true"
		navigatorDeleteOptions="{height:280,reloadAfterSubmit:true}"
		gridModel="gridModel"
		rowList="15,30,45"
		rowNum="15"
		editurl="%{editurl}"
		editinline="true"
		multiselect="true"
		onSelectRowTopics="rowselect"
		onEditInlineSuccessTopics="oneditsuccess"
		viewrecords="false"
		
	>
		<sjg:gridColumn name="id" index="id" title="%{getText('administration.users.id')}" width="50" formatter="integer" hidden="true" editable="false" sortable="false" search="false" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
		<sjg:gridColumn name="login" index="login" title="%{getText('administration.users.login')}" width="150" editable="true" edittype="text" sortable="true" search="false" searchoptions="{sopt:['eq']}"/>
		<sjg:gridColumn name="password" index="password" title="%{getText('administration.users.password')}" width="150" editable="true" edittype="text" sortable="false" search="false"  />
		<sjg:gridColumn name="roles" index="roles" title="%{getText('administration.users.roles')}" width="500" editable="true" edittype="select" editoptions="%{editRules}" sortable="false" search="false" />
	</sjg:grid>
	</center>