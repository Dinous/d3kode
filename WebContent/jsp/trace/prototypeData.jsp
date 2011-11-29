<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:iterator value="traceModelSelected.obselTypes">
	<s:property value="label" />
	<br />
	<table class="border">
		<thead>
			<tr>
				<s:iterator value="getAttributeFromObselType(traceModelSelected, uri)">
					<td>
						<s:if test="label != null">
							<s:property value="label" />
						</s:if>
						<s:else>
							<s:property value="localName" />
						</s:else>
					</td>
				</s:iterator>
			</tr>
		</thead>
		<tfoot></tfoot>
		<tbody>
			<tr>
				<s:iterator value="getAttributeFromObselType(traceModelSelected, uri)">
					<td>
						<s:iterator value="ranges">
							<s:property value="[0].uri.substring([0].uri.indexOf('#'))" />
						</s:iterator>
					</td>
				</s:iterator>
			</tr>
		</tbody>
	</table>
	<br/>
</s:iterator>