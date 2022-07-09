package org.cinow.omh.explore;

public class ExploreDataItem {
	
	private String location;
	private String year;
	private String race;
	private String age;
	private String sex;
	private String education;
	private String income;
	private Double value;
	private Double moeLow;
	private Double moeHigh;
	private Double universeValue;
	private boolean suppressed;

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
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
