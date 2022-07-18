package org.cinow.omh.aboutdata;

import java.util.List;

import org.cinow.omh.indicators.IndicatorCategory;

public class AboutDataCategory {
	
	private IndicatorCategory category;
	private List<AboutDataItem> items;
	private List<AboutDataCategory> subcategories;

	public IndicatorCategory getCategory() {
		return category;
	}
	public void setCategory(IndicatorCategory category) {
		this.category = category;
	}
	public List<AboutDataItem> getItems() {
		return items;
	}
	public void setItems(List<AboutDataItem> items) {
		this.items = items;
	}
	public List<AboutDataCategory> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(List<AboutDataCategory> subcategories) {
		this.subcategories = subcategories;
	}
}
