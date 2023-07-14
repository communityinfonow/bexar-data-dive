package org.cinow.omh.community;

import java.util.List;

/**
 * Repository for the Community view.
 * 
 * @author brian
 */
public interface CommunityRepository {
	
	/**
	 * Get the data for a community.
	 * 
	 * @param location the location id
	 * @param locationType the location type id
	 * @param filterType the filter type id
	 * @return the data for the community
	 */
	List<CommunityDataCategory> getCommunityData(String location, String locationType, String filterType);

	/**
	 * Get the data for a custom community.
	 * 
	 * @param location the location id
	 * @param filterType the filter type id
	 * @return the data for the custom community
	 */
	List<CommunityDataCategory> getCustomCommunityData(String location, String filterType);

	/**
	 * Get the locations for a community.
	 * 
	 * @param locationType the location type id
	 * @return the locations for the community
	 */
	List<CommunityLocation> getCommunityLocations(String locationType);

	/**
	 * Get the location for a community.
	 * 
	 * @param location the location id
	 * @param locationType the location type id
	 * @return the location for the community
	 */
	CommunityLocation getCommunityLocation(String location, String locationType);

	/**
	 * Get the location for a custom community.
	 * 
	 * @param location the location id
	 * @return the location for the custom community
	 */
	CommunityLocation getCustomCommunityLocation(String location);
}
