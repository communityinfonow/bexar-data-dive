package org.cinow.omh.tables;

/**
 * This object represents a row in the Tables and Downloads view.
 * 
 * @author brian
 */
public class TablesDataItem {
	
	/**
	 * The location.
	 */
	private String location;

	/**
	 * The year.
	 */
	private String year;

	/**
	 * The race.
	 */
	private String race;

	/**
	 * The age.
	 */
	private String age;

	/**
	 * The sex.
	 */
	private String sex;

	/**
	 * The education.
	 */
	private String education;

	/**
	 * The income.
	 */
	private String income;

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

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the race
	 */
	public String getRace() {
		return race;
	}

	/**
	 * @param race the race to set
	 */
	public void setRace(String race) {
		this.race = race;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the education
	 */
	public String getEducation() {
		return education;
	}

	/**
	 * @param education the education to set
	 */
	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * @return the income
	 */
	public String getIncome() {
		return income;
	}

	/**
	 * @param income the income to set
	 */
	public void setIncome(String income) {
		this.income = income;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * @return the moeLow
	 */
	public Double getMoeLow() {
		return moeLow;
	}

	/**
	 * @param moeLow the moeLow to set
	 */
	public void setMoeLow(Double moeLow) {
		this.moeLow = moeLow;
	}

	/**
	 * @return the moeHigh
	 */
	public Double getMoeHigh() {
		return moeHigh;
	}

	/**
	 * @param moeHigh the moeHigh to set
	 */
	public void setMoeHigh(Double moeHigh) {
		this.moeHigh = moeHigh;
	}

	/**
	 * @return the universeValue
	 */
	public Double getUniverseValue() {
		return universeValue;
	}

	/**
	 * @param universeValue the universeValue to set
	 */
	public void setUniverseValue(Double universeValue) {
		this.universeValue = universeValue;
	}

	/**
	 * @return the suppressed
	 */
	public boolean isSuppressed() {
		return suppressed;
	}

	/**
	 * @param suppressed the suppressed to set
	 */
	public void setSuppressed(boolean suppressed) {
		this.suppressed = suppressed;
	}	
}
