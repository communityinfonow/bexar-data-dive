package org.cinow.omh.faq;

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
public class FaqController {
	
	@Autowired
	private FaqService faqService;

	@GetMapping(path = "/api/faqs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Faq>> getFaqs() {
		return ResponseEntity.ok(this.faqService.getFaqs());
	}

	@PostMapping(path = "/api/admin/faqs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addFaqs(@AuthenticationPrincipal OAuth2User principal, @RequestBody Faq faq) {
		this.faqService.addFaq(faq, principal.getAttribute("email"));
		return ResponseEntity.ok().build();
	}

	@PutMapping(path = "/api/admin/faqs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateFaq(@AuthenticationPrincipal OAuth2User principal, @RequestBody Faq faq) {
		this.faqService.updateFaq(faq, principal.getAttribute("email"));
		return ResponseEntity.ok().build();
	}
}
