package org.cinow.omh.dashboard;

import java.util.Map;

import org.cinow.omh.locations.Location;
import org.cinow.omh.locations.LocationType;
import org.geojson.GeoJsonObject;

public class DashboardDataLocation {
	
	private Location location;
	private LocationType locationType;
	private GeoJsonObject geometry;
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
	public GeoJsonObject getGeometry() {
		return geometry;
	}
	public void setGeometry(GeoJsonObject geometry) {
		this.geometry = geometry;
	}
}
