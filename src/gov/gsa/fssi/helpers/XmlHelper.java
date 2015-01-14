package gov.gsa.fssi.helpers;

import java.util.HashMap;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;

public class XmlHelper {

	public static HashMap<String, String>convertXmlAttributeToHashMap(NamedNodeMap inputNodeMap){
		HashMap<String,String> outputHashMap = new HashMap<String,String>();
		
		for (int i = 0; i < inputNodeMap.getLength(); i++) {
			Attr inputNodeMapAttribute = (Attr) inputNodeMap.item(i);
			outputHashMap.put(inputNodeMapAttribute.getName().trim(), inputNodeMapAttribute.getValue().trim());
		}
		
		return outputHashMap;
	}
	
	
}
