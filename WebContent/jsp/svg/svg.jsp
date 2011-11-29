<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>


	<object 
		id="objSvg" 
		type="image/svg+xml" 
		data="<%= request.getAttribute("uriSvgFile") %>"
	></object>
	<sj:div id="divtmp" onCompleteTopics="majVisualisationHistoryTopic"	 />