package org.cinow.omh.explore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExploreController {

	@Autowired
	private ExploreService exploreService;
	
	@PostMapping(path = "/api/explore-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExploreData> getExploreData(@RequestBody ExploreDataRequest dataRequest) {
		return ResponseEntity.ok(this.exploreService.getExploreData(dataRequest));
	}
}
