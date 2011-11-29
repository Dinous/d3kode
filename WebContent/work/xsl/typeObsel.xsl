<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" exclude-result-prefixes="xsl">  
  <xsl:variable name="obselTypeDefinition">
    <obselType>
      <label>Utiliser Les Bonnes Consignes</label>
      <xlinkHref>SurveillanceOK.png</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel1" /></traceLevel>
    </obselType>
    <obselType>
      <label>Alarme Acquittee</label>
      <xlinkHref>alarmeAcquittee.png</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel0" /></traceLevel>
    </obselType>
    <obselType>
      <label>EvenementAlarme</label>
      <xlinkHref>alarme.gif</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel3" /></traceLevel>
    </obselType>
    <obselType>
      <label>EvenementProcedure</label>
      <xlinkHref>EvenementProcedure.png</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel4" /></traceLevel>
    </obselType>
    <obselType>
      <label>Invalidites</label>
      <xlinkHref>Invalidites.png</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel0" /></traceLevel>
    </obselType>
    <obselType>
      <label>Alarme</label>
      <xlinkHref>alarme.gif</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel0" /></traceLevel>
    </obselType>
    <obselType>
      <label>ActionOperateur</label>
      <xlinkHref>actionOperateur.gif</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel1" /></traceLevel>
    </obselType>
    <obselType>
      <label>ActionInstructeur</label>
      <xlinkHref>actionInstructeur.gif</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel2" /></traceLevel>
    </obselType>
    <obselType>
      <label>ActionANA</label>
      <xlinkHref>ANA.png</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel3" /></traceLevel>
    </obselType>
    <obselType>
      <label>ActionTOR</label>
      <xlinkHref>TOR.png</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel4" /></traceLevel>
    </obselType>
    <obselType>
      <label>Telephonie</label>
      <xlinkHref>Telephonie.png</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel5" /></traceLevel>
    </obselType>
    <obselType>
      <label>RepereInstructeur</label>
      <xlinkHref>RepereInstructeur.png</xlinkHref>
      <traceLevel><xsl:value-of select="$traceLevel6" /></traceLevel>
    </obselType>
  </xsl:variable>  
</xsl:stylesheet>