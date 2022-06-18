package org.cinow.omh.dashboard;

import org.cinow.omh.filters.FilterRequest;

public interface DashboardRepository {
	
	DashboardDataLocation getDashboardData(FilterRequest filterRequest);
}
