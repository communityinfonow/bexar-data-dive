package org.cinow.omh.filters;

/**
 * This object represents a filter option.
 */
public class FilterOption {
	
	/**
	 * The id.
	 */
	private String id;

	/**
	 * The type id.
	 */
	private String typeId;

	/**
	 * The name (English).
	 */
	private String name_en;

	/**
	 * The name (Spanish).
	 */
	private String name_es;

	/**
	 * The sort order.
	 */
	private int sortOrder;

	/**
	 * Whether to display.
	 */
	private boolean display;

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
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the name_en
	 */
	public String getName_en() {
		return name_en;
	}

	/**
	 * @param name_en the name_en to set
	 */
	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	/**
	 * @return the name_es
	 */
	public String getName_es() {
		return name_es;
	}

	/**
	 * @param name_es the name_es to set
	 */
	public void setName_es(String name_es) {
		this.name_es = name_es;
	}

	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the display
	 */
	public boolean isDisplay() {
		return display;
	}

	/**
	 * @param display the display to set
	 */
	public void setDisplay(boolean display) {
		this.display = display;
	}	
}
