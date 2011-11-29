/**
 * 
 */
var documentHTML = this.parent.document;
var SVGDocument = null;
var SVGRoot = null;
var SVGViewBox = null;
var svgns = 'http://www.w3.org/2000/svg';
var xlinkns = 'http://www.w3.org/1999/xlink';
var toolTip = null;
var TrueCoords = null;
var tipBox = null;
var tipText = null;
var tipTitle = null;
var tipDesc = null;

var lastElement = null;
var titleText = '';
var titleDesc = '';