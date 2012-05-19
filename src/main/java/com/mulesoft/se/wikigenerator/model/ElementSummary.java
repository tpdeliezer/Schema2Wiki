package com.mulesoft.se.wikigenerator.model;

import org.dom4j.Document;

public class ElementSummary {

	private Document elementDocument;
	
	private Document complexTypeDocument;
	
	public ElementSummary(Document elementDocument, Document complexTypeDocument) {
		this.elementDocument = elementDocument;
		this.complexTypeDocument = complexTypeDocument;
	}

	public Document getElementDocument() {
		return elementDocument;
	}

	public Document getComplexTypeDocument() {
		return complexTypeDocument;
	}
}
