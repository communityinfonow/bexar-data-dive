package org.cinow.omh.dashboard;

import java.util.List;

import org.cinow.omh.filters.FilterRequest;

public interface DashboardRepository {
	
	List<DashboardDataLocation> getDashboardData(FilterRequest filterRequest);
}
