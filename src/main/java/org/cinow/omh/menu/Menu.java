package org.cinow.omh.menu;

import java.util.List;

/**
 * Represents a menu.
 * 
 * @author brian
 */
public interface Menu<T extends Category> {

	/**
	 * @return the categories
	 */
	List<T> getCategories();

	/**
	 * @param categories the categories to set
	 */
	void setCategories(List<T> categories);
}
