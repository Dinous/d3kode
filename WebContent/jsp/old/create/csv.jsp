<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:form id="frmCreateTraceModel2" method="POST" action="modelUploadCsv" enctype="multipart/form-data">
				<s:hidden name="traceModelLocalName" value="%{ktbsResource.localName}" />
				<s:property value="%{ktbsResource.localName}" />
				<s:file name="upload" size="75" key="file.model.csv" required="true"/>
					<tr><td colspan="2" align="right">
				 <sj:submit
					id="submitCreateTraceModel2"
					targets="content"
					formIds="frmCreateTraceModel2"
			      	indicator="indicator"
			      	button="true"
			      	onSuccessTopics="updateListTraceModelFromModel"
			      	parentTheme="simple"
			      	cssStyle="text-align:center"
			      	value="%{getText('update.trace.model.csv')}"
				 />
		            </td></tr>
			</s:form>