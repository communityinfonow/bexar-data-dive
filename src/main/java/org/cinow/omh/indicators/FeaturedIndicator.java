package org.cinow.omh.indicators;

import java.math.BigDecimal;

public class FeaturedIndicator extends Indicator {
	
	private BigDecimal value;

	private boolean suppressed;

	private String year;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public boolean isSuppressed() {
		return suppressed;
	}

	public void setSuppressed(boolean suppressed) {
		this.suppressed = suppressed;
	}
}
