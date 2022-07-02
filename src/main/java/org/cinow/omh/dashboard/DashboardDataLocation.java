package org.cinow.omh.dashboard;

import java.util.Map;

import org.cinow.omh.locations.Location;
import org.cinow.omh.locations.LocationType;

public class DashboardDataLocation {
	
	private Location location;
	private LocationType locationType;
	private String geojson;
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
	public LocationType getLocationType() {
		return locationType;
	}
	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}
	public String getGeojson() {
		return geojson;
	}
	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}
}
