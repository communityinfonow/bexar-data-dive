package org.cinow.omh.explore;

import java.util.Map;

import org.cinow.omh.filters.FilterOption;

/**
 * This object represents a request to filter data in the Explore Data view.
 * 
 * @author brian
 */
public class FilterRequest {
	
	/**
	 * The location type.
	 */
	private String locationType;

	/**
	 * The location.
	 */
	private String location;

	/**
	 * The year.
	 */
	private String year;

	/**
	 * The indicator filters.
	 */
	private Map<String, FilterOption> indicatorFilters;

	/**
	 * @return the locationType
	 */
	public String getLocationType() {
		return locationType;
	}

	/**
	 * @param locationType the locationType to set
	 */
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the indicatorFilters
	 */
	public Map<String, FilterOption> getIndicatorFilters() {
		return indicatorFilters;
	}

	/**
	 * @param indicatorFilters the indicatorFilters to set
	 */
	public void setIndicatorFilters(Map<String, FilterOption> indicatorFilters) {
		this.indicatorFilters = indicatorFilters;
	}
}
