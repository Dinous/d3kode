<%@page import="org.apache.struts2.ServletActionContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml" >
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		<title>Identification</title>
		<link href="./css/index.css" rel="stylesheet" type="text/css"/>
	</head>
	<body onload="document.getElementById('j_username').focus()">
	<script type="text/javascript">
		if(document.getElementById('main')){
			var str = window.location.toString().substring(0, window.location.toString().lastIndexOf("/"));
			window.location.replace(str);
		}
	</script>
<% 	try{
		if(ServletActionContext.getRequest().getUserPrincipal() != null){ %>
			<script type="text/javascript">
				window.location.replace(window.location + "accueil.action");
			</script>
		<% }
		
	}catch(Exception e){}
%>
		<center>
		    <form method="post" action='j_security_check' >
		  		<table class="borderIndex">
			  		<tbody>
					    <tr>
					      <td align="right">Username:</td>
					      <td colspan="2" align="left"><input id="j_username" type="text" name="j_username"/></td>
					    </tr>
					    <tr>
					      <td align="right">Password:</td>
					      <td colspan="2"  align="left"><input type="password" name="j_password"/></td>
					    </tr>
					    <tr>
					      <td></td>
					      <td align="right"><input type="submit" value="Log In"/></td>
					      <td align="left"><input type="reset" value="Reset"/></td>
					    </tr>
				    </tbody>
				</table>
			</form>
		</center>
	</body>
</html>