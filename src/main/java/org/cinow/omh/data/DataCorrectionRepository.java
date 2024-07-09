package org.cinow.omh.data;

import java.util.List;

public interface DataCorrectionRepository {
	
	List<DataCorrection> findDisplayed();

	List<DataCorrection> findAll();

	void add(DataCorrection dataCorrection);

	void update(DataCorrection dataCorrection);

	boolean hasRecent(int indicatorId);
}
