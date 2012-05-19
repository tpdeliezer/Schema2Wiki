package com.mulesoft.se.wikigenerator.transformer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class HtmlTransformer extends AbstractMessageTransformer {
	private String text;
	
	public HtmlTransformer(String text) {
		this.text = text;
	}
	
	public HtmlTransformer() {
		
	}
	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		String xml = null;
		try {
			String xmlTemplate = "<?xml version=\"1.0\"?><schemas>[SCHEMA ELEMENTS]</schemas>";
			String html = message.getPayloadAsString();
			Pattern pattern = Pattern.compile("href\\=\"[a-z\\-]+/\"");
			Matcher matcher = pattern.matcher(html);
			StringBuilder stringBuilder = new StringBuilder();
			while (matcher.find()) {
				String href = matcher.group();
				String link = href.split("=")[1];
				link = link.substring(1, link.indexOf('/'));
				if (false) { //link.startsWith("activiti") || link.equals("ee") || link.startsWith("mqseries")) {
					// ignore
				} else {
					stringBuilder.append("<s>" + link + "</s>");
				}
			}
			xml = xmlTemplate.replace("[SCHEMA ELEMENTS]", stringBuilder.toString());
			System.out.println(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}
	
	public static void main(String[] args) throws TransformerException {
		new HtmlTransformer("<table><tr><th><img src=\"/icons/blank.gif\" alt=\"[ICO]\"></th><th><a href=\"?C=N;O=D\">Name</a></th><th><a href=\"?C=M;O=A\">Last modified</a></th>" +
				"<th><a href=\"?C=S;O=A\">Size</a></th><th><a href=\"?C=D;O=A\">Description</a></th></tr><tr><th colspan=\"5\"><hr></th></tr>" +
				"<tr><td valign=\"top\"><img src=\"/icons/back.gif\" alt=\"[DIR]\"></td><td><a href=\"/mule/schema/\">Parent Directory</a></td><td>&nbsp;</td><td align=\"right\">  - </td></tr>" +
				"<tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"abdera/\">abdera/</a></td><td align=\"right\">16-Nov-2008 01:48  </td><td align=\"right\">  - </td></tr>" +
				"<tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"acegi/\">acegi/</a></td><td align=\"right\">06-Dec-2010 12:51  </td><td align=\"right\">  - </td></tr>" +
				"<tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"activiti-embedded/\">activiti-embedded/</a></td><td align=\"right\">21-Apr-2011 17:47  </td><td align=\"right\">  - </td></tr>" +
				"<tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"activiti-remote/\">activiti-remote/</a></td><td align=\"right\">21-Apr-2011 17:46  </td><td align=\"right\">  - </td></tr>" +
				"<tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"activiti/\">activiti/</a></td><td align=\"right\">17-Jan-2011 19:10  </td><td align=\"right\">  - </td></tr>" +
				"<tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"ajax/\">ajax/</a></td><td align=\"right\">27-Mar-2012 22:47  </td><td align=\"right\">  - </td></tr>").transformMessage(null, "");
	}

}
