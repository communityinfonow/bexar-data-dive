package org.cinow.omh.dashboard;

import org.cinow.omh.filters.FilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping(path = "/api/dashboard-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DashboardData> getLocationMenu(FilterRequest filterRequest) {
		return ResponseEntity.ok(this.dashboardService.getDashboardData(filterRequest));
	}
}
