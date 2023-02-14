package org.cinow.omh.announcements;

import java.time.LocalDate;

/**
 * This object represents an announcement.
 * 
 * @author brian
 */
public class Announcement {

	/**
	 * The id.
	 */
	private int id;
	
	/**
	 * The English title.
	 */
	private String title_en;

	/**
	 * The Spanish title.
	 */
	private String title_es;

	/**
	 * The English message (html).
	 */
	private String message_en;

	/**
	 * The Spanish message (html).
	 */
	private String message_es;

	/**
	 * The announcement date.
	 */
	private LocalDate date;

	/**
	 * Whether to display the announcement.
	 */
	private boolean display;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle_en() {
		return title_en;
	}

	public void setTitle_en(String title_en) {
		this.title_en = title_en;
	}

	public String getTitle_es() {
		return title_es;
	}

	public void setTitle_es(String title_es) {
		this.title_es = title_es;
	}

	public String getMessage_en() {
		return message_en;
	}

	public void setMessage_en(String message_en) {
		this.message_en = message_en;
	}

	public String getMessage_es() {
		return message_es;
	}

	public void setMessage_es(String message_es) {
		this.message_es = message_es;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}	
}
