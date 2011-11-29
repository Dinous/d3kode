<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<s:url var="realAccueil" action="realAccueil" />
<s:url var="administration" action="administration" />
<s:url var="manageBase" action="base_execute" />
<s:url var="csvUpload" action="doUpload_show" />
<s:url var="csvReadAndInjectKtbs" action="doUpload_execute" />
<s:url var="buildBaseXml" action="buildBaseXml_execute" />
<s:url var="svgCreate" action="buildSvgXml_execute" />
<s:url var="svgLoad" action="SvgLoad_execute" />
<s:url var="preCreateMethodInkTBSWHERE" action="preCreate_MethodInkTBS_execute_WHERE" />
<s:url var="preCreateMethodInkTBSCONSTRUCT" action="preCreate_MethodInkTBS_execute_CONSTRUCT" />
<s:url var="executeMethod" action="executeMethod" />
<table class="tabHead">
	<thead></thead>
	<tfoot></tfoot>
	<tbody>
		<tr>
			<td>
				<sj:a id="realAccueil" href="%{realAccueil}" targets="main">
					<s:text name="header.accueil"/>
				</sj:a>
			</td>
			<td>
				<sj:a id="manageBaseId" href="%{manageBase}" 
					targets="main">
					<s:text name="base.manage"/>
				</sj:a>
			</td>
			<td>
				<sj:a id="administration" href="%{administration}" 
					targets="main">
					<s:text name="header.manage"/>
				</sj:a>
			</td>
			<%-- <td >
				<sj:a id="csvUploadId" href="%{csvUpload}" 
					targets="main">
					<s:text name="header.upload.file.csv"/>
				</sj:a>
			</td>
		
			 <td >
				<sj:a id="csvReadAndInjectKtbsId" href="%{csvReadAndInjectKtbs}" 
					targets="main">
					<s:text name="header.read.file.csv.inject.in.ktbs"/>
				</sj:a>
			</td>
		
			 <td >
				<sj:a id="buildBaseXmlId" href="%{buildBaseXml}" 
					targets="main">
					<s:text name="header.extract.ktbs.in.xml"/>
				</sj:a>
			</td>
		
			<td >
				<sj:a id="svgCreateId" href="%{svgCreate}" 
					targets="main">
					<s:text name="header.create.file.svg.and.html"/>
				</sj:a>
			</td>
		
			 <td >
				<sj:a id="svgLoadId" href="%{svgLoad}" 
					targets="main">
					<s:text name="header.load.svg"/>
				</sj:a>
			</td> 
		
			<td >
				<sj:a id="preCreateMethodInkTBSWHEREId" href="%{preCreateMethodInkTBSWHERE}" 
					targets="main">
					<s:text name="header.create.method.in.ktbs.where"/>
				</sj:a>
			</td>
		
			<td >
				<sj:a id="preCreateMethodInkTBSCONSTRUCTId" href="%{preCreateMethodInkTBSCONSTRUCT}" 
					targets="main">
					<s:text name="header.create.method.in.ktbs.construct"/>
				</sj:a>
			</td>
		
			<td >
				<sj:a id="executeMethodId" href="%{executeMethod}" 
					targets="main">
					<s:text name="header.execute.method.in.ktbs"/>
				</sj:a>
			</td> --%>
		
			<td>
				<s:url var="logout" action="logout" />
				<s:a href="%{#logout}">
					<s:text name="header.logout"/>
				</s:a>
			</td>
		</tr>
	</tbody>
</table>