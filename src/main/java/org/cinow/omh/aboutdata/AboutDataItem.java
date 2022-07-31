package org.cinow.omh.aboutdata;

import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.sources.Source;

/**
 * This object represents an item in the About the Data view.
 * 
 * @author brian
 */
public class AboutDataItem {

	/**
	 * The indicator.
	 */
	private Indicator indicator;

	/**
	 * The source.
	 */
	private Source source;
	
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
}
