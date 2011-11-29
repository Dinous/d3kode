<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" exclude-result-prefixes="xsl">
	<xsl:output method="xml" indent="yes" encoding="UTF-8"/>

	<xsl:template match="/">
		<tables>
			<xsl:apply-templates select="/base/traces/trace/obsels/obsel" />
		</tables>
	</xsl:template>

	<xsl:template match="obsel">
		<table>
		<xsl:attribute name="id">table_<xsl:value-of select="./id" /></xsl:attribute>
		<xsl:attribute name="class">tip</xsl:attribute>
			<thead>
				<tr>
					<td colspan="2">
					<a href="javascript:void(0)">
						<xsl:attribute name="onmousedown">document.getElementById('table_<xsl:value-of select="./id" />').style.display = 'none';</xsl:attribute>
						Close</a>
					</td>
				</tr>
			</thead>
			<tbody>
            <xsl:variable name="modelUri" select="ancestor::trace/model/@uri" />
				<xsl:for-each select="node()" >
					<xsl:choose>
						<xsl:when test="name() = ''">
						</xsl:when>
						<xsl:when test="name() = 'id'">
						</xsl:when>
						<xsl:when test="name() = 'lines'">
						</xsl:when>
						<xsl:when test="name() = 'params'">
							<xsl:apply-templates select="child::node()" />
						</xsl:when>
                        <xsl:when test="name() = 'obselType'">
                          <tr>
                            <td><xsl:value-of select="name()" /></td>
                            <td><xsl:value-of select="substring-after(@id, $modelUri)" /></td>
                          </tr>
                        </xsl:when>
						<xsl:otherwise>
							<tr>
								<td><xsl:value-of select="name()" /></td>
								<td><xsl:value-of select="normalize-space(.)" /></td>
							</tr>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</tbody>
		</table>
	</xsl:template>
	
	<xsl:template match="param">
    <xsl:variable name="modelUri" select="ancestor::trace/model/@uri" />
		<tr>			
			<td><xsl:value-of select="substring-after(name/text(), $modelUri)" /></td>
			<td><xsl:value-of select="value" /></td>
		</tr>
	</xsl:template>
</xsl:stylesheet>