<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

 <s:actionerror/>
        <s:actionmessage />
<s:if test="traceModels != null">	
		<br />	
		<s:form id="idFormCriteria" theme="simple" action="create_MethodInkTBS_preCreateCriteria_CONSTRUCT" 
			cssStyle="width:100%" >
		<table><thead></thead><tfoot></tfoot><tbody>
		<tr>
		<td>
    		<fieldset>
    			<legend><s:text name="method.create.method.construct" /></legend>
                <center>
                	<s:label required="true" for="methodLabel" key="labelMethodName"/><s:textfield id="methodLabel" name="methodLabel" theme="simple"  />
    				<table class="border" width="60%">
    					<thead>
    						<tr>
                                <td width="25%"><s:label key="load.method" theme="simple"/></td>
    							<td width="50%"><s:label key="list.trace.model" theme="simple" /></td>
    							<td width="25%"><s:label key="list.trace.obsel.type" theme="simple" /></td>
    						</tr>
    					</thead>
    					<tbody>
    						<tr>
                                <td>
                                  <table width="100%">
                                    <thead></thead>
                                    <tbody>
                                      <tr>
                                        <td>
                                          <s:form id="frmCriteriaWhere" action="create_MethodInkTBS_preCreateCriteria_WHERE" >
                                          <s:url var="listCriteria" action="listCriteria"/>
            								<sj:select 
												name="conditionMethodSelected"
												id="methodSelected"
												href="%{listCriteria}"
												list="criteriaFileListWhere"
												formIds="frmCriteriaWhere"
												listKey="myKey"
												listValue="myValue"
												/>
            								</s:form>
            							</td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </td>
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
									size="10"
									id="listObselType"
								 	name="obselTypeId" 
								 	list="listObselType"
								 	listKey="myKey"
									listValue="myValue"
								 	reloadTopics="reloadsecondlist"
								 	indicator="indicator"
								 	/><br />
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
						                    value="%{getText('button.save.obsel.construct')}"
						                    buttonIcon="ui-icon-refresh" />
							   </td>
    						</tr>
    					</tfoot>
    				</table>
              </center>
    		</fieldset>
		</td>
        <td width="50%">
          <fieldset>
            <legend><s:text name="method.construct.method" /></legend>
            <table class="border" width="100%">
              <thead></thead>
              <tbody>
                <tr>
                  <td>
                    <s:form id="frmCriteriaConstruct" action="create_MethodInkTBS_preCreateCriteria_CONSTRUCT" >
                    <s:url var="listCriteria" action="listCriteria"/>
  					<sj:select 
							name="constructionMethodSelected"
							id="constructionMethodSelected"
							href="%{listCriteria}"
							list="criteriaFileListConstruct"
							formIds="frmCriteriaConstruct"
							listKey="myKey"
							listValue="myValue"
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
                           	formIds="frmCriteriaConstruct"
                            value="%{getText('button.load.request')}"
                            buttonIcon="ui-icon-refresh" />
					</td>
				</tr>
              </tbody>
            </table>
          </fieldset>
        </td>
		</tr>
    </tbody>
    </table>
</s:form>
</s:if>