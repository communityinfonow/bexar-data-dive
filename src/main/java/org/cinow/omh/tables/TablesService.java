package org.cinow.omh.tables;

import org.cinow.omh.indicators.IndicatorRepository;
import org.cinow.omh.sources.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TablesService {

	@Autowired
	private TablesRepository tablesRepository;

	@Autowired
	private IndicatorRepository indicatorRepository;

	@Autowired
	private SourceRepository sourceRepository;

	public TablesData getTablesData(String indicator) {
		TablesData tablesData = new TablesData();
		tablesData.setIndicator(this.indicatorRepository.getIndicator(indicator));
		tablesData.setSource(this.sourceRepository.getSourceByIndicator(indicator));
		tablesData.setItems(this.tablesRepository.getTablesData(indicator));

		return tablesData;
	}
}
