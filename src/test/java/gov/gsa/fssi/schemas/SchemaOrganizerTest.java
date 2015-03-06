package test.java.gov.gsa.fssi.schemas;

import java.util.ArrayList;
import java.util.List;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.helpers.mockdata.MockFieldConstraint;
import main.java.gov.gsa.fssi.helpers.mockdata.MockSchema;
import main.java.gov.gsa.fssi.helpers.mockdata.MockSchemaField;

import org.junit.Assert;
import org.junit.Test;

public class SchemaOrganizerTest {
	Config config = new Config("./testfiles/", "config.properties");

	/*
	 * 
	 */
	@Test
	public void schemaCopy() {
		
		Schema schema = new Schema();
		schema.setName("TEST");
		schema.setExportStatus(true);
		List<String> schema1StringList = new ArrayList<String>();
		schema1StringList.add("schema1-1");
		schema.setExportStatusMessages(schema1StringList);
		schema.setValidatorStatus(true);
		schema.setVersion("1.0");
		
		
		Schema schema2 = new Schema(schema);
		schema.setName("I CHANGED!");
		schema.setExportStatus(false);
		List<String> schema2StringList = new ArrayList<String>();
		schema2StringList.add("schema2-1");
		schema.setExportStatusMessages(schema2StringList);
		schema.setValidatorStatus(false);
		schema.setVersion("2.0");		
		
		
		Assert.assertEquals("failure - SchemaOrganizerTest", "TEST", schema2.getName());	
		Assert.assertEquals("failure - SchemaOrganizerTest", true, schema2.getExportStatus());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "schema1-1", schema2.getExportStatusMessages().get(0).toString());	
		Assert.assertEquals("failure - SchemaOrganizerTest", true, schema2.getValidatorStatus());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "1.0", schema2.getVersion());	
		
		Assert.assertEquals("failure - SchemaOrganizerTest", false, schema.getExportStatus());
		Assert.assertEquals("failure - SchemaOrganizerTest", false, schema.getValidatorStatus());	
	}	
	
	
	/*
	 * 
	 */
	@Test
	public void schemaReverseCopy() {
		
		Schema schema = new Schema();
		schema.setName("TEST");
		schema.setExportStatus(true);
		List<String> schema1StringList = new ArrayList<String>();
		schema1StringList.add("schema1-1");
		schema.setExportStatusMessages(schema1StringList);
		schema.setValidatorStatus(true);
		schema.setVersion("1.0");
		
		
		Schema schema2 = new Schema(schema);
		schema2.setName("I CHANGED!");
		schema2.setExportStatus(false);
		List<String> schema2StringList = new ArrayList<String>();
		schema2StringList.add("schema2-1");
		schema2.setExportStatusMessages(schema2StringList);
		schema2.setValidatorStatus(false);
		schema2.setVersion("2.0");		
		
		
		Assert.assertEquals("failure - SchemaOrganizerTest", "TEST", schema.getName());	
		Assert.assertEquals("failure - SchemaOrganizerTest", true, schema.getExportStatus());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "schema1-1", schema.getExportStatusMessages().get(0).toString());	
		Assert.assertEquals("failure - SchemaOrganizerTest", true, schema.getValidatorStatus());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "1.0", schema.getVersion());	
	}	
	
	
	
	
	/**
	 *
	 */
	@Test
	public void schemaFieldCopy() {
		FieldConstraint constraint = MockFieldConstraint.make("required", "true");
		List<SchemaField> fields = new ArrayList<SchemaField>();
		fields.add(MockSchemaField.make("TEST", "string", constraint));	
		Schema schema = MockSchema.make("TEST", fields);
		
		Schema schema2 = new Schema(schema);
		Assert.assertEquals("failure - SchemaOrganizerTest", 1, schema2.getFields().size());
		
		schema.getField("TEST").setHeaderIndex(50);
		
		Assert.assertEquals("failure - SchemaOrganizerTest", -1, schema2.getField("TEST").getHeaderIndex());	
	}	
		
	
	
	
	/*
	 * 
	 */
	@Test
	public void fieldCopy() {
		
		SchemaField field = new SchemaField();
		field.addConstraint(MockFieldConstraint.make(FieldConstraint.TYPE_REQUIRED, "true"));	
		List<String> aliass = new ArrayList<String>();
		aliass.add("alias1");
		field.setAlias(aliass);
		field.setDescription("description1");
		field.setFormat("format1");
		field.setName("name1");
		field.setTitle("title1");
		field.setType(SchemaField.TYPE_ANY);
		
		SchemaField field2 = new SchemaField(field);
		field.addConstraint(MockFieldConstraint.make(FieldConstraint.TYPE_REQUIRED, "true"));	
		aliass.add("alias2");
		field.setAlias(aliass);
		field.setDescription("description2");
		field.setFormat("format2");
		field.setName("name2");
		field.setTitle("title2");
		field.setType(SchemaField.TYPE_DATE);
	
		Assert.assertEquals("failure - SchemaOrganizerTest", "name2", field.getName());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "format2", field.getFormat());	
		Assert.assertEquals("failure - SchemaOrganizerTest", SchemaField.TYPE_DATE, field.getType());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "description2", field.getDescription());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "title2", field.getTitle());	
		Assert.assertEquals("failure - SchemaOrganizerTest", 2, field.getAlias().size());	
		
		Assert.assertEquals("failure - SchemaOrganizerTest", "name1", field2.getName());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "format1", field2.getFormat());	
		Assert.assertEquals("failure - SchemaOrganizerTest", SchemaField.TYPE_ANY, field2.getType());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "description1", field2.getDescription());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "title1", field2.getTitle());	
		Assert.assertEquals("failure - SchemaOrganizerTest", 1, field2.getAlias().size());				
	}	
		
	
	/*
	 * 
	 */
	@Test
	public void fieldReverseCopy() {
		
		SchemaField field = new SchemaField();
		field.addConstraint(MockFieldConstraint.make(FieldConstraint.TYPE_REQUIRED, "true"));	
		List<String> aliass = new ArrayList<String>();
		aliass.add("alias1");
		field.setAlias(aliass);
		field.setDescription("description1");
		field.setFormat("format1");
		field.setName("name1");
		field.setTitle("title1");
		field.setType(SchemaField.TYPE_ANY);
		
		SchemaField field2 = new SchemaField(field);
		field2.addConstraint(MockFieldConstraint.make(FieldConstraint.TYPE_REQUIRED, "true"));	
		List<String> aliass2 = new ArrayList<String>();
		aliass2.add("alias1");
		aliass2.add("alias2");
		field2.setAlias(aliass2);
		field2.setDescription("description2");
		field2.setFormat("format2");
		field2.setName("name2");
		field2.setTitle("title2");
		field2.setType(SchemaField.TYPE_DATE);
		
		Assert.assertEquals("failure - SchemaOrganizerTest", "name2", field2.getName());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "format2", field2.getFormat());	
		Assert.assertEquals("failure - SchemaOrganizerTest", SchemaField.TYPE_DATE, field2.getType());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "description2", field2.getDescription());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "title2", field2.getTitle());	
		Assert.assertEquals("failure - SchemaOrganizerTest", 2, field2.getAlias().size());	
		
		Assert.assertEquals("failure - SchemaOrganizerTest", "name1", field.getName());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "format1", field.getFormat());	
		Assert.assertEquals("failure - SchemaOrganizerTest", SchemaField.TYPE_ANY, field.getType());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "description1", field.getDescription());	
		Assert.assertEquals("failure - SchemaOrganizerTest", "title1", field.getTitle());	
		Assert.assertEquals("failure - SchemaOrganizerTest", 1, field.getAlias().size());				
	}		
	
}
