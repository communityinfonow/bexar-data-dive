package org.cinow.omh.explore;

import java.util.List;

public interface ExploreRepository {
	
	List<ExploreDataLocation> getExploreData(ExploreDataRequest dataRequest, boolean allLocations);
}
