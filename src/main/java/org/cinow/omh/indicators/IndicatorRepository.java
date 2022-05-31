package org.cinow.omh.indicators;

import java.util.List;

public interface IndicatorRepository {
	
	List<IndicatorCategory> findIndicatorCategories();

	List<Indicator> findIndicatorsByCategory(long categoryId);
}
