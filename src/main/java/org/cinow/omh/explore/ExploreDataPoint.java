package org.cinow.omh.explore;

/**
 * This object represents a data point in the Explore Data view.
 * 
 * @author brian
 */
public class ExploreDataPoint {

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
	 * Whether the indicator value is a suppressed.
	 */
	private boolean suppressed;

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
