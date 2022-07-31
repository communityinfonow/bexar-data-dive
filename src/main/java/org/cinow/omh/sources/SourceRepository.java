package org.cinow.omh.sources;

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
}
