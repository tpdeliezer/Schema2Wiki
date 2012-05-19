<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="" xmlns:xsd="http://www.w3.org/2001/XMLSchema" version="2.0">
	<xsl:output method="text" />
	<xsl:param name="namespace" />
	<xsl:param name="schemaName" />
	<xsl:template match="/">
		<xsl:text>!!Namespace!!&#10;</xsl:text>
		<xsl:value-of select="$namespace" />
		<xsl:text>&#10;!!Global Elements!!&#10;&#10;</xsl:text>
		<xsl:for-each select="/xsd:schema/xsd:element">
			<xsl:text>   * [[</xsl:text>
			<xsl:value-of select="./@name" />
			<xsl:text>]]&#10;</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>