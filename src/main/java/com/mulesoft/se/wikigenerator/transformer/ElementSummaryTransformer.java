package com.mulesoft.se.wikigenerator.transformer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jaxen.JaxenException;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.XPath;
import org.jaxen.dom4j.Dom4jXPath;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import com.mulesoft.se.wikigenerator.model.ElementSummary;

public class ElementSummaryTransformer extends AbstractMessageTransformer {
	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		String wiki = null;
		try {
			ElementSummary elementSummary = (ElementSummary) message.getPayload();
			Element element = elementSummary.getElementDocument().getRootElement();
			String name = element.attributeValue("name");
			String documentation = getDocumentation(element);
			String complexType = null;
			if (elementSummary.getComplexTypeDocument() == null) {
				Element root = elementSummary.getElementDocument().getRootElement();
				//Document document = getChildElement(root, "/xsd:element/xsd:complexType").getDocument();
				complexType = getXmlText(elementSummary.getElementDocument());
			} else {
				complexType = getXmlText(elementSummary.getComplexTypeDocument());
			}
			String template= "!!Documentation!!\n[DOCUMENTATION]\n!!Type!!\n[TYPE]";
			wiki = template.replace("[DOCUMENTATION]", documentation)
			    .replace("[TYPE]", "[<java>]" + complexType + "[</java>]");
			message.setInvocationProperty("elementName", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wiki;
	}
	
	private String getDocumentation(Element element) throws JaxenException {
		String documentation = null;
		Map map = new HashMap();
	  	map.put("xsd", "http://www.w3.org/2001/XMLSchema");
	  	XPath xpath = new Dom4jXPath( "/xsd:element/xsd:annotation/xsd:documentation");
	  	xpath.setNamespaceContext(new SimpleNamespaceContext(map));
	  	List nodes = xpath.selectNodes(element);
	  	if (nodes.isEmpty()) {
	  		documentation = "";
	  	} else {
	  		documentation = ((Node) nodes.get(0)).getText();
	  	}
	  	return documentation;
	}

	private Element getChildElement(Element element, String xpathText) throws JaxenException {
		Element documentation = null;
		Map map = new HashMap();
	  	map.put("xsd", "http://www.w3.org/2001/XMLSchema");
	  	XPath xpath = new Dom4jXPath(xpathText);
	  	xpath.setNamespaceContext(new SimpleNamespaceContext(map));
	  	List nodes = xpath.selectNodes(element);
	  	if (nodes.isEmpty()) {
	  		// skip
	  	} else {
	  		documentation = ((Element) nodes.get(0));
	  	}
	  	return documentation;
	}
	
	private String getXmlText(Document document) throws IOException {
		String text = null;
		StringWriter stringWriter = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
	    XMLWriter writer = new XMLWriter( stringWriter, format );
	    writer.write( document );
	    text = stringWriter.toString();
	    return text;
	}

}
