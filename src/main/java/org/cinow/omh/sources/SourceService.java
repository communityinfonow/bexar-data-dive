package org.cinow.omh.sources;

import java.util.List;

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

	/**
	 * Finds sources.
	 * 
	 * @return the sources
	 */
	public List<Source> findSources() {
		return this.sourceRepository.findSources();
	}

	/**
	 * Adds a source.
	 * 
	 * @param source the source to add
	 */
	public void addSource(Source source, String username) {
		this.sourceRepository.addSource(source, username);
	}

	/**
	 * Updates a source.
	 * 
	 * @param source the source to update
	 */
	public void updateSource(Source source, String username) {
		this.sourceRepository.updateSource(source, username);
	}
}
