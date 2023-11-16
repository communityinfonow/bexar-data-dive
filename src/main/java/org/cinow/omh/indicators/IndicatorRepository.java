package org.cinow.omh.indicators;

import java.util.List;

/**
 * Repository for indicators.
 * 
 * @author brian
 */
public interface IndicatorRepository {
	
	/**
	 * Finds indicator categories.
	 * 
	 * @return indicator categories
	 */
	List<IndicatorCategory> findIndicatorCategories();

	/**
	 * Finds indicators by category.
	 * 
	 * @param categoryId the category id
	 * @return the indicators
	 */
	List<Indicator> findIndicatorsByCategory(String categoryId);

	/**
	 * Gets an indicator.
	 * 
	 * @param id the id
	 * @return the indicator
	 */
	Indicator getIndicator(String id);

	/**
	 * Gets featured indicators.
	 * 
	 * @return the featured indicators
	 */
	List<FeaturedIndicator> getFeaturedIndicators();

	/**
	 * Gets the indicator category.
	 * 
	 * @param id the id
	 * @return the indicator category
	 */
	IndicatorCategory getIndicatorCategory(String id);

	/**
	 * Determines whether any data is present for an indicator.
	 * 
	 * @param id the id
	 * @return <code>true</code> if data is present
	 */
	boolean hasData(String id);

	/**
	 * Finds indicators.
	 * 
	 * @return the indicators
	 */
	List<Indicator> findIndicators();

	/**
	 * Finds indicator types.
	 * 
	 * @return the indicator types
	 */
	List<IndicatorType> findIndicatorTypes();

	/**
	 * Adds an indicator.
	 * 
	 * @param indicator the indicator
	 */
	void addIndicator(Indicator indicator, String username);

	/**
	 * Updates an indicator.
	 * 
	 * @param indicator the indicator
	 */
	void updateIndicator(Indicator indicator, String username);
}
