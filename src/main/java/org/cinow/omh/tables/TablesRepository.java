package org.cinow.omh.tables;

import java.util.List;

/**
 * Repository for the Tables and Downloads view.
 */
public interface TablesRepository {
	
	/**
	 * Get the tables data.
	 * 
	 * @param indicator the indicator
	 * @return the tables data
	 */
	List<TablesDataItem> getTablesData(String indicator);
}
