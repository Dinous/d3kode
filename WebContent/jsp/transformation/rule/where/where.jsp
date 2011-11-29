<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:url var="urlVarRuleSelected" action="list_var_json" />
<s:url var="urlAttributeType" action="attributeList" />
<s:url var="operatorList" action="operatorList" />
<s:url var="urlFilter" action="filter" />

<s:if test="ruleSelected != null">
<fieldset>
	<legend><s:text name="update.rule" /> : <s:property value="ruleSelected.label" /></legend>

	<table width="100%" class="border">
		<thead></thead>
		<tbody>
			<tr>
				<td>
					<s:url var="remove_operator"  action="remove_operator_or_operande_from_filter" />
					<sj:div id="divFilter%{ruleSelected.id}"/>
					<sj:a
						title="remove.last.operator"
						href="%{remove_operator}"
						targets="divFilter%{ruleSelected.id}"
						button="true"
						formIds="frmCreateCriteria%{ruleSelected.id}"
					 ><----</sj:a>
				</td>
			</tr>
			<tr>
			<td>
				<sj:tabbedpanel id="localFirstOperandTabs%{transformation.localName}_%{ruleSelected.id}" >
				<s:iterator value="ruleSelected.wherePart.listWhere" status="where">
					<s:set var="operandeUri" value="uri"/>
					<sj:tab id="tab%{#where.index}" target="div%{#where.index}" label="%{label}" />
					<s:div id="div%{#where.index}" >
						<s:iterator value="listAttribute" status="attribute">
							<s:url var="urlAddOperandeOrOperator"  action="filter" escapeAmp="false">
								<s:param name="operande.uri" value="#operandeUri" />
								<s:param name="operande.attribute.uri" value="uri" />
							</s:url>
							<sj:a
								name="filter.firstOperande.attribute.uri"
								href="%{urlAddOperandeOrOperator}"
								targets="divFilter%{ruleSelected.id}"
								formIds="frmCreateCriteria%{ruleSelected.id}"
								button="true"
								onClickTopics="reloadDivFiler"
							 >
							 	<s:property value="%{label}" />
							 </sj:a>
						</s:iterator>
					</s:div>
				</s:iterator>
				<sj:tab id="tabOperator%{ruleSelected.id}" target="tOperator%{ruleSelected.id}" label="%{getText('operator')}"/>
				<sj:tab id="tabFirstOperandeText%{ruleSelected.id}" target="tText%{ruleSelected.id}" label="%{getText('obsel.text')}"/>
			      <s:div id="tText%{ruleSelected.id}">
			      	<s:form id="frmOperandeText%{ruleSelected.id}" action="filter" theme="simple"> 
			      		<table><tr>
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
				      			targets="divFilter%{ruleSelected.id}"
				      			button="true"
				      			buttonIcon="ui-icon-check"
				      			formIds="frmOperandeText%{ruleSelected.id}"
				      			title="%{getText('add.text')}"
				      			value="%{getText('add.text')}"
				      		 />
			      		 	</td>
			      		 </tr></table>
			      	</s:form>
			      </s:div>
			      <s:div id="tOperator%{ruleSelected.id}">
			      	<s:iterator value="operatorList" status="operator">
						<s:url var="urlAddOperandeOrOperator"  action="filter" escapeAmp="false">
							<s:param name="operator.valueStr" value="valueStr" />
						</s:url>
						<sj:a
							name="filter.operator" 
							href="%{urlAddOperandeOrOperator}"
							targets="divFilter%{ruleSelected.id}"
							formIds="frmCreateCriteria%{ruleSelected.id}"
							button="true"
							onClickTopics="reloadDivFiler">
							<s:property value="%{valueStr}" />
						</sj:a>
					</s:iterator>
			      </s:div>
				</sj:tabbedpanel>
			</td>
		</tr>
		</tbody>
		<tfoot>
			<tr>
				<td align="center">
					<s:form id="frmCreateCriteria%{ruleSelected.id}" theme="simple" action="save_criteria_where">
								<s:hidden name="transformation.uri" />
								<s:hidden name="ruleSelected.uri" />
								<s:hidden name="where.uri" />
								<s:hidden name="criteriaPart"  />
						<sj:submit name="submitCriteria" 
								targets="content"
								indicator="indicator" 
								button="true"
								onSuccessTopics="closeDialog"
								value="%{getText('submit.create.criteria')}"
								buttonIcon="ui-icon-refresh">
						</sj:submit>
					</s:form>
				</td>
			</tr>
		</tfoot>
	</table>
</fieldset>
</s:if>