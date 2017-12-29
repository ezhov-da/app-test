<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html"/>

    <xsl:template match="/">
        <head>
        </head>
        <body>

            <xsl:for-each select="apps/applicationInstances/applicationInstance">
                <hr/>
                <b><xsl:value-of select="./nameAppUser"/></b>
                <table>
                    <tr>
                        <th>Name property</th>
                        <th>Value</th>
                    </tr>
                    <tr><td>Name application system</td><td><b><xsl:value-of select="./nameAppSystem"/></b></td></tr>
                    <tr><td>Application version</td><td><b><xsl:value-of select="./version"/></b></td></tr>
                    <tr><td>Application version</td><td><b><xsl:value-of select="./fileZip"/></b></td></tr>
                    <tr><td>Http load</td><td><b><xsl:value-of select="./httpLoad"/></b></td></tr>
                    <tr><td>Command run application</td><td><b><xsl:value-of select="./commandRunApp"/></b></td></tr>
                    <tr><td>Http image icon</td><td><b><xsl:value-of select="./httpIconImage"/></b></td></tr>
                    <tr><td>News</td><td><b><xsl:value-of select="./news"/></b></td></tr>
                    <tr><td>List of changes</td><td><b><xsl:value-of select="./listOfChanges"/></b></td></tr>
                    <tr>
                        <td>White list</td>
                        <td>
                            <xsl:for-each select="./listUsersGood/string">
                                <b><xsl:value-of select="."/></b><br/>
                            </xsl:for-each>
                        </td>
                    </tr>
                    <tr>
                        <td>Black list</td>
                        <td>
                            <xsl:for-each select="./listUsersBad/string">
                                <b><xsl:value-of select="."/></b><br/>
                            </xsl:for-each>
                        </td>
                    </tr>
                    <tr><td>Description</td><td><b><xsl:value-of select="./description"/></b></td></tr>
                </table>
                <br/>
            </xsl:for-each>
        </body>
    </xsl:template>
</xsl:stylesheet>