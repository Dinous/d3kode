<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xlink="http://www.w3.org/1999/xlink"
    xmlns:exsl="http://exslt.org/common"
    extension-element-prefixes="exsl"
  xmlns:batik="http://xml.apache.org/batik/ext"
  exclude-result-prefixes="xsl">

  <xsl:include href="typeObsel.xsl"/>

  <xsl:output method="xml" indent="yes" encoding="UTF-8"
    doctype-system="http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd"
    doctype-public="-//W3C//DTD SVG 1.0//EN" />

  <!-- <xsl:param name="nbSuperMethod" select="count(//superMethod[count(./methods/method) &gt; 0])" /> -->
  <xsl:param name="nbSuperMethod" select="1" />
  <!-- <xsl:param name="widthSVG" select="$size * $nbLevel" />
  <xsl:param name="heightSVG" select="$size * $nbLevel" />
 -->  
  <xsl:param name="timeVariable" select="50" />
  
  <xsl:param name="ecartTypeObsel" select="25" />
  <xsl:param name="traceLevel6" select="30" />
  <xsl:param name="traceLevel5" select="$traceLevel6 + $ecartTypeObsel" /><!-- 55 -->
  <xsl:param name="traceLevel4" select="$traceLevel5 + $ecartTypeObsel" /><!-- 80 -->
  <xsl:param name="traceLevel3" select="$traceLevel4 + $ecartTypeObsel" /><!-- 105 -->
  <xsl:param name="traceLevel2" select="$traceLevel3 + $ecartTypeObsel" /><!-- 130 -->
  <xsl:param name="traceLevel1" select="$traceLevel2 + $ecartTypeObsel" /><!-- 155 -->
  <xsl:param name="traceLevel0" select="$traceLevel1 + $ecartTypeObsel" /><!-- 180 -->
  <xsl:param name="picturePath" select="string('../image/')" />
  <xsl:param name="sizeObselType" select="20" />
  
  <xsl:param name="step" select="450"/>
  <xsl:param name="size" select="$step + $nbSuperMethod * 500" />
    <xsl:param name="seconde" select="1000"/>
    <xsl:param name="beginStone" select="0"/>
    <xsl:param name="scale"/>
  
  <xsl:param name="temporalScale" select="30"/>
  
  <xsl:template match="/">
  <!-- viewbox="0 0 5500 450" -->   
     <svg id="svgRoot" version="1.1" width="{400+($seconde + 100) * $scale}" height="{$size}" 
            xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:batik="http://xml.apache.org/batik/ext" 
      xml:lang="fr" xmlns="http://www.w3.org/2000/svg" >
      <title>Trace</title>
          <script type="text/ecmascript" xlink:href="../js/obselInfo.js"></script>
          <xsl:call-template name="timeLine" >
            <xsl:with-param name="xTrace" select="$size - $step" />
          </xsl:call-template>
          <xsl:call-template name="trace" >
            <xsl:with-param name="trace" select="base/traces/trace[@firstTrace = 'true']" />
            <xsl:with-param name="xTrace" select="$size - $step" />
          </xsl:call-template>
       </svg>
  </xsl:template>
  
    <xsl:template name="timeLine">
      <xsl:param name="xTrace" />
        <line id="rectTime" x1="10" y1="{225 + $xTrace}" x2="{40 + 10 + 10 +$seconde* $scale}" y2="{225 + $xTrace}" style="stroke: #CCCCCC; stroke-width: 5;" />
        
        <xsl:if test="$beginStone = 0">
          <line x1="10" y1="{225 + $xTrace}" x2="10" y2="{250 + $xTrace}" style="stroke: black; stroke-width: 2;" />
          <text x="5" y="{260 + $xTrace + 20}" style="font-family: sans-serif; font-size: 8pt;"><xsl:value-of select="$beginStone" /></text>
        </xsl:if>
        
        <xsl:if test="((ceiling((number($beginStone) div $temporalScale)) * $temporalScale) mod $temporalScale) != 0">
          <line x1="10" y1="{225 + $xTrace}" x2="10" y2="{250 + $xTrace}" style="stroke: black; stroke-width: 2;" />
          <text x="5" y="{260 + $xTrace + 20}" style="font-family: sans-serif; font-size: 8pt;"><xsl:value-of select="$beginStone" /></text>
        </xsl:if>
        
        <polyline points="{40 + 10 + $seconde * $scale} {210 + $xTrace} , {40 + 10 + 10 + $seconde * $scale} {225 + $xTrace} , {40 + 10 + $seconde * $scale} {240 + $xTrace}"/> 
        <xsl:call-template name="boucle">
          <xsl:with-param name="num"  select="ceiling((number($beginStone) div $temporalScale)) * $temporalScale"/>
          <xsl:with-param name="total"  select="number($beginStone + $seconde)"/>
          <xsl:with-param name="xTrace"  select="$xTrace"/>
        </xsl:call-template>
    </xsl:template>

  <xsl:template name="trace">
    <xsl:param name="trace" />
    <xsl:param name="xTrace"/>
    
    <rect id="rectObsel{$trace/@index}" x="10" y="{5 + $xTrace}" 
      rx="10" ry="10" 
      width="{40 + $seconde * $scale}" height="200" 
      style="stroke :blue; stroke-width:3; fill:#FFFFFF" >
      <title><xsl:value-of select="$trace/label"/></title>
    </rect>
    <xsl:for-each select="$trace/obsels/child::obsel">
      <xsl:sort select="hasEnd" order="ascending" />
      <xsl:apply-templates select=".">
        <xsl:with-param name="xTrace" select="$xTrace" />
      </xsl:apply-templates>
    </xsl:for-each>
  
    <xsl:for-each select="$trace/obsels/child::obsel">
        <xsl:sort select="hasEnd" order="ascending" />
        <xsl:variable name="label" select="./obselType/label" />
        <xsl:variable name="modelUri" select="../../model/@uri" />
        
          <xsl:call-template name="paramsObselType" >
            <xsl:with-param name="begin" select="hasEnd - $beginStone" />
            <xsl:with-param name="traceLevel" select="/base/traceModelFigures/traceModelFigure[@uri = $modelUri]/obselTypes/obselTypeFigure[label = $label]/traceLevel" />
            <xsl:with-param name="id" select="id" />
            <xsl:with-param name="xTrace" select="$xTrace" />
            <xsl:with-param name="nbAttribute" select="params" />
          </xsl:call-template> 
    </xsl:for-each>
      
      <xsl:if test="$trace/superMethod and count($trace/superMethod/methods/method) &gt; 0">
        <xsl:call-template name="transformation" >
          <xsl:with-param name="superMethod" select="$trace/superMethod" />
          <xsl:with-param name="xTransformation" select="$xTrace - 100"/>
        </xsl:call-template>
        
         <xsl:for-each select="$trace/superMethod/trace/obsels/obsel">
        <xsl:variable name="label2" select="obselType/label" />
        <xsl:variable name="hasBegin2" select="hasBegin + (hasEnd - hasBegin) div 2" />
        <xsl:variable name="hasEnd2" select="hasEnd" />
        <!-- Trait qui va de la règle à l'obsel transformé -->
        <xsl:call-template name="draw">
          <!-- Haut -->
          <xsl:with-param name="xB" select="$hasEnd2 - $beginStone" />
          <xsl:with-param name="yB" select="$xTrace - 50 - 50 - 200 - 50 + /base/traceModelFigures/traceModelFigure/obselTypes/obselTypeFigure[label = $label2]/traceLevel" />
          <!-- Bas -->
          <xsl:with-param name="xE" select="$hasBegin2  - $beginStone" />
          <xsl:with-param name="yE" select="$xTrace - 50 -25" />
        </xsl:call-template>
        </xsl:for-each>
      </xsl:if>
      
  </xsl:template>

    <xsl:template name="transformation">
      <xsl:param name="superMethod"  />
      <xsl:param name="xTransformation"  />
      <rect id="rectTransformation{$superMethod/trace/@index}" x="{10}" y="{5 + $xTransformation}" 
            width="{40 +$seconde * $scale }" height="50" 
            style="stroke:#303030; fill:#FFFFFF; stroke-dasharray: 3 2" >
          <title>Transformation : <xsl:value-of select="$superMethod/methods/description"/></title>
      </rect>
      <xsl:for-each select="$superMethod/methods/method">
        <xsl:variable name="end" select="end" />
        <xsl:variable name="ruleId" select="generate-id(.)" />
        
        <xsl:choose>
          <xsl:when test="$end = begin">
            <rect id="rectRegle" x="{($end - $beginStone) * $scale}" y="{5 + $xTransformation + 10}" 
            rx="10" ry="10" 
            width="30" height="30" 
            stroke="#303030" fill="#FFFFFF" >
            <xsl:attribute name="onclick" >methodInfo(evt, "<xsl:value-of select="$ruleId"/>" ) </xsl:attribute>
          <title>Regle : <xsl:value-of select="@label"/></title>
         </rect>
          </xsl:when>
          <xsl:otherwise>
            <rect id="rectRegle_{$ruleId}" x="{(begin - $beginStone)  * $scale}" y="{5 + $xTransformation + 10}" 
                  rx="10" ry="10" 
                  width="{25+($end - begin)  * $scale}" height="30" 
                  stroke="#303030" fill="#FFFFFF" >
              <xsl:attribute name="onclick" >methodInfo(evt, "<xsl:value-of select="$ruleId"/>" ) </xsl:attribute>
              <title>Regle : <xsl:value-of select="@label"/></title>
            </rect>
          </xsl:otherwise>
        </xsl:choose>
        <text 
          x="{10+(begin - $beginStone)  * $scale}" 
          y="{5 + $xTransformation +25}" 
          style="font-family: sans-serif; font-size: 8pt;stroke: red" >
          R
          <!-- <xsl:value-of select="position()" /> -->
        </text>
        
        <xsl:apply-templates select="lines/line">
          <xsl:with-param name="xTransform" select="$xTransformation" />
        </xsl:apply-templates>
      </xsl:for-each>
      <xsl:call-template name="trace" >
        <xsl:with-param name="trace" select="$superMethod/trace" />
        <xsl:with-param name="xTrace" select="$size - ($superMethod/trace/@index * 200 + ($superMethod/trace/@index - 1) * 150) - 250" />
      </xsl:call-template>
      
      <xsl:for-each select="$superMethod/methods/method">
        <xsl:variable name="end" select="end" />
        <xsl:variable name="ruleId" select="generate-id(.)" />
        <g id="{$ruleId}" style="display:none">
          <rect x="{(begin - $beginStone)  * $scale}" y="{5 + $xTransformation + 10}" 
              rx="10" ry="10" 
              width="{500}" height="{12*count(./description/d) + 10 }" 
              stroke="black" fill="#FFFFFF">
          </rect>
          <xsl:variable name="beginMethod" select="begin" />
          <xsl:for-each select="./description/d">
          <xsl:variable name="index" select="position()" />
            <text x="{10+10+($beginMethod - $beginStone)  * $scale}" 
            y="{5+5 + $xTransformation + 10+ 12*$index}"
                style="font-family: sans-serif; font-size: 8pt">
                <xsl:value-of select="."/>
            </text>
          </xsl:for-each>
          
          <text x="{500+(begin - $beginStone)  * $scale}" y="{5 + $xTransformation + 10}" 
                style="font-family: sans-serif; font-size: 12pt;stroke: red" 
          ><xsl:attribute name="onclick" >hide(evt, "<xsl:value-of select="$ruleId"/>" ) </xsl:attribute>X</text>
        </g>
        </xsl:for-each>
    </xsl:template>

  <xsl:template name="imageObselType">
    <xsl:param name="xlinkHref"  />
    <xsl:param name="begin"  />
    <xsl:param name="traceLevel"  />
    <xsl:param name="id"  />
    <xsl:param name="xTrace"  />
        <xsl:param name="nbAttribute" />
        <xsl:param name="heightAttributeTable" select="15 * count($nbAttribute/param) + 60" />
        <image id="{$id}" onclick="obselInfo(evt)"
          width="{$sizeObselType}px" height="{$sizeObselType}px" 
          xlink:href="{concat($picturePath,$xlinkHref)}" 
          x="{$begin * $scale}" y="{$traceLevel - 20  + $xTrace}" >
        <title><xsl:value-of select="concat(string($nbAttribute/../obselType/label), ' : ',string($nbAttribute/../label) )"></xsl:value-of></title>
        </image> 
  </xsl:template>
  
  <xsl:template name="paramsObselType">
    <xsl:param name="begin"  />
    <xsl:param name="traceLevel"  />
    <xsl:param name="id"  />
    <xsl:param name="xTrace"  />
    <xsl:param name="nbAttribute" />
    <xsl:param name="heightAttributeTable" select="15 * count($nbAttribute/param) + 60" />
    <g id="rectObsel_{$id}" style="display:none">
      <rect 
        x="{$begin * $scale + 20}" y="{$traceLevel - 20 + $xTrace}"
        rx="10" ry="10" 
        width="350" height="{$heightAttributeTable}" 
        stroke="#303030" fill="#FFFFFF" >
      </rect>
      <text x="{$begin * $scale + 350 + 20}" y="{$traceLevel - 10 + $xTrace}" 
        style="font-family: sans-serif; font-size: 12pt;stroke: red" 
      ><xsl:attribute name="onclick" >hide(evt, "rectObsel_<xsl:value-of select="$id"/>" ) </xsl:attribute>X</text>
      <xsl:for-each select="$nbAttribute/param">
        <xsl:apply-templates select="." >            
          <xsl:with-param name="xTrace" select="$xTrace" />
          <xsl:with-param name="positionX" select="$begin" />
          <xsl:with-param name="positionY" select="$traceLevel + $xTrace - 20 + position() * 18" />
        </xsl:apply-templates>
      </xsl:for-each>
    </g>
  </xsl:template>

