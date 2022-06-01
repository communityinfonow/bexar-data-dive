package org.cinow.omh.indicators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndicatorService {
	
	@Autowired
	private IndicatorRepository indicatorRepository;
	
	public IndicatorMenu buildIndicatorMenu() {
		IndicatorMenu indicatorMenu = new IndicatorMenu();
		List<IndicatorCategory> indicatorCategories = this.indicatorRepository.findIndicatorCategories();
		indicatorCategories.stream().forEach(indicatorCategory -> {
			IndicatorMenuCategory menuCategory = new IndicatorMenuCategory();
			menuCategory.setId(indicatorCategory.getId());
			menuCategory.setName_en(indicatorCategory.getName_en());
			menuCategory.setName_es(indicatorCategory.getName_es()); 
			List<Indicator> indicators = this.indicatorRepository.findIndicatorsByCategory(menuCategory.getId());
			indicators.stream().forEach(indicator -> {
				IndicatorMenuItem item = new IndicatorMenuItem();
				item.setId(indicator.getId());
				item.setCategoryId(menuCategory.getId());
				item.setName_en(indicator.getName_en());
				item.setName_es(indicator.getName_es());
				menuCategory.getItems().add(item);
			});
			indicatorMenu.getCategories().add(menuCategory);
		});

		return indicatorMenu;
	}
}
