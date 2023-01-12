package org.cinow.omh.faq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaqService {
	
	@Autowired
	private FaqRepository faqRepository;

	public List<Faq> getFaqs() {
		return this.faqRepository.getFaqs();
	}
}
