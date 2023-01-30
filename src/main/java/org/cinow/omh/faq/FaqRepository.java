package org.cinow.omh.faq;

import java.util.List;

public interface FaqRepository {
	
	 List<Faq> getFaqs();

	 void addFaq(Faq faq);

	 void updateFaq(Faq faq);
}
