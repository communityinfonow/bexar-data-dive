package org.cinow.omh.faq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> addFaqs(@RequestBody Faq faq) {
		this.faqService.addFaq(faq);
		return ResponseEntity.ok().build();
	}

	@PutMapping(path = "/api/admin/faqs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateFaq(@RequestBody Faq faq) {
		this.faqService.updateFaq(faq);
		return ResponseEntity.ok().build();
	}
}
