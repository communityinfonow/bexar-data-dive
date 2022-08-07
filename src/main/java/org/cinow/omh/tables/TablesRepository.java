package org.cinow.omh.tables;

/**
 * Repository for the Tables and Downloads view.
 */
public interface TablesRepository {
	
	/**
	 * Populates the tables data by settinb both the items and total rows.
	 * 
	 * @param request the request
	 * @param data the data to populate
	 * @return the tables data
	 */
	void populateTablesData(TablesDataRequest request, TablesData data);
}
