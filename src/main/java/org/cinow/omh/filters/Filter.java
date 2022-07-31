package org.cinow.omh.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * This object represents a filter.
 * 
 * @author brian
 */
public class Filter {
	
	/**
	 * The filter type.
	 */
	private FilterType type;

	/**
	 * The filter options.
	 */
	private List<FilterOption> options;

	/**
	 * Constructor initializes fields.
	 */
	public Filter() {
		this.options = new ArrayList<>();
	}

	/**
	 * @return the type
	 */
	public FilterType getType() {
		return type;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(FilterType type) {
		this.type = type;
	}

	/**
	 * @return the options
	 */
	public List<FilterOption> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<FilterOption> options) {
		this.options = options;
	}
}
