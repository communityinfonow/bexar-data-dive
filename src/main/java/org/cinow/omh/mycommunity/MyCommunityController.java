package org.cinow.omh.mycommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyCommunityController {

	@Autowired
	private MyCommunityService myCommunityService;

	@GetMapping(path = "/api/my-community", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyCommunity> getLocationMenu(
			@RequestParam long location, @RequestParam long locationType) {
		
		return ResponseEntity.ok(this.myCommunityService.getMyCommunity(location, locationType));
	}
}
