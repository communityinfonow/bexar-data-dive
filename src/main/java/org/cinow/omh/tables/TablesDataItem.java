package org.cinow.omh.tables;

import java.math.BigDecimal;

/**
 * This object represents a row in the Tables and Downloads view.
 * 
 * @author brian
 */
public interface TablesDataItem {
	
	String getLocationTypeId();

	String getLocationType_en();

	String getLocationType_es();

	String getLocationId();

	String getLocation_en();

	String getLocation_es();

	String getYear();

	String getRaceId();

	String getRace_en();

	String getRace_es();

	String getAgeId();

	String getAge_en();

	String getAge_es();

	String getSexId();

	String getSex_en();

	String getSex_es();

	String getEducationId();

	String getEducation_en();

	String getEducation_es();

	String getIncomeId();

	String getIncome_en();

	String getIncome_es();

	BigDecimal getValue();

	BigDecimal getMoeLow();

	BigDecimal getMoeHigh();

	BigDecimal getUniverseValue();

	boolean isSuppressed();

	BigDecimal getCountValue();
}
