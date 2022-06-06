package org.cinow.omh.filters;

import java.util.List;

public class Filters {
	
	private Filter locationTypeFilter;
	private Filter locationFilter;
	private Filter yearFilter;
	private List<Filter> indicatorFilters;
	
	public Filter getLocationTypeFilter() {
		return locationTypeFilter;
	}
	public void setLocationTypeFilter(Filter locationTypeFilter) {
		this.locationTypeFilter = locationTypeFilter;
	}
	public Filter getLocationFilter() {
		return locationFilter;
	}
	public void setLocationFilter(Filter locationFilter) {
		this.locationFilter = locationFilter;
	}
	public Filter getYearFilter() {
		return yearFilter;
	}
	public void setYearFilter(Filter yearFilter) {
		this.yearFilter = yearFilter;
	}
	public List<Filter> getIndicatorFilters() {
		return indicatorFilters;
	}
	public void setIndicatorFilters(List<Filter> indicatorFilters) {
		this.indicatorFilters = indicatorFilters;
	}
}
