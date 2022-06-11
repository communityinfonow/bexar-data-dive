package org.cinow.omh.community;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.indicators.IndicatorCategory;

public class CommunityDataCategory {
	
	private IndicatorCategory category;
	private List<CommunityDataIndicator> indicators;

	public CommunityDataCategory() {
		this.indicators = new ArrayList<>();
	}
	
	public IndicatorCategory getCategory() {
		return category;
	}
	
	public void setCategory(IndicatorCategory category) {
		this.category = category;
	}
	
	public List<CommunityDataIndicator> getIndicators() {
		return indicators;
	}
	
	public void setIndicators(List<CommunityDataIndicator> indicators) {
		this.indicators = indicators;
	}
}
