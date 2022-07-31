package org.cinow.omh.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for filters.
 * 
 * @author brian
 */
@Service
public class FilterService {
	
	/**
	 * The filter repository.
	 */
	@Autowired
	private FilterRepository filterRepository;

	/**
	 * Get the filters.
	 * 
	 * @param indicatorId the indicator id
	 * @return the filters
	 */
	public Filters getFilters(String indicatorId) {
		Filters filters = new Filters();
		filters.setLocationTypeFilter(this.filterRepository.getLocationTypeFilter(indicatorId));
		filters.setLocationFilter(this.filterRepository.getLocationFilter());
		filters.setYearFilter(this.filterRepository.getYearFilter(indicatorId));
		filters.setIndicatorFilters(this.filterRepository.getIndicatorFilters(indicatorId));

		return filters;
	}
}
