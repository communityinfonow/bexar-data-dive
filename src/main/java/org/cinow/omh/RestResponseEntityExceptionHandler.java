package org.cinow.omh;

import org.cinow.omh.data.MaterializedViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MaterializedViewRepository materializedViewRepository;

	@ExceptionHandler(value = { UncategorizedSQLException.class })
	protected ResponseEntity<Object> handleUnpopulatedMaterializedView(UncategorizedSQLException ex, WebRequest request) {
		if ("55000".equals(ex.getSQLException().getSQLState())) {
			this.materializedViewRepository.populateMaterializedViews();
			String bodyOfResponse = "please try again";
			return handleExceptionInternal(ex, bodyOfResponse, 
			  new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
		}
		
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
