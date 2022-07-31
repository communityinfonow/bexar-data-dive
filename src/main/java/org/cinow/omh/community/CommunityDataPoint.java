package org.cinow.omh.community;

import org.cinow.omh.filters.FilterOption;

/**
 * This object represents a data point in the Commmunity view.
 * 
 * @author brian
 */
public class CommunityDataPoint {
	
	/**
	 * The 'base' filter is one that is required for an indicator. 
	 * It will not apply to all indicators.
	 * For example, Educationl Attainment requires an 'attainment level' filter, 
	 * because there is no 'overall' educational attainment.
	 * Other indicators, such as population total, do not require a 'base' filter,
	 * because 'overall' population is a legitimate metric.
	 * 
	 * TODO: is this still needed?
	 */
	private FilterOption baseFilter;

	/**
	 * The race filter.
	 */
	private FilterOption raceFilter;

	/**
	 * The indicator value.
	 */
	private Double value;

	/**
	 * The 'high' margin of error.
	 */
	private Double moeHigh;

	/**
	 * The 'low' margin of error.
	 */
	private Double moeLow;

	/**
	 * The indicator universe value.
	 */
	private Double universeValue;

	/**
	 * Whether the data value is suppressed.
	 */
	private boolean suppressed;

	/**
	 * @return the baseFilter
	 */
	public FilterOption getBaseFilter() {
		return baseFilter;
	}

	/**
	 * @param baseFilter the baseFilter to set
	 */
	public void setBaseFilter(FilterOption baseFilter) {
		this.baseFilter = baseFilter;
	}

	/**
	 * @return the raceFilter
	 */
	public FilterOption getRaceFilter() {
		return raceFilter;
	}

	/**
	 * @param raceFilter the raceFilter to set
	 */
	public void setRaceFilter(FilterOption raceFilter) {
		this.raceFilter = raceFilter;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * @return the moeHigh
	 */
	public Double getMoeHigh() {
		return moeHigh;
	}

	/**
	 * @param moeHigh the moeHigh to set
	 */
	public void setMoeHigh(Double moeHigh) {
		this.moeHigh = moeHigh;
	}

	/**
	 * @return the moeLow
	 */
	public Double getMoeLow() {
		return moeLow;
	}

	/**
	 * @param moeLow the moeLow to set
	 */
	public void setMoeLow(Double moeLow) {
		this.moeLow = moeLow;
	}

	/**
	 * @return the universeValue
	 */
	public Double getUniverseValue() {
		return universeValue;
	}

	/**
	 * @param universeValue the universeValue to set
	 */
	public void setUniverseValue(Double universeValue) {
		this.universeValue = universeValue;
	}

	/**
	 * @return the suppressed
	 */
	public boolean isSuppressed() {
		return suppressed;
	}

	/**
	 * @param suppressed the suppressed to set
	 */
	public void setSuppressed(boolean suppressed) {
		this.suppressed = suppressed;
	}
}
