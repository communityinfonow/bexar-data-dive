package org.cinow.omh.community;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.cinow.omh.filters.FilterOption;
import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.locations.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the Community view.
 * 
 * @author brian
 */
@Service
public class CommunityService {

	/**
	 * The community repository.
	 */
	@Autowired
	private CommunityRepository communityRepository;

	/**
	 * The location repository.
	 */
	@Autowired
	private LocationRepository locationRepository;

	/**
	 * Get the data for a community.
	 * 
	 * @param location the location id
	 * @param locationType the location type id
	 * @param filterType the filter type id
	 * @return the data for the community
	 */
	public CommunityData getCommunityData(String location, String locationType, String filterType) {
		CommunityData communityData = new CommunityData();
		if (!"7".equals(locationType)) {
			communityData.setLocation(this.locationRepository.findLocation(location, locationType));
			communityData.setIndicatorData(this.communityRepository.getCommunityData(location, locationType, filterType));
		} else {
			communityData.setLocation(this.locationRepository.findCustomLocation(location));
			communityData.setIndicatorData(this.communityRepository.getCustomCommunityData(location, filterType));
		}
		communityData.getIndicatorData().stream().forEach(data -> {
			if (data.getCategory().getParentCategoryId() != null) {
				communityData.getIndicatorData()
					.stream()
					.filter(c -> c.getCategory().getId().equals(data.getCategory().getParentCategoryId()))
					.findFirst()
					.get().getSubcategories().add(data);
			}
		});
		communityData.setIndicatorData(communityData.getIndicatorData()
			.stream()
			.filter(data -> data.getCategory().getParentCategoryId() == null)
			.collect(java.util.stream.Collectors.toList()));

		if (Arrays.asList("1", "2", "3").contains(filterType)) {
			CommunityDataCategory filterCategory = communityData.getIndicatorData()
				.stream()
				.filter(data -> data.getCategory().getId().equals("3"))
				.findFirst()
				.get()
				.getSubcategories()
				.stream()
				.filter(data -> (data.getCategory().getId().equals("9") && filterType.equals("2"))
					|| (data.getCategory().getId().equals("10") && filterType.equals("1"))
					|| (data.getCategory().getId().equals("11") && filterType.equals("3")))
				.findFirst()
				.get();
			
			CommunityDataIndicator mergedIndicator = new CommunityDataIndicator();
			mergedIndicator.setIndicator(new Indicator());
			mergedIndicator.getIndicator().setAggregable(true);
			mergedIndicator.getIndicator().setCategoryId(filterCategory.getCategory().getId());
			mergedIndicator.getIndicator().setDisplay(filterCategory.getIndicators().get(0).getIndicator().isDisplay());
			mergedIndicator.getIndicator().setFeatured(false);
			mergedIndicator.getIndicator().setHasData(filterCategory.getIndicators().get(0).getIndicator().isHasData());
			mergedIndicator.getIndicator().setRatePer(0);
			mergedIndicator.getIndicator().setSourceId(filterCategory.getIndicators().get(0).getIndicator().getSourceId());
			mergedIndicator.getIndicator().setTypeId(filterCategory.getIndicators().get(0).getIndicator().getTypeId());
			mergedIndicator.setIndicatorType(filterCategory.getIndicators().get(0).getIndicatorType());
			mergedIndicator.setSource(filterCategory.getIndicators().get(0).getSource());
			mergedIndicator.setYear(filterCategory.getIndicators().get(0).getYear());
			mergedIndicator.setDemographicData(filterCategory.getIndicators()
				.stream()
				.map(indicator -> {
					CommunityDataPoint dataPoint = indicator.getDemographicData().get(0);
					dataPoint.setDemographicFilter(new FilterOption());
					dataPoint.getDemographicFilter().setId(indicator.getIndicator().getId());
					dataPoint.getDemographicFilter().setName_en(indicator.getIndicator().getName_en());
					dataPoint.getDemographicFilter().setName_es(indicator.getIndicator().getName_es());
					return dataPoint;
				})
				.collect(Collectors.toList()));
			
			filterCategory.setIndicators(Arrays.asList(mergedIndicator));
		}

		return communityData;
	}

	/**
	 * Get the locations for a community.
	 * 
	 * @param locationType the location type id
	 * @return the locations for the community
	 */
	public List<CommunityLocation> getCommunityLocations(String locationType) {
		return this.communityRepository.getCommunityLocations(locationType);
	}

	/**
	 * Get the location for a community.
	 * 
	 * @param location the location id
	 * @param locationType the location type id
	 * @return the location for the community
	 */
	public CommunityLocation getCommunityLocation(String location, String locationType) {
		if (!"7".equals(locationType)) {
			return this.communityRepository.getCommunityLocation(location, locationType);
		} else {
			return this.communityRepository.getCustomCommunityLocation(location);
		}
	}
}
