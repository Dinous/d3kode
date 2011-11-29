<%@page import="org.apache.catalina.Role"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.catalina.users.MemoryUser"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@ taglib prefix ="s" uri="/struts-tags" %>

<s:actionerror/>
<s:actionmessage/>

<br/><s:text name="welcome.message"/> <s:property value="userLogin" />
<br />
<s:text name="welcome.user.roles"/><s:iterator value="roleLogin">
	<s:property />&nbsp;
</s:iterator>
<br /><br />
<s:url action="accueil" id="langueFr">
	<s:param name="request_locale">fr</s:param>
</s:url>

<s:url action="accueil" id="langueEng">
	<s:param name="request_locale">eng</s:param>
</s:url>

<s:a href="%{langueFr}"><img src="jsp/picto/fr.jpg" border="0" width="40px" height="20px"></img> </s:a>
<s:a href="%{langueEng}"><img src="jsp/picto/eng.jpg" border="0"  width="40px" height="20px"></img> </s:a>