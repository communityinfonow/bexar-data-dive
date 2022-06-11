package org.cinow.omh.community;

import org.cinow.omh.locations.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {

	@Autowired
	private CommunityRepository communityRepository;

	@Autowired
	private LocationRepository locationRepository;
	
	public CommunityData getCommunityData(long location, long locationType) {
		CommunityData communityData = new CommunityData();
		communityData.setLocation(this.locationRepository.findLocation(location, locationType));
		communityData.setIndicatorData(this.communityRepository.getCommunityData(location, locationType));

		return communityData;
	}
}
