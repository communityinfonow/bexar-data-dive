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

	/**
	 * Gets the compatible filter type ids.
	 * 
	 * @param indicatorId the indicator id
	 * @param filterTypeId the filter type id
	 * @return the compatible filter type ids
	 */
	List<List<String>> getCompatibleFitlerTypeIds(String indicatorId, String filterTypeId);

	/**
	 * Gets the location type years.
	 * 
	 * @param indicatorId the indicator id
	 * @return the location types and years
	 */
	Map<String, List<String>> getLocationTypeYears(String indicatorId);

	/**
	 * Finds the filter types.
	 * 
	 * @return the filter types
	 */
	List<FilterType> findFilterTypes();
	
	/**
	 * Finds the filter options.
	 * 
	 * @return the filter options
	 */
	List<FilterOption> findFilterOptions();

	/**
	 * Adds a filter option.
	 * 
	 * @param filterOption the filter option
	 */
	void addFilterOption(FilterOption filterOption, String username);

	/**
	 * Updates a filter option.
	 * 
	 * @param filterOption the filter option
	 */
	void updateFilterOption(FilterOption filterOption, String username);
}
