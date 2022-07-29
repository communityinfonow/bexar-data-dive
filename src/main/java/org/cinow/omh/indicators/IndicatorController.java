package org.cinow.omh.indicators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndicatorController {
	
	@Autowired
	private IndicatorService indicatorService;

	@GetMapping(path = "/api/indicator-menu", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IndicatorMenu> getIndicatorMenu() {
		return ResponseEntity.ok(this.indicatorService.buildIndicatorMenu());
	}

	@GetMapping(path = "/api/featured-indicators", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Indicator>> getFeaturedIndicators() {
		return ResponseEntity.ok(this.indicatorService.getFeaturedIndicators());
	}
}
