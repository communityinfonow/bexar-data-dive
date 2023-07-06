package org.cinow.omh.explore;

import java.util.Map;

import org.cinow.omh.locations.Location;
import org.cinow.omh.locations.LocationType;

/**
 * This object represents a location in the Explore Data view.
 * 
 * @author brian
 */
public class ExploreDataLocation<T extends ExploreDataPoint> {
	
	/**
	 * The location.
	 */
	private Location location;

	/**
	 * The location type.
	 */
	private LocationType locationType;

	/**
	 * The geojson.
	 */
	private String geojson;

	/**
	 * The year data.
	 */
	private Map<String, T> yearData;
	
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the locationType
	 */
	public LocationType getLocationType() {
		return locationType;
	}

	/**
	 * @param locationType the locationType to set
	 */
	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	/**
	 * @return the geojson
	 */
	public String getGeojson() {
		return geojson;
	}

	/**
	 * @param geojson the geojson to set
	 */
	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}

	/**
	 * @return the yearData
	 */
	public Map<String, T> getYearData() {
		return yearData;
	}

	/**
	 * @param yearData the yearData to set
	 */
	public void setYearData(Map<String, T> yearData) {
		this.yearData = yearData;
	}
}
