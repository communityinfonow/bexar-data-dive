package org.cinow.omh.mycommunity;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.indicators.IndicatorCategory;

public class MyCommunityDataCategory {
	
	private IndicatorCategory category;
	private List<MyCommunityDataIndicator> indicators;

	public MyCommunityDataCategory() {
		this.indicators = new ArrayList<>();
	}
	
	public IndicatorCategory getCategory() {
		return category;
	}
	
	public void setCategory(IndicatorCategory category) {
		this.category = category;
	}
	
	public List<MyCommunityDataIndicator> getIndicators() {
		return indicators;
	}
	
	public void setIndicators(List<MyCommunityDataIndicator> indicators) {
		this.indicators = indicators;
	}
}
