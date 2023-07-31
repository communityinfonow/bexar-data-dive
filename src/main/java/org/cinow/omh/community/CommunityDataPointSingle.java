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
	 * The demographic filter.
	 */
	private FilterOption demographicFilter;

	/**
	 * The indicator value.
	 */
	private BigDecimal value;

	/**
	 * The 'high' margin of error.
	 */
	private BigDecimal moeHigh;

	/**
	 * The 'low' margin of error.
	 */
	private BigDecimal moeLow;

	/**
	 * The indicator universe value.
	 */
	private BigDecimal universeValue;

	/**
	 * Whether the data value is suppressed.
	 */
	private boolean suppressed;

	/**
	 * The indicator count value.
	 */
	private BigDecimal countValue;

	/**
	 * @return the demographicFilter
	 */
	public FilterOption getDemographicFilter() {
		return demographicFilter;
	}

	/**
	 * @param demographicFilter the demographicFilter to set
	 */
	public void setDemographicFilter(FilterOption demographicFilter) {
		this.demographicFilter = demographicFilter;
	}

	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		return this.suppressed ? null : value;
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
	public BigDecimal getMoeHigh() {
		return moeHigh;
	}

	/**
	 * @param moeHigh the moeHigh to set
	 */
	public void setMoeHigh(BigDecimal moeHigh) {
		this.moeHigh = moeHigh;
	}

	/**
	 * @return the moeLow
	 */
	public BigDecimal getMoeLow() {
		return moeLow;
	}

	/**
	 * @param moeLow the moeLow to set
	 */
	public void setMoeLow(BigDecimal moeLow) {
		this.moeLow = moeLow;
	}

	/**
	 * @return the universeValue
	 */
	public BigDecimal getUniverseValue() {
		return universeValue;
	}

	/**
	 * @param universeValue the universeValue to set
	 */
	public void setUniverseValue(BigDecimal universeValue) {
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

	public BigDecimal getCountValue() {
		return countValue;
	}

	public void setCountValue(BigDecimal countValue) {
		this.countValue = countValue;
	}
}
