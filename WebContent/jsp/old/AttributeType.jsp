<%@ taglib prefix ="s" uri="/struts-tags" %>

<s:label key="list.trace.obsel.attribute.type" theme="simple" />
<s:select 
		id="attributeTypeSelected" 
		list="listAttributeType"  
		listValue="uri" 
		listKey="localName"
		headerKey="null"
		headerValue="%{getText('choice.trace.obsel.attribute.type')}"
	 	name="attributeTypeSelected"
	 	theme="simple"  />