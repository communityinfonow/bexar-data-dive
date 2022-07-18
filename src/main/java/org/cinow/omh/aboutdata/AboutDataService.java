package org.cinow.omh.aboutdata;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.indicators.IndicatorRepository;
import org.cinow.omh.sources.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutDataService {
	
	@Autowired
	private IndicatorRepository indicatorRepository;

	@Autowired
	private SourceRepository sourceRepository;
	
	public AboutData getAboutData() {

		AboutData aboutData = new AboutData();
		aboutData.setCategories(new ArrayList<>());
		this.indicatorRepository.findIndicatorCategories().stream().forEach(indicatorCategory -> {
			AboutDataCategory aboutCategory = new AboutDataCategory();
			aboutCategory.setCategory(indicatorCategory);
			aboutCategory.setItems(new ArrayList<>());
			aboutCategory.setSubcategories(new ArrayList<>());
			List<Indicator> indicators = this.indicatorRepository.findIndicatorsByCategory(aboutCategory.getCategory().getId());
			indicators.stream().forEach(indicator -> {
				AboutDataItem item = new AboutDataItem();
				item.setIndicator(indicator);
				item.setSource(this.sourceRepository.getSourceByIndicator(indicator.getId()));
				aboutCategory.getItems().add(item);
			});
			if (indicatorCategory.getParentCategoryId() == null) {
				aboutData.getCategories().add(aboutCategory);
			} else {
				aboutData.getCategories()
					.stream()
					.filter(c -> c.getCategory().getId().equals(indicatorCategory.getParentCategoryId()))
					.findFirst()
					.get().getSubcategories().add(aboutCategory);
			}
		});

		return aboutData;
	}
}
