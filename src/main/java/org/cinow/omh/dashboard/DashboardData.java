package org.cinow.omh.dashboard;

import java.util.List;

import org.cinow.omh.filters.Filters;
import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.sources.Source;

public class DashboardData {
	
	private Indicator indicator;
	private Filters filters;
	private Source source;
	private List<DashboardDataLocation> locationData;
	private List<DashboardDataLocation> compareData;
	
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
	public List<DashboardDataLocation> getLocationData() {
		return locationData;
	}
	public void setLocationData(List<DashboardDataLocation> locationData) {
		this.locationData = locationData;
	}
	public List<DashboardDataLocation> getCompareData() {
		return compareData;
	}
	public void setCompareData(List<DashboardDataLocation> compareData) {
		this.compareData = compareData;
	}
	
}
