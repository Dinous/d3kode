<%@ page language="java" contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:svg="http://www.w3.org/2000/svg" >
    <head>
    	<s:head />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <title />
         <link href="<s:url value="/css/index.css"/>" rel="stylesheet" type="text/css"/>
         <script type="text/javascript">
			window.innerHeight = screen.height / 2;
			window.innerWidth = screen.width / 2;
			window.moveTo(window.innerHeight, window.innerWidth);
		</script>
    </head>
    <body>
    	<s:actionmessage />
    	<s:actionerror />
		<s:fielderror />
    	<tiles:insertAttribute name="body" />
    </body>
</html>