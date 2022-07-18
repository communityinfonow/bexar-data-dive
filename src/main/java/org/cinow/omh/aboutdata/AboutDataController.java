package org.cinow.omh.aboutdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AboutDataController {

	@Autowired
	private AboutDataService aboutDataService;

	@GetMapping(path = "/api/about-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AboutData> getAboutData() {
		return ResponseEntity.ok(this.aboutDataService.getAboutData());
	}
}
