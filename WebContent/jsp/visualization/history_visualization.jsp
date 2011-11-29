<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>


<s:url id="remoteurlVisualization" action="jsontableVisualization"/>
<s:url id="editurlVisualization" action="editGridEntryVisualization"/>
<sjg:grid
	id="gridedittableVisualization"
	caption="%{getText('manage.visualization')}"
	dataType="json"
	href="%{remoteurlVisualization}"
	pager="true"
	navigator="true"
	navigatorSearch="false"
	navigatorAdd="false"
	navigatorEdit="false"
	navigatorView="false"
	navigatorDelete="true"
	navigatorDeleteOptions="{height:280,reloadAfterSubmit:true}"
	gridModel="gridedittableVisualization"
	editurl="%{editurlVisualization}"
	reloadTopics="majVisualisationHistoryTopic"
	rowList="15,30,45"
	rowNum="15"
	multiselect="true"
	onSelectRowTopics="rowselect"
	viewrecords="false"
	
>
	<sjg:gridColumn name="creationDateStr" index="creationDateStr" title="%{getText('visualization.creation.date')}" width="200"  sortable="true" search="false"  />
	<sjg:gridColumn  name="localName" index="localName" title="%{getText('visualization.localname')}" width="200" sortable="false" search="false" />
	<sjg:gridColumn name="begin" index="begin" title="%{getText('visualization.begin')}" width="75"  sortable="false" search="false" />
	<sjg:gridColumn name="end" index="end" title="%{getText('visualization.end')}" width="75"  sortable="false" search="false"  />
	<sjg:gridColumn name="scale" index="scale" title="%{getText('visualization.scale')}" width="75"  sortable="false" search="false"  />
	<sjg:gridColumn name="nbTransformation" index="nbTransformation" title="%{getText('visualization.nb.transformation')}" width="200"  sortable="false" search="false"  />
	
</sjg:grid>