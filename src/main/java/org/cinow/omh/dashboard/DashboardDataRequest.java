package org.cinow.omh.dashboard;

public class DashboardDataRequest {
	
	private String indicator;
	private FilterRequest filters;
	private CompareRequest comparisons;

	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public FilterRequest getFilters() {
		return filters;
	}
	public void setFilters(FilterRequest filters) {
		this.filters = filters;
	}
	public CompareRequest getComparisons() {
		return comparisons;
	}
	public void setComparisons(CompareRequest comparisons) {
		this.comparisons = comparisons;
	}	
}
