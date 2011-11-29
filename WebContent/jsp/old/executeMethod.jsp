<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:actionmessage />
<s:actionerror />
<s:if test="actionerror == null">	
	<br />	
		
	<s:form id="idFormCriteria" action="executeMethod" cssStyle="width:100%" >
    	<thead></thead><tfoot></tfoot><tbody>
    	<tr>
    	<td>
      		<fieldset>
      			<legend><s:text name="method.execute.method" /></legend>
                  <center>
      				<table class="border" width="60%">
      					<thead>
      						<tr>
                                  <td width="33%"><s:label key="list.trace" theme="simple"/></td>
                                  <td width="33%"><s:label key="list.method" theme="simple"/></td>    							
                                  <td width="34%"><s:label key="trace.computed.name" theme="simple"/></td>    							
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
                                            <s:select 
								                  theme="simple"
								                  name="traceSrc"
								                  id="traceSrc"
								                  list="traces"
								                  listKey="uri"
								                  listValue="localName"
								                  headerKey=""
								                  headerValue="" />
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </td>
      							<td>
      								<s:select 
							                  theme="simple"
							                  name="exeMethod"
							                  id="exeMethod"
							                  list="exeMethods"
							                  listKey="uri"
							                  listValue="localName"
							                  headerKey=""
							                  headerValue="" />
      							</td>
                                  <td>
                                    <s:textfield 
		                                 	name="computedTraceName" 
		                                 	id="computedTraceName"
		                                 	key="computedTraceName"
		                                 	theme="simple" 
		                 					/>
    
                                  </td>
      						</tr>
      					</tbody>
      					<tfoot>
      						<tr>
      							<td colspan="3">
                                   <center>
      								<br />
                      				<sj:submit id="buttonSaveRequest"
                          				name="buttonSaveRequest"
		                                targets="main"
		                                indicator="indicator" 
		                                button="true"
		                                value="%{getText('button.save.request')}"
		                                buttonIcon="ui-icon-refresh" />
                      
                                  </center>
      							</td>
      						</tr>
      					</tfoot>
      				</table>
                </center>
      		</fieldset>
    	</td>
    	</tr>
      </tbody>
	</s:form>	
</s:if>