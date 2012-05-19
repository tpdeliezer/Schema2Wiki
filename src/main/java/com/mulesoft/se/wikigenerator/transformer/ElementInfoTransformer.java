package com.mulesoft.se.wikigenerator.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import com.mulesoft.se.wikigenerator.model.ElementInfo;

public class ElementInfoTransformer extends AbstractMessageTransformer {
	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		String wiki = null;
		try {
			ElementInfo elementInfo = (ElementInfo) message.getPayload();
			String template= "!!Documentation!!\n[DOCUMENTATION]\n!!Type!!\n[TYPE]";
			wiki = template.replace("[DOCUMENTATION]", elementInfo.getDocumentation())
			    .replace("[TYPE]", "[<java>]" + elementInfo.getComplexTypeXml() + "[</java>]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wiki;
	}

}
