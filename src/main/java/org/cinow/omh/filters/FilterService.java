package org.cinow.omh.filters;

import java.util.List;
import java.util.stream.Collectors;

import org.cinow.omh.admin.AdminRepository;
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
	 * The admin repository
	 */
	@Autowired
	private AdminRepository adminRepository;

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
		filters.getLocationFilter().setOptions(filters.getLocationFilter().getOptions()
			.stream()
			.filter(o -> filters.getLocationTypeFilter().getOptions().stream().anyMatch(lt -> lt.getId().equals(o.getTypeId())))
			.collect(Collectors.toList()));
		filters.setYearFilter(this.filterRepository.getYearFilter(indicatorId));
		filters.setIndicatorFilters(this.filterRepository.getIndicatorFilters(indicatorId));

		filters.getIndicatorFilters().stream().forEach(filter -> {
			filter.getOptions().add(0, allFilterOption());
			filter.setCompatibleFilterTypeIds(this.filterRepository.getCompatibleFitlerTypeIds(indicatorId, filter.getType().getId()));
		});
		filters.setLocationTypeYears(this.filterRepository.getLocationTypeYears(indicatorId));

		return filters;
	}

	/**
	 * Finds the filter types.
	 * 
	 * @return the filter types
	 */
	public List<FilterType> findFilterTypes() {
		return this.filterRepository.findFilterTypes();
	}

	/**
	 * Finds the filter options.
	 * 
	 * @return the filter options
	 */
	public List<FilterOption> findFilterOptions() {
		return this.filterRepository.findFilterOptions();
	}

	/**
	 * Adds a filter option.
	 * 
	 * @param filterOption the filter option
	 */
	public void addFilterOption(FilterOption filterOption, String username) {
		this.filterRepository.addFilterOption(filterOption, username);
	}

	/**
	 * Updates a filter option.
	 * 
	 * @param filterOption the filter option
	 */
	public void updateFilterOption(FilterOption filterOption, String username) {
		this.filterRepository.updateFilterOption(filterOption, username);
		this.adminRepository.refreshMaterializedViews();
	}

	private FilterOption allFilterOption() {
		FilterOption option = new FilterOption();
		option.setId(null);
		option.setName_en("All");
		option.setName_es("Todos");
		return option;
	}
}
