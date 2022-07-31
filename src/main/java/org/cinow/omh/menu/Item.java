package org.cinow.omh.menu;

/**
 * Represents a menu item.
 * 
 * @author brian
 */
public interface Item {

	/**
	 * @return the id
	 */
	String getId();

	/**
	 * @param id the id to set
	 */
	void setId(String id);

	/**
	 * @return the categoryId
	 */
	String getCategoryId();

	/**
	 * @param categoryId the categoryId to set
	 */
	void setCategoryId(String id);

	/**
	 * @return the name_en
	 */
	String getName_en();

	/**
	 * @param name_en the name_en to set
	 */
	void setName_en(String name_en);

	/**
	 * @return the name_es
	 */
	String getName_es();

	/**
	 * @param name_es the name_es to set
	 */
	void setName_es(String name_es);

	/**
	 * @return the description_en
	 */
	String getDescription_en();

	/**
	 * @param description_en the description_en to set
	 */
	void setDescription_en(String description_en);

	/**
	 * @return the description_es
	 */
	String getDescription_es();

	/**
	 * @param description_es the description_es to set
	 */
	void setDescription_es(String description_es);
}
