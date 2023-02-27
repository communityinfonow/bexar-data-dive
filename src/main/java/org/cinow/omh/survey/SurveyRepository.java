package org.cinow.omh.survey;

import java.util.List;

/**
 * Repository for {@link SurveyResponse}.
 * 
 * @author brian
 */
public interface SurveyRepository {
	
	/**
	 * Adds a survey response.
	 * 
	 * @param surveyResponse the survey response
	 */
	void addSurveyResponse(SurveyResponse surveyResponse);

	/**
	 * Finds all survey responses.
	 * 
	 * @return all survey responses
	 */
	List<SurveyResponse> findAll();
}
