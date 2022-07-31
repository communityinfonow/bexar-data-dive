package org.cinow.omh.sources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for sources.
 * 
 * @author brian
 */
@RestController
public class SourceController {
	
	/**
	 * The source service.
	 */
	@Autowired
	private SourceService sourceService;

	/**
	 * @param indicator the indicator
	 * @return the source
	 */
	@GetMapping(path = "/api/indicator-source", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Source> getIndicatorSource(@RequestParam String indicator) {
		return ResponseEntity.ok(this.sourceService.getSourceByIndicator(indicator));
	}
}
