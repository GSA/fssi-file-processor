package gov.gsa.fssi.fileprocessor.schemas;

import gov.gsa.fssi.fileprocessor.FileHelper;
import gov.gsa.fssi.fileprocessor.schemas.fields.Field;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SchemaManager {

	
	public static ArrayList<Schema> initializeSchemas(String schemaDirectory) {
		
		ArrayList<Schema> schemas = new ArrayList<Schema>();	
		
		System.out.println("Setting up Schemas");
		System.out.println("----------------------------");
		
		ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(schemaDirectory, ".xml");
		
		
		for (String fileName : fileNames) {
		try {
				Schema newSchema = new Schema();	
				File fXmlFile = new File(schemaDirectory + fileName);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
			 
				//optional, but recommended
				//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
				doc.getDocumentElement().normalize();
			 
				Node schemaNode = doc.getFirstChild();
				Element schemaElement = (Element) schemaNode;
				
				newSchema.setName(schemaElement.getElementsByTagName("name").item(0).getTextContent());
				newSchema.setProvider(schemaElement.getElementsByTagName("provider").item(0).getTextContent());
				newSchema.setVersion(schemaElement.getElementsByTagName("version").item(0).getTextContent());
				newSchema.setEffectiveReportingPeriod(schemaElement.getElementsByTagName("effectiveReportingPeriod").item(0).getTextContent());
				newSchema.setEndingReportingPeriod(schemaElement.getElementsByTagName("endingReportingPeriod").item(0).getTextContent());
				newSchema.setFields(initializeFields(doc.getElementsByTagName("field")));
				
				schemas.add(newSchema);
				
			    } catch (Exception e) {
			    	
			    	e.printStackTrace();
			    }
		
				// System.out.println("     Successfully processed " + fileName);
			}
			
			System.out.println("Completed Schema setup. Added " + schemas.size() + " Schemas");
			System.out.println(" ");
			
			return schemas;		
		}	


		public static ArrayList<Field> initializeFields(NodeList fieldNodes) {
			ArrayList<Field> fields = new ArrayList<Field>();
			
			for (int temp = 0; temp < fieldNodes.getLength(); temp++) {
				fields.add(initializeField(fieldNodes.item(temp)));
			}
		
			return fields;
			
		}
		
		public static Field initializeField(Node node) {
			Field field = new Field();
			ArrayList<String> aliasArray = new ArrayList<String>();
			HashMap<String,String> constraintMap = new HashMap<String, String>();
			
			NodeList alias = null;
			Node constraintNode = null;
			NodeList constraintList = null;
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element fieldElement = (Element) node;
				field.setDescription(fieldElement.getElementsByTagName("description").item(0).getTextContent());
				field.setName(fieldElement.getElementsByTagName("name").item(0).getTextContent());
				field.setTitle(fieldElement.getElementsByTagName("title").item(0).getTextContent());

				
				//Getting Constraints		
				constraintNode = fieldElement.getElementsByTagName("constraints").item(0);
				constraintList = constraintNode.getChildNodes();
				for (int i = 0; i < constraintList.getLength(); i++) {
					constraintNode = constraintList.item(i);
					if (constraintNode.getNodeType() == Node.ELEMENT_NODE) {
						//System.out.println(constraintNode.getNodeName() + " - " + constraintNode.getTextContent());
						constraintMap.put(constraintNode.getNodeName(), constraintNode.getTextContent());
					}
				}						
				field.setConstraints(constraintMap);
				
				
				//Getting Alias
				alias = fieldElement.getElementsByTagName("alias");
				for (int i = 0; i < alias.getLength(); i++) {
					Element currentElement = (Element) alias.item(i);
					aliasArray.add(currentElement.getTextContent().trim());
				}
				field.setAlias(aliasArray);
				
				
				//System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
	 
			}
			
		return field;
		
	}
}
