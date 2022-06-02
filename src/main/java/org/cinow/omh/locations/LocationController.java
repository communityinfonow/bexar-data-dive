package org.cinow.omh.locations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {
	
	@Autowired
	private LocationService locationService;

	@GetMapping(path = "/api/location-menu", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LocationMenu> getLocationMenu() {
		return ResponseEntity.ok(this.locationService.buildLocationMenu());
	}
}
