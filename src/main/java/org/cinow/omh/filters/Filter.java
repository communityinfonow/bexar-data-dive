package org.cinow.omh.filters;

import java.util.ArrayList;
import java.util.List;

public class Filter {
	
	private FilterType type;
	private List<FilterOption> options;

	public Filter() {
		this.options = new ArrayList<>();
	}

	public FilterType getType() {
		return type;
	}

	public void setType(FilterType type) {
		this.type = type;
	}

	public List<FilterOption> getOptions() {
		return options;
	}

	public void setOptions(List<FilterOption> options) {
		this.options = options;
	}
}
