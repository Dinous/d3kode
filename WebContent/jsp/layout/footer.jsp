<%@ taglib prefix ="s" uri="/struts-tags" %>
<center>
<s:text name="layout.footer.user.connected" /><s:property value="userLogin" />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<s:text name="welcome.user.roles"/>
<s:iterator value="roleLogin">
	<s:if test="[0].toString() != 'manager-gui'
	&& [0].toString() != 'manager-script'">
		<s:property />&nbsp;
	</s:if>
</s:iterator>
</center>