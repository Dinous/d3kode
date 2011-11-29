<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix ="sjg" uri="/struts-jquery-grid-tags" %>

<s:actionerror />
<s:actionmessage />
<s:url var="operandeList" action="obselList" />
<s:url var="attributeList" action="attributeList" />
<s:url var="secondOperandeChoosen" action="secondOperandeChoosen" />
<s:url var="typeSecondOperandeList" action="typeSecondOperandeList" />
<s:url var="operatorList" action="operatorList" />
<br />
<s:form theme="simple" id="idFormCriteria" action="create_MethodInkTBS_createCriteria_WHERE" cssStyle="width:100%" >
<s:hidden name="traceModelSelected" />
<s:hidden name="conditionMethodSelected" />

<s:url id="create_MethodInkTBS_createCriteria_WHERE" action="create_MethodInkTBS_createCriteria_WHERE"/>
    <sj:div formIds="idFormCriteria" id="criteria" href="%{create_MethodInkTBS_createCriteria_WHERE}" indicator="indicator">
        <img id="indicator" src="images/indicator.gif" alt="Loading..." style="display:none"/>
    </sj:div>

<fieldset>
	<legend><s:text name="creating.method.criteria" /></legend>
	<center>
		<table class="border" width="50%">
			<thead>
				<tr>
					<td></td>
					<td width="50%"><s:label key="first.operande" theme="simple" /></td>
					<td width="50%"><s:label key="second.operande" theme="simple" /></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<s:hidden name="obselListWithId" />
					<td><s:label key="obsel" theme="simple" /></td>
					<td>
						<sj:radio id="firstOperande" name="firstOperande"
						 list="obselList"
						 listKey="myKey"
						 listValue="myValue"
						 formIds="idFormCriteria"
						 onChangeTopics="reloadFirstAttribute"
						 onBeforeTopics="secondOperandSelected"
						 href="%{operandeList}"
						 emptyOption="true"
						 headerKey="-1"
						 headerValue=""
						/>
					</td>
					<td>	
						<s:textfield  id="secondOperandeText" name="secondOperandeText" />							
						<sj:radio id="secondOperande" name="secondOperande"
							list="secondObselList" 
							listKey="myKey" 
							listValue="myValue"
							formIds="idFormCriteria"
							onChangeTopics="reloadSecondAttribute" 
							href="%{operandeList}" 
							emptyOption="true" 
							headerKey="-1" 
							headerValue=""
						/>
					</td>
				</tr>
				<tr>
					<td><s:label key="attribute" theme="simple" /></td>
					<td>
						<sj:select id="firstAttribute" name="firstAttribute"
							list="attributeList"
							href="%{attributeList}"
							formIds="idFormCriteria"
							reloadTopics="reloadFirstAttribute" 
							listKey="myKey" 
							listValue="myValue"
							emptyOption="true" 
							headerKey="-1" 
							headerValue=""
						/>
					</td>
					<td>
						<center>
							<sj:select id="secondAttribute" name="secondAttribute" 
								list="secondAttributeList"
								href="%{attributeList}"
								formIds="idFormCriteria"
								reloadTopics="reloadSecondAttribute" 
								listKey="myKey" 
								listValue="myValue"
								emptyOption="true" 
								headerKey="-1" 
								headerValue=""
							/>
						</center>
					</td>
				</tr>
				<tr>
					<td><s:label key="operator" theme="simple" /></td>
					<td>
						<sj:radio id="operator" name="operator" 
							list="operatorList"
							href="%{operatorList}"
							formIds="idFormCriteria"
							listKey="myKey" 
							listValue="myValue" 
						/>
					</td>
				</tr>
				<tr>
					<td><s:label key="typeSecondOperande" theme="simple" /></td>
					<td>
						<sj:radio id="typeSecondOperande" name="typeSecondOperande"
							list="typeSecondOperandeList"
							href="%{typeSecondOperandeList}"
							formIds="idFormCriteria"
							listKey="myKey"
							onChangeTopics="secondOperandSelected" 
							listValue="myValue" 
							onclick="showSecondOperande"
						/>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td>
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