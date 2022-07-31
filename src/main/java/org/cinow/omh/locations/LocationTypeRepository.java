package org.cinow.omh.locations;

import java.util.List;

/**
 * Repository for location types.
 * 
 * @author brian
 */
public interface LocationTypeRepository {
	
	/**
	 * Finds location types.
	 * 
	 * @return location types
	 */
	List<LocationType> findLocationTypes();
}
