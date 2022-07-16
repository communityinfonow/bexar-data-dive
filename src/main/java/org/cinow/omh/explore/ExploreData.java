package org.cinow.omh.explore;

import java.util.List;

import org.cinow.omh.filters.Filters;
import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.sources.Source;

public class ExploreData {
	
	private Indicator indicator;
	private Filters filters;
	private Source source;
	private List<ExploreDataLocation> locationData;
	private List<ExploreDataLocation> compareData;
	
	public Indicator getIndicator() {
		return indicator;
	}
	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}
	public Filters getFilters() {
		return filters;
	}
	public void setFilters(Filters filters) {
		this.filters = filters;
	}
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}
	public List<ExploreDataLocation> getLocationData() {
		return locationData;
	}
	public void setLocationData(List<ExploreDataLocation> locationData) {
		this.locationData = locationData;
	}
	public List<ExploreDataLocation> getCompareData() {
		return compareData;
	}
	public void setCompareData(List<ExploreDataLocation> compareData) {
		this.compareData = compareData;
	}
	
}
