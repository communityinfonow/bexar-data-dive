package org.cinow.omh.menu;

import java.util.List;

/**
 * Represents a menu category.
 * 
 * @author brian
 */
public interface Category<T extends Item> {
	
	/**
	 * @return the id
	 */
	String getId();

	/**
	 * @param id the id to set
	 */
	void setId(String id);

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
	 * @return the subcategories
	 */
	List<Category<T>> getSubcategories();

	/**
	 * @param subcategories the subcategories to set
	 */
	void setSubcategories(List<Category<T>> subcategories);

	/**
	 * @return the items
	 */
	List<T> getItems();

	/**
	 * @param items the items to set
	 */
	void setItems(List<T> items);
}
