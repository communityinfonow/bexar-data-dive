package org.cinow.omh.explore;

import java.util.List;

public interface ExploreRepository {
	
	List<ExploreDataItem> getExploreData(String indicator);
}
