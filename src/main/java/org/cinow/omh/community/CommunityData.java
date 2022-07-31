package org.cinow.omh.community;

import java.util.List;

import org.cinow.omh.locations.Location;

/**
 * This object represents data for a community in the Community view.
 * 
 * @author brian
 */
public class CommunityData {
	
	/**
	 * The location.
	 */
	private Location location;

	/**
	 * The indicator data.
	 */
	private List<CommunityDataCategory> indicatorData;

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
	 * @return the indicatorData
	 */
	public List<CommunityDataCategory> getIndicatorData() {
		return indicatorData;
	}

	/**
	 * @param indicatorData the indicatorData to set
	 */
	public void setIndicatorData(List<CommunityDataCategory> indicatorData) {
		this.indicatorData = indicatorData;
	}
}
