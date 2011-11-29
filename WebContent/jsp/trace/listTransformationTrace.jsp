<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:actionerror />
<s:actionmessage />

<table class="border">
	<thead>
		<tr>
			<td>Date création</td>
			<td>Trace source</td>
			<td>Observés</td>
			<td>Transformation</td>
			<td>Trace intermediaire</td>
			<td>Trace transformée</td>
			<td>Observé(s) calculé(s)</td>
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