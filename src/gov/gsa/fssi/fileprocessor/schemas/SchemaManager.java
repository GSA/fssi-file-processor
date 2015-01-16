package gov.gsa.fssi.fileprocessor.schemas;

import gov.gsa.fssi.fileprocessor.Config;
import gov.gsa.fssi.fileprocessor.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.fileprocessor.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.helpers.FileHelper;
import gov.gsa.fssi.helpers.XmlHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
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


	public static void initializeSchema(ArrayList<Schema> schemas, String fileName) {
		Document doc = null;
		boolean dupeSchemaCheck = false;
		Schema newSchema = new Schema();	
		
		try {
			File fXmlFile = new File(config.getProperty(Config.SCHEMAS_DIRECTORY) + fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		} catch (Exception e) {
			logger.error("Received Exception error '{}' while processing file {}", e.getMessage(), fileName);		
			//e.printStackTrace();
	    }
		
		if(doc != null){
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
		}
		// logger.info("     Successfully processed " + fileName);
	}	


		public static ArrayList<SchemaField> initializeFields(NodeList fieldNodes) {
			ArrayList<SchemaField> fields = new ArrayList<SchemaField>();
			for (int temp = 0; temp < fieldNodes.getLength(); temp++) {
				initializeField(fieldNodes, fields, temp);
			}
			return fields;		
		}


		/**
		 * @param fieldNodes
		 * @param fields
		 * @param temp
		 */
		private static void initializeField(NodeList fieldNodes,ArrayList<SchemaField> fields, int temp) {
			boolean dupeCheck = false;
			SchemaField newField = initializeField(fieldNodes.item(temp));
			ArrayList<String> dupeFields = new ArrayList<String>();
			//logger.info("Processed field, now adding to array");
			
			for (SchemaField schemaField : fields) {
				if(schemaField.getName().equals(newField.getName())){
					logger.warn("Duplicate field {} found. Ignoring", newField.getName());
					dupeCheck = true;
				}
				for(String alias:schemaField.getAlias()){
					for(String newAlias:newField.getAlias()){
						if (alias.equals(newAlias)){
							dupeFields.add(alias);
						}
					}
				}
			}
			
			if(!dupeFields.isEmpty()){
				for (String dupeFieldAlias : dupeFields) {
					logger.warn("alias {} in field {} is a duplicate in another field. It is being removed", dupeFieldAlias, newField.getName());
					newField.removeAlias(dupeFieldAlias);
				}
			}
			
			if(dupeCheck == false){
				fields.add(newField);		
				logger.info("Successfully added field '{}'", newField.getName());
			}
		}
		
		public static SchemaField initializeField(Node node) {
			SchemaField field = new SchemaField();
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element fieldElement = (Element) node;
				
				NodeList nodeList = node.getChildNodes();
				for (int j = 0; j < nodeList.getLength(); j++){
					Node currentNode = nodeList.item(j);
					if (currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName() != null){
						//logger.debug("{} - {}", currentNode.getNodeName(), currentNode.getTextContent());	
						if(currentNode.getNodeName().equals("name")){
							field.setName(currentNode.getTextContent());					
						}else if (currentNode.getNodeName().equals("description")){
							field.setDescription(currentNode.getTextContent());	
						}else if (currentNode.getNodeName().equals("title")){
							field.setTitle(currentNode.getTextContent());	
						}else if (currentNode.getNodeName().equals("format")){
							field.setFormat(currentNode.getTextContent());	
						}else if (currentNode.getNodeName().equals("type")){
							field.setType(currentNode.getTextContent());	
						}else if(currentNode.getNodeName().equals("constraints")){
							logger.info("Processing Constraints");
							NodeList constraintList = currentNode.getChildNodes();
								if(constraintList != null){
								try {
									processConstraints(field, constraintList);
								} catch (DOMException e) {
									logger.error("Received DOMException '{}'",e.getMessage());
									//e.printStackTrace();
								}	
							}else{
								logger.info("Did not find any constraints");
							}			
							logger.info("Completed processing Constraints");
						}else if (currentNode.getNodeName().equals("alias")){
							if(currentNode.getNodeValue() != null && !isDuplicateConstraintAlias(field, currentNode.getNodeValue().trim().toUpperCase())){
								field.addAlias(currentNode.getTextContent().trim().toUpperCase());		
								logger.info("added alias {} to field {}", currentNode.getTextContent().trim().toUpperCase(), field.getName());
							}else{
								logger.warn("Ignoring duplicate Alias '{}' from field: '{}'", currentNode.getTextContent().trim().toUpperCase(),  field.getName());
							}
						}
					}
				}
				
				logger.info("Processing field '{}'", field.getName());
			}			
		return field;	
	}


		/**
		 * @param field
		 * @param constraintList
		 * @throws DOMException
		 */
		private static void processConstraints(SchemaField field, NodeList constraintList) throws DOMException {
			Node currentNode;
			for (int i = 0; i < constraintList.getLength(); i++) {
				currentNode = constraintList.item(i);
				processConstraint(field, currentNode);
			}
		}


		/**
		 * @param field
		 * @param currentNode
		 * @throws DOMException
		 */
		private static void processConstraint(SchemaField field, Node currentNode) throws DOMException {
			FieldConstraint newConstraint = new FieldConstraint();
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

				newConstraint.setConstraintType(currentNode.getNodeName().trim());
				newConstraint.setValue(currentNode.getTextContent().trim());									
				
				//logger.info("Processing Constraint '{}' for field {}", newConstraint.getConstraintType(), field.getName());
				// get a map containing the attributes of this constraint 
				
				//NamedNodeMap attributeMap = constraintNode.getAttributes();
				HashMap<String,String> attributeMap = XmlHelper.convertXmlAttributeToHashMap(currentNode.getAttributes());
				Iterator optionsIterator = attributeMap.entrySet().iterator();
				
				while (optionsIterator.hasNext()) {
					Map.Entry<String, String> optionsPair = (Map.Entry)optionsIterator.next();
					if(!newConstraint.isValidOption(optionsPair.getKey())){
						logger.warn("Ignoring invalid Option from Constraint: '{}'. {}  is not a valid type", newConstraint.getConstraintType(), optionsPair.getKey());
					}else if(optionsPair.getKey() == FieldConstraint.OPTION_LEVEL && !newConstraint.isValidOptionLevel(optionsPair.getValue())){
						logger.warn("Ignoring invalid Option level from Constraint: '{}'. {} is not a valid level", newConstraint.getConstraintType(), optionsPair.getKey());
					}else{
						newConstraint.addOption(optionsPair.getKey(),optionsPair.getValue()) ;
						logger.info("Adding Attribute {} - {} to Constraint {}", optionsPair.getKey(), optionsPair.getValue(), currentNode.getNodeName());		
					}
				}
				
				//if duplicate not found, add constraint								
				if(isDuplicateConstraint(field, newConstraint)){
					logger.warn("Ignoring duplicate Constraint '{}' from Field: '{}'", newConstraint.getConstraintType(),  field.getName());
				}else if(!newConstraint.isValidType(newConstraint.getConstraintType())){
					logger.warn("Ignoring invalid Constraint from Field: '{}'. '{}' is not a valid type", newConstraint.getConstraintType(), field.getName(), newConstraint.getConstraintType());									
				}else{
					logger.info("Successfully added Constraint '{}'", newConstraint.getConstraintType());	
					field.addConstraint(newConstraint);								
				}
			}
		}

		/**
		 * @param newAlias
		 * @param field
		 * @return 
		 */
		private static boolean isDuplicateConstraintAlias(SchemaField field, String newAlias) {
			for(String alias: field.getAlias()){
				return (alias.equals(newAlias.trim().toUpperCase())? true:false);
			}
			return false;
		}


		/**
		 * Checks for a duplicate constraint based upon type and option effectivedate
		 * @param field
		 * @param newConstraint
		 * @return
		 */
		private static boolean isDuplicateConstraint(SchemaField field,FieldConstraint newConstraint) {
			for (FieldConstraint constraintCheck : field.getConstraints()) {
				if(newConstraint.getConstraintType().trim().toUpperCase().equals(constraintCheck.getConstraintType().trim().toUpperCase())){
					if(newConstraint.getOptions().containsKey(FieldConstraint.OPTION_EFFECTIVEDATE) && constraintCheck.getOptions().containsKey(FieldConstraint.OPTION_EFFECTIVEDATE)){
						return (newConstraint.getOptionValue(FieldConstraint.OPTION_EFFECTIVEDATE) == constraintCheck.getOptionValue(FieldConstraint.OPTION_EFFECTIVEDATE)? true:false);
					}else if(!newConstraint.getOptions().containsKey(FieldConstraint.OPTION_EFFECTIVEDATE) && !constraintCheck.getOptions().containsKey(FieldConstraint.OPTION_EFFECTIVEDATE)){
						//logger.debug("Both constraints had no effective date, is duplicate");
						return true;
					}
				}
			}
			return false;
		}
		
		public static void printAllSchemas(ArrayList<Schema> schemas){
			for(Schema schema: schemas){
				schema.printAll();
			}
		}
}
