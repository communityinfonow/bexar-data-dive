package org.cinow.omh.filters;

import java.util.List;

public class FilterRequest {
	
	private String indicator;
	private String locationType;
	private String location;
	private String year;
	private List<String> filterTypes;
	private List<String> filterOptions;
	private String comparisonType;
	private List<String> comparisonOptions;

	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
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
	public List<String> getFilterTypes() {
		return filterTypes;
	}
	public void setFilterTypes(List<String> filterTypes) {
		this.filterTypes = filterTypes;
	}
	public List<String> getFilterOptions() {
		return filterOptions;
	}
	public void setFilterOptions(List<String> filterOptions) {
		this.filterOptions = filterOptions;
	}
	public String getComparisonType() {
		return comparisonType;
	}
	public void setComparisonType(String comparisonType) {
		this.comparisonType = comparisonType;
	}
	public List<String> getComparisonOptions() {
		return comparisonOptions;
	}
	public void setComparisonOptions(List<String> comparisonOptions) {
		this.comparisonOptions = comparisonOptions;
	}
}
