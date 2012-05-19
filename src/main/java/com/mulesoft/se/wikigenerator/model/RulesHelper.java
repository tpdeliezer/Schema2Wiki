package com.mulesoft.se.wikigenerator.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultDocument;
import org.jaxen.JaxenException;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.XPath;
import org.jaxen.dom4j.Dom4jXPath;

public class RulesHelper {

	private static String getXmlText(Document document) throws IOException {
		String text = null;
		StringWriter stringWriter = new StringWriter();
		OutputFormat format = OutputFormat.createCompactFormat();
	    XMLWriter writer = new XMLWriter( stringWriter, format );
	    writer.write( document );
	    text = stringWriter.toString();
	    return text;
	}
	
	public static ElementInfo summarise(Attribute elementNameAttribute, DefaultDocument elementDocument, DefaultDocument complexTypeDocument) throws JaxenException, IOException {
		ElementInfo elementInfo = new ElementInfo();
		elementInfo.setName(elementNameAttribute.getValue());
		Map map = new HashMap();
	  	map.put("xsd", "http://www.w3.org/2001/XMLSchema");
	  	XPath xpath = new Dom4jXPath( "/xsd:element/xsd:annotation/xsd:documentation");
	  	xpath.setNamespaceContext(new SimpleNamespaceContext(map));
	  	String documentation = ((Node) xpath.selectNodes(elementDocument).get(0)).getText();
		elementInfo.setDocumentation(documentation);
		elementInfo.setComplexTypeXml(getXmlText(complexTypeDocument));
		return elementInfo;
	}
}
