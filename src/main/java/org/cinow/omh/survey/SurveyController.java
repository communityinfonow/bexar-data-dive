package org.cinow.omh.survey;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for {@link SurveyResponse}.
 * 
 * @author brian
 */
@RestController
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;

	/**
	 * Adds a survey response.
	 * 
	 * @param surveyResponse the survey response
	 */
	@PostMapping(path = "/api/survey-responses", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody SurveyResponse surveyResponse, HttpServletRequest request) {
		surveyResponse.setIpAddress(request.getHeader("X-FORWARDED-FOR"));
		this.surveyService.addSurveyResponse(surveyResponse);

		return ResponseEntity.ok().build();
	}

	/**
	 * Finds all survey responses.
	 * 
	 * @return all survey responses
	 */
	@GetMapping(path = "/api/admin/survey-responses", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SurveyResponse>> findAll() {
		return ResponseEntity.ok(this.surveyService.findAll());
	}
}
