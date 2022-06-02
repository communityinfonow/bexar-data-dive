package org.cinow.omh.mycommunity;

import java.util.List;

public interface MyCommunityRepository {
	
	List<MyCommunityDataCategory> getMyCommunityData(long location, long locationType);
}
