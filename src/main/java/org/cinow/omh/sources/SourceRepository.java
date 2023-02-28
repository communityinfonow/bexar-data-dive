package org.cinow.omh.sources;

import java.util.List;

/**
 * Repository for sources.
 * 
 * @author brian
 */
public interface SourceRepository {
	
	/**
	 * Gets the source for an indicator.
	 * 
	 * @param indicatorId the indicator id
	 * @return the source
	 */
	Source getSourceByIndicator(String indicatorId);

	/**
	 * Finds sources.
	 * 
	 * @return the sources
	 */
	List<Source> findSources();

	/**
	 * Adds a source.
	 * 
	 * @param source the source to add
	 */
	void addSource(Source source, String username);

	/**
	 * Updates a source.
	 * 
	 * @param source the source to update
	 */
	void updateSource(Source source, String username);
}
