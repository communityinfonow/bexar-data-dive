package org.cinow.omh.explore;

import java.util.Map;

import org.cinow.omh.filters.FilterOption;

public class FilterRequest {
	
	private String locationType;
	private String location;
	private String year;
	private Map<String, FilterOption> indicatorFilters;

	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Map<String, FilterOption> getIndicatorFilters() {
		return indicatorFilters;
	}
	public void setIndicatorFilters(Map<String, FilterOption> indicatorFilters) {
		this.indicatorFilters = indicatorFilters;
	}
}
