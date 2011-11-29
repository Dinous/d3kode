<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:if test="%{isExpert}">
	<s:form id="transformationFormId" action="create_or_update_transformation">
		<s:if test="%{transformation.uri != null}">
			<s:textfield key="transformation.uri" readonly="true" size="100" />
		</s:if>
		<s:textfield key="transformation.label" size="100" />
		<s:select 
			list="traceModels" 
			key="transformation.traceModelSourceSelected"
			listKey="uri"
			listValue="label" />
		<s:select 
			list="traceModels" 
			key="transformation.traceModelTargetSelected"
			listKey="uri"
			listValue="label" />
			
			<s:hidden name="escape" value="false"/>
			<s:textarea 
				key="transformation.description" 
				rows="10" 
				cols="80" 
				width="550"
			/>
			
		<tr>
			<td colspan="2" align="center">
				<sj:submit
					id="submitCreateTransformation"
					targets="empty"
					formIds="transformationFormId"
			      	button="true"
			      	cssStyle="text-align:center"
			      	onSuccessTopics="dialogBeforeCloseTopic,closeDialog"
			      	value="%{getText('submit.transformation.create')}"
				 />
		 	</td>
		 </tr>
	</s:form>
</s:if>
<s:else>

<table>
	<tr>
		<td><s:label key="transformation.label" /></td>
	</tr>
	<tr>
		<td><s:label key="transformation.description" /></td>		
	</tr>

</table>
		
	

</s:else>