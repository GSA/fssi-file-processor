package main.java.gov.gsa.fssi.helpers;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;

public class XmlHelper {

	public static HashMap<String, String> convertXmlAttributeToHashMap(
			NamedNodeMap inputNodeMap) {
		HashMap<String, String> outputHashMap = new HashMap<String, String>();

		for (int i = 0; i < inputNodeMap.getLength(); i++) {
			Attr inputNodeMapAttribute = (Attr) inputNodeMap.item(i);
			outputHashMap.put(inputNodeMapAttribute.getName().trim(),
					inputNodeMapAttribute.getValue().trim());
		}

		return outputHashMap;
	}

	
	
	static boolean validateAgainstXSD(InputStream xml, InputStream xsd)
	{
	    try
	    {
	        SchemaFactory factory = 
	            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	        Schema schema = factory.newSchema(new StreamSource(xsd));
	        Validator validator = schema.newValidator();
	        validator.validate(new StreamSource(xml));
	        return true;
	    }
	    catch(Exception ex)
	    {
	        return false;
	    }
	}	
	
}
