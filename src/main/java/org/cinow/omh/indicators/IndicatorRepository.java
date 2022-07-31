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
	List<Indicator> getFeaturedIndicators();
}
