package main.java.gov.gsa.fssi.helpers.mockData;

import java.util.ArrayList;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;

public class MockSchemaField {

	public static SchemaField make() {
		SchemaField schemaField = new SchemaField();
		return schemaField;
	}

	public static SchemaField make(String fieldName) {
		SchemaField schemaField = new SchemaField();
		schemaField.setName(fieldName);
		return schemaField;
	}

	public static SchemaField make(String fieldName, String fieldType) {
		SchemaField schemaField = new SchemaField();
		schemaField.setName(fieldName);
		schemaField.setType(fieldType);
		return schemaField;
	}

	public static SchemaField make(String fieldName, String fieldType,
			FieldConstraint fieldConstraint) {
		SchemaField schemaField = new SchemaField();
		schemaField.setName(fieldName);
		schemaField.setType(fieldType);
		schemaField.addConstraint(fieldConstraint);
		return schemaField;
	}

	public static SchemaField make(String fieldName, String fieldType,
			ArrayList<FieldConstraint> fieldConstraints) {
		SchemaField schemaField = new SchemaField();
		schemaField.setName(fieldName);
		schemaField.setType(fieldType);
		for (FieldConstraint fieldConstraint : fieldConstraints) {
			schemaField.addConstraint(fieldConstraint);
		}
		return schemaField;
	}

}
