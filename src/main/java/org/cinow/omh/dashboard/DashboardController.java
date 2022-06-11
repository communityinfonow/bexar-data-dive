package org.cinow.omh.dashboard;

import org.cinow.omh.filters.FilterRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
	
	@GetMapping(path = "/api/dashboard-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getLocationMenu(FilterRequest filterRequest) {
		return ResponseEntity.ok("okay");
	}
}
