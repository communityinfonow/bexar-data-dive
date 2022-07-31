package org.cinow.omh.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for filters.
 * 
 * @author brian
 */
@RestController
public class FilterController {
	
	/**
	 * The filter service.
	 */
	@Autowired
	private FilterService filterService;

	/**
	 * @param indicator the indicator
	 * @return the filters
	 */
	@GetMapping(path = "/api/filters", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Filters> getFilters(@RequestParam String indicator) {
		return ResponseEntity.ok(this.filterService.getFilters(indicator));
	}
}
