package org.cinow.omh.tables;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cinow.omh.indicators.IndicatorRepository;
import org.cinow.omh.sources.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
			Cell valueCell = row.createCell(8);
			if (item.getValue() != null) { 
				valueCell.setCellValue(item.getValue()); 
			}
			Cell moeLowCell = row.createCell(9);
			if (item.getMoeLow() != null) {
				moeLowCell.setCellValue(item.getMoeLow());
			}
			Cell moeHighCell = row.createCell(10);
			if (item.getMoeHigh() != null) {
				moeHighCell.setCellValue(item.getMoeHigh());
			}
			Cell universeValueCell = row.createCell(11);
			if (item.getUniverseValue() != null) {
				universeValueCell.setCellValue(item.getUniverseValue());
			}
		}
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		outputStream.close();
		workbook.close();
		
		return outputStream.toByteArray();
	}

	/**
	 * Gets the tables data.
	 * 
	 * @param indicatorId the indicator id
	 * @return the tables data
	 * @throws IOException
	 */
	public byte[] getTablesDataDownloadCsv(TablesDataRequest request) throws IOException {
		//TODO: espanol for headers and rows
		//TODO: apply filtering?
		StringBuilder csv = new StringBuilder();
		csv.append(enquote("Location Type") + ",");
		csv.append(enquote("Location") + ",");
		csv.append(enquote("Year") + ",");
		csv.append(enquote("Race/Ethnicity") + ",");
		csv.append(enquote("Age") + ",");
		csv.append(enquote("Sex") + ",");
		csv.append(enquote("Education") + ",");
		csv.append(enquote("Income") + ",");
		csv.append(enquote("Value") + ",");
		csv.append(enquote("MOE (low)") + ",");
		csv.append(enquote("MOE (high)") + ",");
		csv.append(enquote("Universe") + ",");
		csv.append("\n");
		TablesData data = this.getTablesData(request);
		for (TablesDataItem item : data.getItems()) {
			csv.append(enquote(item.getLocationType_en()) + ",");
			csv.append(enquote(item.getLocation_en()) + ",");
			csv.append(enquote(item.getYear()) + ",");
			csv.append(enquote(item.getRace_en()) + ",");
			csv.append(enquote(item.getAge_en()) + ",");
			csv.append(enquote(item.getSex_en()) + ",");
			csv.append(enquote(item.getEducation_en()) + ",");
			csv.append(enquote(item.getIncome_en()) + ",");
			csv.append((item.getValue() != null ? item.getValue() : "") + ",");
			csv.append((item.getMoeLow() != null ? item.getMoeLow() : "") + ",");
			csv.append((item.getMoeHigh() != null ? item.getMoeHigh() : "") + ",");
			csv.append((item.getUniverseValue() != null ? item.getUniverseValue() : "") + ",");
			csv.append("\n");
		}
		
		return csv.toString().getBytes();
	}

	private String enquote(String text) {
		return "\"" + (text != null ? text : "") + "\"";
	}
}
