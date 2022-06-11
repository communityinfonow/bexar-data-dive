package org.cinow.omh.dashboard;

import java.util.List;

import org.cinow.omh.filters.FilterRequest;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepositoryPostgresql implements DashboardRepository {

	@Override
	public List<DashboardDataLocation> getDashboardData(FilterRequest filterRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
