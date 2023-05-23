package org.cinow.omh.community;

import org.cinow.omh.data.DataItemMultiple;
import org.cinow.omh.filters.FilterOption;

/**
 * This object represents a data point in the Commmunity view for multiple communities.
 * 
 * @author brian
 */
public class CommunityDataPointMultiple extends DataItemMultiple implements CommunityDataPoint {

	/**
	 * The race filter.
	 */
	private FilterOption raceFilter;

	/**
	 * @return the raceFilter
	 */
	public FilterOption getRaceFilter() {
		return raceFilter;
	}

	/**
	 * @param raceFilter the raceFilter to set
	 */
	public void setRaceFilter(FilterOption raceFilter) {
		this.raceFilter = raceFilter;
	}
}
