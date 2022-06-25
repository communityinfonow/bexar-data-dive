package org.cinow.omh.dashboard;
import java.util.stream.Collectors;

import org.cinow.omh.filters.Filter;
import org.cinow.omh.filters.FilterRepository;
import org.cinow.omh.filters.FilterRequest;
import org.cinow.omh.filters.Filters;
import org.cinow.omh.indicators.IndicatorRepository;
import org.cinow.omh.sources.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

	@Autowired
	private DashboardRepository dashboardRepository;

	@Autowired
	private IndicatorRepository indicatorRepository;

	@Autowired
	private SourceRepository sourceRepository;

	@Autowired
	private FilterRepository filterRepository;
	
	public DashboardData getDashboardData(FilterRequest filterRequest) {
		DashboardData dashboardData = new DashboardData();
		dashboardData.setIndicator(this.indicatorRepository.getIndicator(filterRequest.getIndicator()));
		dashboardData.setSource(this.sourceRepository.getSourceByIndicator(filterRequest.getIndicator()));
		dashboardData.setFilters(this.getIndicatorFilters(filterRequest));
		dashboardData.setLocationData(this.dashboardRepository.getDashboardData(filterRequest));

		return dashboardData;
	}

	private Filters getIndicatorFilters(FilterRequest filterRequest) {
		Filters filters = new Filters();
		filters.setLocationTypeFilter(this.filterRepository.getLocationTypeFilter());
		filters.getLocationTypeFilter().setOptions(filters.getLocationTypeFilter().getOptions()
			.stream()
			.filter(o -> o.getId().equals(filterRequest.getLocationType()))
			.collect(Collectors.toList()));
		filters.setLocationFilter(this.filterRepository.getLocationFilter());
		filters.getLocationFilter().setOptions(filters.getLocationFilter().getOptions()
			.stream()
			.filter(o -> o.getId().equals(filterRequest.getLocation()) && o.getTypeId().equals(filterRequest.getLocationType()))
			.collect(Collectors.toList()));
		filters.setYearFilter(this.filterRepository.getYearFilter(filterRequest.getIndicator()));
		filters.getYearFilter().setOptions(filters.getYearFilter().getOptions()
			.stream()
			.filter(o -> o.getId().equals(filterRequest.getYear()))
			.collect(Collectors.toList()));
		filters.setIndicatorFilters(this.filterRepository.getIndicatorFilters(filterRequest.getIndicator()));
		for (int i = 0; i < filterRequest.getFilterTypes().size(); i++) {
			final int j = i;
			Filter filter = filters.getIndicatorFilters()
				.stream()
				.filter(f -> f.getType().getId().equals(filterRequest.getFilterTypes().get(j)))
				.findFirst()
				.get();
			filter.setOptions(filter.getOptions()
				.stream()
				.filter(o -> o.getId().equals(filterRequest.getFilterOptions().get(j)))
				.collect(Collectors.toList()));
		}

		return filters;
	}
}
