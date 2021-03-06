package main.java.gov.gsa.fssi.files.schemas.utils.strategies.loaders;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.schemas.utils.strategies.SchemaLoaderStrategy;
import main.java.gov.gsa.fssi.helpers.XmlHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class XMLSchemaLoaderStrategy implements SchemaLoaderStrategy {
	private static final Logger logger = LoggerFactory
			.getLogger(XMLSchemaLoaderStrategy.class);

	/**
	 *
	 * @return Schema loaded from fileName in schemas_directory
	 */
	@Override
	public void load(File file, Schema schema) {
		schema.setLoadStage(main.java.gov.gsa.fssi.files.File.STAGE_LOADING);
		Document doc = null;
		if (schema.getFileName() != null) {
			try {
				if (file.isFile()) {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					doc = dBuilder.parse(file);
				} else {
					schema.setMaxErrorLevel(3);
					schema.addLoadStatusMessage(schema.getFileName()
							+ " is not a File.");
					logger.error("'{}' is not a file. unable to process",
							schema.getFileName());
				}
			} catch (Exception e) {
				logger.error(
						"Received Exception error '{}' while processing file {}",
						e.getMessage(), schema.getFileName());
				schema.setMaxErrorLevel(3);
				schema.addLoadStatusMessage("Received Exception error '"
						+ e.getMessage() + "' while processing file '"
						+ schema.getFileName() + "'");
			}

			if (doc != null) {
				// optional, but recommended
				doc.getDocumentElement().normalize();

				// We assume their is only 1 schema in each file.
				Node schemaNode = doc.getFirstChild();
				Element schemaElement = (Element) schemaNode;
				logger.info("Attempting to load schema '{}' in file '{}'",
						schemaElement.getElementsByTagName("name").item(0)
								.getTextContent(), schema.getFileName());

				schema.setName(schemaElement.getElementsByTagName("name")
						.item(0).getTextContent());
				schema.setProviderName(schemaElement
						.getElementsByTagName("provider").item(0)
						.getTextContent());
				schema.setVersion(schemaElement.getElementsByTagName("version")
						.item(0).getTextContent());
				schema.setFields(loadFields(doc.getElementsByTagName("field")));

				if (schema.getLoadStage().equals(
						main.java.gov.gsa.fssi.files.File.STATUS_ERROR)) {
					logger.error(
							"Could not load Schema '{}' in file '{}' as it is in error status",
							schema.getName(), schema.getFileName());
				}

				logger.info("successfully loaded Schema '{}' from file '{}'",
						schema.getName(), schema.getFileName());
				schema.setLoadStage(main.java.gov.gsa.fssi.files.File.STAGE_LOADED);
			} else {
				logger.error(
						"No document found in file '{}'. Unable to load any schema",
						schema.getFileName());
				schema.setMaxErrorLevel(3);
				schema.addLoadStatusMessage("No document found in file '"
						+ schema.getFileName() + "'. Unable to load any schema");
			}
		} else {
			logger.error("Could not build Schema from file '{}', no fileName was set");
			schema.setMaxErrorLevel(3);
			schema.addLoadStatusMessage(file.getPath() + " is not a File.");
		}
	}

	/**
	 * @param field
	 * @param currentNode
	 * @throws DOMException
	 */
	private FieldConstraint loadConstraint(Node currentNode)
			throws DOMException {
		FieldConstraint newConstraint = new FieldConstraint();
		if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
			logger.info("Adding Constraint {} - {}", currentNode.getNodeName()
					.trim(), currentNode.getTextContent().trim());
			newConstraint.setType(currentNode.getNodeName().trim());
			newConstraint.setValue(currentNode.getTextContent().trim());

			Map<String, String> attributeMap = XmlHelper
					.convertXmlAttributeToHashMap(currentNode.getAttributes());
			Iterator<?> optionsIterator = attributeMap.entrySet().iterator();

			while (optionsIterator.hasNext()) {
				Map.Entry<String, String> optionsPair = (Entry<String, String>) optionsIterator
						.next();
				newConstraint.addOption(optionsPair.getKey(),
						optionsPair.getValue());
				logger.info("Adding Attribute {} - {} to Constraint {}",
						optionsPair.getKey(), optionsPair.getValue(),
						currentNode.getNodeName());
			}
			return newConstraint;
		}
		return null;
	}

	/**
	 * @param field
	 * @param constraintList
	 * @throws DOMException
	 */
	private ArrayList<FieldConstraint> loadConstraints(NodeList constraintList)
			throws DOMException {
		Node currentNode;
		ArrayList<FieldConstraint> constraints = new ArrayList<FieldConstraint>();
		for (int i = 0; i < constraintList.getLength(); i++) {
			currentNode = constraintList.item(i);
			FieldConstraint fieldConstraint = loadConstraint(currentNode);
			if (fieldConstraint != null)
				constraints.add(fieldConstraint);
		}
		return constraints;
	}

	/**
	 * @param node
	 * @return
	 */
	public SchemaField loadField(Node node) {
		SchemaField field = new SchemaField();

		if (node.getNodeType() == Node.ELEMENT_NODE) {

			NodeList nodeList = node.getChildNodes();
			for (int j = 0; j < nodeList.getLength(); j++) {
				Node currentNode = nodeList.item(j);
				if (currentNode.getNodeType() == Node.ELEMENT_NODE
						&& currentNode.getNodeName() != null) {
					if ("name".equals(currentNode.getNodeName())) {
						field.setName(currentNode.getTextContent());
					} else if ("description".equalsIgnoreCase(currentNode
							.getNodeName())) {
						field.setDescription(currentNode.getTextContent());
					} else if ("title".equalsIgnoreCase(currentNode
							.getNodeName())) {
						field.setTitle(currentNode.getTextContent());
					} else if ("format".equalsIgnoreCase(currentNode
							.getNodeName())) {
						
						field.setFormat(currentNode.getTextContent());
						
					} else if ("type".equalsIgnoreCase(currentNode
							.getNodeName())) {
						field.setType(currentNode.getTextContent()
								.toLowerCase().trim());
						
						/**
						 * Added code to capture level from type field in schema
						 */
						Map<String, String> attributeMap = XmlHelper
								.convertXmlAttributeToHashMap(currentNode.getAttributes());
						Iterator<?> optionsIterator = attributeMap.entrySet().iterator();

						while (optionsIterator.hasNext()) {
							Map.Entry<String, String> optionsPair = (Entry<String, String>) optionsIterator
									.next();
							field.addTypeOption(optionsPair.getKey(),
									optionsPair.getValue());
							logger.info("Adding Attribute {} - {} to field format {}",
									optionsPair.getKey(), optionsPair.getValue(),
									currentNode.getNodeName());
						}
					} else if ("constraints".equalsIgnoreCase(currentNode
							.getNodeName())) {
						logger.info("Processing Constraints");
						NodeList constraintList = currentNode.getChildNodes();
						if (constraintList != null) {
							try {
								List<FieldConstraint> constraints = loadConstraints(constraintList);
								field.setConstraints(constraints);
							} catch (DOMException e) {
								logger.error("Received DOMException '{}'",
										e.getMessage());
							}
						} else {
							logger.info("Did not find any constraints");
						}
						logger.info("Completed processing Constraints");
					} else if (currentNode.getNodeName().equals("alias")) {
						field.addAlias(currentNode.getTextContent().trim()
								.toUpperCase());
					}
				}
			}
			logger.info("Processing field '{}'", field.getName());
		}
		return field;
	}

	public List<SchemaField> loadFields(NodeList fieldNodes) {
		List<SchemaField> fields = new ArrayList<SchemaField>();
		for (int temp = 0; temp < fieldNodes.getLength(); temp++) {
			SchemaField field = loadField(fieldNodes.item(temp));
			fields.add(field);
			logger.info("succesfully added field '{}' to the schema.",
					field.getName());
		}
		return fields;
	}

}
