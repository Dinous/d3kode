<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:svg="http://www.w3.org/2000/svg" >
    <head>
    	<script type="text/javascript" src="${pageContext.request.contextPath}/struts/utils.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/struts/xhtml/validation.js"></script>
    	<sj:head debug="true" 
    		compressed="false" 
    		scriptPath="/mTraceProject/jquery/"
    		defaultIndicator="myDefaultIndicator" 
    		defaultLoadingText="Please wait ..."
    		ajaxcache="false"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <title>
               toto
         </title>
         <link href="<s:url value="/css/index.css"/>" rel="stylesheet" type="text/css"/>
         <script type="text/javascript" >
         	var mouse_x = 0;
 			var mouse_y = 0;
         	function register_position(){
        		document.onmousemove = position;
        	}
         	function position(evt){
         		if(!evt) evt = window.event;	
         		mouse_x = evt.clientX;
         		mouse_y = evt.clientY;
         	}
         	function printPosition(){
         		alert(mouse_x + " "+ mouse_y);
         	}
         </script>
    </head>
   <body onload="javascript:register_position()">
        <table class="layout">
        	<thead>
	            <tr>
	                <td>
	                    header
	                </td>
	            </tr>
            </thead>
            <tbody>
	            <tr>
	                <td class="body">
	                	<s:actionmessage />
	                	<s:actionerror />
						<s:fielderror />
<div id="col3">
  <div id="col3_content" class="clearfix">
	<h2>Select Box with AJAX Content</h2>
	<p class="text">
	    A Select Box with remote json content. This Component works together with the Struts2 JSON Plugin.
	</p>
    <s:form id="formSelectOne" action="echo" theme="simple" cssClass="yform">
        <fieldset>
            <legend>AJAX Form populated by a String List</legend>
	        <div class="type-text">
	            <label for="echo">Echo: </label>
				<s:url id="remoteurl" action="jsonsample" namespace="/ajax" /> 
				<sj:select 
					href="%{remoteurl}" 
					id="echo" 
					name="echo" 
					list="languageList" 
					emptyOption="true" 
					headerKey="-1" 
					headerValue="Please Select a Language"
				/>
	        </div>
	        <div class="type-button">
				<sj:submit 
					targets="result1" 
					value="AJAX Submit" 
					indicator="indicator" 
					button="true"
				/>
				<img id="indicator" src="images/indicator.gif" alt="Loading..." style="display:none"/>
	        </div>
        </fieldset>
    </s:form>
 
    <strong>Result Div 1 :</strong>
	<div id="result1" class="result ui-widget-content ui-corner-all">Submit form above.</div>
	
    <s:form id="formSelectTwo" action="echo" theme="simple" cssClass="yform">
        <fieldset>
            <legend>AJAX Form populated by a Map</legend>
	        <div class="type-text">
	            <label for="echo">Echo: </label>
				<s:url id="remoteurl" action="jsonsample"/> 
				<sj:select 
					href="%{remoteurl}" 
					id="echo2" 
					name="echo" 
					list="languageMap" 
					emptyOption="true" 
					headerKey="-1" 
					headerValue="Please Select a Language"
				/>
	        </div>
	        <div class="type-button">
				<sj:submit 
					targets="result2" 
					value="AJAX Submit" 
					indicator="indicator"
					button="true"
				/>
				<img id="indicator" 
					src="images/indicator.gif" 
					alt="Loading..." style="display:none"
				/>
	        </div>
        </fieldset>
    </s:form>

    <strong>Result Div 2 :</strong>
	<div id="result2" class="result ui-widget-content ui-corner-all">Submit form above.</div>
	
    <s:form id="formSelectThree" action="echo" theme="simple" cssClass="yform">
        <fieldset>
            <legend>AJAX Form populated by a List with Objects</legend>
	        <div class="type-text">
	            <label for="echo">Echo: </label>
				<s:url id="remoteurl" action="jsonsample"/> 
				<sj:select 
					href="%{remoteurl}" 
					id="echo3" 
					name="echo" 
					list="languageObjList" 
					listKey="myKey" 
					listValue="myValue" 
					emptyOption="true" 
					headerKey="-1" 
					headerValue="Please Select a Language"
				/>
	        </div>
	        <div class="type-button">
				<sj:submit 
					targets="result3" 
					value="AJAX Submit" 
					indicator="indicator"
					button="true"
				/>
				<img id="indicator" 
					src="images/indicator.gif" 
					alt="Loading..." 
					style="display:none"/>
	        </div>
        </fieldset>
    </s:form>

    <strong>Result Div 3 :</strong>
	<div id="result3" class="result ui-widget-content ui-corner-all">Submit form above.</div>
  </div>
  <!-- IE Column Clearing -->
  <div id="ie_clearing"> &#160; </div>
</div>
</td>
</tr>
</tbody>
</table>
</body>
</html>
