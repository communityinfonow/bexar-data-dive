package org.cinow.omh.indicators;

/**
 * This object represents an indicator type.
 * 
 * @author brian
 */
public class IndicatorType {
	
	/**
	 * The id.
	 */
	private String id;

	/**
	 * The name.
	 */
	private String name;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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
}
