package org.cinow.omh.tables;

import java.io.IOException;
import java.util.List;

import org.cinow.omh.filters.FilterRepository;
import org.cinow.omh.filters.IndicatorFilter;
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
	 * The filter repository.
	 */
	@Autowired
	private FilterRepository filterRepository;

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
	public byte[] getTablesDataDownloadCsv(TablesDataRequest request) throws IOException {
		TablesData data = this.getTablesData(request);
		List<IndicatorFilter> filters = this.filterRepository.getIndicatorFilters(request.getIndicator());

		StringBuilder csv = new StringBuilder();
		csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Indicador" : "Indicator") + ",");
		csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Fuente" : "Source") + ",");
		csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Tipo de Ubicación" : "Location Type") + ",");
		csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Ubicación" : "Location") + ",");
		csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Año" : "Year") + ",");
		if (filters.stream().anyMatch(f -> f.getType().getId().equals("1"))) {
			csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Raza/Etnia" : "Race/Ethnicity") + ",");
		}
		if (filters.stream().anyMatch(f -> f.getType().getId().equals("2"))) {
			csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Edad" : "Age") + ",");
		}
		if (filters.stream().anyMatch(f -> f.getType().getId().equals("3"))) {
			csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Sexo" : "Sex") + ",");
		}
		if (filters.stream().anyMatch(f -> f.getType().getId().equals("4"))) {
			csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Nivel Educativo" : "Education") + ",");
		}
		if (filters.stream().anyMatch(f -> f.getType().getId().equals("5"))) {
			csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Nivel de Ingresos" : "Income") + ",");
		}
		csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Valor" : "Value") + ",");
		csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "ME (bajo)" : "MOE (low)") + ",");
		csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "ME (alto)" : "MOE (high)") + ",");
		csv.append(enquote("es".equalsIgnoreCase(request.getLang()) ? "Universo" : "Universe") + ",");
		csv.append("\n");
		
		for (TablesDataItem item : data.getItems()) {
			csv.append(enquote((data.getCategory().getParentCategoryId() != null ? data.getCategory().getName_en() + " - " : "") + data.getIndicator().getName_en()) + ",");
			csv.append(enquote(data.getSource().getName_en()) + ",");
			csv.append(enquote(item.getLocationType_en()) + ",");
			csv.append(enquote(item.getLocation_en()) + ",");
			csv.append(enquote(item.getYear()) + ",");
			if (filters.stream().anyMatch(f -> f.getType().getId().equals("1"))) {
				csv.append(enquote(item.getRace_en()) + ",");
			}
			if (filters.stream().anyMatch(f -> f.getType().getId().equals("2"))) {
				csv.append(enquote(item.getAge_en()) + ",");
			}
			if (filters.stream().anyMatch(f -> f.getType().getId().equals("3"))) {
				csv.append(enquote(item.getSex_en()) + ",");
			}
			if (filters.stream().anyMatch(f -> f.getType().getId().equals("4"))) {
				csv.append(enquote(item.getEducation_en()) + ",");
			}
			if (filters.stream().anyMatch(f -> f.getType().getId().equals("5"))) {
				csv.append(enquote(item.getIncome_en()) + ",");
			}
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
