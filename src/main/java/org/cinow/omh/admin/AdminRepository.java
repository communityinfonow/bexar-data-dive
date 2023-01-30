package org.cinow.omh.admin;

public interface AdminRepository {
	
	/**
	 * Refreshes all materialized views.
	 */
	void refreshMaterializedViews();
}
