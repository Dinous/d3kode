<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:actionerror />
<s:actionmessage />

<s:if test="storedTraces.isEmpty()">
	<s:text name="base.first.trace.none" />
</s:if>
<s:else>
	<table class="border">
		<thead>
			<tr>
				<td></td>
				<td><s:property value="getText('trace.creation.date')" /></td>
				<td><s:property value="getText('trace.begin.date')" /></td>
				<td><s:property value="getText('trace.end.date')" /></td>
				<td><s:property value="getText('trace.label')" /></td>
				<td><s:property value="getText('trace.model')" /></td>
				<td><s:property value="getText('trace.obselTypes')" /></td>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="storedTraces" status="storedTrace">
				<tr>					
					<td><s:property value="%{#storedTrace.index}" /></td>
					<td><s:property value="getCreationDate(localName)" /></td>
					<td><s:property value="strDateFromkTBSDateDT(traceBeginDT)" /></td>
					<td><s:property value="strDateFromkTBSDateDT(traceEndDT)" /></td>
					<td>
						<%-- <s:url var="buildSvgForOneTrace" action="buildSvgForOneTrace" escapeAmp="false">
							<s:param name="traceUri" value="uri"/>
							<s:param name="beginStone" value="0"/>
							<s:param name="endStone" value="1000"/>
							<s:param name="scale" value="1.0"/>							
						</s:url> --%>
						<%-- <sj:a openDialog="dialogTrace" href="%{buildSvgForOneTrace}"> --%>
							<s:property value="labelOfResource(labels)" />
						<%-- </sj:a> --%>
					</td>
					<td>
						<s:url var="managementModel" action="management_model" />
						<sj:a openDialog="dialogModelTrace" href="%{managementModel}">
							<s:property value="labelOfResource(traceModel.labels)" />
						</sj:a>
					</td>
					<td>
						<s:iterator value="listObselInTrace(obsels).entrySet()" >
							<s:property value="value" /> <s:property value="key" />
							<br />
						</s:iterator>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
</s:else>
<br />
<sj:dialog 
   	id="dialogTraceCreate" 
   	autoOpen="false" 
   	modal="true"
   	title="%{getText('create.first.trace')}"
   	width="1200">
   	<fieldset>
		<legend><s:text name="upload.form" /></legend>
		<s:form id="frmUpload" method="POST" action="upload_obsel_create_trace" enctype="multipart/form-data">
			<s:textfield key="traceBuilder.label" size="75"  />
			<s:file key="traceBuilder.obselFile" size="100" accept="text/csv,text/comma-separated-values"/>
			<s:select 
				label="%{getText('list.model.trace')}"
				name="traceBuilder.traceModel"
				list="listTraceModel" 
				listKey="myKey" 
				listValue="myValue" />
			<sj:datepicker key="traceBuilder.beginDT" 
				timepicker="true" 
				timepickerShowSecond="true" 
				timepickerFormat="hh:mm:ss"
				displayFormat="yy-mm-dd"
				timepickerCurrentText="%{getText('current')}"
				timepickerCloseText="%{getText('close')}"
				timepickerSeparator="T"
				size="30"/>
			<sj:datepicker key="traceBuilder.endDT" 
				timepicker="true" 
				timepickerShowSecond="true" 
				timepickerFormat="hh:mm:ss"
				displayFormat="yy-mm-dd"
				timepickerCurrentText="%{getText('current')}"
				timepickerCloseText="%{getText('close')}"
				timepickerSeparator="T"
				size="30"/>
			<tr>
				<td colspan="2" align="center">
					<sj:submit 
		            	id="formSubmit1"
		            	targets="content"
		            	formIds="frmUpload"
		            	indicator="indicator"
		            	parentTheme="simple"
		            	button="true"
		            	value="%{getText('submit.create.trace')}"
		            	/>
				</td>
			</tr>
		</s:form>
	</fieldset>
</sj:dialog>
<sj:a id="opentopicdialogCreateTrace"
	openDialog="dialogTraceCreate" 
	cssClass="buttonlink ui-state-default ui-corner-all"
	button="true"
	buttonIcon="ui-icon-newwin"
	title="%{getText('create.first.trace')}"
>
	<s:property value="getText('create.simulation')" />
</sj:a>

<s:url var="urlTrace" action="listTraceModel"/>
<sj:dialog 
   	id="dialogTraceModelList" 
   	autoOpen="false" 
   	title="%{getText('trace.models')}"
   	width="1000"
   	href="%{urlTrace}">
</sj:dialog>
<sj:a id="opentopicdialogTraceModelList"
	openDialog="dialogTraceModelList" 
	cssClass="buttonlink ui-state-default ui-corner-all"
	button="true"
	buttonIcon="ui-icon-newwin"
>
	<s:property value="getText('read.trace.model')" />
</sj:a>
<sj:dialog 
   	id="dialogModelTrace" 
   	autoOpen="false" 
   	modal="true"
   	title="%{getText('tree.base')}"
   	width="600">
</sj:dialog>
<sj:dialog 
   	id="dialogTrace" 
   	autoOpen="false" 
   	modal="true"
   	title="%{getText('svg.trace')}"
   	width="600">
</sj:dialog>