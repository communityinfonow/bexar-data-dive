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
	List<ExploreDataLocation<ExploreDataPoint>> getExploreData(ExploreDataRequest dataRequest, boolean allLocations);

	/**
	 * Get the data for the Explore Data view for custom locations.
	 * 
	 * @param dataRequest the dataDequest
	 * @param allLocations whether to get data for all locations
	 * @return the data
	 */
	ExploreDataLocation<ExploreDataPoint> getCustomExploreData(ExploreDataRequest dataRequest, boolean allLocations);

	/**
	 * Gets the map points.
	 * 
	 * @return the map points
	 */
	List<PointCollection> getPoints();
}
