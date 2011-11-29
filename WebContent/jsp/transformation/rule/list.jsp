<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>
<s:iterator value="transformation.rules" status="ruleStatus">
<s:set var="ruleSelectedUri" value="uri"/>
<s:if test="%{ruleSelected != null && (ruleSelected.id == #ruleStatus.index)}">
	<s:set name="activeRule"  value="0" />
</s:if>
<s:else>
	<s:set name="activeRule"  value="1" />
</s:else>
<sj:accordion 
	id="accordionRule%{#ruleStatus.index}" 
	list="accordion"
	collapsible="true"
	active="%{#activeRule}"
	 >
	<sj:accordionItem title="%{label}" >
		<table class="border">
		<thead><tr><th colspan="2" align="center"><s:property value="getText('list.construct')" /></th></tr></thead>
		<tbody>
			<tr>
				<td>
					<s:iterator value="constructPart.listConstruct" status="construct" >				
							<s:property value="label" />
							<s:url var="urlDeleteConstruct" action="delete_var" escapeAmp="false" >
								<s:param name="transformation.uri" value="transformation.uri" />
								<s:param name="ruleSelected.uri" value="ruleSelectedUri" />
								<s:param name="criteriaPart" value="%{'construct'}" />
								<s:param name="varToDeleteIndice" value="#construct.index" />							
							</s:url>
							<sj:a 
									href="%{urlDeleteConstruct}"
								  	cssClass="buttonlink ui-state-default ui-corner-all"
								  	button="true" 
									buttonIcon="ui-icon-trash"
								  	targets="content"
								  	title="%{getText('del.construct')}"
								  >
							</sj:a>
					</s:iterator>
					<s:url var="urlObseltypeTarget" action="list_obseltype_target" escapeAmp="false">
						<s:param name="transformation.uri" value="transformation.uri" />
						<s:param name="ruleSelected.uri" value="ruleSelectedUri" />
					</s:url>
				 	<sj:dialog 
					   	id="addObseltypeTarget%{#ruleStatus.index}" 
					   	autoOpen="false" 
					   	modal="false" 
					   	title="%{getText('add.obselType.target')} : %{transformation.traceModelTarget.label}"
					   	width="550"
					   	closeTopics="closeDialog">
					   	
					  </sj:dialog>
					  <sj:a id="opentopicdialogAddObseltypeTarget%{#ruleStatus.index}"
					  		openDialog="addObseltypeTarget%{#ruleStatus.index}" 
					  		cssClass="buttonlink ui-state-default ui-corner-all"
					  		button="true"
					  		buttonIcon="ui-icon-plusthick"
					  		href="%{urlObseltypeTarget}"
					  		title="%{getText('add.obselType.target')}"
					   >
					  </sj:a>
					  <hr />
				</td>
			</tr>
			<tr>
			<td>
				<s:iterator value="constructPart.listConstruct" status="itemConstruct">
					<div style="overflow: auto;" align="center">
						<table style="border: black solid 1px ;">
							<thead>
								<tr class="borderLigth">
									<td></td>
									<s:iterator value="listConstructItem" status="constructItem"  >
										<td>
											<s:property value="attribute.label"/>
											<s:if test="%{attribute.nullable == false}">
												<span style="required">*</span>
											</s:if>												
										</td>
									</s:iterator>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td >
										<s:set var="uriOperande" value="uri" />
										<s:if test="label != ''">
											<s:property value="label"/>
										</s:if>
										<s:else>
											<s:property value="localName"/>
										</s:else>
									</td>
									<s:iterator value="listConstructItem" status="attributeIndex" >
										<s:if test="%{construct.uri != null &&
										 construct.uri == #uriOperande &&
										 constructItem.attribute.uri != null && constructItem.attribute.uri == attribute.uri}">
											<s:set var="bgColor" value="%{'green'}"/>
										</s:if>
										<s:else>
											<s:set var="bgColor" value="%{'white'}"/>
										</s:else>
										
										<td bgcolor="<%= pageContext.getAttribute("bgColor", PageContext.PAGE_SCOPE) %>">
											<s:iterator value="operatorOrOperande" status="op" >
												<s:property value="toString()" />
											</s:iterator>
											
											<s:url var="urlUpdateAttributeConstruct" action="update_rule_prepare_construct" escapeAmp="false" >
												<s:param name="transformation.uri" value="transformation.uri" />
												<s:param name="ruleSelected.uri" value="ruleSelectedUri" />
												<s:param name="criteriaPart" value="%{'construct'}" />
												<s:param name="construct.uri" value="uri"/>
												<s:param name="constructItem.attribute.uri" value="attribute.uri"/>
												<s:param name="constructItem.operande.uri" value="operande.uri"/>
												<s:param name="constructItem.operande.attribute.uri" value="operande.attribute.uri"/>
											</s:url>
											<sj:dialog 
											   	id="updateAttributeObselTarget_%{#attributeIndex.index}_%{#itemConstruct.index}_%{#ruleStatus.index}" 
											   	autoOpen="false" 
											   	modal="false" 
											   	title="%{getText('add.attribute.value')}"
											   	width="650"
											   	closeTopics="closeDialog">
											  </sj:dialog>
											  <sj:a openDialog="updateAttributeObselTarget_%{#attributeIndex.index}_%{#itemConstruct.index}_%{#ruleStatus.index}"
											  		href="%{urlUpdateAttributeConstruct}"
											  		cssClass="buttonlink ui-state-default ui-corner-all"
											  		button="true"
											  		buttonIcon="ui-icon-newwin"
											  		title="%{getText('update.obsel.target')}"
											   >
											  </sj:a>
										</td>
									</s:iterator>
								</tr>
							</tbody>
						</table>
					</div>
					<br/>
				</s:iterator>
			</td>
			</tr>
		</tbody>
		</table>
		<table class="border">
		<thead><tr><th colspan="2" align="center"><s:property value="getText('list.criteria')" /></th></tr></thead>
		<tfoot>
		<tr><td colspan="2">
			<center>
				<s:url var="urlUpdateRulePrepareGeneral" action="update_rule_prepare_where" escapeAmp="false" >
				<s:param name="transformation.uri" value="transformation.uri" />
				<s:param name="ruleSelected.uri" value="%{uri}" />
				<s:param name="ruleSelected.id" value="%{id}" />
				<s:param name="criteriaPart" value="where" />
				</s:url>
				<sj:dialog 
				   	id="addFilter%{#ruleStatus.index}%{transformation.localName}" 
				   	autoOpen="false" 
				   	modal="false" 
				   	title="%{getText('add.criteria')}"
				   	width="750"
				   	closeTopics="closeDialog">
				  </sj:dialog>
				  <sj:a openDialog="addFilter%{#ruleStatus.index}%{transformation.localName}" 
				  		cssClass="buttonlink ui-state-default ui-corner-all"
				  		href="%{urlUpdateRulePrepareGeneral}"
				  		button="true"
				  		buttonIcon="ui-icon-newwin">
				  		<s:property value="getText('add.criteria')" />
				  </sj:a>
				  
					<s:url var="urlUpdateRuleNotExists" action="update_rule_not_exists" escapeAmp="false" >
						<s:param name="transformation.uri" value="transformation.uri" />
						<s:param name="ruleSelected.uri" value="%{uri}" />
						<s:param name="ruleSelected.id" value="%{id}" />
						<s:param name="criteriaPart" value="where" />
					</s:url>
					<sj:a href="%{urlUpdateRuleNotExists}" 
					  		cssClass="buttonlink ui-state-default ui-corner-all"
					  		button="true"
					  		targets="content"
					  		buttonIcon="ui-icon-refresh">
					  		<s:if test="!isNotExistsRule">
					  			<s:property value="getText('not.exists')" />
					  		</s:if>
					  		<s:else>
					  			<s:property value="getText('exists')" />
					  		</s:else>
				  	</sj:a>  
				  
			</center>
			</td></tr></tfoot>
		<tbody>
		<tr>
			<td>
				<s:iterator value="wherePart.listWhere" status="where">				
						<s:property value="label" />
						<s:url var="urlDeleteWhere" action="delete_var" escapeAmp="false" >
							<s:param name="transformation.uri" value="transformation.uri" />
							<s:param name="ruleSelected.uri" value="ruleSelectedUri" />
							<s:param name="criteriaPart" value="%{'where'}" />
							<s:param name="varToDeleteIndice" value="#where.index" />							
						</s:url>
						<sj:a 
								href="%{urlDeleteWhere}"
							  	cssClass="buttonlink ui-state-default ui-corner-all"
							  	button="true" 
								buttonIcon="ui-icon-trash"
							  	targets="content"
							  	title="%{getText('del.where')}"
							  >
						</sj:a>
				</s:iterator>
				<s:url var="urlObseltypeSource" action="list_obseltype_source" escapeAmp="false">
					<s:param name="transformation.uri" value="transformation.uri" />
					<s:param name="ruleSelected.uri" value="ruleSelectedUri" />
				</s:url>
				 	<sj:dialog 
					   	id="addObseltypeSource%{#ruleStatus.index}%{transformation.localName}" 
					   	autoOpen="false" 
					   	modal="false" 
					   	title="%{getText('add.obselType.source')} : %{transformation.traceModelSource.label}"
					   	width="550"
					   	closeTopics="closeDialog">
					   	
					  </sj:dialog>
					  <sj:a id="opentopicdialogAddRuleInTransformation%{#ruleStatus.index}%{transformation.localName}"
					  		openDialog="addObseltypeSource%{#ruleStatus.index}%{transformation.localName}" 
					  		href="%{urlObseltypeSource}"
					  		cssClass="buttonlink ui-state-default ui-corner-all"
					  		button="true"
					  		buttonIcon="ui-icon-plusthick"
					  		title="%{getText('add.obselType.source')}"
					  >
				  </sj:a>
				  <hr />
			</td>
		</tr>
		<tr>
			<td>
			<s:set name="filterNotExists"  value="false"  />
				<s:iterator value="wherePart.listFilter" status="statFilter" >
					<s:iterator value="operatorOrOperande" status="op" >
						<s:if test="toString().contains('NOT EXISTS') || toString().contains(']')">
							<b><s:property value="toString()" /></b>
							<s:set name="filterNotExists"  value="true"  />
						</s:if>
						<s:else>
							<s:property value="toString()" />
							<s:set name="filterNotExists"  value="false"  />
						</s:else>
					</s:iterator>
					<s:if test="%{!#filterNotExists}">
						<s:url var="urlDeleteRuleWhere" action="delete_filter_or_construct" escapeAmp="false" >
							<s:param name="transformation.uri" value="transformation.uri" />
							<s:param name="ruleSelected.uri" value="ruleSelectedUri" />
							<s:param name="filterToDeleteId" value="%{#statFilter.index}" />
							<s:param name="criteriaPart" value="%{'where'}" />
						</s:url>
						<sj:a 
							href="%{urlDeleteRuleWhere}"
						  	cssClass="buttonlink ui-state-default ui-corner-all"
						  	button="true" 
							buttonIcon="ui-icon-trash"
						  	targets="content"
						  	title="%{getText('del.criteria')}">
						</sj:a>
					</s:if>
					<br />
				</s:iterator>
			</td>
		</tr>
		</tbody>
		</table>
		<div align="right">
			<s:url var="urlUpdateRule" action="create_rule_prepare" escapeAmp="false">
			 	<s:param name="transformation.uri" value="transformation.uri" />
				<s:param name="ruleSelected.uri" value="%{uri}" />
			 </s:url>
			 <sj:a id="aMajRule%{#ruleStatus.index}"
					cssClass="buttonlink ui-state-default ui-corner-all"
					button="true"
					openDialog="addRuleInTransformation"
					href="%{urlUpdateRule}"
					buttonIcon="ui-icon-newwin"
					title="%{getText('update.rule')}"
				 >
			</sj:a>
			 <s:url var="urlDeleteRule" action="delete_rule" escapeAmp="false">
			 	<s:param name="transformation.uri" value="transformation.uri" />
					<s:param name="ruleSelected.uri" value="uri" />
			 </s:url>
			 <sj:a id="aDeleteRule%{#ruleStatus.index}"
					cssClass="buttonlink ui-state-default ui-corner-all"
					button="true"
					targets="empty"
					href="%{urlDeleteRule}"
					onCompleteTopics="refreshAfterDelete"
					buttonIcon="ui-icon-trash"
					title="%{getText('delete.rule')}"
				 >
			</sj:a>
			
		</div>
	</sj:accordionItem>
	</sj:accordion>
</s:iterator>