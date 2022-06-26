package org.cinow.omh.dashboard;

import org.cinow.omh.filters.FilterOption;

public class DashboardDataPoint {
	
	/**
	 * The 'base' filter is one that is required for an indicator. 
	 * It will not apply to all indicators.
	 * For example, Educationl Attainment requires an 'attainment level' filter, 
	 * because there is no 'overall' educational attainment.
	 * Other indicators, such as population total, do not require a 'base' filter,
	 * because 'overall' population is a legitimate metric.
	 * 
	 * TODO: is this needed? we may just have separate indicators in this scenario
	 */
	private FilterOption baseFilter;

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

	public FilterOption getBaseFilter() {
		return baseFilter;
	}

	public void setBaseFilter(FilterOption baseFilter) {
		this.baseFilter = baseFilter;
	}

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
}
