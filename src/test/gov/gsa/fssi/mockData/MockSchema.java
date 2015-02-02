package test.gov.gsa.fssi.mockData;

import java.util.ArrayList;

import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;

public class MockSchema{
	
	public static Schema make(){
		Schema schema = new Schema();
		return schema;
	}

	public static Schema make(String name){
		Schema schema = new Schema();
		schema.setName(name);
		return schema;
	}

	public static Schema make(String name, SchemaField schemaField){
		Schema schema = new Schema();
		schema.setName(name);
		schema.addField(schemaField);
		return schema;
	}	
	

	public static Schema make(String name, ArrayList<SchemaField> schemaFields){
		Schema schema = new Schema();
		schema.setName(name);
		for(SchemaField schemaField: schemaFields){
			schema.addField(schemaField);			
		}
		return schema;
	}		
}
