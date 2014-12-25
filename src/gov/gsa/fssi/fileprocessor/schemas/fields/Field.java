package gov.gsa.fssi.fileprocessor.schemas.fields;

import java.util.ArrayList;
import java.util.HashMap;

public class Field {
	private String name = null;
	private String title = null;	
	private String description = null;
	private HashMap<String, String> constraints = new HashMap<String, String>();	
	private ArrayList<String> alias = new ArrayList<String>();		
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the constraints
	 */
	public HashMap<String, String> getConstraints() {
		return constraints;
	}
	/**
	 * @param constraintMap the constraints to set
	 */
	public void setConstraints(HashMap<String, String> constraintMap) {
		this.constraints = constraintMap;
	}
	/**
	 * @return the alias
	 */
	public ArrayList<String> getAlias() {
		return alias;
	}
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(ArrayList<String> alias) {
		this.alias = alias;
	}
}
