package org.cinow.omh.filters;

import java.util.List;

public class IndicatorFilter extends Filter {
	
	private List<List<String>> compatibleFilterTypeIds;

	public List<List<String>> getCompatibleFilterTypeIds() {
		return compatibleFilterTypeIds;
	}

	public void setCompatibleFilterTypeIds(List<List<String>> compatibleFilterTypeIds) {
		this.compatibleFilterTypeIds = compatibleFilterTypeIds;
	}
}
