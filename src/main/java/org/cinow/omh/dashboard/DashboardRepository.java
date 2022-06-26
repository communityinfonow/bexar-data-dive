package org.cinow.omh.dashboard;

import java.util.List;

public interface DashboardRepository {
	
	List<DashboardDataLocation> getDashboardData(DashboardDataRequest dataRequest, boolean allLocations);
}
