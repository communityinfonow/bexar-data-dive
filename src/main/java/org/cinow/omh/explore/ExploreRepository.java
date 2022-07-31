package org.cinow.omh.explore;

import java.util.List;

/**
 * Repository for the Explore Data view.
 */
public interface ExploreRepository {
	
	/**
	 * Get the data for the Explore Data view.
	 * 
	 * @param dataRequest the dataDequest
	 * @param allLocations whether to get data for all locations
	 * @return the data
	 */
	List<ExploreDataLocation> getExploreData(ExploreDataRequest dataRequest, boolean allLocations);
}
