package org.cinow.omh.dashboard;

import java.util.List;

import org.cinow.omh.filters.Filter;
import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.sources.Source;

public class DashboardData {
	
	private Indicator indicator;
	private List<Filter> filters;
	private Source source;
	private List<DashboardDataLocation> locationData;
	
	public Indicator getIndicator() {
		return indicator;
	}
	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}
	public List<Filter> getFilters() {
		return filters;
	}
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}
	public List<DashboardDataLocation> getLocationData() {
		return locationData;
	}
	public void setLocationData(List<DashboardDataLocation> locationData) {
		this.locationData = locationData;
	}
}
