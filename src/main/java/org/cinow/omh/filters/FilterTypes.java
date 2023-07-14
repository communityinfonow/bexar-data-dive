package org.cinow.omh.filters;

/**
 * The filter types.
 * 
 * @author brian
 */
public enum FilterTypes {

	/**
	 * The race filter type.
	 */
	RACE("1"),

	/**
	 * The age filter type.
	 */
	AGE("2"),

	/**
	 * The sex filter type.
	 */
	SEX("3"),

	/**
	 * The education filter type.
	 */
	EDUCATION("4"),

	/**
	 * The income filter type.
	 */
	INCOME("5");

	/**
	 * The id.
	 */
	private final String id;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Constructor.
	 * 
	 * @param id the id
	 */
	private FilterTypes(String id) {
		this.id = id;
	}
}
