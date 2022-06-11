package org.cinow.omh.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityController {

	@Autowired
	private CommunityService communityService;

	@GetMapping(path = "/api/community-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommunityData> getCommunityData(
			@RequestParam long location, @RequestParam long locationType) {
		
		return ResponseEntity.ok(this.communityService.getCommunityData(location, locationType));
	}
}
