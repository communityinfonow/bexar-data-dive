package org.cinow.omh.mycommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyCommunityService {

	@Autowired
	private MyCommunityRepository myCommunityRepository;
	
	public MyCommunity getMyCommunity(long location, long locationType) {
		MyCommunity community = new MyCommunity();
		community.setLocation(null); //TODO
		community.setIndicatorData(this.myCommunityRepository.getMyCommunityData(location, locationType));

		return community;
	}
}
