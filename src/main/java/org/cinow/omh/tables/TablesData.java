package org.cinow.omh.tables;

import java.util.List;

import org.cinow.omh.indicators.Indicator;
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
