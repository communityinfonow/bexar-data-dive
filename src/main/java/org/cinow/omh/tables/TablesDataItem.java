package org.cinow.omh.tables;

/**
 * This object represents a row in the Tables and Downloads view.
 * 
 * @author brian
 */
public class TablesDataItem {

	/**
	 * The location type (English).
	 */
	private String locationType_en;

	/**
	 * The location type (Spanish).
	 */
	private String locationType_es;
	
	
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
	 * The race (English).
	 */
	private String race_en;

	/**
	 * The race (Spanish).
	 */
	private String race_es;

	/**
	 * The age (English).
	 */
	private String age_en;

	/**
	 * The age (Spanish).
	 */
	private String age_es;

	/**
	 * The sex (English).
	 */
	private String sex_en;

	/**
	 * The sex (Spanish).
	 */
	private String sex_es;

	/**
	 * The education (English).
	 */
	private String education_en;

	/**
	 * The education (Spanish).
	 */
	private String education_es;

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
	private Double value;

	/**
	 * The moeLow.
	 */
	private Double moeLow;

	/**
	 * The moeHigh.
	 */
	private Double moeHigh;

	/**
	 * The universe value.
	 */
	private Double universeValue;

	/**
	 * Whether the value is suppressed.
	 */
	private boolean suppressed;

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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getMoeLow() {
		return moeLow;
	}

	public void setMoeLow(Double moeLow) {
		this.moeLow = moeLow;
	}

	public Double getMoeHigh() {
		return moeHigh;
	}

	public void setMoeHigh(Double moeHigh) {
		this.moeHigh = moeHigh;
	}

	public Double getUniverseValue() {
		return universeValue;
	}

	public void setUniverseValue(Double universeValue) {
		this.universeValue = universeValue;
	}

	public boolean isSuppressed() {
		return suppressed;
	}

	public void setSuppressed(boolean suppressed) {
		this.suppressed = suppressed;
	}	
}
