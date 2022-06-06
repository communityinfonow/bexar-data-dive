package org.cinow.omh.filters;

import java.util.List;

public interface FilterRepository {

	Filter getLocationTypeFilter();

	Filter getLocationFilter();

	Filter getYearFilter(long indicatorId);
	
	List<Filter> getIndicatorFilters(long indicatorId);
}
