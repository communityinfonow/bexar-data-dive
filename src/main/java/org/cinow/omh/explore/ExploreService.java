package org.cinow.omh.explore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.cinow.omh.filters.Filter;
import org.cinow.omh.filters.FilterOption;
import org.cinow.omh.filters.FilterRepository;
import org.cinow.omh.filters.Filters;
import org.cinow.omh.indicators.IndicatorRepository;
import org.cinow.omh.locations.Location;
import org.cinow.omh.locations.LocationRepository;
import org.cinow.omh.locations.LocationType;
import org.cinow.omh.locations.LocationTypeRepository;
import org.cinow.omh.sources.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the Explore Data view.
 */
@Service
public class ExploreService {

	/**
	 * The explore repository.
	 */
	@Autowired
	private ExploreRepository exploreRepository;

	/**
	 * The indicator repository.
	 */
	@Autowired
	private IndicatorRepository indicatorRepository;

	/**
	 * The source repository.
	 */
	@Autowired
	private SourceRepository sourceRepository;

	/**
	 * The filter repository.
	 */
	@Autowired
	private FilterRepository filterRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private LocationTypeRepository locationTypeRepository;
	
	/**
	 * Get the data for the Explore Data view.
	 * 
	 * @param dataRequest the dataDequest
	 * @return the data
	 */
	public ExploreData getExploreData(ExploreDataRequest dataRequest) {
		ExploreData exploreData = new ExploreData();
		exploreData.setIndicator(this.indicatorRepository.getIndicator(dataRequest.getIndicator()));
		exploreData.setCategory(this.indicatorRepository.getIndicatorCategory(dataRequest.getIndicator()));
		exploreData.setSource(this.sourceRepository.getSourceByIndicator(dataRequest.getIndicator()));
		exploreData.setFilters(this.getIndicatorFilters(dataRequest));
		if (!"7".equals(dataRequest.getFilters().getLocationType())){
			exploreData.setLocationData(this.exploreRepository.getExploreData(dataRequest, true));
		} else {
			List<ExploreDataLocation<ExploreDataPoint>> locationData = new ArrayList<>();
			locationData.add(this.exploreRepository.getCustomExploreData(dataRequest, false));
			ExploreDataRequest matchedLocationTypeRequest = new ExploreDataRequest();
			matchedLocationTypeRequest.setIndicator(dataRequest.getIndicator());
			matchedLocationTypeRequest.setFilters(new FilterRequest());
			matchedLocationTypeRequest.getFilters().setLocationType(this.locationRepository.findCustomLocationType(dataRequest.getFilters().getLocation()));
			matchedLocationTypeRequest.getFilters().setLocation(null);
			matchedLocationTypeRequest.getFilters().setYear(dataRequest.getFilters().getYear());
			matchedLocationTypeRequest.getFilters().setIndicatorFilters(dataRequest.getFilters().getIndicatorFilters());
			matchedLocationTypeRequest.setComparisons(dataRequest.getComparisons());

			List<String> locationIds = this.locationRepository.findCustomLocationIds(dataRequest.getFilters().getLocation());
			locationData.addAll(this.exploreRepository.getExploreData(matchedLocationTypeRequest, true)
				.stream()
				.filter(data -> !locationIds.contains(data.getLocation().getId()))
				.collect(Collectors.toList()));
			exploreData.setLocationData(locationData);
		}

		if (dataRequest.getComparisons() != null) {
			exploreData.setCompareData(new ArrayList<>());
			if (dataRequest.getComparisons().getType().getId().equals("i")) { 
				for (FilterOption option : dataRequest.getComparisons().getOptions()) {
					dataRequest.setIndicator(option.getId());
					exploreData.getCompareData().addAll(this.exploreRepository.getExploreData(dataRequest, false));
				}
			} else if (dataRequest.getComparisons().getType().getId().equals("l")) {
				for (FilterOption option : dataRequest.getComparisons().getOptions()) {
					dataRequest.getFilters().setLocation(option.getId());
					dataRequest.getFilters().setLocationType(option.getTypeId());
					if (!"7".equals(dataRequest.getFilters().getLocationType())) {
						exploreData.getCompareData().addAll(this.exploreRepository.getExploreData(dataRequest, false));
					} else {
						exploreData.getCompareData().add(this.exploreRepository.getCustomExploreData(dataRequest, false));
					}
					
				}		
			} else if (dataRequest.getComparisons().getType().getId().equals("y")) { 
				for (FilterOption option : dataRequest.getComparisons().getOptions()) {
					dataRequest.getFilters().setYear(option.getId());
					if (!"7".equals(dataRequest.getFilters().getLocationType())) {
						exploreData.getCompareData().addAll(this.exploreRepository.getExploreData(dataRequest, false));
					} else {
						exploreData.getCompareData().add(this.exploreRepository.getCustomExploreData(dataRequest, false));
					}
				}
			} else {
				for (FilterOption option : dataRequest.getComparisons().getOptions()) {
					dataRequest.getFilters().getIndicatorFilters().put(dataRequest.getComparisons().getType().getId(), option);
					if (!"7".equals(dataRequest.getFilters().getLocationType())) {
						exploreData.getCompareData().addAll(this.exploreRepository.getExploreData(dataRequest, false));
					} else {
						exploreData.getCompareData().add(this.exploreRepository.getCustomExploreData(dataRequest, false));
					}
				}
			}
		}

		if (dataRequest.getTrendComparisons() != null) {
			exploreData.setTrendCompareData(new ArrayList<>());
			for (FilterOption option : dataRequest.getTrendComparisons().getOptions()) {
				dataRequest.getFilters().getIndicatorFilters().put(dataRequest.getTrendComparisons().getType().getId(), option);
				if (!"7".equals(dataRequest.getFilters().getLocationType())) {
					exploreData.getTrendCompareData().addAll(this.exploreRepository.getExploreData(dataRequest, false));
				} else {
					exploreData.getTrendCompareData().add(this.exploreRepository.getCustomExploreData(dataRequest, false));
				}
			}
		}

		return exploreData;
	}

