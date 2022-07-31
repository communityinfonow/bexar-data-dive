package org.cinow.omh.explore;

import java.util.List;

import org.cinow.omh.filters.FilterOption;
import org.cinow.omh.filters.FilterType;

/**
 * This object represents a request to compare data in the Explore view.
 * 
 * @author brian
 */
public class CompareRequest {
	
	/**
	 * The type.
	 */
	private FilterType type;

	/**
	 * The options.
	 */
	private List<FilterOption> options;

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
	public void setFilterOptions(List<FilterOption> options) {
		this.options = options;
	}
}
