package org.cinow.omh.tables;

import org.cinow.omh.indicators.IndicatorRepository;
import org.cinow.omh.sources.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the Tables and Downloads view.
 * 
 * @author brian
 */
@Service
public class TablesService {

	/**
	 * The tables repository.
	 */
	@Autowired
	private TablesRepository tablesRepository;

	/**
	 * The indicator repository.
	 */
	@Autowired
	private IndicatorRepository indicatorRepository;

	/**
	 * The source repository.
	 */
	@Autowired
	private SourceRepository sourceRepository;

	/**
	 * Gets the tables data.
	 * 
	 * @param indicatorId the indicator id
	 * @return the tables data
	 */
	public TablesData getTablesData(String indicator) {
		TablesData tablesData = new TablesData();
		tablesData.setIndicator(this.indicatorRepository.getIndicator(indicator));
		tablesData.setCategory(this.indicatorRepository.getIndicatorCategory(indicator));
		tablesData.setSource(this.sourceRepository.getSourceByIndicator(indicator));
		tablesData.setItems(this.tablesRepository.getTablesData(indicator));

		return tablesData;
	}
}
