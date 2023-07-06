package org.cinow.omh.community;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for the Community view.
 * 
 * @author brian
 */
@RestController
public class CommunityController {

	/**
	 * The community service.
	 */
	@Autowired
	private CommunityService communityService;

	/**
	 * @param location the location
	 * @param locationType the locationType
	 * @param filterType the filterType
	 * @return the community data
	 */
	@GetMapping(path = "/api/community-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommunityData> getCommunityData(
			@RequestParam String location, @RequestParam String locationType, String filterType) {
		
		return ResponseEntity.ok(this.communityService.getCommunityData(location, locationType, filterType));
	}

	/**
	 * @param locationType the locationType
	 * @return the community locations
	 */
	@GetMapping(path = "/api/community-locations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommunityLocation>> getCommunityLocations(
			@RequestParam String locationType) {
		
		return ResponseEntity.ok(this.communityService.getCommunityLocations(locationType));
	}

	/**
	 * @param location the location
	 * @param locationType the locationType
	 * @return the community location
	 */
	@GetMapping(path = "/api/community-location", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommunityLocation> getCommunityLocation(
			@RequestParam String location, @RequestParam String locationType) {
		
		return ResponseEntity.ok(this.communityService.getCommunityLocation(location, locationType));
	}
}
