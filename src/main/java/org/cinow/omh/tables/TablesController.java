package org.cinow.omh.tables;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	@PostMapping(path = "/api/tables-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TablesData> getTablesData(@RequestBody TablesDataRequest request) {
		return ResponseEntity.ok(this.tablesService.getTablesData(request));
	}

	/**
	 * Download the tables data.
	 * 
	 * @param indicator the indicator
	 * @return the tables data spreadsheet
	 * @throws IOException
	 */
	@PostMapping(value="/api/tables-download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<ByteArrayResource> downloatTable(@RequestBody TablesDataRequest request) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=table-download.csv");
		
		return new ResponseEntity<>(new ByteArrayResource(this.tablesService.getTablesDataDownloadCsv(request)), header, HttpStatus.CREATED);
    }
	
}