<xsl:template match="param">
  <xsl:param name="positionX" />
  <xsl:param name="positionY" />
  
  <text x="{$positionX * $scale + 25}" y="{$positionY }" style="font-family: sans-serif; font-size: 8pt">
    <xsl:attribute name="title"><xsl:value-of select="name" /></xsl:attribute>
    <xsl:value-of select="substring(name,0,15)" />
  </text>
  <text x="{$positionX * $scale + 100 + 25}" y="{$positionY }" style="font-family: sans-serif; font-size: 8pt" >
    <xsl:value-of select="value" />
  </text>
</xsl:template>

  <xsl:template match="obsel">
    <xsl:param name="xTrace" />
    <xsl:param name="begin" select="hasEnd" />
    <xsl:param name="id" select="id" />
      <xsl:variable name="modelUri" select="../../model/@uri" />
      <xsl:variable name="label" select="./obselType/label" />
        <xsl:call-template name="imageObselType" >
          <!-- <xsl:with-param name="xlinkHref" select="exsl:node-set($obselTypeDefinition)/obselType[label = $label]/xlinkHref" /> -->
          <xsl:with-param name="xlinkHref" select="/base/traceModelFigures/traceModelFigure[@uri = $modelUri]/obselTypes/obselTypeFigure[label = $label]/xlinkHref" />
          <xsl:with-param name="begin" select="$begin - $beginStone" />
