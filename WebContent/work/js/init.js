/**
 * 
 */

function initTrace(){	
         //TrueCoords = SVGRoot.createSVGPoint();

		var rootTrace = document.getElementById("objSvg");
	
         toolTip = document.getElementById('ToolTip');
         tipBox = document.getElementById('tipbox');
         tipText = document.getElementById('tipText');
         tipTitle = document.getElementById('tipTitle');
         tipDesc = document.getElementById('tipDesc');

         //create event for object
         //SVGRoot.addEventListener('mousemove', ShowTooltip, false);
         //SVGRoot.addEventListener('mouseout', HideTooltip, false);
         
         
         var images = rootTrace.getElementsByTagNameNS("http://www.w3.org/2000/svg",'image');

     	for(var i=0;i<images.length;i++){
     		var image = images.item(i);
     		image.addEventListener('click', function(evt){obselInfo(evt);}, false);
     	}
        
     	//document.getElementById('objSvg').addEventListener('mousedown', function(evt){down(evt);}, false);
     	//document.getElementById('objSvg').addEventListener('mouseup', function(evt){up(evt);}, false);
     	//document.getElementById('objSvg').addEventListener('mousemove', function(evt){move(evt);}, false);
}