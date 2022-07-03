package org.cinow.omh.filters;

import java.util.List;

public interface FilterRepository {

	Filter getLocationTypeFilter(String indicatorId);

	Filter getLocationFilter();

	Filter getYearFilter(String indicatorId);
	
	List<Filter> getIndicatorFilters(String indicatorId);
}
