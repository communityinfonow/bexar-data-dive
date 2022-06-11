package org.cinow.omh.dashboard;

import java.util.Map;

import org.cinow.omh.locations.Location;

public class DashboardDataLocation {
	
	private Location location;
	private Map<String, DashboardDataPoint> yearData;
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Map<String, DashboardDataPoint> getYearData() {
		return yearData;
	}
	public void setYearData(Map<String, DashboardDataPoint> yearData) {
		this.yearData = yearData;
	}
}
