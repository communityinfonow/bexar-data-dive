package org.cinow.omh.announcements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for {@link Announcement}.
 * 
 * @author brian
 */
@RestController
public class AnnouncementController {
	
	@Autowired
	private AnnouncementService announcementService;

	/**
	 * Finds all announcements to display,
	 * 
	 * @return displayable announcements
	 */
	@GetMapping(path = "/api/announcements", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Announcement>> findDisplayed() {
		return ResponseEntity.ok(this.announcementService.findDisplayed());
	}

	/**
	 * Finds all announcements.
	 * 
	 * @return all announcements
	 */
	@GetMapping(path = "/api/admin/announcements", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Announcement>> findAll() {
		return ResponseEntity.ok(this.announcementService.findAll());
	}

	/**
	 * Adds an announcement.
	 * 
	 * @param announcement the announcement to add
	 */
	@PostMapping(path = "/api/admin/announcements", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Announcement announcement) {
		this.announcementService.add(announcement);

		return ResponseEntity.ok().build();
	}

	/**
	 * Updates an announcement.
	 * 
	 * @param announcement the announcement to update
	 */
	@PutMapping(path = "/api/admin/announcements", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Announcement announcement) {
		this.announcementService.update(announcement);

		return ResponseEntity.ok().build();
	}
}
