package org.cinow.omh.data;

import java.util.List;

import org.cinow.omh.filters.FilterType;
import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.locations.LocationType;
import org.cinow.omh.sources.Source;

public class DataCorrection {

	private int id;
	private String dateCorrected;
	private String correctionType;
	private Indicator indicator;
	private Source source;
	private List<String> years;
	private List<LocationType> locationTypes;
	private List<FilterType> filterTypes;
	private String note_en;
	private String note_es;
	private boolean display;

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
	public String getCorrectionType() {
		return correctionType;
	}
	public void setCorrectionType(String correctionType) {
		this.correctionType = correctionType;
	}
	public Indicator getIndicator() {
		return indicator;
	}
	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
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
	public String getNote_en() {
		return note_en;
	}
	public void setNote_en(String note_en) {
		this.note_en = note_en;
	}
	public String getNote_es() {
		return note_es;
	}
	public void setNote_es(String note_es) {
		this.note_es = note_es;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
}