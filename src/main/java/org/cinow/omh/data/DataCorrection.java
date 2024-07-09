package org.cinow.omh.data;

import java.util.List;

import org.cinow.omh.filters.FilterType;
import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.locations.LocationType;

public class DataCorrection {

	private int id;
	private String dateCorrected;
	private Indicator indicator;
	private List<String> years;
	private List<LocationType> locationTypes;
	private List<FilterType> filterTypes;
	//TODO: include a note about the correction?

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDateCorrected() {
		return dateCorrected;
	}
	public void setDateCorrected(String dateCorrected) {
		this.dateCorrected = dateCorrected;
	}
	public Indicator getIndicator() {
		return indicator;
	}
	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}
	public List<String> getYears() {
		return years;
	}
	public void setYears(List<String> years) {
		this.years = years;
	}
	public List<LocationType> getLocationTypes() {
		return locationTypes;
	}
	public void setLocationTypes(List<LocationType> locationTypes) {
		this.locationTypes = locationTypes;
	}
	public List<FilterType> getFilterTypes() {
		return filterTypes;
	}
	public void setFilterTypes(List<FilterType> filterTypes) {
		this.filterTypes = filterTypes;
	}	
}