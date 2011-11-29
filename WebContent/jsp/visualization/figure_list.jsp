<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<sj:div id="resultObselTypeFigure">
	<s:actionmessage />
	<sj:tabbedpanel id="tabListTraceModelFigure">
		<s:iterator value="listTraceModel" status="traceModelFigure">
			<s:url var="urlTraceModelFigure" action="obselTypeFigure" escapeAmp="false">
				<s:param name="traceModelUri" value="uri"/>
				<s:param name="traceModelLocalName" value="localName"/>
			</s:url>
			<sj:tab 
				id="tabTraceModel_%{#traceModelFigure.index}" 
				href="%{urlTraceModelFigure}" 
				label="%{label}" />
		</s:iterator>
		<sj:tab 
			id="tabAddPicture" 
			label="%{getText('add.picture')}"
			target="addPictureDiv" />
			<div id="addPictureDiv">
				<s:form id="addPictureForm" action="pictureUpload" method="post" enctype="multipart/form-data">
					<s:file 
						key="icone" 
						size="100" 
						/>
						<tr>
							<td colspan="2" align="center">
								<sj:submit
									targets="resultObselTypeFigure"
									formIds="addPictureForm"
									label="%{getText('submit.add.picture')}"
									parentTheme="simple"
									key="upload"
								 />
					 	</td>
					 	</tr>
				</s:form>
			</div>
	</sj:tabbedpanel>
</sj:div>