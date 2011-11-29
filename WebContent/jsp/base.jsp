<%@page import="org.apache.struts2.ServletActionContext"%>
<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<script type="text/javascript">
function cache_menu(){
	 var menu =document.getElementById("sideMenu");
     var myContent =document.getElementById("content");
     var retractbutton =document.getElementById("retractButtonContent");
     if(menu.style.display=="none"){
           menu.style.display="";
           myContent.style.width="80%";
         //ne pas supprimer l espace apres le <
           retractbutton.innerHTML='&lt;&nbsp;';
     }
     else{
         menu.style.display="none";
         myContent.style.width="100%";
         //ne pas supprimer l espace apres le >
         retractbutton.innerHTML='&gt;&nbsp;';
     }
}

function loadPicture(el, imgTarget){
	var img = document.getElementById(imgTarget);
	img.src = "work/image/"+el.options[el.selectedIndex].value;
}
</script>

<div id="sideMenu" style="position:relative;float:left" >
	
	<sj:accordion 
	   	id="accordionBase" 
	   	list="accordion"
	   	collapsible="true" >
	   	<sj:accordionItem title="%{getText('base.management')}" cssStyle="height:100%">
	   		<s:if test="%{!ktbsBases.isEmpty()}">
	   		<s:url var="managementModel" action="management_model" />
	   		<sj:a targets="content" button="false" href="%{managementModel}">
	   			<s:text name="trace.model.manage" />
	   		</sj:a>
	   		<br />
	   		<br />
	   		<s:url var="managementTranceFormation" action="management_transformation" />
	   		<sj:a targets="content" button="false" href="%{managementTranceFormation}">
	   			<s:text name="transformation.manage" />
	   		</sj:a>	
	   		<br/>
	   		<br/>
	   		<s:url var="firstTrace" action="firstTrace" />
	   		<sj:a targets="content" button="false" href="%{firstTrace}">
	   			<s:text name="trace.new.first" />
	   		</sj:a>				   		
	   		<br />
	   		<br />
	   		<s:url var="calculatedTrace" action="calculatedTrace" />
	   		<sj:a button="false" targets="content" href="%{calculatedTrace}">
	   			<s:text name="trace.new" />
	   		</sj:a>
	   		<br />
	   		<br />
	   		<s:url var="visualization" action="visualization_choice" />
	   		<sj:a button="false" targets="content" href="%{visualization}">
	   			<s:text name="trace.visualization" />
	   		</sj:a>
	   		<br /><br /><br /><br /><br /><br />
		</s:if>
	   	</sj:accordionItem>
	 </sj:accordion>
				 
</div>
<div id="retractButton" style="position:relative;float:left" onclick="cache_menu()">
   <div id="retractButtonContent" style="background-color: #c9ebe3;">&lt;</div>
</div>
				<sj:div id="content" style="position:relative;float:left;width:80%" >
					<s:if test="currentBase.storedTraces.isEmpty()">
						<s:text name="base.first.trace.none" />
					</s:if>
					<s:else>
					<table class="border">
						<thead>
							<tr>
								<td><s:text name="storedTrace.id" /></td>
								<td><s:text name="storedTrace.creationDate" /></td>
								<td><s:text name="storedTrace.labels" /></td>
								<td><s:text name="storedTrace.traceBeginDT" /></td>
								<td><s:text name="storedTrace.traceEndDT" /></td>
								<td><s:text name="storedTrace.traceModel" /></td>
								<%-- <td><s:text name="storedTrace.obsels.size" /></td> --%>
								<td><s:text name="storedTrace.transformedTraces.size" /></td>
							</tr>
						</thead><tfoot></tfoot><tbody>
						<s:iterator value="currentBase.storedTraces" status="svg">
							<s:url var="urlCreateSvg" action="buildSvgForOneTrace" >
								<s:param name="traceUri" value="uri" />
							</s:url>
							<tr>
								<td><s:property value="localName" /></td>
								<td><s:property value="getDateStr(localName)" /></td>
								<td><s:property value="getLabel(labels)" /></td>
								<td><s:property value="getDateStrFromDT(traceBeginDT)" /></td>
								<td><s:property value="getDateStrFromDT(traceEndDT)" /></td>
								<td><s:property value="getLabel(traceModel.labels)" /></td>
								<%-- <td><s:property value="getObsels().size()" /></td> --%>
								<td><s:property value="getTransformedTraces().size()" /></td>
							</tr>
							<%-- <sj:div
								id="divSvg%{#svg.index}"
								indicator="indicator"
								href="%{urlCreateSvg}"
							/> --%>
						</s:iterator>
						
							</tbody>
							</table>
					</s:else>
				</sj:div>