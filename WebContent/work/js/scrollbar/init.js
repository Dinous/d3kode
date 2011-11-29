/**
 * 
 */
var mapApp;
var scrolledObject1;
var scrolledObject2;

function init() {
	myMapApp = new mapApp(false,undefined);
	//scrollbar styles
	var scrollbarStyles = {"fill":"whitesmoke","stroke":"dimgray","stroke-width":1};
	var scrollerStyles = {"fill":"lightgray","stroke":"dimgray","stroke-width":1};
	var triangleStyles = {"fill":"dimgray"};
	var highlightStyles = {"fill":"dimgray","stroke":"dimgray","stroke-width":1};
	//button styles
	var buttonTextStyles = {"font-family":"Arial,Helvetica","fill":"dimgray","font-size":12};
	var buttonStyles = {"fill":"gainsboro"};
	var shadeLightStyles = {"fill":"white"};
	var shadeDarkStyles = {"fill":"dimgray"};
	//create scrolledObjects to react on scrollbar
	scrolledObject1 = new scrolledObject("content1",0,-2707,0,-220.5,"sb1horiz","sb1vert");
	//id,parentNode,x,y,width,height,startValue,endValue,initialWidthPerc,initialOffset,scrollButtonLocations,scrollbarStyles,scrollerStyles,triangleStyles,highlightStyles,functionToCall
	myMapApp.scrollbars["sb1horiz"] = new scrollbar("sb1horiz","scrollbars1",10,1000,2415,25,scrolledObject1.maxX,scrolledObject1.minX,0.2495,0,0.005,"top_bottom",scrollbarStyles,scrollerStyles,triangleStyles,highlightStyles,scrolledObject1);
	myMapApp.scrollbars["sb1vert"] = new scrollbar("sb1vert","scrollbars1",2400,10,25,977,scrolledObject1.maxY,scrolledObject1.minY,0.4756,0,0.025,"top_bottom",scrollbarStyles,scrollerStyles,triangleStyles,highlightStyles,scrolledObject1);
	//myMapApp.scrollbars["sb2horiz"] = new scrollbar("sb2horiz","scrollbars2",50.5,550.5,899,10,scrolledObject2.maxX,scrolledObject2.minX,0.2528,0,0.005,"bottom_bottom",scrollbarStyles,scrollerStyles,triangleStyles,highlightStyles,scrolledObject2);
	//myMapApp.scrollbars["sb2vert"] = new scrollbar("sb2vert","scrollbars2",950.5,350.5,10,199,scrolledObject2.maxY,scrolledObject2.minY,0.2903,0,0.02,"bottom_bottom",scrollbarStyles,scrollerStyles,triangleStyles,highlightStyles,scrolledObject2);
	//create buttons
	//myMapApp.buttons["showHideButton2"] = new button("showHideButton2","buttons",scrolledObject2,"rect","Hide Scrollbar",undefined,50,580,110,20,buttonTextStyles,buttonStyles,shadeLightStyles,shadeDarkStyles,1);		
	//myMapApp.buttons["removeButton2"] = new button("removeButton2","buttons",scrolledObject2,"rect","Remove Scrollbar",undefined,170,580,110,20,buttonTextStyles,buttonStyles,shadeLightStyles,shadeDarkStyles,1);
	/*onmousedown="scrolledObject1.handleEvent(evt)" 
		onmousemove="scrolledObject1.handleEvent(evt)" 
		onmouseup="scrolledObject1.handleEvent(evt)" 
		onmouseout="scrolledObject1.handleEvent(evt)"*/
	/*document.getElementById('content1').addEventListener('onmousedown', function(evt){onmousedown(evt);}, false);
	document.getElementById('content1').addEventListener('onmousemove', function(evt){onmousemove(evt);}, false);
	document.getElementById('content1').addEventListener('onmouseup', function(evt){onmouseup(evt);}, false);
	document.getElementById('content1').addEventListener('onmouseout', function(evt){onmouseout(evt);}, false);*/
}

//this object controls the panning and scrolling of the svg elements (panoramas)
function scrolledObject(contentId,maxX,minX,maxY,minY,sbXId,sbYId) {
	this.content = document.getElementById(contentId);
	this.transX = 0;
	this.transY = 0;
	this.maxX = maxX;
	this.minX = minX;
	this.maxY = maxY;
	this.minY = minY;
	this.sbXId = sbXId;
	this.sbYId = sbYId;
	this.panActive = false;
	this.parent = this.content.parentNode;
}
scrolledObject.prototype.scrollbarChanged = function(id,changeType,valueAbs,valuePerc) {
	if (changeType == "scrollChange" || "scrolledStep") {
		if (id.match(/horiz/gi)) {
			this.transX = valueAbs;
		}
		if (id.match(/vert/gi)) {
			this.transY = valueAbs;
		}
		this.content.setAttributeNS(null,"transform","translate("+this.transX+","+this.transY+")");
	}
};
scrolledObject.prototype.handleEvent = function(evt) {
	if (evt.type == "mousedown") {
		this.coords = myMapApp.calcCoord(evt,this.parent);
		this.panActive = true;
	}
	if (evt.type == "mousemove" && this.panActive) {
		var coords = myMapApp.calcCoord(evt,this.parent);
		this.transX += coords.x - this.coords.x;
		this.transY += coords.y - this.coords.y;
		if (this.transX < this.minX) {
			this.transX = this.minX;
		}
		if (this.transX > this.maxX) {
			this.transX = this.maxX;
		}
		if (this.transY < this.minY) {
			this.transY = this.minY;
		}
		if (this.transY > this.maxY) {
			this.transY = this.maxY;
		}
		this.content.setAttributeNS(null,"transform","translate("+this.transX+","+this.transY+")");
		//set scrollbars
		if (myMapApp.scrollbars[this.sbXId]) {
			myMapApp.scrollbars[this.sbXId].scrollToValue(this.transX);
		}
		if (myMapApp.scrollbars[this.sbYId]) {
			myMapApp.scrollbars[this.sbYId].scrollToValue(this.transY);
		}
		this.coords = coords;
	}
	if (evt.type == "mouseup" || evt.type == "mouseout") {
		this.panActive = false;
	}
	//the preventDefault() method is called because there is some evt problem in Webkit because it tries to drag the raster image to desktop or other applications
	evt.preventDefault();
	
};
