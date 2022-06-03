package org.cinow.omh.locations;

import java.util.List;

public interface LocationRepository {
	
	List<Location> findLocationByType(long typeId);

	Location findLocation(long id, long typeId);
}
