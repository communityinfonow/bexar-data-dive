package org.cinow.omh.community;

import java.math.BigDecimal;

import org.cinow.omh.filters.FilterOption;

/**
 * This object represents a data point in the Commmunity view.
 * 
 * @author brian
 */
public interface CommunityDataPoint {

	/**
	 * @return the raceFilter
	 */
	FilterOption getRaceFilter();

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

	BigDecimal getUniverseMoe();

	BigDecimal getCountMoe();
}
