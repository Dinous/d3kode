<?xml version="1.0" encoding="utf-8"?>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><s:property value="getText('application.name')" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" ></meta>
    <meta http-equiv="Content-Style-Type" content="text/css" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<link rel="icon" href="${pageContext.request.contextPath}/favicon.ico"/>
	<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/select.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/struts/styles.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="http://static.jquery.com/ui/css/demo-docs-theme/ui.theme.css" type="text/css" media="all" />
	<!-- This files are needed for AJAX Validation of XHTML Forms -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/struts/utils.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/struts/xhtml/validation.js"></script>
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/work/js/svgUtils.js"></script> --%>
    <sj:head debug="true" 
    ajaxcache="false"
    jquerytheme="redmond"
    locale="fr"
    compressed="false" 
    ajaxhistory="false"
    defaultIndicator="myDefaultIndicator" defaultLoadingText="Please wait ..."/>
    <script type="text/javascript">
    		function fieldFilter(element){
alert('plop');
    			
    			return true;
    			
    			}
    </script>
</head>
	<body id="body">
	<div id="top"></div>
	<div id="container">
	     <div id="header" class="ui-widget-header">
	       <div id="headline" >
	       	<jsp:include page="header.jsp" />
	      	<img id="myDefaultIndicator" src="${pageContext.request.contextPath}/icone/ajax-loader.gif" alt="Loading..." style="display:none"/>
	      </div>
	    </div>
	    <div id="empty"></div>
		<sj:div id="main" href="%{realAccueil}">
			<img id="indicator" src="${pageContext.request.contextPath}/icone/indicator.gif" alt="Loading..."/>
		</sj:div>
		 <div id="footer-spacer"></div>
	</div>
    <div id="footer">
      <jsp:include page="footer.jsp" />
     </div>
	</body>
</html>
