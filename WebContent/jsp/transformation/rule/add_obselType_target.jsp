<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:bean name="org.liris.mTrace.tools.LabelComparator" var="labelComparator" />

<center>
	<table>
		<s:sort comparator="#labelComparator" source="transformation.traceModelTarget.obselTypes">
			<s:iterator status="obselType">
				<tr>
					<td align="center">
						<s:url var="urlAdd_war_construct" action="add_var_construct" escapeAmp="false">
							<s:param name="obselTypeUri" value="%{uri}" />
							<s:param name="obselTypeLabel" value="%{label}" />		
							<s:param name="transformation.uri" value="transformation.uri" />
							<s:param name="ruleSelected.uri" value="ruleSelected.uri" />
						</s:url>
						<sj:a 
							onCompleteTopics="refreshUpdateRule" 
							targets="content"
							cssStyle="width:100%"
							cssClass="buttonlink ui-state-default ui-corner-all"
							button="true" 
							href="%{urlAdd_war_construct}"
							indicator="indicator"
							onSuccessTopics="closeDialog"
							title="%{label}">
								<s:property value="%{label}"  />
					 	</sj:a>
					</td>
				</tr>
			</s:iterator>
		</s:sort>
	</table>
</center>