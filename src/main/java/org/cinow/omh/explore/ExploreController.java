package org.cinow.omh.explore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for the Explore view.
 * 
 * @author brian
 */
@RestController
public class ExploreController {

	/**
	 * The explore service.
	 */
	@Autowired
	private ExploreService exploreService;
	
	/**
	 * @param dataRequest the dataRequest
	 * @return the explore data
	 */
	@PostMapping(path = "/api/explore-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExploreData> getExploreData(@RequestBody ExploreDataRequest dataRequest) {
		return ResponseEntity.ok(this.exploreService.getExploreData(dataRequest));
	}
}
