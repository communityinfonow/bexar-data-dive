package org.cinow.omh.indicators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FeaturedIndicator extends Indicator {
	
	private BigDecimal value;
	private BigDecimal moeLow;
	private BigDecimal moeHigh;
	private boolean suppressed;
	private String year;
	private String race_en;
	private String race_es;
	private String age_en;
	private String age_es;
	private String sex_en;
	private String sex_es;
	private String income_en;
	private String income_es;
	private String education_en;
	private String education_es;
	private List<FeaturedIndicator> filteredIndicators = new ArrayList<>();

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

	public boolean isSuppressed() {
		return suppressed;
	}

	public void setSuppressed(boolean suppressed) {
		this.suppressed = suppressed;
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

	public List<FeaturedIndicator> getFilteredIndicators() {
		return filteredIndicators;
	}

	public void setFilteredIndicators(List<FeaturedIndicator> filteredIndicators) {
		this.filteredIndicators = filteredIndicators;
	}
}
