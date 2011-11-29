<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:form theme="simple" id="idFormCriteria2" cssStyle="width:100%" >
<s:hidden name="obselListWithId" />
<s:if test="%{typeSecondOperande == 'text'}">
	<s:textfield key="secondOperandeText" id="secondOperandeText" name="secondOperandeText" />
</s:if>
<s:else>
	<s:url var="secondOperandeList" action="obselList" />
	<sj:select id="secondOperande" name="secondOperande" 
		list="obselList" 
		listKey="myKey" 
		listValue="myValue"
		formIds="idFormCriteria2"
		onChangeTopics="reloadSecondAttribute" 
		href="%{secondOperandeList}" 
		emptyOption="true" 
		headerKey="-1" 
		headerValue=""
	/>
	<br/>
	<s:url var="attributeList2" action="attributeList" />
	<sj:select id="secondAttribute" name="secondAttribute" 
		list="secondAttributeList"
		href="%{attributeList2}"
		formIds="idFormCriteria2"
		reloadTopics="reloadSecondAttribute" 
		listKey="myKey" 
		listValue="myValue" 
	/>
</s:else>
</s:form>