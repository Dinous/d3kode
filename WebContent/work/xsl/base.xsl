<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:batik="http://xml.apache.org/batik/ext"
	exclude-result-prefixes="xsl">
	<xsl:output method="xml" indent="yes" encoding="UTF-8"
		doctype-system="http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd"
		doctype-public="-//W3C//DTD SVG 1.0//EN" />

	<xsl:param name="nbLevel" select="count(/base/traces/trace)" />
	<xsl:param name="size" select="1000" />

	<xsl:param name="widthSVG" select="$size * $nbLevel" />
	<xsl:param name="heightSVG" select="$size * $nbLevel" />
	
	<xsl:param name="timeVariable" select="50" />
	
	<xsl:param name="traceCoeficient" select="300" />
	
	<xsl:param name="traceLevel6" select="$nbLevel*$traceCoeficient + 45" />
	<xsl:param name="traceLevel5" select="$nbLevel*$traceCoeficient + 90" />
	<xsl:param name="traceLevel4" select="$nbLevel*$traceCoeficient +145" />
	<xsl:param name="traceLevel3" select="$nbLevel*$traceCoeficient +195" />
	<xsl:param name="traceLevel2" select="$nbLevel*$traceCoeficient +245" />
	<xsl:param name="traceLevel1" select="$nbLevel*$traceCoeficient +295" />
	<xsl:param name="traceLevel0" select="$nbLevel*$traceCoeficient +345" />
	<xsl:param name="picturePath" select="string('../image/')" />
	<xsl:param name="sizeObselType" select="45" />
	
	<xsl:template match="/">
	<svg id="svgRoot" xmlns="http://www.w3.org/2000/svg" 
	xmlns:xlink="http://www.w3.org/1999/xlink" 
	xmlns:batik="http://xml.apache.org/batik/ext" width="100%" height="100%" 
	viewBox="0 0 2500 1200" onload="init()">
	<title>Base</title>
		<script type="text/ecmascript" xlink:href="../js/scrollbar/mapApp.js"></script>
		<script type="text/ecmascript" xlink:href="../js/scrollbar/helper_functions.js"></script>
		<script type="text/ecmascript" xlink:href="../js/scrollbar/timer.js"></script>
		<script type="text/ecmascript" xlink:href="../js/scrollbar/scrollbar.js"></script>
		<script type="text/ecmascript" xlink:href="../js/scrollbar/init.js"></script>
		
	<!-- viewbox="0 0 {$widthSVG} {$heightSVG}" -->
		<svg id="objSvg" viewbox="0 0 2000 1100" version="1.1" width="2400px" height="{$widthSVG}px" 
			xml:lang="fr" xmlns="http://www.w3.org/2000/svg" onload="initTrace()" >
			<title>Trace</title>
			
			<script type="text/ecmascript" xlink:href="../js/obselInfo.js"></script>
			<script type="text/ecmascript" xlink:href="../js/init.js"></script>
			<!-- <script type="text/ecmascript" xlink:href="./work/js/svgRedLine.js"/> -->
			<!-- <script type="text/ecmascript" xlink:href="./work/js/init.js"/>-->
			<g id="content1" cursor="move" batik:static="true" >
			
			<!-- <xsl:apply-templates select="//trace" >
				<xsl:sort order="ascending" select="@uri"/>
			</xsl:apply-templates> -->
			<xsl:apply-templates select="base/traces/trace" />
			</g>
			<g id='ToolTip' opacity='0.8' visibility='hidden' pointer-events='none'>
		      <rect id='tipbox' x='0' y='5' width='88' height='20' rx='2' ry='2' fill='white' stroke='black'/>
		      <text id='tipText' x='5' y='20' font-family='Arial' font-size='12'>
		         <tspan id='tipTitle' x='5' font-weight='bold'>&#160;</tspan>
		         <tspan id='tipDesc' x='5' dy='15' fill='blue'>&#160;</tspan>
		      </text>
   			</g>
		</svg>
		<g id="scrollbars1" />
		</svg>
	</xsl:template>

	<xsl:template match="trace">
		<!-- variable scaleTrace qui permet de créer un cadre pour une trace en décalage les uns aux autres -->
		<xsl:param name="scaleTrace" select="number(count(methods/method[not(contains(label/text(), 'fusion'))])) * 450" />
		<!-- cadre où sera dessiné la trace -->
		
		<xsl:param name="minHasBegin" select="obsels/obsel[position() = 1]/hasBegin" />
		<xsl:param name="maxHasEnd" select="obsels/obsel[position() = last()]/hasEnd" />
		<!-- width="{$maxHasEnd * 25 + 25}px" -->
		
		<rect id="rectObsel" x="10px" y="{$nbLevel*$traceCoeficient - $scaleTrace}px" 
			rx="10" ry="10" 
			width="{1500 * 2}" height="350px" 
			stroke="#303030" fill="#FFFFFF" >
			<title><xsl:value-of select="label"/></title>
		</rect>
		<!-- <polyline stroke-width="5" stroke="red" points="0,100 0,300" id="svgRedLine" transform="" stroke-opacity="0.5"/> -->
		<!-- <text id="ligne1" x="{($minHasBegin * 25)+150}px" y="{900 - $scaleTrace +90}px"><xsl:value-of select="label"/></text> -->
		
    	<xsl:for-each select="obsels/child::obsel">
    		<xsl:sort select="hasEnd" order="ascending" />
    		<xsl:apply-templates select=".">
    			<xsl:with-param name="scaleTrace" select="$scaleTrace" />
    		</xsl:apply-templates>
    	</xsl:for-each>
		
	</xsl:template>

	<xsl:template name="imageObselType">
		<xsl:param name="xlinkHref"  />
		<xsl:param name="begin"  />
		<xsl:param name="traceLevel"  />
		<xsl:param name="id"  />
		<xsl:param name="scaleTrace"  />
		<image id="{$id}" onclick="obselInfo(evt)"
			width="{$sizeObselType}px" height="{$sizeObselType}px" 
			xlink:href="{concat($picturePath,$xlinkHref)}" 
		  	x="{$begin*$timeVariable}px" y="{$traceLevel - $scaleTrace - 50}px" /> 
	</xsl:template>

	<xsl:template match="obsel">
		<xsl:param name="scaleTrace" />
		<xsl:param name="begin" select="hasBegin" />
		<xsl:param name="id" select="id" />
		
		<xsl:choose>
			<xsl:when test="contains(./obselType/@id,'Alarme')">
				<xsl:call-template name="imageObselType" >
					<xsl:with-param name="xlinkHref" select="string('alarme.gif')" />
					<xsl:with-param name="begin" select="$begin" />
					<xsl:with-param name="traceLevel" select="$traceLevel0" />
					<xsl:with-param name="id" select="$id" />
					<xsl:with-param name="scaleTrace" select="$scaleTrace" />
				</xsl:call-template>			
			</xsl:when>
			<xsl:when test="contains(./obselType/@id, 'ActionOperateur')">
				<xsl:call-template name="imageObselType" >
					<xsl:with-param name="xlinkHref" select="string('actionOperateur.gif')" />
					<xsl:with-param name="begin" select="$begin" />
					<xsl:with-param name="traceLevel" select="$traceLevel1" />
					<xsl:with-param name="id" select="$id" />
					<xsl:with-param name="scaleTrace" select="$scaleTrace" />
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains(./obselType/@id, 'ActionInstructeur')">
				<xsl:call-template name="imageObselType" >
					<xsl:with-param name="xlinkHref" select="string('actionInstructeur.gif')" />
					<xsl:with-param name="begin" select="$begin" />
					<xsl:with-param name="traceLevel" select="$traceLevel2" />
					<xsl:with-param name="id" select="$id" />
					<xsl:with-param name="scaleTrace" select="$scaleTrace" />
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains(./obselType/@id, 'ActionANA')">
				<xsl:call-template name="imageObselType" >
					<xsl:with-param name="xlinkHref" select="string('ANA.png')" />
					<xsl:with-param name="begin" select="$begin" />
					<xsl:with-param name="traceLevel" select="$traceLevel3" />
					<xsl:with-param name="id" select="$id" />
					<xsl:with-param name="scaleTrace" select="$scaleTrace" />
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains(./obselType/@id, 'ActionTOR')">
				<xsl:call-template name="imageObselType" >
					<xsl:with-param name="xlinkHref" select="string('TOR.png')" />
					<xsl:with-param name="begin" select="$begin" />
					<xsl:with-param name="traceLevel" select="$traceLevel4" />
					<xsl:with-param name="id" select="$id" />
					<xsl:with-param name="scaleTrace" select="$scaleTrace" />
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains(./obselType/@id, 'Telephonie')">
				<xsl:call-template name="imageObselType" >
					<xsl:with-param name="xlinkHref" select="string('Telephonie.png')" />
					<xsl:with-param name="begin" select="$begin" />
					<xsl:with-param name="traceLevel" select="$traceLevel5" />
					<xsl:with-param name="id" select="$id" />
					<xsl:with-param name="scaleTrace" select="$scaleTrace" />
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains(./obselType/@id, 'RepereInstructeur')">
				<xsl:call-template name="imageObselType" >
					<xsl:with-param name="xlinkHref" select="string('RepereInstructeur.png')" />
					<xsl:with-param name="begin" select="$begin" />
					<xsl:with-param name="traceLevel" select="$traceLevel6" />
					<xsl:with-param name="id" select="$id" />
					<xsl:with-param name="scaleTrace" select="$scaleTrace" />
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>

		<xsl:apply-templates select="lines/line">
			<xsl:with-param name="scaleTrace" select="$scaleTrace" />
		</xsl:apply-templates>

	</xsl:template>

	<xsl:template match="line">
		<xsl:param name="scaleTrace" />
		<xsl:choose>
			<xsl:when test="contains(@dest,'ActionOperateur') and contains(@src,'ActionOperateur')">
				<xsl:call-template name="draw">
					<xsl:with-param name="xE" select="number(begin) * $timeVariable +18" />
					<xsl:with-param name="xB" select="number(../../hasBegin)*$timeVariable + 18" />
					<xsl:with-param name="yB" select="$traceLevel2 - $scaleTrace  + 40" />
					<xsl:with-param name="yE" select="$traceLevel0 - 40  - $sizeObselType" />
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains(@dest,'ActionOperateur')">
				<xsl:call-template name="draw">
					<xsl:with-param name="xE" select="number(begin) * $timeVariable +18" />
					<xsl:with-param name="xB" select="number(../../hasBegin)*$timeVariable + 18" />
					<xsl:with-param name="yB" select="$traceLevel2 - $scaleTrace " />
					<xsl:with-param name="yE" select="$traceLevel0 - 40  - $sizeObselType" />
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains(@dest,'ActionInstructeur')">
				<xsl:call-template name="draw">
					<xsl:with-param name="xE" select="number(begin) * $timeVariable +18" />
					<xsl:with-param name="xB" select="number(../../hasBegin)*$timeVariable + 18" />
					<xsl:with-param name="yB" select="$traceLevel2 - $scaleTrace " />
					<xsl:with-param name="yE" select="$traceLevel0 - 40 - 40 - $sizeObselType" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="draw">
					<!-- Haut -->
					<xsl:with-param name="xB" select="number(../../hasBegin)*$timeVariable + 18" />
					<xsl:with-param name="yB" select="$traceLevel2 - $scaleTrace + 40 + 40 + 10" />
					<!-- Bas -->
					<xsl:with-param name="xE" select="number(begin) * $timeVariable +18" />
					<xsl:with-param name="yE" select="$traceLevel0 - $sizeObselType" />
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="draw">
		<xsl:param name="xE" />
		<xsl:param name="xB" />
		<xsl:param name="yE" />
		<xsl:param name="yB" />

		<line x1="{$xB}" y1="{$yB}" x2="{$xE}" y2="{$yE}"
			style="fill:none;stroke:black;stroke-width:2px;" />

		<!-- <text id="ligne1" x="{$xE}px" y="{$yE}px">
			(<xsl:value-of select="$xE" />, <xsl:value-of select="$yE" />)
		</text>
		<text id="ligne2" x="{$xB}px" y="{$yB}px">
			(<xsl:value-of select="$xB" />, <xsl:value-of select="$yB" />)
		</text> -->
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
</xsl:stylesheet>