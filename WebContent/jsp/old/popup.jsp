<%@ taglib prefix ="s" uri="/struts-tags" %>

<table><thead></thead><tfoot></tfoot>
	<tbody>
		<tr>
			<td><s:property value="ktbsRepresentation" escapeHtml="false"/></td>
			<td>
				<s:if test="!exist">
					<s:form action="generateSparql_save" >
						<s:hidden name="transformationRuleFactory.traceModelSource"/>
						<s:hidden name="transformationRuleFactory.traceModelDestination"/>
						<s:hidden name="transformationRuleFactory.mapObselAttributeInst"/>
						
						<s:iterator value="%{transformationRuleFactory.criterias}" status="row">
							<s:hidden id="criterias_%{#row.index}_idCriteria" name="transformationRuleFactory.criterias[%{#row.index}].idCriteria"/>
							<s:hidden id="criterias_%{#row.index}_attributeTypeSelected" name="transformationRuleFactory.criterias[%{#row.index}].attributeType"/>
							<s:hidden id="criterias_%{#row.index}_obsel" name="transformationRuleFactory.criterias[%{#row.index}].obsel"/>
							<s:hidden id="criterias_%{#row.index}_obselTypeSelected" name="transformationRuleFactory.criterias[%{#row.index}].obselType"/>
							<s:hidden id="criterias_%{#row.index}_operandeTypeSelected" name="transformationRuleFactory.criterias[%{#row.index}].operandeType"/>
							<s:hidden id="criterias_%{#row.index}_operatorSelected" name="transformationRuleFactory.criterias[%{#row.index}].operator"/>
							<s:hidden id="criterias_%{#row.index}_texteOperandeSelected" name="transformationRuleFactory.criterias[%{#row.index}].texteOperande"/>
						</s:iterator>
						<s:iterator value="%{transformationRuleFactory.constructs}" status="row">
					     <s:hidden id="constructs_%{#row.index}_idCriteria" name="transformationRuleFactory.constructs[%{#row.index}].idCriteria"/>
					     <s:hidden id="constructs_%{#row.index}_attributeTypeSelected" name="transformationRuleFactory.constructs[%{#row.index}].attributeType"/>
					     <s:hidden id="constructs_%{#row.index}_obselType" name="transformationRuleFactory.constructs[%{#row.index}].obselType"/>
					     <s:hidden id="constructs_%{#row.index}_operandeType" name="transformationRuleFactory.constructs[%{#row.index}].operandeType"/>
					     <s:hidden id="constructs_%{#row.index}_texteOperande" name="transformationRuleFactory.constructs[%{#row.index}].texteOperande"/>
					   </s:iterator>
						<s:textfield key="methodName" required="true" size="50" />
						<s:submit
							label="%{getText('submit.request.sparql')}" 
							type="button" 
							/>
					</s:form>
				</s:if>
				<s:else>
					<s:text name="method.exist" />
				</s:else>
			</td>
		</tr>
	</tbody>
</table>