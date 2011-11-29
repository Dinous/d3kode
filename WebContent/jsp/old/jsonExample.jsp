<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
plop
<div id="div1">Div 1</div>
    <s:url id="ajaxTest" action="/ajax/jsonExample"/>

    <sj:a id="link1" href="%{ajaxTest}" targets="div1">
      Update Content
    </sj:a>