<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:actionerror/>
<s:actionmessage />
<s:if test="actionerror == null">	
		<br />	
		<s:form id="idFormCriteria" theme="simple" action="create_MethodInkTBS_preCreateCriteria_WHERE" cssStyle="width:100%" >
		<table><thead>
        </thead><tfoot></tfoot><tbody>
		<tr>
		<td>
		<fieldset>
			<legend><s:text name="method.create.method" /></legend>
      			<s:label required="true" for="methodLabel" key="labelMethodName"/><s:textfield id="methodLabel" name="methodLabel" theme="simple"  />
				<table class="border">
					<thead>
						<tr>
							<td width="35%"><s:label key="list.trace.model" theme="simple" /></td>
							<td width="17%"><s:label key="list.trace.obsel.type" theme="simple" /></td>
							<td width="48%"></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
              					<s:url id="traceModelList" action="traceModel"/> 
								<sj:select
									href="%{traceModelList}"  
									name="traceModelSelected"
									id="traceModelSelected"
									onChangeTopics="reloadsecondlist"
									list="listTraceModel"
									listKey="myKey"
									listValue="myValue"
									emptyOption="true"
									headerKey="-1"
									headerValue=""
									indicator="indicator"
									 />
							</td>
							<td>
								<sj:select
									href="%{traceModelList}"  
									cssClass="criteria"
									formIds=""
									size="10"
									multiple="multiple"
									id="listObselType"
								 	name="listObselType" 
								 	list="listObselType"
								 	listKey="myKey"
									listValue="myValue"
								 	reloadTopics="reloadsecondlist"
								 	onclick="" 
								 	indicator="indicator"
								 	onmouseup="addObselType('listObselType','tableComposeCriteria')" /><br />
							</td>
							<td>
								<table class="border">
									<thead>
										<tr>
											<td><s:label key="list.trace.obsel.type" theme="simple" /></td>
											<td><s:label key="list.trace.obsel.id" theme="simple" /></td>
											<td></td>
										</tr>
									</thead>
									<tbody id="tableComposeCriteria"></tbody>
									<tfoot></tfoot>
								</table>
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td></td>
							<td></td>
							<td>
								<br />
                                <sj:submit id="buttonSaveRequest" 
                                	name="buttonSaveRequest"
				                    targets="main"
				                    indicator="indicator" 
				                    button="true"
				                    formFilter="conditionMethodSelected"
				                    formIds="idFormCriteria"
				                    value="%{getText('button.save.request')}"
				                    buttonIcon="ui-icon-refresh" />
							</td>
						</tr>
					</tfoot>
				</table>
		</fieldset>
		</td>
		<td width="50%">
			<fieldset>
				<legend><s:text name="method.select.method" /></legend>
				<table class="border" width="100%">
					<thead></thead>
					<tbody>
						<tr>
							<td>
                              <s:form id="frmCriteriaWhere" action="create_MethodInkTBS_preCreateCriteria_WHERE" >
                              <s:url var="listCriteria" action="listCriteria"/>
								<sj:select 
									name="conditionMethodSelected"
									id="conditionMethodSelected"
									href="%{listCriteria}"
									list="criteriaFileListWhere"
									formIds="frmCriteriaWhere"
									listKey="myKey"
									listValue="myValue"
									emptyOption="true"
									headerKey="-1"
									headerValue=""
									/>
								</s:form>
							</td>
                        </tr>
                        <tr>
							<td>
								<br />
                				<sj:submit id="buttonLoadRequest"
                					name="buttonLoadRequest"
	                            	targets="main"
	                            	indicator="indicator" 
	                            	button="true"
		                            value="%{getText('button.load.request')}"
		                            buttonIcon="ui-icon-refresh" />
							</td>
						</tr>
					</tbody>
				</table>
			</fieldset>
		</td></tr></tbody></table>
		</s:form>
	</s:if>