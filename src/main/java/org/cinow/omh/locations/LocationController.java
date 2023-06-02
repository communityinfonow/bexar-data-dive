package org.cinow.omh.locations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for locations.
 * 
 * @author brian
 */
@RestController
public class LocationController {
	
	/**
	 * The location service.
	 */
	@Autowired
	private LocationService locationService;

	/**
	 * Get the location menu.
	 * 
	 * @return the location menu
	 */
	@GetMapping(path = "/api/location-menu", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LocationMenu> getLocationMenu() {
		return ResponseEntity.ok(this.locationService.buildLocationMenu());
	}

	@PostMapping(path = "/api/custom-locations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveCustomLocation(@RequestBody CustomLocation location) {
		this.locationService.saveCustomLocation(location);
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/api/custom-locations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomLocation> getCustomLocation(@RequestParam("id") String id) {
		return ResponseEntity.ok(this.locationService.getCustomLocation(id));
	}
}