	/**
	 * Get the filters for a data request.
	 * 
	 * @param dataRequest the dataDequest
	 * @return the filters
	 */
	private Filters getIndicatorFilters(ExploreDataRequest dataRequest) {
		Filters filters = new Filters();
		filters.setLocationTypeFilter(this.filterRepository.getLocationTypeFilter(dataRequest.getIndicator()));
		filters.setLocationFilter(this.filterRepository.getLocationFilter());
		if ("7".equals(dataRequest.getFilters().getLocationType())) {
			Location customLocation = this.locationRepository.findCustomLocation(dataRequest.getFilters().getLocation());
			LocationType customLocationType = this.locationTypeRepository.findLocationTypes().stream()
				.filter(lt -> lt.getId().equals(customLocation.getTypeId()))
				.findFirst()
				.orElse(null);
			FilterOption locationTypeFilterOption = new FilterOption();
			locationTypeFilterOption.setId(dataRequest.getFilters().getLocationType());
			locationTypeFilterOption.setName_en(customLocationType.getName_en());
			locationTypeFilterOption.setName_es(customLocationType.getName_es());
			filters.getLocationTypeFilter().setOptions(Arrays.asList(locationTypeFilterOption));
			FilterOption locationFilterOption = new FilterOption();
			locationFilterOption.setId(dataRequest.getFilters().getLocation());
			locationFilterOption.setName_en(customLocation.getName_en());
			locationFilterOption.setName_es(customLocation.getName_es());
			filters.getLocationFilter().setOptions(Arrays.asList(locationFilterOption));
		} else {
			filters.getLocationTypeFilter().setOptions(filters.getLocationTypeFilter().getOptions()
				.stream()
				.filter(o -> o.getId().equals(dataRequest.getFilters().getLocationType()))
				.collect(Collectors.toList()));
			filters.getLocationFilter().setOptions(filters.getLocationFilter().getOptions()
				.stream()
				.filter(o -> o.getId().equals(dataRequest.getFilters().getLocation()) && o.getTypeId().equals(dataRequest.getFilters().getLocationType()))
				.collect(Collectors.toList()));
		}
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

	public List<PointCollection> getPoints() {
		return this.exploreRepository.getPoints();
	}

	public List<ExploreLocationReportItem> getLocationReport(int locationTypeId, String locationId) {
		return this.exploreRepository.getLocationReport(locationTypeId, locationId);
	}
}
