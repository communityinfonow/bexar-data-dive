package org.cinow.omh.aboutdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The REST API controller for the About the Data view.
 * 
 * @author brian
 */
@RestController
public class AboutDataController {

	/**
	 * The about data service.
	 */
	@Autowired
	private AboutDataService aboutDataService;

	/**
	 * @return the about data
	 */
	@GetMapping(path = "/api/about-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AboutData> getAboutData() {
		return ResponseEntity.ok(this.aboutDataService.getAboutData());
	}
}
