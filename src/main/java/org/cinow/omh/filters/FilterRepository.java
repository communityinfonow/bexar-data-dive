package org.cinow.omh.filters;

import java.util.List;
import java.util.Map;

/**
 * Repository for filters.
 *
 * @author brian
 */
public interface FilterRepository {

	/**
	 * Get the location type filter.
	 *
	 * @param indicatorId the indicator id
	 * @return the filter
	 */
	Filter getLocationTypeFilter(String indicatorId);

	/**
	 * Get the location filter.
	 *
	 * @return the filter
	 */
	Filter getLocationFilter();

	/**
	 * Get the year filter.
	 *
	 * @param indicatorId the indicator id
	 * @return the filter
	 */
	Filter getYearFilter(String indicatorId);
	
	/**
	 * Get the indicator filters.
	 *
	 * @param indicatorId the indicator id
	 * @return the filters
	 */
	List<IndicatorFilter> getIndicatorFilters(String indicatorId);

	List<List<String>> getCompatibleFitlerTypeIds(String indicatorId, String filterTypeId);

	Map<String, List<String>> getLocationTypeYears(String indicatorId);
}
