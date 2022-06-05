package org.cinow.omh.filters;

import java.util.List;

public interface FilterRepository {
	
	List<Filter> getFilters(long indicatorId);
}
