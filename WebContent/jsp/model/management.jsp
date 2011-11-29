<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:url var="urlListTraceModel"  action="list_trace_model"/>
<sj:div id="toneList" href="%{urlListTraceModel}" reloadTopics="updateListTraceModel" >
	<img id="indicator" src="${pageContext.request.contextPath}/icone/indicator.gif" alt="Loading..."/> 
</sj:div>