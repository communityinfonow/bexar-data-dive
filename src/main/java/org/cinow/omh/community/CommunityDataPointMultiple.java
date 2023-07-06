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
	 * The demographic filter.
	 */
	private FilterOption demographicFilter;

	/**
	 * @return the demographicFilter
	 */
	public FilterOption getDemographicFilter() {
		return demographicFilter;
	}

	/**
	 * @param demographicFilter the demographicFilter to set
	 */
	public void setDemographicFilter(FilterOption demographicFilter) {
		this.demographicFilter = demographicFilter;
	}
}
