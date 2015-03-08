package test.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.organizers;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.SourceFileBuilder;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.SourceFileOrganizerContext;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.organizers.ExplodeSourceFileOrganizerStrategy;
import main.java.gov.gsa.fssi.helpers.mockdata.MockData;
import main.java.gov.gsa.fssi.helpers.mockdata.MockSchema;
import main.java.gov.gsa.fssi.helpers.mockdata.MockSchemaField;

import org.junit.Assert;
import org.junit.Test;

public class ExplodeSourceFileOrganizerStrategyTest {
	Config config = new Config("./testfiles/", "config.properties");
	private static final String SOURCEFILENAME = "goodfileschematest_012015.csv";

	
	
	/**
	 * The Schema has more columns than the sourceFile...
	 */
	@Test
	public void ImplodeWithExtraColumnsFromSchema() {
		
		SourceFile sourceFile = new SourceFile(SOURCEFILENAME);	
		
		SourceFileRecord sourceFileRecord1 = new SourceFileRecord();
		sourceFileRecord1.addData(MockData.make("data0", 0));
		sourceFileRecord1.addData(MockData.make("data1", 1));		
		sourceFileRecord1.addData(MockData.make("data2", 2));		
		sourceFileRecord1.addData(MockData.make("data3", 3));
		sourceFileRecord1.addData(MockData.make("data4", 4));
		
		SourceFileRecord sourceFileRecord2 = new SourceFileRecord();
		sourceFileRecord2.addData(MockData.make("data0", 0));
		sourceFileRecord2.addData(MockData.make("data1", 1));		
		sourceFileRecord2.addData(MockData.make("data2", 2));		
		sourceFileRecord2.addData(MockData.make("data3", 3));		
		sourceFileRecord2.addData(MockData.make("data4", 4));	
		
		sourceFile.addRecord(sourceFileRecord1);
		sourceFile.addRecord(sourceFileRecord2);
		
		sourceFile.addSourceHeader(0, "field0");
		sourceFile.addSourceHeader(1, "field1");		
		sourceFile.addSourceHeader(2, "field2");			
		sourceFile.addSourceHeader(3, "field3");	
		sourceFile.addSourceHeader(4, "field4");		
		
		Schema schema = MockSchema.make("TESTSCHEMA");
		SchemaField field0 = MockSchemaField.make("FIELD0", SchemaField.TYPE_ANY);
		SchemaField field1 = MockSchemaField.make("FIELD1", SchemaField.TYPE_ANY);
		SchemaField field3 = MockSchemaField.make("FIELD3", SchemaField.TYPE_ANY);	
		SchemaField field5 = MockSchemaField.make("FIELD5", SchemaField.TYPE_ANY);		
		SchemaField field6 = MockSchemaField.make("FIELD6", SchemaField.TYPE_ANY);		
		
		schema.addField(field0);
		schema.addField(field1);
		schema.addField(field3);
		schema.addField(field5);	
		schema.addField(field6);
		
		sourceFile.setSchema(new Schema(schema));
		
		SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
		sourceFileBuilder.mapSourceFileFieldsToSchema(sourceFile);
		
		SourceFileOrganizerContext context = new SourceFileOrganizerContext();		
		context.setSourceFileOrganizerStrategy(new ExplodeSourceFileOrganizerStrategy());
		context.organize(sourceFile);
		
		
		Assert.assertEquals("failure - ImplodeWithExtraColumns", 7,sourceFile.getSourceHeaders().size());
		Assert.assertEquals("failure - ImplodeWithExtraColumns", "field0",sourceFile.getSourceHeaders().get(0));
		Assert.assertEquals("failure - ImplodeWithExtraColumns", "field1",sourceFile.getSourceHeaders().get(1));		
		Assert.assertEquals("failure - ImplodeWithExtraColumns", "field3",sourceFile.getSourceHeaders().get(2));
		Assert.assertEquals("failure - ImplodeWithExtraColumns", "FIELD5",sourceFile.getSourceHeaders().get(3));	
		Assert.assertEquals("failure - ImplodeWithExtraColumns", "FIELD6",sourceFile.getSourceHeaders().get(4));
		/*
		 * These fields are swapped because we take the ordr of the schema over the source file.
		 */
		Assert.assertEquals("failure - ImplodeWithExtraColumns", "field2",sourceFile.getSourceHeaders().get(5));	
		Assert.assertEquals("failure - ImplodeWithExtraColumns", "field4",sourceFile.getSourceHeaders().get(6));			
		Assert.assertEquals("failure - ImplodeWithExtraColumns", 7,sourceFile.getRecords().get(0).getDatas().size());
		Assert.assertEquals("failure - ImplodeWithExtraColumns", 7,sourceFile.getRecords().get(1).getDatas().size());					
	
		
	}
	
	
}
