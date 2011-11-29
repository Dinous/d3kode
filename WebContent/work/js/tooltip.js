/**
 * http://svg-whiz.com/svg/Tooltip2.svg
 */
      /*var SVGDocument = null;
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
      var titleDesc = '';*/

      /*function GetTrueCoords(evt){
         // find the current zoom level and pan setting, and adjust the reported
         //    mouse position accordingly
         var newScale = SVGRoot.currentScale;
         var translation = SVGRoot.currentTranslate;
         TrueCoords.x = (evt.clientX - translation.x)/newScale;
         TrueCoords.y = (evt.clientY - translation.y)/newScale;
      };*/

      function HideTooltip( evt ){
         toolTip.setAttributeNS(null, 'visibility', 'hidden');
      };

      function ShowTooltip( evt ){
         //GetTrueCoords( evt );

         var tipScale = 1/1;
         var textWidth = 0;
         var tspanWidth = 0;
         var boxHeight = 20;

         tipBox.setAttributeNS(null, 'transform', 'scale(' + tipScale + ',' + tipScale + ')' );
         tipText.setAttributeNS(null, 'transform', 'scale(' + tipScale + ',' + tipScale + ')' );

         var titleValue = '';
         var descValue = '';
         var targetElement = evt.target;
         if ( lastElement != targetElement ){
            var targetTitle = targetElement.getElementsByTagName('title').item(0);
            if ( targetTitle ){
               // if there is a 'title' element, use its contents for the tooltip title
               titleValue = targetTitle.firstChild.nodeValue;
            }

            var targetDesc = targetElement.getElementsByTagName('desc').item(0);
            if ( targetDesc ){
               // if there is a 'desc' element, use its contents for the tooltip desc
               descValue = targetDesc.firstChild.nodeValue;

               if ( '' == titleValue )
               {
                  // if there is no 'title' element, use the contents of the 'desc' element for the tooltip title instead
                  titleValue = descValue;
                  descValue = '';
               }
            }

            // if there is still no 'title' element, use the contents of the 'id' attribute for the tooltip title
            if ( '' == titleValue )
            {
               titleValue = targetElement.getAttributeNS(null, 'id');
            }

            // selectively assign the tooltip title and desc the proper values,
            //   and hide those which don't have text values
            //
            var titleDisplay = 'none';
            if ( '' != titleValue )
            {
               tipTitle.firstChild.nodeValue = titleValue;
               titleDisplay = 'inline';
            }
            tipTitle.setAttributeNS(null, 'display', titleDisplay );


            var descDisplay = 'none';
            if ( '' != descValue ){
               tipDesc.firstChild.nodeValue = descValue;
               descDisplay = 'inline';
            }
            tipDesc.setAttributeNS(null, 'display', descDisplay );
         }

         // if there are tooltip contents to be displayed, adjust the size and position of the box
         if ( '' != titleValue ){
            var xPos = evt.clientX  - 35;
            var yPos = evt.clientY  - 35;

            //return rectangle around text as SVGRect object
            var outline = tipText.getBBox();
            tipBox.setAttributeNS(null, 'width', Number(outline.width) + 10);
            tipBox.setAttributeNS(null, 'height', Number(outline.height) + 10);

            // update position
            toolTip.setAttributeNS(null, 'transform', 'translate(' + xPos + ',' + yPos + ')');
            toolTip.setAttributeNS(null, 'visibility', 'visible');
         }
      };