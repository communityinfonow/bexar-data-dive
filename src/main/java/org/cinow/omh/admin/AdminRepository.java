package org.cinow.omh.admin;

import java.util.List;

public interface AdminRepository {
	
	/**
	 * Refreshes all materialized views.
	 */
	void refreshMaterializedViews();

	List<AuditLogEntry> findAuditLogEntries();

	List<MissedTranslation> findMissedTranslations();
}
