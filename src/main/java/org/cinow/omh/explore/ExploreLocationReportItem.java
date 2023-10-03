package org.cinow.omh.explore;

import java.math.BigDecimal;

public class ExploreLocationReportItem {
	
	private String indicatorName_en;
	private String indicatorName_es;
	private String year_;
	private String locationId;
	private String locationTypeId;
	private BigDecimal indicatorValue;
	private BigDecimal moe;
	private int sort;

	public String getIndicatorName_en() {
		return indicatorName_en;
	}
	public void setIndicatorName_en(String indicatorName_en) {
		this.indicatorName_en = indicatorName_en;
	}
	public String getIndicatorName_es() {
		return indicatorName_es;
	}
	public void setIndicatorName_es(String indicatorName_es) {
		this.indicatorName_es = indicatorName_es;
	}
	public String getYear_() {
		return year_;
	}
	public void setYear_(String year_) {
		this.year_ = year_;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getLocationTypeId() {
		return locationTypeId;
	}
	public void setLocationTypeId(String locationTypeId) {
		this.locationTypeId = locationTypeId;
	}
	public BigDecimal getIndicatorValue() {
		return indicatorValue;
	}
	public void setIndicatorValue(BigDecimal indicatorValue) {
		this.indicatorValue = indicatorValue;
	}
	public BigDecimal getMoe() {
		return moe;
	}
	public void setMoe(BigDecimal moe) {
		this.moe = moe;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
}
