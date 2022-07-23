package org.cinow.omh.community;

import java.util.List;

import org.cinow.omh.locations.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {

	@Autowired
	private CommunityRepository communityRepository;

	@Autowired
	private LocationRepository locationRepository;
	
	public CommunityData getCommunityData(String location, String locationType) {
		CommunityData communityData = new CommunityData();
		communityData.setLocation(this.locationRepository.findLocation(location, locationType));
		communityData.setIndicatorData(this.communityRepository.getCommunityData(location, locationType));

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

	public List<CommunityLocation> getCommunityLocations(String locationType) {
		return this.communityRepository.getCommunityLocations(locationType);
	}

	public CommunityLocation getCommunityLocation(String location, String locationType) {
		return this.communityRepository.getCommunityLocation(location, locationType);
	}
}
