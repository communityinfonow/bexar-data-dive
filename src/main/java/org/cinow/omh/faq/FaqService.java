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

	public void addFaq(Faq faq, String username) {
		this.faqRepository.addFaq(faq, username);
	}

	public void updateFaq(Faq faq, String username) {
		this.faqRepository.updateFaq(faq, username);
	}
}
