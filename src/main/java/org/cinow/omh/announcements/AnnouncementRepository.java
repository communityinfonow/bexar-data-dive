package org.cinow.omh.announcements;

import java.util.List;

/**
 * Repository for {@link Announcement}.
 * 
 * @author brian
 */
public interface AnnouncementRepository {
	
	/**
	 * Finds all announcements to display,
	 * 
	 * @return displayable announcements
	 */
	List<Announcement> findDisplayed();

	/**
	 * Finds all announcements.
	 * 
	 * @return all announcements
	 */
	List<Announcement> findAll();

	/**
	 * Adds an announcement.
	 * 
	 * @param announcement the announcement to add
	 */
	void add(Announcement announcement);

	/**
	 * Updates an announcement.
	 * 
	 * @param announcement the announcement to update
	 */
	void update(Announcement announcement);
}
