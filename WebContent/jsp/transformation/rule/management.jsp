<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>
<s:actionerror />
<s:url var="listRule" action="list_rule" escapeAmp="false">
	<s:param name="transformation.uri" value="transformation.uri" />
	<s:param name="ruleSelected.id" value="ruleSelected.id" />
	<s:param name="construct.uri" value="construct.uri" />
	<s:param name="constructItem.operande.uri" value="constructItem.operande.uri"/>
	<s:param name="constructItem.attribute.uri" value="constructItem.attribute.uri"/>
</s:url>
<s:url  var="urlNewRule" action="create_rule_prepare">
	<s:param name="transformation.uri" value="transformation.uri" />
</s:url>

<fieldset>
	<legend>
		<b><s:text name="transformation.list.rule" /></b> : <s:property value="transformation.label" />
</legend>
 <sj:div id="divListRule" href="%{listRule}" reloadTopics="refreshUpdateRule,refreshAfterDelete,refreshUpdateConstructRule" />
 <sj:dialog 
   	id="addRuleInTransformation" 
   	autoOpen="false" 
   	modal="true" 
   	title="%{getText('add.rule')}"
   	width="750"
   	closeTopics="closeDialog">
</sj:dialog>
<s:url action="create_rule_prepare" var="hrefAddRule" >
	<s:param name="transformation.uri" value="transformation.uri" />
</s:url>
<sj:a id="opentopicdialogAddRuleInTransformation"
		openDialog="addRuleInTransformation"
		href="%{hrefAddRule}" 
		cssClass="buttonlink ui-state-default ui-corner-all"
		button="true"
		buttonIcon="ui-icon-newwin">
		<s:property value="getText('add.rule')" />
		
</sj:a>
</fieldset>