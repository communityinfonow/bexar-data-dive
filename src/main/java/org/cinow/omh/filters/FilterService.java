package org.cinow.omh.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
	
	@Autowired
	private FilterRepository filterRepository;

	public Filters getFilters(String indicatorId) {
		Filters filters = new Filters();
		filters.setLocationTypeFilter(this.filterRepository.getLocationTypeFilter(indicatorId));
		filters.setLocationFilter(this.filterRepository.getLocationFilter());
		filters.setYearFilter(this.filterRepository.getYearFilter(indicatorId));
		filters.setIndicatorFilters(this.filterRepository.getIndicatorFilters(indicatorId));

		return filters;
	}
}
