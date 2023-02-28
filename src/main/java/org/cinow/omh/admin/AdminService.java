package org.cinow.omh.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;

	public List<AuditLogEntry> findAuditLogEntries() {
		return this.adminRepository.findAuditLogEntries();
	}

	public List<MissedTranslation> findMissedTranslations() {
		return this.adminRepository.findMissedTranslations();
	}
}
