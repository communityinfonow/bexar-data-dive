package org.cinow.omh.tables;

import java.util.List;

import org.cinow.omh.filters.IndicatorFilter;

/**
 * Repository for the Tables and Downloads view.
 */
public interface TablesRepository {
	
	/**
	 * Populates the tables data by setting both the items and total rows.
	 * 
	 * @param request the request
	 * @param data the data to populate
	 * @return the tables data
	 */
	void populateTablesData(TablesDataRequest request, TablesData data);

	/**
	 * Populates the tables data by setting both the items and total rows.
	 * 
	 * @param request the request
	 * @param data the data to populate
	 * @param filters the filters to apply
	 * @return the tables data
	 */
	void populateTablesDataExport(TablesDataRequest request, TablesDataExport data, List<IndicatorFilter> filters);
}
