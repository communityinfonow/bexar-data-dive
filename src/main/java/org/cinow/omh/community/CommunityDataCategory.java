package org.cinow.omh.community;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.indicators.IndicatorCategory;

/**
 * This object represents a category of data in the Community view.
 * 
 * @author brian
 */
public class CommunityDataCategory {
	
	/**
	 * The category.
	 */
	private IndicatorCategory category;

	/**
	 * The indicators.
	 */
	private List<CommunityDataIndicator> indicators;

	/**
	 * The subcategories.
	 */
	private List<CommunityDataCategory> subcategories;

	/**
	 * Constructor initializes fields.
	 */
	public CommunityDataCategory() {
		this.indicators = new ArrayList<>();
		this.subcategories = new ArrayList<>();
	}
	
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
	 * @return the indicators
	 */
	public List<CommunityDataIndicator> getIndicators() {
		return indicators;
	}
	
	/**
	 * @param indicators the indicators to set
	 */
	public void setIndicators(List<CommunityDataIndicator> indicators) {
		this.indicators = indicators;
	}

	/**
	 * @return the subcategories
	 */
	public List<CommunityDataCategory> getSubcategories() {
		return subcategories;
	}

	/**
	 * @param subcategories the subcategories to set
	 */
	public void setSubcategories(List<CommunityDataCategory> subcategories) {
		this.subcategories = subcategories;
	}
}
