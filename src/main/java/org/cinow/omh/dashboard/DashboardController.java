package org.cinow.omh.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;
	
	@PostMapping(path = "/api/dashboard-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DashboardData> getDashboardData(@RequestBody DashboardDataRequest dataRequest) {
		return ResponseEntity.ok(this.dashboardService.getDashboardData(dataRequest));
	}
}
