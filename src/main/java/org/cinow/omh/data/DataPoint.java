package org.cinow.omh.data;

import java.util.Map;

import org.cinow.omh.filters.FilterOption;
import org.cinow.omh.filters.FilterType;
import org.cinow.omh.locations.Location;
import org.cinow.omh.sources.Source;

public class DataPoint {
	
	private Location location;
	private String year;
	private Source source;
	private Map<FilterType, FilterOption> filters;
	private Double value;
}
