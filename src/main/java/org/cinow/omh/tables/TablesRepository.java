package org.cinow.omh.tables;

import java.util.List;

public interface TablesRepository {
	
	List<TablesDataItem> getTablesData(String indicator);
}
