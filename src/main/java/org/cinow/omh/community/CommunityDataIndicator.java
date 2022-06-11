package org.cinow.omh.community;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.indicators.IndicatorType;
import org.cinow.omh.sources.Source;

/**
 * Represents all of the data for an indicator in the Community feature.
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

	public CommunityDataIndicator() {
		this.demographicData = new ArrayList<>();
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public Source getSource() {
		return source;
	}
	
	public IndicatorType getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(IndicatorType indicatorType) {
		this.indicatorType = indicatorType;
	}

	
	public void setSource(Source source) {
		this.source = source;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<CommunityDataPoint> getDemographicData() {
		return demographicData;
	}

	public void setDemographicData(List<CommunityDataPoint> demographicData) {
		this.demographicData = demographicData;
	}
}
