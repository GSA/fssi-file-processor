package test.gov.gsa.fssi.mockData;

import java.util.Date;

import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;

public class MockFieldConstraint{
	
	public static FieldConstraint make(){
		FieldConstraint fieldConstraint = new FieldConstraint();
		return fieldConstraint;
	}
	
	public static FieldConstraint make(String type, String value){
		FieldConstraint fieldConstraint = new FieldConstraint();
		fieldConstraint.setType(type);
		fieldConstraint.setValue(value);
		return fieldConstraint;
	}	
	
	public static FieldConstraint make(String type, String value, Date date){
		FieldConstraint fieldConstraint = new FieldConstraint();
		fieldConstraint.setType(type);
		fieldConstraint.setEffectiveDate(date);
		fieldConstraint.setValue(value);
		return fieldConstraint;
	}
	
	public static FieldConstraint make(String type, String value, String level){
		FieldConstraint fieldConstraint = new FieldConstraint();
		fieldConstraint.setType(type);
		fieldConstraint.setLevel(level);
		fieldConstraint.setValue(value);
		return fieldConstraint;
	}		
	
	public static FieldConstraint make(String type, String value, String level, Date date){
		FieldConstraint fieldConstraint = new FieldConstraint();
		fieldConstraint.setType(type);
		fieldConstraint.setEffectiveDate(date);
		fieldConstraint.setLevel(level);
		fieldConstraint.setValue(value);
		return fieldConstraint;
	}		
}
