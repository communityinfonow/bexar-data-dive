package org.cinow.omh.tables;

import java.util.List;

import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.sources.Source;

public class TablesData {
	
	private Indicator indicator;
	private Source source;
	private List<TablesDataItem> items;

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public List<TablesDataItem> getItems() {
		return items;
	}

	public void setItems(List<TablesDataItem> items) {
		this.items = items;
	}
}
