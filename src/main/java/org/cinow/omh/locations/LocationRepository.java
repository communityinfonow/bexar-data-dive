package org.cinow.omh.locations;

import java.util.List;

/**
 * Repository for locations.
 * 
 * @author brian
 */
public interface LocationRepository {
	
	/**
	 * Get locations by type.
	 * 
	 * @param typeId the type id
	 * @return the locations
	 */
	List<Location> findLocationByType(String typeId);

	/**
	 * Get location.
	 *  
	 * @param id the id
	 * @param typeId the type id
	 * @return the location
	 */
	Location findLocation(String id, String typeId);

	/**
	 * Saves a custom location
	 * 
	 * @param location the custom location
	 */
	void saveCustomLocation(CustomLocation location);

	Location findCustomLocation(String id);

	String findCustomLocationType(String id);

	List<String> findCustomLocationIds(String id);
}
