package com.skcc.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.skcc.option.Stock;

public class XmlUtils {

	public static Document getStockInfo(String xmlData) throws JDOMException, IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		InputStream xmlStream = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));
		Document xml = saxBuilder.build(xmlStream);
		List<Element> xmlInput = xml.getRootElement().getChildren();
		
		for(Element item : xmlInput) {
	        JsonNode stockInfo = JsonUtils.getResponse(item.getAttributeValue("code"));
	        item.removeAttribute("code");
	        for(String key : Stock.codeMap.keySet()) {
	        	item.setAttribute(Stock.codeMap.get(key), stockInfo.get(key).toString().replaceAll("\"", ""));
	        }
		}
		
		return xml;
	}

}
