<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>
<sj:tabbedpanel id="tabPanelChoiceRule">
	<sj:tab id="tabCreateRule" 
			label="%{getText('create.rule')}"
			target="createRule" />
				<div id="createRule">
					<s:form  id="createRuleFormId" action="create_or_update_rule">
						<s:hidden name="transformation.uri" />
						<s:hidden name="ruleSelected.uri" />
						
						<s:textfield key="ruleSelected.label" size="70" />
						<s:hidden name="escape" value="false"/>
						<s:textarea 
							key="ruleSelected.description" 
							rows="10" 
							cols="70" 
							width="500"
						/>
						<tr>
						<td class="tdLabel" ></td>
						<td>
							<sj:submit
								id="submitCreateRule"
								targets="empty"
								formIds="createRuleFormId"
						      	indicator="indicator"
						      	button="true"
						      	cssStyle="text-align:center"
						      	onSuccessTopics="refreshUpdateRule,closeDialog"
						      	value="%{getText('submit.rule.create')}"
						      	parentTheme="simple"
							 />
						 </td>
						 </tr>
					</s:form>
				</div>
	<sj:tab id="tabCopyRule" 
			label="%{getText('copy.rule')}"
			target="copyRule" />
				<div id="copyRule">
					
						<s:set name="transformationUri"  value="transformation.uri"/>
						<s:set name="source"  value="transformation.traceModelSourceSelected"/>
						<s:set name="target"  value="transformation.traceModelTargetSelected"/>
						
						<table class="border">
							<thead>
								<tr>
									<td><s:text name="transformation.copy.label" /></td>
									<td><s:text name="transformation.copy.description" /></td>
									<td><s:text name="rule.copy.label" /></td>
									<td><s:text name="rule.copy.description" /></td>
									<td></td>
								</tr>
							</thead>
						
							<tbody>
								<s:iterator status="ruleIndex" value="getListRulePojo(#transformationUri, #source, #target)">
									<s:if test="uri != null">
										<tr>
											<td><s:property value="getTransformationOfRuleUri(uri).label"/></td>
											<td><s:property value="getTransformationOfRuleUri(uri).description"/></td>
											<td><s:property value="label" /></td>
											<td><s:property value="description" /></td>
											<td >
												<s:url var="importRuleInTransformation" action="import_rule_in_transformation" escapeAmp="false" >
													<s:param name="transformation.uri" value="#transformationUri" />
													<s:param name="ruleToCopyUri" value="uri" />
												</s:url>
												<sj:a
													id="submitCopyRule%{#ruleIndex.index}"
													targets="empty"
													href="%{importRuleInTransformation}"
											      	indicator="indicator"
											      	button="true"
											      	buttonIcon="ui-icon-plusthick"
											      	cssStyle="text-align:center"
											      	onSuccessTopics="refreshUpdateRule,closeDialog">
											 	</sj:a>
									 		</td>
										</tr>
									</s:if>
								</s:iterator>
							</tbody>
						 </table>
					<script>$(".border>tbody>tr").filter(":odd").css("background-color", "#DDDDFF");</script>
				</div>
</sj:tabbedpanel>