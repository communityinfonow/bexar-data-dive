package org.cinow.omh.tables;

import java.util.ArrayList;
import java.util.List;

/**
 * This object represents a data request for the Tables and Downloads view.
 * 
 * @author brian
 */
public class TablesDataRequest {
	
	/**
	 * The indicator.
	 */
	private String indicator;

	/**
	 * The page of data.
	 */
	private int page;

	/**
	 * The number of rows per page.
	 */
	private int perPage;

	/**
	 * The sort by.
	 */
	private String sortBy;

	/**
	 * Whether to sort descending.
	 */
	private boolean sortDesc;

	/**
	 * The search query.
	 */
	private String search;

	/**
	 * The language.
	 */
	private String lang;

	/**
	 * The location types.
	 */
	private List<String> locationTypes;

	/**
	 * The locations.
	 */
	private List<String> locations;

	/**
	 * The years.
	 */
	private List<String> years;

	/**
	 * The races.
	 */
	private List<String> races;

	/**
	 * The ages.
	 */
	private List<String> ages;

	/**
	 * The sexes.
	 */
	private List<String> sexes;

	/**
	 * The educations.
	 */
	private List<String> educations;

	/**
	 * The incomes.
	 */
	private List<String> incomes;

	/**
	 * Constructor initializes fields.
	 */
	public TablesDataRequest() {
		this.locationTypes = new ArrayList<>();
		this.locations = new ArrayList<>();
		this.years = new ArrayList<>();
		this.races = new ArrayList<>();
		this.ages = new ArrayList<>();
		this.sexes = new ArrayList<>();
		this.educations = new ArrayList<>();
		this.incomes = new ArrayList<>();
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public boolean isSortDesc() {
		return sortDesc;
	}

	public void setSortDesc(boolean sortDesc) {
		this.sortDesc = sortDesc;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public List<String> getLocationTypes() {
		return locationTypes;
	}

	public void setLocationTypes(List<String> locationTypes) {
		this.locationTypes = locationTypes;
	}

	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public List<String> getRaces() {
		return races;
	}

	public void setRaces(List<String> races) {
		this.races = races;
	}

	public List<String> getAges() {
		return ages;
	}

	public void setAges(List<String> ages) {
		this.ages = ages;
	}

	public List<String> getSexes() {
		return sexes;
	}

	public void setSexes(List<String> sexes) {
		this.sexes = sexes;
	}

	public List<String> getEducations() {
		return educations;
	}

	public void setEducations(List<String> educations) {
		this.educations = educations;
	}

	public List<String> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<String> incomes) {
		this.incomes = incomes;
	}
}
