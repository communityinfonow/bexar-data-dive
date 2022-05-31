package org.cinow.omh.indicators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndicatorController {
	
	@Autowired
	private IndicatorService indicatorService;

	@GetMapping(path = "/indicator-menu", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IndicatorMenu> getIndicatorMenu() {
		return ResponseEntity.ok(this.indicatorService.buildIndicatorMenu());
	}
}
