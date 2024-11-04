package org.cinow.omh.data;

import java.util.Collection;
import java.util.List;

public interface DataCorrectionRepository {
	
	List<DataCorrection> findDisplayed();

	List<DataCorrection> findAll();

	void add(DataCorrection dataCorrection);

	void update(DataCorrection dataCorrection);

	boolean hasRecent(String indicatorId, String year, String locationTypeId, Collection<String> filterTypes);

	boolean hasRecent(String indicatorId, Collection<String> years, Collection<String> locationTypeIds, Collection<String> filterTypes);
}
