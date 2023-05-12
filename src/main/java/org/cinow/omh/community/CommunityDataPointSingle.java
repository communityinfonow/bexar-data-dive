package org.cinow.omh.community;

import java.math.BigDecimal;

import org.cinow.omh.filters.FilterOption;

/**
 * This object represents a data point in the Commmunity view for a single community.
 * 
 * @author brian
 */
public class CommunityDataPointSingle implements CommunityDataPoint {

	/**
	 * The race filter.
	 */
	private FilterOption raceFilter;

	/**
	 * The indicator value.
	 */
	private BigDecimal value;

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
	 * The indicator count value.
	 */
	private Double countValue;

	/**
	 * The indicator universe margin of error.
	 */
	private Double universeMoe;

	/**
	 * The indicator count margin of error.
	 */
	private Double countMoe;

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
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(BigDecimal value) {
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

	public Double getCountValue() {
		return countValue;
	}

	public void setCountValue(Double countValue) {
		this.countValue = countValue;
	}

	public Double getUniverseMoe() {
		return universeMoe;
	}

	public void setUniverseMoe(Double universeMoe) {
		this.universeMoe = universeMoe;
	}

	public Double getCountMoe() {
		return countMoe;
	}

	public void setCountMoe(Double countMoe) {
		this.countMoe = countMoe;
	}
}
