package org.cinow.omh.tables;

import java.util.List;

import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.indicators.IndicatorCategory;
import org.cinow.omh.sources.Source;

/**
 * This object represents data for the Tables and Downloads view.
 * 
 * @author brian
 */
public class TablesData {
	
	/**
	 * The indicator.
	 */
	private Indicator indicator;

	/**
	 * The category.
	 */
	private IndicatorCategory category;

	/**
	 * The source.
	 */
	private Source source;

	/**
	 * The items.
	 */
	private List<TablesDataItem> items;

	/**
	 * @return the indicator
	 */
	public Indicator getIndicator() {
		return indicator;
	}

	/**
	 * @param indicator the indicator to set
	 */
	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
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
	 * @return the source
	 */
	public Source getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Source source) {
		this.source = source;
	}

	/**
	 * @return the items
	 */
	public List<TablesDataItem> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<TablesDataItem> items) {
		this.items = items;
	}
}
