package org.cinow.omh.data;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataCorrectionController {
	
	@Autowired
	private DataCorrectionService dataCorrectionService;

	@GetMapping(path = "/api/data-corrections", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DataCorrection>> findDisplayed() {
		return ResponseEntity.ok(dataCorrectionService.findDisplayed());
	}

	@GetMapping(path = "/api/admin/data-corrections", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DataCorrection>> findAll() {
		return ResponseEntity.ok(dataCorrectionService.findAll());
	}

	@PostMapping(path = "/api/admin/data-corrections", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@AuthenticationPrincipal OAuth2User principal, @RequestBody DataCorrection dataCorrection) {
		dataCorrectionService.add(dataCorrection);
		return ResponseEntity.ok().build();
	}

	@PutMapping(path = "/api/admin/data-corrections", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@AuthenticationPrincipal OAuth2User principal, @RequestBody DataCorrection dataCorrection) {
		dataCorrectionService.update(dataCorrection);
		return ResponseEntity.ok().build();
	}
}
