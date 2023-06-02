package org.cinow.omh.explore;

import java.math.BigDecimal;

/**
 * This object represents a data point in the Explore Data view.
 * 
 * @author brian
 */
interface ExploreDataPoint {
	
	/**
	 * @return the value
	 */
	BigDecimal getValue();

	/**
	 * @return the moeHigh
	 */
	BigDecimal getMoeHigh();

	/**
	 * @return the moeLow
	 */
	BigDecimal getMoeLow();

	/**
	 * @return the universeValue
	 */
	BigDecimal getUniverseValue();

	/**
	 * @return the suppressed
	 */
	boolean isSuppressed();

	BigDecimal getCountValue();
}
