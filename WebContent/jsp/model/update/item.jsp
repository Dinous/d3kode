<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:if test="%{ktbsResource != null}">
<fieldset><legend><s:property value="getText('seleted.resource')"/></legend>
	<s:form id="createItem">
		<s:hidden name="ktbsResourceUri" value="%{ktbsResource.uri}"  />
		<s:set name="labelResource" value=" "  />
		<s:iterator value="ktbsResource.labels">
			<s:if test="#labelResource != null">
				<s:set name="labelResource"  value="#labelResource + [0].toString()"  />
			</s:if>
			<s:else>
				<s:set name="labelResource"  value="[0].toString()"  />
			</s:else>
		</s:iterator>
		<s:textfield key="ktbsResource.localName"  readonly="true" size="50"/>
		<s:textfield id="ktbsResourceLabel" key="ktbsResourceLabel" value="%{#labelResource}"  size="50"/>
		<s:if test="%{isAttribute}">
			<s:iterator value="ktbsResource.ranges" >
				<s:select
					key="attributeType.range"
				   	name="attributeTypeRange"
				   	value="getLocalnameAttributeType([0].toString())"
				   	list="%{listXsdType}">
				</s:select>
			</s:iterator>
		</s:if>
		<s:if test="%{!isBase && isExpert}">
			<tr>
				<td colspan="2" align="right">
					<s:url var="urlUpdateResource"  action="updateResource"/>
					<sj:submit
						href="%{urlUpdateResource}"
						value="update.resource"
						parentTheme="simple"
						type="image"
						formFilter="filtre"
						src="icone/update_16.png"
						targets="empty"
						title="%{getText('update')}"
						onCompleteTopics="updateListTraceModel"
					 />
					 <s:url var="urlDeleteResource"  action="deleteResource"/>
					 <sj:submit
						href="%{urlDeleteResource}"
						value="delete.resource"
						parentTheme="simple"
						type="image"
						src="icone/delete_16.png"
						targets="empty"
						title="%{getText('delete')}"
						onCompleteTopics="updateListTraceModel"
					 />
					</td>
			 </tr>
		 </s:if>
	</s:form>
	</fieldset>
	<br/>
		<s:if test="%{isBase  && isExpert}">
		  <fieldset><legend><s:property value="getText('create.new.trace.model')"/></legend>
			<s:form id="frmCreateTraceModelIhm"  action="modelCreate">
				<s:textfield label="%{getText('trace.model.label')}" size="75" 
					name="traceModelLabel" required="true" />
					<tr><td colspan="2" align="right">
				 <sj:submit
					id="submitCreateTraceModelIhm"
					targets="content"
					formIds="frmCreateTraceModelIhm"
			      	indicator="indicator"
			      	button="true"
			      	onSuccessTopics="updateListTraceModelFromModel"
			      	parentTheme="simple"
			      	cssStyle="text-align:center"
			      	value="%{getText('create.traceModel')}"
				 />
		            </td></tr>
			</s:form>
			</fieldset>
			<br/>
		</s:if>
		<s:elseif test="%{isTraceModel  && isExpert}">
		<fieldset><legend><s:property value="getText('load.csv.file')"/></legend>
			<s:form id="frmCreateTraceModel2" method="POST" action="modelUploadCsv" 
			enctype="multipart/form-data">
				<s:hidden name="traceModelLocalName" value="%{ktbsResource.localName}" />
				<s:file name="upload" size="45" key="file.model.csv" required="true"/>
					<tr><td colspan="2" align="right">
				 <sj:submit
					id="submitCreateTraceModel2"
					targets="content"
					formIds="frmCreateTraceModel2"
			      	indicator="indicator"
			      	button="true"
			      	onSuccessTopics="updateListTraceModelFromModel"
			      	parentTheme="simple"
			      	cssStyle="text-align:center"
			      	value="%{getText('update.trace.model.csv')}"
				 />
		            </td></tr>
			</s:form>
		</fieldset>
		<br />
		<fieldset><legend><s:property value="getText('create.new.obsel.type')"/></legend>
		   	<s:form id="frmCreateObselType" action="create_TraceModel_obselType" enctype="multipart/form-data">
				<s:hidden name="ktbsResourceUri" value="%{ktbsResource.uri}"  />			
			   	<%-- <s:textfield name="obselTypeLocalName" key="obselType.localName" /> --%>
			   	<s:textfield name="obselTypeLabel" key="obselType.label" size="50" />
			   	<tr><td colspan="2" align="right">
			   	<sj:submit
					id="submitCreateObselType"
					targets="content"
					formIds="frmCreateObselType"
			      	indicator="indicator"
			      	button="true"
			      	parentTheme="simple"
			      	cssStyle="text-align:center"
			      	onSuccessTopics="updateListTraceModel"
			      	value="%{getText('create.obselType')}"
				 />
				 </td>
				 </tr>
		   	</s:form>
		   	</fieldset>
		   	<br/>
		</s:elseif>
		<s:elseif test="%{isObselType  && isExpert}">
			<fieldset><legend><s:property value="getText('create.new.attribute.type')"/></legend>
		   	<s:form id="frmCreateAttributeType" action="create_TraceModel_attributeType">
				<s:hidden name="ktbsResourceUri" value="%{ktbsResource.uri}"  />	   		
			   	<s:textfield name="attributeTypeLocalName" key="attributeType.localName" />
			   	<s:select
			   		key="attributeType.range"
			   		name="typeAttributeType"
			   		list="%{listXsdType}">
			   	</s:select>
			   	<tr><td colspan="2" align="right">
			   	<sj:submit
					id="submitCreateAttributeType"
					targets="content"
					formIds="frmCreateAttributeType"
			      	indicator="indicator"
			      	button="true"
			      	parentTheme="simple"
			      	cssStyle="text-align:center"
			      	onSuccessTopics="updateListTraceModel"
			      	value="%{getText('create.attributeType')}"
				 />
				 </td>
				 </tr>
		   	</s:form>
		   	</fieldset>
		</s:elseif>
</s:if>
<s:else>
</s:else>