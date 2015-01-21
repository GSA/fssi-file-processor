package gov.gsa.fssi.loaders.schemas;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.LoaderStatus;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
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

public interface SchemaLoader {
	static Logger logger = LoggerFactory.getLogger(SchemaLoader.class);
	static Config config = new Config();	
	static String fileName = new String();
	
	public Schema load(String fileName);
}
