package org.cinow.omh.filters;

import java.util.List;

public class FilterRequest {
	
	private long indicator;
	private long locationType;
	private long location;
	private String year;
	private List<Long> filterTypes;
	private List<Long> filterOptions;
	private long comparisonType;
	private List<Long> comparisonOptions;

	public long getIndicator() {
		return indicator;
	}
	public void setIndicator(long indicator) {
		this.indicator = indicator;
	}
	public long getLocationType() {
		return locationType;
	}
	public void setLocationType(long locationType) {
		this.locationType = locationType;
	}
	public long getLocation() {
		return location;
	}
	public void setLocation(long location) {
		this.location = location;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public List<Long> getFilterTypes() {
		return filterTypes;
	}
	public void setFilterTypes(List<Long> filterTypes) {
		this.filterTypes = filterTypes;
	}
	public List<Long> getFilterOptions() {
		return filterOptions;
	}
	public void setFilterOptions(List<Long> filterOptions) {
		this.filterOptions = filterOptions;
	}
	public long getComparisonType() {
		return comparisonType;
	}
	public void setComparisonType(long comparisonType) {
		this.comparisonType = comparisonType;
	}
	public List<Long> getComparisonOptions() {
		return comparisonOptions;
	}
	public void setComparisonOptions(List<Long> comparisonOptions) {
		this.comparisonOptions = comparisonOptions;
	}
}
