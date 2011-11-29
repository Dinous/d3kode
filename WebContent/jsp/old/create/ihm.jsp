<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<sj:dialog 
    	id="addTraceModel" 
    	autoOpen="false" 
    	modal="true"
    	width="750" 
    	title="%{getText('add.trace.model')}"
    	closeTopics="closeTopic">
    	
    	<fieldset>
			<legend><s:text name="upload.form" /></legend>
			<s:form id="frmUpload" action="modelCreateIhm" >
				<s:textfield label="%{getText('trace.model.label')}" size="75"
					name="traceModelLabel" required="true" />
				 <sj:submit 
		            	id="formSubmit1"
		            	formIds="frmUpload"
		            	indicator="indicator"
		            	targets="empty"
		            	button="true"
		            	onSuccessTopics="updateListTraceModel,closeTopic"
		            	value="%{getText('submit.file')}"
		            	/>
			</s:form>
		</fieldset>
    	
    </sj:dialog>
    <sj:a id="opentopicdialoglink"
    	openDialog="addTraceModel" 
    	cssClass="buttonlink ui-state-default ui-corner-all"
    	button="true"
    	buttonIcon="ui-icon-newwin"
    >
    	<s:property value="getText('add.trace.model')" />
    </sj:a>



<%-- <s:if test="%{traceModelSelected != null}">
	<s:form id="frmUpdateIhm" method="POST" action="modelUploadUploadCsv">
		<s:textfield label="%{getText('trace.model.label')}" value="%{traceModelSelected.label}"
			name="mTraceLabel" size="50" required="true" />
			<s:iterator value="%{traceModelSelected.getProperties()}">
			<tr>
				<td>
					<s:label key="list.properties" theme="simple" />
				</td>
				<td>
					<s:textfield 
						value="%{property}"
						name="properties" 
						size="50"
						theme="simple"
				 	/>
				</td>
				<td>
					<s:textfield 
						value="%{value}"
						name="properties" 
						size="25"
						theme="simple"
				 	/>
				</td>
			</tr>
			</s:iterator>
			<s:iterator value="%{traceModelSelected.getObselTypes()}">
				<s:textfield 
					label="%{getText('list.obsel.type')}" 
					value="%{localName}"
					name="obselType" 
					size="50"
				 />
			</s:iterator>
			<s:iterator value="%{traceModelSelected.getAttributeTypes()}">
					<s:textfield 
						label="%{getText('list.attribute.type')}" 
						value="%{localName}"
						name="attribute" 
						size="25"
				 	/>
				 	
				</s:iterator>
		 <sj:submit 
            	id="formSubmitIhm"
            	targets="traceModelList"
            	formIds="frmUpdateIhm"
            	indicator="indicator"
            	button="true"
            	value="%{getText('submit.file')}"
            	/>
	</s:form>
</s:if>
<sj:dialog 
   	id="addTraceModelIhm" 
   	autoOpen="false" 
   	modal="true" 
   	title="%{getText('add.trace.model')}"
   	width="600"
   	closeTopics="dialogbeforeclosetopic">
	<s:form id="frmCreationIhm" method="POST" action="modelCreationIhm">
		<s:textfield label="%{getText('trace.model.label')}"
			name="traceModelLabel" size="100" required="true" />
		 <sj:submit 
            	id="formSubmitIhm"
            	formIds="frmCreationIhm"
            	indicator="indicator"
            	targets="empty"
            	button="true"
            	onSuccessTopics="dialogbeforeclosetopic"
            	value="%{getText('submit.creation.trace.model')}"
            	/>
	</s:form>
   </sj:dialog>
   <sj:a id="opentopicdialoglinkIhm"
   	openDialog="addTraceModelIhm" 
   	cssClass="buttonlink ui-state-default ui-corner-all"
   	button="true"
   	buttonIcon="ui-icon-newwin"
   >
   	<s:property value="getText('add.trace.model')" />
   </sj:a>
 --%>