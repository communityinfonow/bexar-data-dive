package org.cinow.omh.tables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TablesController {
	
	@Autowired
	private TablesService tablesService;
	
	@GetMapping(path = "/api/tables-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TablesData> getTablesData(@RequestParam String indicator) {
		return ResponseEntity.ok(this.tablesService.getTablesData(indicator));
	}
}
