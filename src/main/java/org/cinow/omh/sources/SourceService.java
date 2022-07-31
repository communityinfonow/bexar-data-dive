package org.cinow.omh.sources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for sources.
 * 
 * @author brian
 */
@Service
public class SourceService {
	
	/**
	 * The source repository.
	 */
	@Autowired
	private SourceRepository sourceRepository;

	/**
	 * Gets the source for an indicator.
	 * 
	 * @param indicatorId the indicator id
	 * @return the source
	 */
	public Source getSourceByIndicator(String indicatorId) {
		return this.sourceRepository.getSourceByIndicator(indicatorId);
	}
}
