package org.cinow.omh.sources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	/**
	 * @return the indicator sources
	 */
	@GetMapping(path = "/api/admin/indicator-sources", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Source>> getIndicatorSources() {
		return ResponseEntity.ok(this.sourceService.findSources());
	}

	/**
	 * @param source the source to add
	 * @return 200 ok
	 */
	@PostMapping(path = "/api/admin/indicator-sources", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addIndicatorSource(@RequestBody Source source) {
		this.sourceService.addSource(source);
		return ResponseEntity.ok().build();
	}

	/**
	 * @param source the source to update
	 * @return 200 ok
	 */
	@PutMapping(path = "/api/admin/indicator-sources", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateIndicatorSource(@RequestBody Source source) {
		this.sourceService.updateSource(source);
		return ResponseEntity.ok().build();
	}
}
