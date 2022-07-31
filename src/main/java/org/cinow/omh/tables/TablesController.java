package org.cinow.omh.tables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for the Tables and Downloads view.
 * 
 * @author brian
 */
@RestController
public class TablesController {
	
	/**
	 * The table service.
	 */
	@Autowired
	private TablesService tablesService;
	
	/**
	 * Get the tables data.
	 * 
	 * @param indicator the indicator
	 * @return the tables data
	 */
	@GetMapping(path = "/api/tables-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TablesData> getTablesData(@RequestParam String indicator) {
		return ResponseEntity.ok(this.tablesService.getTablesData(indicator));
	}
}