<!--           <xsl:with-param name="traceLevel" select="exsl:node-set($obselTypeDefinition)/obselType[label = $label]/traceLevel" /> -->
          <xsl:with-param name="traceLevel" select="/base/traceModelFigures/traceModelFigure[@uri = $modelUri]/obselTypes/obselTypeFigure[label = $label]/traceLevel" />
          <xsl:with-param name="id" select="$id" />
          <xsl:with-param name="xTrace" select="$xTrace" />
          <xsl:with-param name="nbAttribute" select="params" />
        </xsl:call-template>      
        
    <!-- <xsl:apply-templates select="lines/line">
      <xsl:with-param name="xTransformation" select="$xTrace" />
    </xsl:apply-templates> -->

  </xsl:template>

  <xsl:template match="line">
    <xsl:param name="xTransform" />
    <xsl:variable name="label" select="@dest" />
    <xsl:call-template name="draw">
      <!-- Haut -->
      <xsl:with-param name="xB" select="end - $beginStone" />
      <xsl:with-param name="yB" select="$xTransform + 25" />
      <!-- Bas -->
      <xsl:with-param name="xE" select="begin - $beginStone" />
      <xsl:with-param name="yE" select="/base/traceModelFigures/traceModelFigure/obselTypes/obselTypeFigure[label = $label]/traceLevel - 20  + $xTransform +100" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template name="draw">
    <xsl:param name="xE" />
    <xsl:param name="xB" />
    <xsl:param name="yE" />
    <xsl:param name="yB" />

    <line x1="{12 + $xB * $scale}" y1="{$yB}" x2="{12 + $xE * $scale}" y2="{$yE}"
      style="fill:none;stroke:black;stroke-width:2px;" />
  </xsl:template>
  
  <xsl:template name="Max">
    <xsl:param name="liste_de_nombre"/>
    <xsl:for-each select="$liste_de_nombre">
      <xsl:sort order="descending" select="."></xsl:sort>
      <xsl:if test="position()=1">
        <xsl:value-of select="."></xsl:value-of>
      </xsl:if>
    </xsl:for-each>
  </xsl:template>
    
  <xsl:template name="Min">
    <xsl:param name="liste_de_nombre"/>
    <xsl:for-each select="$liste_de_nombre">
    <xsl:sort order="ascending" select="."></xsl:sort>
    <xsl:if test="position()=1">
    <xsl:value-of select="."></xsl:value-of>
    </xsl:if>
    </xsl:for-each>
  </xsl:template>
    
    <xsl:template name="boucle">
      <xsl:param name="num" />
      <xsl:param name="total" />
      <xsl:param name="xTrace" />
      
      <xsl:if test="($num mod $temporalScale) = 0 and ($num != 0)">
        <line x1="{($num - $beginStone) * $scale +10}" y1="{225 + $xTrace}" x2="{($num - $beginStone) * $scale + 10}" y2="{250 + $xTrace}" style="stroke: black; stroke-width: 2;" />
        <text x="{($num - $beginStone)* $scale + 5}" y="{260 + $xTrace}" style="font-family: sans-serif; font-size: 8pt"><xsl:value-of select="$num"></xsl:value-of></text>
      </xsl:if>
      
      
      
      <xsl:if test="$num &lt; $total">
       <xsl:call-template name="boucle">
        <xsl:with-param name="xTrace" select="$xTrace" />
        <xsl:with-param name="num" select="$num + $temporalScale" />
        <xsl:with-param name="total" select="$total" />
       </xsl:call-template>
      </xsl:if>      
    </xsl:template>

</xsl:stylesheet>

<!-- <xsl:apply-templates select="//trace" >
        <xsl:sort order="ascending" select="@uri"/>
      </xsl:apply-templates> -->
      
      <!-- textLength="60" lengthAdjust="spacingAndGlyphs" -->
      
        <!-- width="{$maxHasEnd * 25 + 25}px" -->
    <!-- <polyline stroke-width="5" stroke="red" points="0,100 0,300" id="svgRedLine" transform="" stroke-opacity="0.5"/> -->
    <!-- <text id="ligne1" x="{($minHasBegin * 25)+150}px" y="{900 - $scaleTrace +90}px"><xsl:value-of select="label"/></text> -->