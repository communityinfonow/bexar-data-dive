package org.cinow.omh.community;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.filters.FilterType;
import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.indicators.IndicatorType;
import org.cinow.omh.sources.Source;

/**
 * This object represents all of the data for an indicator in the Community view.
 * 
 * @author brian
 */
public class CommunityDataIndicator {

	/**
	 * The indicator.
	 */
	private Indicator indicator;

	/**
	 * The indicator type.
	 */
	private IndicatorType indicatorType;
	
	/**
	 * The data source.
	 */
	private Source source;

	/**
	 * The data year.
	 */
	private String year;

	/**
	 * The individual indicator data points.
	 */
	private List<CommunityDataPoint> demographicData;

	/**
	 * The filter type.
	 */
	private FilterType filterType;

	/**
	 * Constructor initializes fields.
	 */
	public CommunityDataIndicator() {
		this.demographicData = new ArrayList<>();
	}

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
	 * @return the indicatorType
	 */
	public Source getSource() {
		return source;
	}
	
	/**
	 * @param source the source to set
	 */
	public IndicatorType getIndicatorType() {
		return indicatorType;
	}

	/**
	 * @param indicatorType the indicatorType to set
	 */
	public void setIndicatorType(IndicatorType indicatorType) {
		this.indicatorType = indicatorType;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Source source) {
		this.source = source;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the demographicData
	 */
	public List<CommunityDataPoint> getDemographicData() {
		return demographicData;
	}

	/**
	 * @param demographicData the demographicData to set
	 */
	public void setDemographicData(List<CommunityDataPoint> demographicData) {
		this.demographicData = demographicData;
	}

	public FilterType getFilterType() {
		return filterType;
	}

	public void setFilterType(FilterType filterType) {
		this.filterType = filterType;
	}

	
}
