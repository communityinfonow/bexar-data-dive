package org.cinow.omh.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for the admin features.
 * 
 * @author brian
 */
@RestController
public class AdminController {

	@Autowired
	public AdminService adminService;
	
	/**
	 * @param principal the principal
	 * @return the username
	 */
	@GetMapping(path = "/api/admin/username", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUsername(@AuthenticationPrincipal OAuth2User principal) {
		
		return ResponseEntity.ok(principal.getAttribute("email"));
	}

	@GetMapping(path = "/api/admin/audit-log", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AuditLogEntry>> getAuditLog() {
		return ResponseEntity.ok(this.adminService.findAuditLogEntries());
	}

	@GetMapping(path = "/api/admin/missed-translations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MissedTranslation>> getMissedTranslations() {
		return ResponseEntity.ok(this.adminService.findMissedTranslations());
	}
}
