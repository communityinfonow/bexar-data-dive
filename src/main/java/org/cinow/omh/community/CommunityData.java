package org.cinow.omh.community;

import java.util.List;

import org.cinow.omh.locations.Location;

public class CommunityData {
	
	private Location location;
	private List<CommunityDataCategory> indicatorData;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<CommunityDataCategory> getIndicatorData() {
		return indicatorData;
	}

	public void setIndicatorData(List<CommunityDataCategory> indicatorData) {
		this.indicatorData = indicatorData;
	}
}
