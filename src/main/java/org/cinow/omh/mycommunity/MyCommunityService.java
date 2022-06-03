package org.cinow.omh.mycommunity;

import org.cinow.omh.locations.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyCommunityService {

	@Autowired
	private MyCommunityRepository myCommunityRepository;

	@Autowired
	private LocationRepository locationRepository;
	
	public MyCommunity getMyCommunity(long location, long locationType) {
		MyCommunity community = new MyCommunity();
		community.setLocation(this.locationRepository.findLocation(location, locationType));
		community.setIndicatorData(this.myCommunityRepository.getMyCommunityData(location, locationType));

		return community;
	}
}
