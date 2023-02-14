package org.cinow.omh.faq;

import java.util.List;

public interface FaqRepository {
	
	 List<Faq> getFaqs();

	 void addFaq(Faq faq, String username);

	 void updateFaq(Faq faq, String username);
}
