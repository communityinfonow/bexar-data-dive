package org.cinow.omh.indicators;

import java.math.BigDecimal;

public class FeaturedIndicator extends Indicator {
	
	private BigDecimal value;

	private BigDecimal moeLow;

	private BigDecimal moeHigh;

	private boolean suppressed;

	private String year;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getMoeLow() {
		return moeLow;
	}

	public void setMoeLow(BigDecimal moeLow) {
		this.moeLow = moeLow;
	}

	public BigDecimal getMoeHigh() {
		return moeHigh;
	}

	public void setMoeHigh(BigDecimal moeHigh) {
		this.moeHigh = moeHigh;
	}

	public boolean isSuppressed() {
		return suppressed;
	}

	public void setSuppressed(boolean suppressed) {
		this.suppressed = suppressed;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	
}
