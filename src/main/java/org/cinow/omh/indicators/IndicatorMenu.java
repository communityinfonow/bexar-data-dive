package org.cinow.omh.indicators;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.menu.Menu;

/**
 * This object represents the indicator menu.
 * 
 * @author brian
 */
public class IndicatorMenu implements Menu<IndicatorMenuCategory> {
	
	/**
	 * The categories.
	 */
	private List<IndicatorMenuCategory> categories;

	/**
	 * Constructor initializes fields.
	 */
	public IndicatorMenu() {
		this.categories = new ArrayList<IndicatorMenuCategory>();
	}

	/**
	 * @return the categories
	 */
	@Override
	public List<IndicatorMenuCategory> getCategories() {
		return this.categories;
	}

	/**
	 * @param categories the categories to set
	 */
	@Override
	public void setCategories(List<IndicatorMenuCategory> categories) {
		this.categories = categories;
	}
}
