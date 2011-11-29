<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:url var="urlPostObselTypeFigure" action="obselTypeFigure_save" />
<s:form id="frmObselTypeFigure_%{traceModelLocalName}" action="obselTypeFigure_save" theme="simple">
<s:hidden name="traceModelUri" />
	<table class="border">
		<thead>
			<tr>
				<td><s:text name="label" /></td>
				<td colspan="2"><s:text name="xLinkHref" /></td>
				<td><s:text name="traceLevel" /></td>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td colspan="4" align="center">
					<sj:submit 
						targets="resultObselTypeFigure"
						formIds="frmObselTypeFigure_%{traceModelLocalName}"
						onCompleteTopics="closeDialog"/>
				</td>
			</tr>
		</tfoot>
		<tbody>
			<s:iterator value="obselTypes.obselTypeFigure" status="oTF">
			<tr>
				<td><s:property value="label" /></td>
				<td><s:select name="xlinkHref" onchange="loadPicture(this, 'img_%{#oTF.index}_%{traceModelLocalName}')" list="listxLinkHref" /></td>
				<td><img height="25px" width="25px" 
					 alt="<s:property value='xlinkHref'/>"
					 id="img_<s:property value="#oTF.index"/>_<s:property value="traceModelLocalName"/>" 
					 src="work/image/<s:property value='xlinkHref'/>" /></td>
				<td><s:select name="traceLevel" list="listTraceLevel" /></td>
			</tr>
			</s:iterator>
		</tbody>
	</table>
</s:form>