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
	Double getMoeHigh();

	/**
	 * @return the moeLow
	 */
	Double getMoeLow();

	/**
	 * @return the universeValue
	 */
	Double getUniverseValue();

	/**
	 * @return the suppressed
	 */
	boolean isSuppressed();

	Double getCountValue();

	Double getUniverseMoe();

	Double getCountMoe();
}
