package org.cinow.omh.faq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaqController {
	
	@Autowired
	private FaqService faqService;

	@GetMapping(path = "/api/faqs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Faq>> getFaqs() {
		return ResponseEntity.ok(this.faqService.getFaqs());
	}
}
