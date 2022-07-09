package org.cinow.omh.explore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExploreController {
	
	@Autowired
	private ExploreService exploreService;
	
	@GetMapping(path = "/api/explore-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExploreData> getDashboardData(@RequestParam String indicator) {
		return ResponseEntity.ok(this.exploreService.getExploreData(indicator));
	}
}
