package org.cinow.omh.filters;

import java.util.List;

/**
 * This object represents the full set of filters.
 * 
 * @author brian
 */
public class Filters {
	
	/**
	 * The location type filter.
	 */
	private Filter locationTypeFilter;

	/**
	 * The location filter.
	 */
	private Filter locationFilter;

	/**
	 * The year filter.
	 */
	private Filter yearFilter;

	/**
	 * The indicator filters.
	 */
	private List<IndicatorFilter> indicatorFilters;
	
	/**
	 * @return the locationTypeFilter
	 */
	public Filter getLocationTypeFilter() {
		return locationTypeFilter;
	}

	/**
	 * @param locationTypeFilter the locationTypeFilter to set
	 */
	public void setLocationTypeFilter(Filter locationTypeFilter) {
		this.locationTypeFilter = locationTypeFilter;
	}

	/**
	 * @return the locationFilter
	 */
	public Filter getLocationFilter() {
		return locationFilter;
	}

	/**
	 * @param locationFilter the locationFilter to set
	 */
	public void setLocationFilter(Filter locationFilter) {
		this.locationFilter = locationFilter;
	}

	/**
	 * @return the yearFilter
	 */
	public Filter getYearFilter() {
		return yearFilter;
	}

	/**
	 * @param yearFilter the yearFilter to set
	 */
	public void setYearFilter(Filter yearFilter) {
		this.yearFilter = yearFilter;
	}

	/**
	 * @return the indicatorFilters
	 */
	public List<IndicatorFilter> getIndicatorFilters() {
		return indicatorFilters;
	}

	/**
	 * @param indicatorFilters the indicatorFilters to set
	 */
	public void setIndicatorFilters(List<IndicatorFilter> indicatorFilters) {
		this.indicatorFilters = indicatorFilters;
	}
}
