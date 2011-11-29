<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:url var="urlVarRuleSelected" action="list_var_json" />
<s:url var="urlAttributeType" action="attributeList" />
<s:if test="ruleSelected != null">
	<fieldset>
		<legend><s:text name="update.transformation.list.rule" /></legend>
			<table width="100%" class="border">
				<tbody>
					<tr>
						<td>
							<s:text name="construct.label" />.<s:text name="constructItem.attribute.label" /> = 
						</td>
						<td>
							<s:url var="remove_operator"  action="remove_operator_or_operande" />
							<sj:div id="divConstruct_%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}"/><sj:a
								title="remove.last.operator"
								href="%{remove_operator}"
								targets="divConstruct_%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}"
								button="true"
								formIds="frmCreateCriteriaConstruct_%{ruleSelected.id}_%{constructItem.attribute.localName}"
							 ><----</sj:a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<sj:tabbedpanel id="localFirstOperandTabs%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}" >
								<s:iterator value="ruleSelected.wherePart.listWhere" status="where">
									<s:set var="operandeUri" value="uri"/>
									<sj:tab 
										id="tab%{transformation.localName}_%{ruleSelected.id}_%{where.index}_%{constructItem.attribute.localName}" 
										target="div%{ruleSelected.id}_%{#where.index}_%{constructItem.attribute.localName}" 
										label="%{label}" />
									<s:div id="div%{ruleSelected.id}_%{#where.index}_%{constructItem.attribute.localName}" >
										<s:iterator value="listAttribute" status="attribute">
											<s:url var="urlAddOperandeOrOperator"  action="construct" escapeAmp="false">
												<s:param name="operande.uri" value="#operandeUri" />
												<s:param name="operande.attribute.uri" value="uri" />
											</s:url>
											<sj:a
												name="filter.firstOperande.attribute.uri"
												href="%{urlAddOperandeOrOperator}"
												targets="divConstruct_%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}"
												formIds="frmCreateCriteria"
												button="true"
												onClickTopics="reloadDivFiler"
											 ><s:property value="%{label}" /></sj:a>
										</s:iterator>
									</s:div>
								</s:iterator>
								<sj:tab id="tabOperator_%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}" 
									target="tOperator_%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}" 
									label="%{getText('operator')}"/>
							      <s:div id="tText_%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}">
							      	<s:form id="frmOperandeText_%{ruleSelected.id}_%{constructItem.attribute.localName}" action="construct" theme="simple">
							      		<table>
							      		<tr> 
								      		<td><s:textfield name="operande.uri" theme="simple"/>
								      			<s:select
											   		key="attributeType.range"
											   		name="operande.range"
											   		list="%{listXsdType}"
											   		>
											   	</s:select>
											</td>
										</tr>
										<tr>
											<td>
									      		<sj:submit
									      			indicator="indicator"
									      			targets="divConstruct_%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}"
									      			button="true"
									      			buttonIcon="ui-icon-check"
									      			formIds="frmOperandeText_%{ruleSelected.id}_%{constructItem.attribute.localName}"
									      			title="%{getText('add.text')}"
									      			value="%{getText('add.text')}"/>
								      		 </td>
							      		 </tr>
							      		</table>
							      	</s:form>
							      </s:div>
							      <s:div id="tOperator_%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}">
							      	<s:iterator value="operatorList" status="operator">
										<s:url var="urlAddOperandeOrOperator"  action="construct" escapeAmp="false">
											<s:param name="operator.valueStr" value="valueStr" />
										</s:url>
										<sj:a
											name="filter.operator" 
											href="%{urlAddOperandeOrOperator}"
											targets="divConstruct_%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}"
											formIds="frmCreateCriteria"
											button="true"
											onClickTopics="reloadDivFiler">
											<s:property value="%{valueStr}" />
										</sj:a>
									</s:iterator>
							      </s:div>
							      <sj:tab id="tabFirstOperandeText_%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}" 
									target="tText_%{transformation.localName}_%{ruleSelected.id}_%{constructItem.attribute.localName}" 
									label="%{getText('obsel.text')}"/>
								</sj:tabbedpanel>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2" align="center">
							<s:form id="frmCreateCriteriaConstruct_%{ruleSelected.id}_%{constructItem.attribute.localName}" theme="simple" action="save_criteria_construct">
								<s:hidden name="transformation.uri" />
								<s:hidden name="ruleSelected.uri" />
								<s:hidden name="criteriaPart"  />
								<s:hidden name="construct.uri"  />
				
								<sj:submit name="submitCriteria" 
										targets="content"
										onSuccessTopics="closeDialog"
										indicator="indicator" 
										button="true"
										value="%{getText('submit.create.construct')}"
										buttonIcon="ui-icon-refresh">
								</sj:submit>
							</s:form>
						</td>
					</tr>
				</tfoot>
			</table>
	</fieldset>
</s:if>