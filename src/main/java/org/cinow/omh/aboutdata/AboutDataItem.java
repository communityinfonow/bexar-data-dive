package org.cinow.omh.aboutdata;

import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.sources.Source;

public class AboutDataItem {

	private Indicator indicator;
	private Source source;
	
	public Indicator getIndicator() {
		return indicator;
	}
	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}
}
