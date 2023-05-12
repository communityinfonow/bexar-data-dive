package org.cinow.omh.indicators;

/**
 * This object represents an indicator type.
 * 
 * @author brian
 */
public class IndicatorType {

	public static final String COUNT = "1";
	public static final String RATE = "2";
	public static final String PERCENTAGE = "3";
	public static final String CURRENCY = "4";
	
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
