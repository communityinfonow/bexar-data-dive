package org.cinow.omh.locations;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.menu.Menu;

/**
 * This object represents the location menu.
 * 
 * @author brian
 */
public class LocationMenu implements Menu<LocationMenuCategory> {

	/**
	 * The categories.
	 */
	private List<LocationMenuCategory> categories;

	/**
	 * Constructor initializes fields.
	 */
	public LocationMenu() {
		this.categories = new ArrayList<LocationMenuCategory>();
	}

	/**
	 * @return the categories
	 */
	@Override
	public List<LocationMenuCategory> getCategories() {
		return this.categories;
	}

	/**
	 * @param categories the categories to set
	 */
	@Override
	public void setCategories(List<LocationMenuCategory> categories) {
		this.categories = categories;
	}
}
