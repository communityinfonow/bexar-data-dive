package org.cinow.omh.announcements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Services for {@link Announcement}.
 * 
 * @author brian
 */
@Service
public class AnnouncementService {

	/**
	 * The announcement repository.
	 */
	@Autowired
	private AnnouncementRepository announcementRepository;
	
	/**
	 * Finds all announcements to display,
	 * 
	 * @return displayable announcements
	 */
	public List<Announcement> findDisplayed() {
		return this.announcementRepository.findDisplayed();
	}

	/**
	 * Finds all announcements.
	 * 
	 * @return all announcements
	 */
	public List<Announcement> findAll() {
		return this.announcementRepository.findAll();
	}

	/**
	 * Adds an announcement.
	 * 
	 * @param announcement the announcement to add
	 */
	public void add(Announcement announcement, String username) {
		this.announcementRepository.add(announcement, username);
	}

	/**
	 * Updates an announcement.
	 * 
	 * @param announcement the announcement to update
	 */
	public void update(Announcement announcement, String username) {
		this.announcementRepository.update(announcement, username);
	}
}
