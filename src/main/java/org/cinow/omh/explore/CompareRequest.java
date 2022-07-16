package org.cinow.omh.explore;

import java.util.List;

import org.cinow.omh.filters.FilterOption;
import org.cinow.omh.filters.FilterType;

public class CompareRequest {
	
	private FilterType type;
	private List<FilterOption> options;

	public FilterType getType() {
		return type;
	}
	public void setType(FilterType type) {
		this.type = type;
	}
	public List<FilterOption> getOptions() {
		return options;
	}
	public void setFilterOptions(List<FilterOption> options) {
		this.options = options;
	}
}
