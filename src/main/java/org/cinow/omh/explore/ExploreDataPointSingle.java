package org.cinow.omh.explore;

import java.math.BigDecimal;

/**
 * This object represents a data point in the Explore Data view.
 * 
 * @author brian
 */
public class ExploreDataPointSingle implements ExploreDataPoint {

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
	 * Whether the indicator value is a suppressed.
	 */
	private boolean suppressed;

	private BigDecimal countValue;

	private BigDecimal universeMoe;

	private BigDecimal countMoe;

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

	public BigDecimal getUniverseMoe() {
		return universeMoe;
	}

	public void setUniverseMoe(BigDecimal universeMoe) {
		this.universeMoe = universeMoe;
	}

	public BigDecimal getCountMoe() {
		return countMoe;
	}

	public void setCountMoe(BigDecimal countMoe) {
		this.countMoe = countMoe;
	}
	
	
}
