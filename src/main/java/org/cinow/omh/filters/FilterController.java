package org.cinow.omh.filters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	/**
	 * @return the filter types
	 */
	@GetMapping(path = "/api/admin/filter-types", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FilterType>> findFilterTypes() {
		return ResponseEntity.ok(this.filterService.findFilterTypes());
	}

	/**
	 * @return the filter options
	 */
	@GetMapping(path = "/api/admin/filter-options", produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FilterOption>> findFilterOptions() {
		return ResponseEntity.ok(this.filterService.findFilterOptions());
	}

	/**
	 * @param filterOption the filter option to add
	 * @return 200 ok
	 */
	@PostMapping(path = "/api/admin/filter-options", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addFilterOption(@AuthenticationPrincipal OAuth2User principal, @RequestBody FilterOption filterOption) {
		this.filterService.addFilterOption(filterOption, principal.getAttribute("email"));
		return ResponseEntity.ok().build();
	}

	/**
	 * @param filterOption the filter option to update
	 * @return 200 ok
	 */
	@PutMapping(path = "/api/admin/filter-options", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateFilterOption(@AuthenticationPrincipal OAuth2User principal, @RequestBody FilterOption filterOption) {
		this.filterService.updateFilterOption(filterOption, principal.getAttribute("email"));
		return ResponseEntity.ok().build();
	}
}
