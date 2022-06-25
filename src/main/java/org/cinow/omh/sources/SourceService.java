package org.cinow.omh.sources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SourceService {
	
	@Autowired
	private SourceRepository sourceRepository;

	public Source getSourceByIndicator(String indicatorId) {
		return this.sourceRepository.getSourceByIndicator(indicatorId);
	}
}
