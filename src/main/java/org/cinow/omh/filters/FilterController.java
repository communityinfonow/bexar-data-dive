package org.cinow.omh.filters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterController {
	
	@Autowired
	private FilterService filterService;

	@GetMapping(path = "/api/filters", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Filter>> getFilters(@RequestParam long indicator) {
		return ResponseEntity.ok(this.filterService.getFilters(indicator));
	}
}
