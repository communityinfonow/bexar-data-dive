package org.cinow.omh.explore;

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

	private boolean suppressed;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getMoeHigh() {
		return moeHigh;
	}

	public void setMoeHigh(Double moeHigh) {
		this.moeHigh = moeHigh;
	}

	public Double getMoeLow() {
		return moeLow;
	}

	public void setMoeLow(Double moeLow) {
		this.moeLow = moeLow;
	}

	public Double getUniverseValue() {
		return universeValue;
	}

	public void setUniverseValue(Double universeValue) {
		this.universeValue = universeValue;
	}

	public boolean isSuppressed() {
		return suppressed;
	}

	public void setSuppressed(boolean suppressed) {
		this.suppressed = suppressed;
	}	
}
