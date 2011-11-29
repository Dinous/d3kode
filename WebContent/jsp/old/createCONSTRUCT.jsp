<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:url var="deleteUrl" action="create_MethodInkTBS_deleteCriteria_CONSTRUCT" />
<s:url var="updateUrl" action="create_MethodInkTBS_preCreateCriteria_CONSTRUCT" />
<s:form theme="simple" id="frmCriteria" action="create_MethodInkTBS_deleteCriteria_CONSTRUCT">
	<s:hidden id="indexCriteria" name="indexCriteria" />
	<s:hidden id="constructionMethodSelected" name="constructionMethodSelected" />
</s:form>
<fieldset>
	<legend>
		<s:if test="transformationRuleFactory.traceModelDestination == null">
			<s:text name="creating.method" />
		</s:if>
		<s:else>
			<s:text name="update.method" /> <s:property value="transformationRuleFactory.ruleName" />
		</s:else>
	</legend>
	<center>
	<table class="border" width="90%" >
		<thead>
			<tr>
				<td colspan="7">
					<s:label key="request.generation.simple" theme="simple" />
				</td>
			</tr>
            <tr>
              <td><s:property value="%{getText('obselType')}"  /></td>
              <td ><s:property value="%{getText('attribute')}"  /></td>
              <td><s:property value="%{getText('operande')}"  /></td>
            </tr>
		</thead>
		<tfoot>
			<tr>
				<td colspan="6">
						<!--<sj:submit 
							id="updateCriteria"
							value="%{getText('update.second.operande')}"
							formIds="frmCriteria"
						 	src="icone/update_16.png"
						 	href="%{updateUrl}"
							type="image"
							parentTheme="simple"
							cssStyle="display:none;"
						 	 />-->
					 	<sj:submit 
					 		id="deleteCriteria"
							value="%{getText('delete.criteria')}"
							src="icone/delete_16.png"
							type="image"
							targets="criteria"
							formIds="frmCriteria"
							cssStyle="display:none;"
							/>
				</td>
			</tr>
		</tfoot>
		<tbody>
			<s:iterator value="%{transformationRuleFactory.constructs}" status="row">		
				<tr id="<s:property value="#row.index"/>" onclick="SelectLigne(this)">	
					<td title="<s:property value="%{getText('obselType')}"  />"><s:property value="[0].firstOperandeType"/></td>
					<td title="<s:property value="%{getText('attribute')}"  />"><s:property value="[0].firstAttribute"/></td>
 					<td title="<s:property value="%{getText('operande')}"  />"><s:property value="[0].secondOperandeText"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	</center>
</fieldset>