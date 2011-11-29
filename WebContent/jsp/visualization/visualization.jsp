<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:if test="baseCurrent.storedTraces.isEmpty() && baseCurrent.computedTraces.isEmpty()">
	<s:text name="base.first.trace.none" />
</s:if>
<s:else>
	<sj:tabbedpanel id="tabVisu" collapsible="true">
	<sj:tab id="visu1" label="%{list.trace}" target="visu1Div"  />
	<div id="visu1Div">
		<table class="border">
			<thead>
				<tr>
					<td><s:text name="storedTrace.id" /></td>
					<td><s:text name="storedTrace.creationDate" /></td>
					<td><s:text name="storedTrace.labels" /></td>
					<td><s:text name="storedTrace.traceBeginDT" /></td>
					<td><s:text name="storedTrace.traceEndDT" /></td>
					<td><s:text name="storedTrace.traceModel" /></td>
					<td><s:text name="storedTrace.obsels.size" /></td>
					<td><s:text name="storedTrace.transformedTraces.size" /></td>
					<%-- <td><s:text name="trace.type" /></td> --%>
					<td></td>
				</tr>
			</thead>
			<tfoot></tfoot>
			<tbody>
				<s:iterator value="currentBase.storedTraces" status="stored">
					<%-- <s:url var="urlCreateSvgWithMethod" action="buildSvgForOneTraceWithMethod">
						<s:param name="traceUri" value="uri" />
					</s:url> --%>
					<tr>
						<td><s:property value="localName" />
						</td>
						<td><s:property value="getDateStr(localName)" />
						</td>
						<td><s:property value="getLabel(labels)" />
						</td>
						<td><s:property value="getFirstBeginObsel(uri)" />
						</td>
						<td><s:property value="getLastEndObsel(uri)" />
						</td>
						<td><s:property value="getLabel(traceModel.labels)" />
						</td>
						<td><s:property value="getObsels().size()" />
						</td>
						<td><s:property value="getTransformedTraces().size()" />
						</td>
						<%-- <td><s:text name="trace.storedTrace" />
						</td> --%>
						<td rowspan="<s:property value="1 + currentBase.computedTraces.size()" />">
						<s:url var="urlFigure" action="obselTypeFigure_list" />
						<sj:dialog 
						   	id="figure%{#stored.index}" 
						   	autoOpen="false" 
						   	modal="false" 
						   	title="%{getText('figure')}"
						   	width="550"
						   	closeTopics="closeDialog"/>
						  <sj:a id="openFigure%{#stored.index}"
						  		href="%{urlFigure}"
						  		openDialog="figure%{#stored.index}" 
						  		cssClass="buttonlink ui-state-default ui-corner-all"
						  		button="true"
						  		buttonIcon="ui-icon-plusthick"
						  		title="%{getText('add.obselType.target')}">
						   <s:text name="trace.parameters"/>
						  </sj:a>
						  
						<s:form id="visu_%{#stored.index}" action="buildSvgForOneTraceWithMethod">
							<s:hidden name="traceUri" value="%{uri}" />
							<s:select list="mapScale" key="scale" />
							<s:textfield key="beginStone" />
							<s:textfield key="endStone" />
							<s:textfield key="temporalScale"/>
							<sj:submit button="true" buttonIcon="ui-icon-refresh" targets="divSvgVisu" formIds="visu_%{#stored.index}"
					   		value="%{getText('trace.visualization.withTransformation')}" />
						</s:form>
						<s:url var="urlHistory" action="history_visu" />
						<%-- <sj:dialog 
						   	id="history%{#stored.index}" 
						   	autoOpen="false" 
						   	modal="false" 
						   	title="%{getText('history')}"
						   	width="550"
						   	closeTopics="closeDialog"/> --%>
						   	<%-- openDialog="history%{#stored.index}"  --%>
						  <sj:a id="openHistory%{#stored.index}"
						  		href="%{urlHistory}"
						  		button="true"
						  		targets="historyVisuDiv"
						  		cssClass="buttonlink ui-state-default ui-corner-all"
						  		title="%{getText('history')}">
						   <img alt="" src="icone/history_25.png" />
						  </sj:a>
						</td>
					</tr>
				</s:iterator>
				<s:iterator value="currentBase.computedTraces" status="computed">
					<s:url var="urlCreateSvg" action="buildSvgForOneTrace">
						<s:param name="traceUri" value="uri" />
					</s:url>
					<tr>
						<td><s:property value="localName" />
						</td>
						<td><s:property value="getDateStr(localName)" />
						</td>
						<td><s:property value="getLabel(labels)" />
						</td>
						<td><s:property value="getFirstBeginObsel(uri)" />
						</td>
						<td><s:property value="getLastEndObsel(uri)" />
						</td>
						<td><s:property value="getLabel(traceModel.getLabels())" />
						</td>
						<td><s:property value="getObsels().size()" />
						</td>
						<td><s:property value="getTransformedTraces().size()" />
						</td>
						<%-- <td>
							<s:if test="isIntermediateSource">
								<s:text name="trace.intermediateTrace" />
							</s:if>
							<s:else>
								<s:text name="trace.computedTrace" />
							</s:else>
						</td> --%>
					</tr>
				</s:iterator>
			</tbody>
			<tfoot>
				<tr>
				<td colspan="9">
					<br />
					<center>
						<div id="historyVisuDiv"/>
					</center>
				</td>
				</tr>
			</tfoot>
		</table>
		<script>$(".border>tbody>tr").filter(":odd").css("background-color", "#DDDDFF");</script>
		
	</div>
	</sj:tabbedpanel>
	<!-- border: medium solid black; -->
	<sj:div id="divSvgVisu" 
		   cssStyle="overflow:auto;width: 100%; height: 80%;" 
		  cssClass="ui-widget-content ui-corner-all"
	 >
	 </sj:div>
	 
</s:else>