package org.cinow.omh.community;

import java.util.List;

public interface CommunityRepository {
	
	List<CommunityDataCategory> getCommunityData(String location, String locationType);
}
