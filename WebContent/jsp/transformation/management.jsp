<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>
<s:actionerror />
<s:actionmessage />

<fieldset>
	<legend><s:property value="%{getText('trace.transformation')}"/></legend> 
<s:url var="urlListTransformation"  action="list_transformation"/>
<sj:div id="divListTransformation" href="%{urlListTransformation}" reloadTopics="dialogBeforeCloseTopic" />
</fieldset>

<center>
	<s:url var="urlIhmTransformation"  action="create_or_update_transformation_prepare"/>
	<sj:dialog 
	   	id="addTransformationIhm" 
	   	autoOpen="false" 
	   	modal="true" 
	   	title="%{getText('add.transformation')}"
	   	width="900"
	   	closeTopics="closeDialog"
	   	>
	</sj:dialog>
	<s:if test="%{isExpert}">
		<sj:a id="openDialogTransformationIhm"
		  	openDialog="addTransformationIhm" 
			cssClass="buttonlink ui-state-default ui-corner-all"
		  	button="true"
		  	buttonIcon="ui-icon-newwin"
		  	href="%{urlIhmTransformation}">
		  	<s:property value="getText('add.transformation')" />
		 </sj:a>
	 </s:if>
</center>