<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sx" uri="/struts-dojo-tags" %>

<s:url var="d_url" value="/detail_Transformation.action" />
<s:url var="c_url" value="/createTransformation_Filter_load.action" />
<s:div cssStyle="height:100%">
<br />
	<s:form id="frm_choice_ktbsElement">
		<fieldset>
		<legend><s:text name="transformation.exist" /></legend>
				<table>
					<tbody>
						<tr>
							<td>
								<s:label key="list.transformation" theme="simple" />
								<s:select  id="elementkTBSSelected"  list="listTransformationKtbs" name="elementkTBSSelected" theme="simple" />
							</td>
							<td>
								<sx:div id="details" listenTopics="show_detail" theme="simple" ></sx:div>
							</td>
						</tr>
						
					</tbody>
				</table>
		</fieldset>
		<sx:div id="createForm" listenTopics="show_detail" theme="simple" ></sx:div>
	</s:form>
</s:div>
<sx:bind href="%{#d_url}" formId="frm_choice_ktbsElement" sources="elementkTBSSelected" 
	events="onchange" notifyTopics="show_detail" targets="details"  />
	<sx:bind href="%{#c_url}"  formId="frm_choice_ktbsElement" sources="elementkTBSSelected" 
		events="onchange" notifyTopics="show_detail" targets="createForm"  />
	