package org.cinow.omh.tables;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cinow.omh.indicators.IndicatorRepository;
import org.cinow.omh.sources.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the Tables and Downloads view.
 * 
 * @author brian
 */
@Service
public class TablesService {

	/**
	 * The tables repository.
	 */
	@Autowired
	private TablesRepository tablesRepository;

	/**
	 * The indicator repository.
	 */
	@Autowired
	private IndicatorRepository indicatorRepository;

	/**
	 * The source repository.
	 */
	@Autowired
	private SourceRepository sourceRepository;

	/**
	 * Gets the tables data.
	 * 
	 * @param indicatorId the indicator id
	 * @return the tables data
	 */
	public TablesData getTablesData(TablesDataRequest request) {
		TablesData tablesData = new TablesData();
		tablesData.setIndicator(this.indicatorRepository.getIndicator(request.getIndicator()));
		tablesData.setCategory(this.indicatorRepository.getIndicatorCategory(request.getIndicator()));
		tablesData.setSource(this.sourceRepository.getSourceByIndicator(request.getIndicator()));
		this.tablesRepository.populateTablesData(request, tablesData);

		return tablesData;
	}

	/**
	 * Gets the tables data.
	 * 
	 * @param indicatorId the indicator id
	 * @return the tables data
	 * @throws IOException
	 */
	public byte[] getTablesDataDownload(TablesDataRequest request) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Data");
		Row header = sheet.createRow(0);
		//TODO: espanol for headers and rows
		//TODO: apply filtering?
		header.createCell(0).setCellValue("Location Type");
		header.createCell(1).setCellValue("Location");
		header.createCell(2).setCellValue("Year");
		header.createCell(3).setCellValue("Race/Ethnicity");
		header.createCell(4).setCellValue("Age");
		header.createCell(5).setCellValue("Sex");
		header.createCell(6).setCellValue("Education");
		header.createCell(7).setCellValue("Income");
		header.createCell(8).setCellValue("Value");
		header.createCell(9).setCellValue("MOE (low)");
		header.createCell(10).setCellValue("MOE (high)");
		header.createCell(11).setCellValue("Universe");

		TablesData data = this.getTablesData(request);
		for (TablesDataItem item : data.getItems()) {
			Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
			row.createCell(0).setCellValue(item.getLocationType_en());
			row.createCell(1).setCellValue(item.getLocation_en());
			row.createCell(2).setCellValue(item.getYear());
			row.createCell(3).setCellValue(item.getRace_en());
			row.createCell(4).setCellValue(item.getAge_en());
			row.createCell(5).setCellValue(item.getSex_en());
			row.createCell(6).setCellValue(item.getEducation_en());
			row.createCell(7).setCellValue(item.getIncome_en());
			row.createCell(8).setCellValue(String.valueOf(item.getValue()));
			row.createCell(9).setCellValue(String.valueOf(item.getMoeLow()));
			row.createCell(10).setCellValue(String.valueOf(item.getMoeHigh()));
			row.createCell(11).setCellValue(String.valueOf(item.getUniverseValue()));
		}
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		outputStream.close();
		workbook.close();
		
		return outputStream.toByteArray();
	}
}
