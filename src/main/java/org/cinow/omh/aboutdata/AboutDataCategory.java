package org.cinow.omh.aboutdata;

import java.util.List;

import org.cinow.omh.indicators.IndicatorCategory;

/**
 * This object represents a category in the About the Data view.
 * A category contains items and may also contain subcategories.
 * 
 * @author brian
 */
public class AboutDataCategory {
	
	/**
	 * The category.
	 */
	private IndicatorCategory category;

	/**
	 * The items.
	 */
	private List<AboutDataItem> items;

	/**
	 * The subcategories.
	 */
	private List<AboutDataCategory> subcategories;

	/**
	 * @return the category
	 */
	public IndicatorCategory getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(IndicatorCategory category) {
		this.category = category;
	}

	/**
	 * @return the items
	 */
	public List<AboutDataItem> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<AboutDataItem> items) {
		this.items = items;
	}

	/**
	 * @return the subcategories
	 */
	public List<AboutDataCategory> getSubcategories() {
		return subcategories;
	}

	/**
	 * @param subcategories the subcategories to set
	 */
	public void setSubcategories(List<AboutDataCategory> subcategories) {
		this.subcategories = subcategories;
	}
}
