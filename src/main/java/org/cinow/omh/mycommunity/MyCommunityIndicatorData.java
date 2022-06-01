package org.cinow.omh.mycommunity;

import java.util.List;
import java.util.Map;

import org.cinow.omh.filters.FilterOption;
import org.cinow.omh.sources.Source;

public class MyCommunityIndicatorData {
	
	private Source source;
	private String year;
	private Map<FilterOption, List<MyCommunityIndicatorDataPoint>> demographicData;

	public Source getSource() {
		return source;
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
	public Map<FilterOption, List<MyCommunityIndicatorDataPoint>> getDemographicData() {
		return demographicData;
	}
	public void setDemographicData(Map<FilterOption, List<MyCommunityIndicatorDataPoint>> demographicData) {
		this.demographicData = demographicData;
	}
}
