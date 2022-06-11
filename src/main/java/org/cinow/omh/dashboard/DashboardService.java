package org.cinow.omh.dashboard;

import org.cinow.omh.filters.FilterRequest;
import org.cinow.omh.indicators.IndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

	@Autowired
	private DashboardRepository dashboardRepository;

	//TODO: build a source repository and add a source_id column to the indicator table since it is a 1:1 mapping and remove that from the 'data' table
	//TODO: but first, run by Jeremy one last time that ind:src is a 1:1 because we can drop that from the data sharing format if true. and if not true or could change, don't do 1:1 setup noted above...
	//@Autowired
	//private SourceRepository sourceRepository;
	
	//public DashboardData getDashboardData(FilterRequest filterRequest) {
	//	DashboardData dashboardData = new DashboardData();
	//	dashboardData.setSource(this.sourceRepository.getSource(filterRequest.getIndicator());
	//	dashboardData.setLocationData(this.dashboardRepository.getDashboardData(filterRequest));
	//}
}
