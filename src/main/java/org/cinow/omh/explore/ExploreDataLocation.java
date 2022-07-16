package org.cinow.omh.explore;

import java.util.Map;

import org.cinow.omh.locations.Location;
import org.cinow.omh.locations.LocationType;

public class ExploreDataLocation {
	
	private Location location;
	private LocationType locationType;
	private String geojson;
	private Map<String, ExploreDataPoint> yearData;
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Map<String, ExploreDataPoint> getYearData() {
		return yearData;
	}
	public void setYearData(Map<String, ExploreDataPoint> yearData) {
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
