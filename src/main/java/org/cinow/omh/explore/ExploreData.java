package org.cinow.omh.explore;

import java.util.List;

import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.sources.Source;

public class ExploreData {
	
	private Indicator indicator;
	private Source source;
	private List<ExploreDataItem> items;

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

	public List<ExploreDataItem> getItems() {
		return items;
	}

	public void setItems(List<ExploreDataItem> items) {
		this.items = items;
	}
}
