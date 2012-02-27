<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:actionerror />
<s:actionmessage />

<table class="border">
	<thead>
		<tr>
			<td><s:text name="creation.date" /></td>
			<td><s:text name="source.trace" /></td>
			<td><s:text name="obsels" /></td>
			<td><s:text name="methods" /></td>
			<td><s:text name="intermediate.trace" /></td>
			<td><s:text name="computed.trace" /></td>
			<td><s:text name="computed.obsels" /></td>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="listTraceToTransform">
		<tr >
			<td><s:property value="getCreationDate(localName)" /></td>
			<td><s:property value="getLabel(labels)" /></td>
			<td><s:property value="obsels.size()" /></td>
			<td>
				<s:iterator value="getListTransformation(uri)">
					<s:property value="label" /><br/>
				</s:iterator>
			</td>
			<td>
				<s:iterator value="transformedTraces">
					<s:if test="isIntermediateSource" >
						<s:property value="getLabel(labels)" /><br/>	
					</s:if>
				</s:iterator>
			</td>
			<td>
				<s:iterator value="transformedTraces">
					<s:if test="!isIntermediateSource" >
						<s:property value="getLabel(labels)" />
						<s:url var="urlDeleteComputedTrace" action="delete_computed" >
							<s:param name="uri" value="uri" />
						</s:url>
						<sj:a 
								href="%{urlDeleteComputedTrace}"
							  	cssClass="buttonlink ui-state-default ui-corner-all"
							  	button="true" 
								buttonIcon="ui-icon-trash"
							  	targets="main"
							  	title="%{getText('delete.computedTrace')}"
							  >
						</sj:a>
						<br />
					</s:if>
				</s:iterator>
			</td>
			<td>
				<s:iterator value="transformedTraces">
					<s:if test="!isIntermediateSource" >
						<s:property value="obsels.size()" /><br />
					</s:if>
				</s:iterator>
			</td>
		</tr>
		</s:iterator>	
	</tbody>
	<tfoot></tfoot>
</table>
<script>$(".border tbody tr").filter(":odd").css("background-color", "#DDDDFF");</script>

<fieldset><legend><s:text name="create.computed.trace"/></legend>
<%-- <s:url var="urlBuildTransformedTrace" action="buildTransformedTrace" /> --%>
<%-- <sj:div
		id="divSvgTransformedTrace"
		indicator="indicator"
		href="%{urlBuildTransformedTrace}"
		formIds="frmCalculetedTrace"
		deferredLoading="false"
		reloadTopics="changeTransformationTopic"
	/> --%>
<s:form id="frmCalculetedTrace" action="buildTransformedTrace">
	<s:url var="listTrace" action="listTrace"/>
	<sj:select
		href="%{listTrace}"
		name="traceUri"
		list="listTrace"
		formIds="frmCalculetedTrace"
		listKey="myKey"
		listValue="myValue"
		emptyOption="true"
		onChangeTopics="changeTraceTopic"
		required="true"
		key="source.trace"
		>
	</sj:select>
	<sj:select
		href="%{listTrace}"
		name="transformationUri"
		list="listTransformation"
		formIds="frmCalculetedTrace"
		listKey="myKey"
		listValue="myValue"
		emptyOption="true"
		deferredLoading="true"
		reloadTopics="changeTraceTopic"
		key="transformation"
		required="true"
		>
	</sj:select>
	<s:textfield required="true" key="computedTraceLabel" />
	<tr>
		<td class="tdLabel">
			
		</td>
		<td>
			<sj:a 
				indicator="indicator"
				formIds="frmCalculetedTrace"
				button="true" 
				buttonIcon="ui-icon-gear"
				targets="content"
			 	onClickTopics="changeTransformationTopic"  >Transformer</sj:a>
		</td>
	</tr>
</s:form>
</fieldset>
<%-- <s:url var="urlCreateSvg" action="buildSvgForOneTrace" />
	<sj:div
		id="divSvg"
		indicator="indicator"
		href="%{urlCreateSvg}"
		formIds="frmCalculetedTrace"
		deferredLoading="true"
		reloadTopics="changeTraceTopic"
	/> --%>
