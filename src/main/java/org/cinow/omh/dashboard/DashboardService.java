package org.cinow.omh.dashboard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.cinow.omh.filters.Filter;
import org.cinow.omh.filters.FilterOption;
import org.cinow.omh.filters.FilterRepository;
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
	
	public DashboardData getDashboardData(DashboardDataRequest dataRequest) {
		DashboardData dashboardData = new DashboardData();
		dashboardData.setIndicator(this.indicatorRepository.getIndicator(dataRequest.getIndicator()));
		dashboardData.setSource(this.sourceRepository.getSourceByIndicator(dataRequest.getIndicator()));
		dashboardData.setFilters(this.getIndicatorFilters(dataRequest));
		dashboardData.setLocationData(this.dashboardRepository.getDashboardData(dataRequest, true));

		if (dataRequest.getComparisons() != null) {
			dashboardData.setCompareData(new ArrayList<>());
			if (dataRequest.getComparisons().getType().getName_en().equals("Location")) {
				for (FilterOption option : dataRequest.getComparisons().getOptions()) {
					dataRequest.getFilters().setLocation(option.getId());
					dataRequest.getFilters().setLocationType(option.getTypeId());
					dashboardData.getCompareData().addAll(this.dashboardRepository.getDashboardData(dataRequest, false));
				}		
			} else {
				for (FilterOption option : dataRequest.getComparisons().getOptions()) {
					dataRequest.getFilters().getIndicatorFilters().put(dataRequest.getComparisons().getType().getId(), option);
					dashboardData.getCompareData().addAll(this.dashboardRepository.getDashboardData(dataRequest, false));
				}
			}
		}

		return dashboardData;
	}

	private Filters getIndicatorFilters(DashboardDataRequest dataRequest) {
		Filters filters = new Filters();
		filters.setLocationTypeFilter(this.filterRepository.getLocationTypeFilter());
		filters.getLocationTypeFilter().setOptions(filters.getLocationTypeFilter().getOptions()
			.stream()
			.filter(o -> o.getId().equals(dataRequest.getFilters().getLocationType()))
			.collect(Collectors.toList()));
		filters.setLocationFilter(this.filterRepository.getLocationFilter());
		filters.getLocationFilter().setOptions(filters.getLocationFilter().getOptions()
			.stream()
			.filter(o -> o.getId().equals(dataRequest.getFilters().getLocation()) && o.getTypeId().equals(dataRequest.getFilters().getLocationType()))
			.collect(Collectors.toList()));
		filters.setYearFilter(this.filterRepository.getYearFilter(dataRequest.getIndicator()));
		filters.getYearFilter().setOptions(filters.getYearFilter().getOptions()
			.stream()
			.filter(o -> o.getId().equals(dataRequest.getFilters().getYear()))
			.collect(Collectors.toList()));
		filters.setIndicatorFilters(this.filterRepository.getIndicatorFilters(dataRequest.getIndicator()));
		for (Filter filter : filters.getIndicatorFilters()) {
			filter.setOptions(Arrays.asList(dataRequest.getFilters().getIndicatorFilters().get(filter.getType().getId())));
		}

		return filters;
	}
}
