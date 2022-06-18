package org.cinow.omh.sources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SourceController {
	
	@Autowired
	private SourceService sourceService;

	@GetMapping(path = "/api/indicator-source", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Source> getIndicatorMenu(@RequestParam long indicator) {
		return ResponseEntity.ok(this.sourceService.getSourceByIndicator(indicator));
	}
}
