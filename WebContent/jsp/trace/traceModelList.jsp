<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>

<sj:tabbedpanel id="listModelPrototype">
	<s:iterator value="traceModels" status="traceModel">
    	<sj:tab id="tabList%{#traceModel.index}"
    		target="tModelPrototype%{#traceModel.index}" 
    		label="%{label}"/>
    
    	<s:url var="urlPrototypeData"  action="prototype_data">
    		<s:param name="modelTraceSelectedUri" value="%{uri}" />
    	</s:url>
		<sj:div 
			id="tModelPrototype%{#traceModel.index}" 
			href="%{urlPrototypeData}"  />
	</s:iterator>
</sj:tabbedpanel>