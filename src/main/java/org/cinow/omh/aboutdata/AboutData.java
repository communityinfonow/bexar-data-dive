package org.cinow.omh.aboutdata;

import java.util.List;

/**
 * This object represents the model for the About the Data view.
 * 
 * @author brian
 */
public class AboutData {
	
	/**
	 * The about data categories.
	 */
	private List<AboutDataCategory> categories;

	/**
	 * @return categories
	 */
	public List<AboutDataCategory> getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<AboutDataCategory> categories) {
		this.categories = categories;
	}
}
