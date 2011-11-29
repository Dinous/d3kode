<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:actionerror />
<s:actionmessage />
<s:url var="attributeList" action="attributeList" />
<s:url var="secondOperandeValueList" action="secondOperandeValueList" />
<s:url id="create_MethodInkTBS_createCriteria_CONSTRUCT" action="create_MethodInkTBS_createCriteria_CONSTRUCT"/>	
<s:form id="idFormCriteria" theme="simple" action="create_MethodInkTBS_createCriteria_CONSTRUCT" cssStyle="width:100%" >
  <s:hidden name="conditionMethodSelected" />
  <s:hidden name="constructionMethodSelected" />
  <s:hidden name="traceModelSelected" id="traceModelSelected" />
	
    <sj:div formIds="idFormCriteria" id="criteria" href="%{create_MethodInkTBS_createCriteria_CONSTRUCT}" indicator="indicator">
        <img id="indicator" src="images/indicator.gif" alt="Loading..." style="display:none"/>
    </sj:div>
	<br />	
	<fieldset>
		<legend><s:text name="creating.method.second.operande" /></legend>
		<center>
			<table class="border" width="50%">
				<thead>
					<tr>
						<td width="40%"><s:label key="list.trace.obsel.type" theme="simple" /></td>
						<td width="20%"><s:label key="list.trace.obsel.attribute.type" theme="simple" /></td>
						<td width="40%"><s:label key="list.trace.operande.value" theme="simple" /></td>
					</tr>
				</thead>
				<tbody>
					<tr>
                        <td>
                          <s:property value="obselTypeDestination" />
                        </td>
                        <td>
                        <s:hidden name="firstOperandeType" value="%{transformationRuleFactory.obselTypeDestination}" />
						<sj:select id="firstAttribute" name="firstAttribute"
			              list="attributeList"
			              href="%{attributeList}"
			              formIds="idFormCriteria"
			              listKey="myKey" 
			              listValue="myValue"
			              emptyOption="true" 
			              headerKey="-1" 
			              headerValue=""
			            />
                        </td>
                        <td>
                          <sj:radio id="secondOperandeText" name="secondOperandeText"
				              list="secondOperandeValueList" 
				              listKey="myKey" 
				              listValue="myValue"
				              formIds="idFormCriteria"
				              href="%{secondOperandeValueList}" 
				              emptyOption="true" 
				              headerKey="-1" 
				              headerValue=""
				            />
                        </td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td></td>
						<td>
							<br />
							<sj:submit id="submitCriteria" name="submitCriteria" 
								targets="criteria"
								indicator="indicator" 
								button="true"
								value="%{getText('submit.create.criteria')}"
								buttonIcon="ui-icon-refresh">
  						</sj:submit>
						</td>
					</tr>
				</tfoot>
			</table>
			</center>
	</fieldset>
	</s:form>