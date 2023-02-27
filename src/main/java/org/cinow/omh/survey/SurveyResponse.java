package org.cinow.omh.survey;

import java.time.LocalDateTime;

/**
 * Represents a survey response.
 * 
 * @author brian
 */
public class SurveyResponse {
	
	/**
	 * The survey question.
	 */
	private String question;

	/**
	 * The survey response.
	 */
	private String response;

	/**
	 * The response date/time.
	 */
	private LocalDateTime date;

	/**
	 * The language.
	 */
	private String lang;

	/**
	 * The ip address.
	 */
	private String ipAddress;

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @param response the response
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date the date
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
