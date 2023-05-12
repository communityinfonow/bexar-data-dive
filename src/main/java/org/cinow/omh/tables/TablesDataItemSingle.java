package org.cinow.omh.tables;

import java.math.BigDecimal;

/**
 * This object represents a row in the Tables and Downloads view.
 * 
 * @author brian
 */
public class TablesDataItemSingle implements TablesDataItem {

	private String locationTypeId;

	/**
	 * The location type (English).
	 */
	private String locationType_en;

	/**
	 * The location type (Spanish).
	 */
	private String locationType_es;
	
	private String locationId;
	
	/**
	 * The location (English).
	 */
	private String location_en;

	/**
	 * The location (Spanish).
	 */
	private String location_es;

	/**
	 * The year.
	 */
	private String year;

	/**
	 * The race id.
	 */
	private String raceId;

	/**
	 * The race (English).
	 */
	private String race_en;

	/**
	 * The race (Spanish).
	 */
	private String race_es;

	private String ageId;

	/**
	 * The age (English).
	 */
	private String age_en;

	/**
	 * The age (Spanish).
	 */
	private String age_es;

	private String sexId;

	/**
	 * The sex (English).
	 */
	private String sex_en;

	/**
	 * The sex (Spanish).
	 */
	private String sex_es;

	private String educationId;

	/**
	 * The education (English).
	 */
	private String education_en;

	/**
	 * The education (Spanish).
	 */
	private String education_es;

	private String incomeId;

	/**
	 * The income (English).
	 */
	private String income_en;

	/**
	 * The income (Spanish).
	 */
	private String income_es;

	/**
	 * The value.
	 */
	private BigDecimal value;

	/**
	 * The moeLow.
	 */
	private BigDecimal moeLow;

	/**
	 * The moeHigh.
	 */
	private BigDecimal moeHigh;

	/**
	 * The universe value.
	 */
	private BigDecimal universeValue;

	/**
	 * Whether the value is suppressed.
	 */
	private boolean suppressed;

	/**
	 * The indicator count value.
	 */
	private BigDecimal countValue;

	/**
	 * The indicator universe margin of error.
	 */
	private BigDecimal universeMoe;

	/**
	 * The indicator count margin of error.
	 */
	private BigDecimal countMoe;

	public String getLocationTypeId() {
		return locationTypeId;
	}

	public void setLocationTypeId(String locationTypeId) {
		this.locationTypeId = locationTypeId;
	}

	public String getLocationType_en() {
		return locationType_en;
	}

	public void setLocationType_en(String locationType_en) {
		this.locationType_en = locationType_en;
	}

	public String getLocationType_es() {
		return locationType_es;
	}

	public void setLocationType_es(String locationType_es) {
		this.locationType_es = locationType_es;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocation_en() {
		return location_en;
	}

	public void setLocation_en(String location_en) {
		this.location_en = location_en;
	}

	public String getLocation_es() {
		return location_es;
	}

	public void setLocation_es(String location_es) {
		this.location_es = location_es;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRaceId() {
		return raceId;
	}

	public void setRaceId(String raceId) {
		this.raceId = raceId;
	}

	public String getRace_en() {
		return race_en;
	}

	public void setRace_en(String race_en) {
		this.race_en = race_en;
	}

	public String getRace_es() {
		return race_es;
	}

	public void setRace_es(String race_es) {
		this.race_es = race_es;
	}

	public String getAgeId() {
		return ageId;
	}

	public void setAgeId(String ageId) {
		this.ageId = ageId;
	}

	public String getAge_en() {
		return age_en;
	}

	public void setAge_en(String age_en) {
		this.age_en = age_en;
	}

	public String getAge_es() {
		return age_es;
	}

	public void setAge_es(String age_es) {
		this.age_es = age_es;
	}

	public String getSexId() {
		return sexId;
	}

	public void setSexId(String sexId) {
		this.sexId = sexId;
	}

	public String getSex_en() {
		return sex_en;
	}

	public void setSex_en(String sex_en) {
		this.sex_en = sex_en;
	}

	public String getSex_es() {
		return sex_es;
	}

	public void setSex_es(String sex_es) {
		this.sex_es = sex_es;
	}

	public String getEducationId() {
		return educationId;
	}

	public void setEducationId(String educationId) {
		this.educationId = educationId;
	}

	public String getEducation_en() {
		return education_en;
	}

	public void setEducation_en(String education_en) {
		this.education_en = education_en;
	}

	public String getEducation_es() {
		return education_es;
	}

	public void setEducation_es(String education_es) {
		this.education_es = education_es;
	}

	public String getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
	}

	public String getIncome_en() {
		return income_en;
	}

	public void setIncome_en(String income_en) {
		this.income_en = income_en;
	}

	public String getIncome_es() {
		return income_es;
	}

	public void setIncome_es(String income_es) {
		this.income_es = income_es;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getMoeLow() {
		return moeLow;
	}

	public void setMoeLow(BigDecimal moeLow) {
		this.moeLow = moeLow;
	}

	public BigDecimal getMoeHigh() {
		return moeHigh;
	}

	public void setMoeHigh(BigDecimal moeHigh) {
		this.moeHigh = moeHigh;
	}

	public BigDecimal getUniverseValue() {
		return universeValue;
	}

	public void setUniverseValue(BigDecimal universeValue) {
		this.universeValue = universeValue;
	}

	public boolean isSuppressed() {
		return suppressed;
	}

	public void setSuppressed(boolean suppressed) {
		this.suppressed = suppressed;
	}

	public BigDecimal getCountValue() {
		return countValue;
	}

	public void setCountValue(BigDecimal countValue) {
		this.countValue = countValue;
	}

	public BigDecimal getUniverseMoe() {
		return universeMoe;
	}

	public void setUniverseMoe(BigDecimal universeMoe) {
		this.universeMoe = universeMoe;
	}

	public BigDecimal getCountMoe() {
		return countMoe;
	}

	public void setCountMoe(BigDecimal countMoe) {
		this.countMoe = countMoe;
	}
	
	
}
