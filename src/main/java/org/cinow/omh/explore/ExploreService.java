package org.cinow.omh.explore;

import org.cinow.omh.indicators.IndicatorRepository;
import org.cinow.omh.sources.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExploreService {

	@Autowired
	private ExploreRepository exploreRepository;

	@Autowired
	private IndicatorRepository indicatorRepository;

	@Autowired
	private SourceRepository sourceRepository;

	public ExploreData getExploreData(String indicator) {
		ExploreData exploreData = new ExploreData();
		exploreData.setIndicator(this.indicatorRepository.getIndicator(indicator));
		exploreData.setSource(this.sourceRepository.getSourceByIndicator(indicator));
		exploreData.setItems(this.exploreRepository.getExploreData(indicator));

		return exploreData;
	}
}
