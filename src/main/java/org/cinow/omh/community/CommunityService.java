package org.cinow.omh.community;

import java.util.List;

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
	 * @return the data for the community
	 */
	public CommunityData getCommunityData(String location, String locationType) {
		CommunityData communityData = new CommunityData();
		if (!"7".equals(locationType)) {
			communityData.setLocation(this.locationRepository.findLocation(location, locationType));
			communityData.setIndicatorData(this.communityRepository.getCommunityData(location, locationType));
		} else {
			communityData.setLocation(this.locationRepository.findCustomLocation(location));
			communityData.setIndicatorData(this.communityRepository.getCustomCommunityData(location));
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
