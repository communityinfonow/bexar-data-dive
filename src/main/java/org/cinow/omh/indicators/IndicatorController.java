package org.cinow.omh.indicators;

import java.util.List;

import org.cinow.omh.community.CommunityDataIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for indicators.
 * 
 * @author brian
 */
@RestController
public class IndicatorController {
	
	/**
	 * The indicator service.
	 */
	@Autowired
	private IndicatorService indicatorService;

	/**
	 * @return the indicator menu
	 */
	@GetMapping(path = "/api/indicator-menu", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IndicatorMenu> getIndicatorMenu() {
		return ResponseEntity.ok(this.indicatorService.buildIndicatorMenu());
	}

	/**
	 * @return the featured indicator
	 */
	@GetMapping(path = "/api/bexar-data-facts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommunityDataIndicator>> getBexarDataFacts() {
		return ResponseEntity.ok(this.indicatorService.getBexarDataFacts());
	}

	/**
	 * @return the featured indicators
	 */
	@GetMapping(path = "/api/featured-indicators", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Indicator>> getFeaturedIndicators() {
		return ResponseEntity.ok(this.indicatorService.getFeaturedIndicators());
	}

	/**
	 * @return the indicators
	 */
	@GetMapping(path = "/api/admin/indicators", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Indicator>> getIndicators() {
		return ResponseEntity.ok(this.indicatorService.findIndicators());
	}

	/**
	 * @return the indicator categories
	 */
	@GetMapping(path = "/api/admin/indicator-categories", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<IndicatorCategory>> getCategories() {
		return ResponseEntity.ok(this.indicatorService.findCategories());
	}

	/**
	 * @return the indicator types
	 */
	@GetMapping(path = "/api/admin/indicator-types", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<IndicatorType>> getTypes() {
		return ResponseEntity.ok(this.indicatorService.findTypes());
	}

	/**
	 * @param indicator the indicator to add
	 * @return 200 ok
	 */
	@PostMapping(path = "/api/admin/indicators", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addIndicator(@AuthenticationPrincipal OAuth2User principal, @RequestBody Indicator indicator) {
		this.indicatorService.addIndicator(indicator, principal.getAttribute("email"));
		return ResponseEntity.ok().build();
	}
	
	/**
	 * @param indicator the indicator to update
	 * @return 200 ok
	 */
	@PutMapping(path = "/api/admin/indicators", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateIndicator(@AuthenticationPrincipal OAuth2User principal, @RequestBody Indicator indicator) {
		this.indicatorService.updateIndicator(indicator, principal.getAttribute("email"));
		return ResponseEntity.ok().build();
	}
}
