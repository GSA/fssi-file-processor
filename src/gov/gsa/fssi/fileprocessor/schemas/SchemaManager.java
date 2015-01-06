package gov.gsa.fssi.fileprocessor.schemas;

import gov.gsa.fssi.fileprocessor.FileHelper;
import gov.gsa.fssi.fileprocessor.schemas.schemaElements.SchemaElement;
import gov.gsa.fssi.fileprocessor.schemas.schemaFields.SchemaField;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SchemaManager {
	static Logger logger = LoggerFactory.getLogger(SchemaManager.class);
	
	public static ArrayList<Schema> initializeSchemas(String schemaDirectory) {
	    logger.debug("Starting initializeSchemas('{}')", schemaDirectory);		
		
	    ArrayList<Schema> schemas = new ArrayList<Schema>();	
		
		ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(schemaDirectory, ".xml");
		
		
		for (String fileName : fileNames) {
		try {
				Schema newSchema = new Schema();	
				File fXmlFile = new File(schemaDirectory + fileName);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
			 
				//optional, but recommended
				doc.getDocumentElement().normalize();
			 
				//We assume their is only 1 schema in each file.
				Node schemaNode = doc.getFirstChild();
				Element schemaElement = (Element) schemaNode;
				
				//All schemas must have a name
				if (schemaElement.getElementsByTagName("name").item(0).getTextContent() == null || schemaElement.getElementsByTagName("name").item(0).getTextContent().equals("")){
					logger.error("Schema in file '{}' does not have required element of 'Name'. Ignoring.", fileName);
				}else{
				    logger.info("Processing schema '{}' in file '{}'", schemaElement.getElementsByTagName("name").item(0).getTextContent(), fileName);
					newSchema.setName(schemaElement.getElementsByTagName("name").item(0).getTextContent());
					newSchema.setProviderName(schemaElement.getElementsByTagName("provider").item(0).getTextContent());
					newSchema.setVersion(schemaElement.getElementsByTagName("version").item(0).getTextContent());
					newSchema.setFields(initializeFields(doc.getElementsByTagName("field")));
					
					schemas.add(newSchema);
				}
			    } catch (Exception e) {
				    logger.error("Received Exception error while processing {}", fileName);		
			    	e.printStackTrace();
			    }
		
				// logger.info("     Successfully processed " + fileName);
			}
			
			logger.info("Completed Schema setup. Added " + schemas.size() + " Schemas");
			
			return schemas;		
		}	


		public static ArrayList<SchemaField> initializeFields(NodeList fieldNodes) {
			ArrayList<SchemaField> fields = new ArrayList<SchemaField>();
			
			for (int temp = 0; temp < fieldNodes.getLength(); temp++) {
				fields.add(initializeField(fieldNodes.item(temp)));
			}
		
			return fields;
			
		}
		
		public static SchemaField initializeField(Node node) {
			SchemaField field = new SchemaField();
			
			NodeList alias = null;
			Node constraintNode = null;
			NodeList constraintList = null;
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element fieldElement = (Element) node;
				field.setDescription(fieldElement.getElementsByTagName("description").item(0).getTextContent().toUpperCase());
				field.setName(fieldElement.getElementsByTagName("name").item(0).getTextContent().toUpperCase());
				field.setTitle(fieldElement.getElementsByTagName("title").item(0).getTextContent().toUpperCase());

				
				//Getting Constraints		
				constraintNode = fieldElement.getElementsByTagName("constraints").item(0);
				if(constraintNode != null){
				constraintList = constraintNode.getChildNodes();
					if(constraintList != null){
						//logger.debug("Attempting to print constraint with {} elements", constraintList.getLength());
						
						for (int i = 0; i < constraintList.getLength(); i++) {
							constraintNode = constraintList.item(i);
							SchemaElement constraint = new SchemaElement();
							if (constraintNode.getNodeType() == Node.ELEMENT_NODE) {
								
								//Checking for Duplicate Coinstraints
								boolean dupeConstraintCheck = false;
								for (SchemaElement constraintList1 : field.getConstraints()) {
									if(constraintNode.getNodeName().toUpperCase().equals(constraintList1.getName().toUpperCase())){
										dupeConstraintCheck = true;
										logger.warn("Ignoring duplicate Constraint '{}' from field: '{}'", constraintList1.getName(),  field.getName());
									}
								}
								if(dupeConstraintCheck == false){
									constraint.setName(constraintNode.getNodeName().toUpperCase());
									constraint.setValue(constraintNode.getTextContent().toUpperCase());
									
									if(constraintNode.getAttributes().getNamedItem("effectiveDate") != null){
										constraint.addOption("effectiveDate", constraintNode.getAttributes().getNamedItem("effectiveDate").getNodeValue().toString().toUpperCase());
									}

									field.addConstraint(constraint);	
								}								
								
							}
						}
					}
				}				
						
				
				//Getting Alias
				alias = fieldElement.getElementsByTagName("alias");
				for (int i = 0; i < alias.getLength(); i++) {
					boolean dupeAliasCheck = false;
					Element currentElement = (Element) alias.item(i);
					for (String aliasList : field.getAlias()) {
						if(currentElement.getTextContent().trim().toUpperCase().equals(aliasList)){
							dupeAliasCheck = true;
							logger.warn("Ignoring duplicate Alias '{}' from field: '{}'", aliasList,  field.getName());
						}
					}
					if(dupeAliasCheck == false){
						field.addAlias(currentElement.getTextContent().trim().toUpperCase());						
					}
				}
			}			
		return field;	
	}
		
		
}
