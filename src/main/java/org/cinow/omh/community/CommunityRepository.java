package org.cinow.omh.community;

import java.util.List;

public interface CommunityRepository {
	
	List<CommunityDataCategory> getCommunityData(String location, String locationType);

	List<CommunityLocation> getCommunityLocations(String locationType);

	CommunityLocation getCommunityLocation(String location, String locationType);
}
