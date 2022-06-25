package org.cinow.omh.locations;

import java.util.List;

public interface LocationRepository {
	
	List<Location> findLocationByType(String typeId);

	Location findLocation(String id, String typeId);
}
