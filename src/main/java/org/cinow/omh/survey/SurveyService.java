package org.cinow.omh.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for {@link SurveyResponse}.
 * 
 * @author brian
 */
@Service
public class SurveyService {

	@Autowired
	private SurveyRepository surveyRepository;
	
	/**
	 * Adds a survey response.
	 * 
	 * @param surveyResponse the survey response
	 */
	public void addSurveyResponse(SurveyResponse surveyResponse) {
		this.surveyRepository.addSurveyResponse(surveyResponse);
	}

	/**
	 * Finds all survey responses.
	 * 
	 * @return all survey responses
	 */
	public List<SurveyResponse> findAll() {
		return this.surveyRepository.findAll();
	}
}
