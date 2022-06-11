package org.cinow.omh.community;

import java.util.List;

public interface CommunityRepository {
	
	List<CommunityDataCategory> getCommunityData(long location, long locationType);
}
