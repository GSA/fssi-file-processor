package gov.gsa.fssi.fileprocessor.schemas;

import gov.gsa.fssi.fileprocessor.Config;
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
	static Config config = new Config();	    
	
	public static ArrayList<Schema> initializeSchemas() {
	    logger.debug("Starting initializeSchemas('{}')", config.getProperty(Config.SCHEMAS_DIRECTORY));		
		
	    ArrayList<Schema> schemas = new ArrayList<Schema>();	
		ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(config.getProperty(Config.SCHEMAS_DIRECTORY), ".xml");
		
		for (String fileName : fileNames) {
			initializeSchema(schemas, fileName);
		}
		logger.info("Completed Schema setup. Added " + schemas.size() + " Schemas");
			
		return schemas;		
	}


	public static void initializeSchema(ArrayList<Schema> schemas,
			String fileName) {
		try {
				boolean dupeSchemaCheck = false;
				Schema newSchema = new Schema();	
				File fXmlFile = new File(config.getProperty(Config.SCHEMAS_DIRECTORY) + fileName);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
			 
				//optional, but recommended
				doc.getDocumentElement().normalize();
			 
				//We assume their is only 1 schema in each file.
				Node schemaNode = doc.getFirstChild();
				Element schemaElement = (Element) schemaNode;
				
				
				for (Schema schema: schemas){
					if (schemaElement.getElementsByTagName("name").item(0).getTextContent().toUpperCase().equals(schema.getName())){
						logger.warn("Duplicate schema {} found in file {}, ignorning", schema.getName(), fileName);
						dupeSchemaCheck = true;
					}
				}
				
				if (dupeSchemaCheck == false){
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
				}
			    } catch (Exception e) {
				    logger.error("Received Exception error while processing {}", fileName);		
			    	e.printStackTrace();
			    }
				
				// logger.info("     Successfully processed " + fileName);
	}	


		public static ArrayList<SchemaField> initializeFields(NodeList fieldNodes) {
			ArrayList<SchemaField> fields = new ArrayList<SchemaField>();
			for (int temp = 0; temp < fieldNodes.getLength(); temp++) {
				boolean dupeCheck = false;
				SchemaField newField = initializeField(fieldNodes.item(temp));
				//logger.info("Processed field, now adding to array");
				for (SchemaField schemaField : fields) {
					if(schemaField.getName().equals(newField.getName())){
						logger.warn("Duplicate field {} found. Ignoring", newField.getName());
						dupeCheck = true;
					}
				}
				
				if(dupeCheck == false){
					fields.add(newField);		
					logger.info("Successfully added field '{}'", newField.getName());
				}

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
				
				if(!(fieldElement.getElementsByTagName("description").item(0).getTextContent()  == null)){
					field.setDescription(fieldElement.getElementsByTagName("description").item(0).getTextContent().toUpperCase());					
				}
				if(!(fieldElement.getElementsByTagName("name").item(0).getTextContent()  == null)){
					field.setName(fieldElement.getElementsByTagName("name").item(0).getTextContent().toUpperCase());	
					logger.info("Processing field '{}'", field.getName());
				}
				if(!(fieldElement.getElementsByTagName("title").item(0).getTextContent()  == null)){
					field.setTitle(fieldElement.getElementsByTagName("title").item(0).getTextContent().toUpperCase());					
				}
				
							
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
				}else{
					logger.info("Did not find any constraints");
				}
						
				
				//Getting Alias
				alias = fieldElement.getElementsByTagName("alias");
				for (int i = 0; i < alias.getLength(); i++) {
					boolean dupeAliasCheck = false;
					Element currentElement = (Element) alias.item(i);
					//Checking for duplicate alias
					for (String aliasList : field.getAlias()) {
						if(currentElement.getTextContent().trim().toUpperCase().equals(aliasList.toUpperCase().trim())){
							dupeAliasCheck = true;
							logger.warn("Ignoring duplicate Alias '{}' from field: '{}'", aliasList,  field.getName());
						}
					}
					if(dupeAliasCheck == false){
						field.addAlias(currentElement.getTextContent().trim().toUpperCase());				
						logger.info("added alias {} to field {}", currentElement.getTextContent().trim().toUpperCase(), field.getName());
					}
				}
			}			
		return field;	
	}
	
		
}
