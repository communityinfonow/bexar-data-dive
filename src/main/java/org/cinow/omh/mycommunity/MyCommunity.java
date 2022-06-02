package org.cinow.omh.mycommunity;

import java.util.List;

import org.cinow.omh.locations.Location;

public class MyCommunity {
	
	private Location location;
	private List<MyCommunityDataCategory> indicatorData;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<MyCommunityDataCategory> getIndicatorData() {
		return indicatorData;
	}

	public void setIndicatorData(List<MyCommunityDataCategory> indicatorData) {
		this.indicatorData = indicatorData;
	}
}
