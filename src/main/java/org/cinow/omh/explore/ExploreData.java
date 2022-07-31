package org.cinow.omh.explore;

import java.util.List;

import org.cinow.omh.filters.Filters;
import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.sources.Source;

/**
 * This object represents the data in the Explore Data view.
 * 
 * @author brian
 */
public class ExploreData {
	
	/**
	 * The indicators.
	 */
	private Indicator indicator;
	
	/**
	 * The filters.
	 */
	private Filters filters;

	/**
	 * The source.
	 */
	private Source source;

	/**
	 * The location data.
	 */
	private List<ExploreDataLocation> locationData;

	/**
	 * The compare data.
	 */
	private List<ExploreDataLocation> compareData;
	
	/**
	 * @return the indicator
	 */
	public Indicator getIndicator() {
		return indicator;
	}

	/**
	 * @param indicator the indicator to set
	 */
	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	/**
	 * @return the filters
	 */
	public Filters getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(Filters filters) {
		this.filters = filters;
	}

	/**
	 * @return the source
	 */
	public Source getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Source source) {
		this.source = source;
	}

	/**
	 * @return the locationData
	 */
	public List<ExploreDataLocation> getLocationData() {
		return locationData;
	}

	/**
	 * @param locationData the locationData to set
	 */
	public void setLocationData(List<ExploreDataLocation> locationData) {
		this.locationData = locationData;
	}

	/**
	 * @return the compareData
	 */
	public List<ExploreDataLocation> getCompareData() {
		return compareData;
	}

	/**
	 * @param compareData the compareData to set
	 */
	public void setCompareData(List<ExploreDataLocation> compareData) {
		this.compareData = compareData;
	}
}
