<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:iterator value="%{#session.filter.operatorOrOperande}">
	<s:property value="toString()" />
</s:iterator>