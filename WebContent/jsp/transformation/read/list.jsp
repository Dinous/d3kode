<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:actionmessage />
<s:actionerror />

<s:if test="%{transformations == null || transformations.isEmpty()}" >
	<s:text name="transformation.empty" />
</s:if>
<s:else>
	<table class="border">
		<thead>
			<tr>
				<td><s:text name="trace.modele.label" /></td>
				<td><s:text name="transformation.trace.model.source" /></td>
				<td><s:text name="transformation.rules" /></td>
				<td><s:text name="transformation.trace.model.target" /></td>
				<td></td>
				<s:if test="%{isExpert}">
					<td></td>
					<td></td>
					<td></td>
				</s:if>
			</tr>
		</thead>
		<tfoot></tfoot>
	<tbody>
			<s:iterator value="transformations" status="stat">
			<s:set var="transformationUri" value="uri" />
			<tr>
				<td><s:property value="%{label}"  /></td>
				<td><s:property value="%{traceModelSource.label}"  /></td>
				<td>
					<s:iterator value="%{rules}" status="rule"  >
						<sj:dialog 
						   	id="ktbsRepresentationRule%{#rule.index}_%{#stat.index}" 
						   	autoOpen="false" 
						   	modal="true" 
						   	title="%{label}"
						   	width="600">
								<s:url var="urlNiceRule" action="nice_rule" escapeAmp="false">
									<s:param name="transformation.uri" value="#transformationUri" />
									<s:param name="ruleSelected.uri" value="uri" />
								</s:url>
						  </sj:dialog>
						  <sj:a id="opentopicdialogKtbsRepresentationRule%{#rule.index}_%{#stat.index}"
						  		openDialog="ktbsRepresentationRule%{#rule.index}_%{#stat.index}" 
						  		href="%{urlNiceRule}"
						  		cssClass="buttonlink ui-state-default ui-corner-all"
						  		button="true"
						  		buttonIcon="ui-icon-newwin"
						  >
						  		<s:property value="label" />
						  </sj:a>
					</s:iterator>
				</td>
				<td><s:property value="%{traceModelTarget.label}"  /></td>
				<td>
				<s:url var="urlUpdateIhmTransformation"  action="create_or_update_transformation_prepare">
					<s:param name="transformation.uri" value="%{uri}" />
				</s:url>
				<s:if test="%{isExpert}">
				   <sj:a 
				   		cssClass="buttonlink ui-state-default ui-corner-all"
				   		button="true"
				   		openDialog="addTransformationIhm"
				   		buttonIcon="ui-icon-newwin"
				   		title="%{getText('update.transformation')}"
				   		href="%{urlUpdateIhmTransformation}"
				   />
				  </s:if>
				  <s:else>
				  <sj:a 
				   		cssClass="buttonlink ui-state-default ui-corner-all"
				   		button="true"
				   		openDialog="addTransformationIhm"
				   		buttonIcon="ui-icon-newwin"
				   		title="%{getText('read.transformation')}"
				   		href="%{urlUpdateIhmTransformation}"
				   />
				  </s:else>
				</td>
				<s:if test="%{isExpert}">
					<td>
						<s:url var="selectTransformation" action="transformation_rule_prepare" >
							<s:param name="transformation.uri" value="%{uri}" />
						</s:url>
						<sj:a 
							var="aTransformationInsertMethod"
							button="true" 
							buttonIcon="ui-icon-extlink"
							value="%{localName}" 
							targets="content"
							href="%{selectTransformation}"
							title="%{getText('insert.method.transformation')}"
							>
						</sj:a>
					</td>				
					<td>
						<%-- isInKtbs : <s:property value="isInKtbs" /> --%>
						<s:if test="!isInKtbs && !rules.isEmpty()">
							<s:url var="urlInsertInKtbs" action="create_or_update_transformation_in_ktbs" >
								<s:param name="transformation.uri" value="%{uri}" />
							</s:url>
							<sj:a 
								var="aInsertInKtbs"
								button="true" 
								buttonIcon="ui-icon-unlocked"
								targets="content"
								href="%{urlInsertInKtbs}"
								title="%{getText('create.transformation')}"
								>
							</sj:a>
						</s:if>
						<s:else>
							<span class="ui-button-icon-primary ui-icon ui-icon-locked" ></span>
						</s:else>
					</td>
					<td>
						<s:url var="urlDeleteTransformation" action="delete_transformation" >
							<s:param name="transformation.uri" value="%{uri}" />
						</s:url>
						<sj:a 
							var="aTransformationDelete"
							button="true" 
							buttonIcon="ui-icon-trash"
							value="%{localName}" 
							targets="content"
							href="%{urlDeleteTransformation}"
							title="%{getText('delete.transformation')}"
							>
						</sj:a>
					</td>
				</s:if>
			</tr>
			</s:iterator>
		</tbody>
	</table>
</s:else>
