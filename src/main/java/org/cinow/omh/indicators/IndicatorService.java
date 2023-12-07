package org.cinow.omh.indicators;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.community.CommunityData;
import org.cinow.omh.community.CommunityDataIndicator;
import org.cinow.omh.community.CommunityRepository;
import org.cinow.omh.filters.FilterRepository;
import org.cinow.omh.filters.IndicatorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liquibase.pro.packaged.A;

/**
 * Service class for indicators.
 * 
 * @author brian
 */
@Service
public class IndicatorService {
	
	/**
	 * The indicator repository.
	 */
	@Autowired
	private IndicatorRepository indicatorRepository;

	@Autowired
	private FilterRepository filterRepository;

	@Autowired
	private CommunityRepository communityRepository;
	
	/**
	 * Builds the indicator menu.
	 * 
	 * @return the indicator menu
	 */
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
				item.setDescription_en(indicator.getDescription_en());
				item.setDescription_es(indicator.getDescription_es());
				item.setHasData(indicator.isHasData());
				item.setAggregable(indicator.isAggregable());
				item.setShowPoints(indicator.isShowPoints());
				item.setShowReport(indicator.isShowReport());
				menuCategory.getItems().add(item);
			});
			if (indicatorCategory.getParentCategoryId() == null) {
				indicatorMenu.getCategories().add(menuCategory);
			} else {
				indicatorMenu.getCategories()
					.stream()
					.filter(c -> c.getId().equals(indicatorCategory.getParentCategoryId()))
					.findFirst()
					.get().getSubcategories().add(menuCategory);
			}
		});

		return indicatorMenu;
	}

	/**
	 * Gets the Bexar Data Facts
	 * 
	 * @return the Bezar Data Facts
	 */
	public List<CommunityDataIndicator> getBexarDataFacts() {
		List<CommunityDataIndicator> data = new ArrayList<>();
		// first, find one random featured indicator
		Indicator indicator = this.indicatorRepository.getFeaturedIndicators().get(0);

		// then, find the available disaggregations for that indicator
		List<IndicatorFilter> filters = this.filterRepository.getIndicatorFilters(indicator.getId());
		
		// then, find the community data for that indicator for each disaggregation for bexar county

		filters.forEach(f -> {
			CommunityDataIndicator i = this.communityRepository.getCommunityData("48029", "1", f.getType().getId(), indicator.getId())
				.get(0).getIndicators().get(0);
			i.setFilterType(f.getType());
			data.add(i);
		});


		return data;
	}

	/**
	 * Gets the featured indicators.
	 * 
	 * @return the featured indicators
	 */
	public List<Indicator> getFeaturedIndicators() {
		return this.indicatorRepository.getFeaturedIndicators();
	}

	/**
	 * Finds indicators.
	 * 
	 * @return the indicators
	 */
	public List<Indicator> findIndicators() {
		return this.indicatorRepository.findIndicators();
	}

	/**
	 * Finds indicator categories.
	 * 
	 * @return the indicator types
	 */
	public List<IndicatorCategory> findCategories() {
		return this.indicatorRepository.findIndicatorCategories();
	}

	/**
	 * Finds indicator types.
	 * 
	 * @return the indicator types
	 */
	public List<IndicatorType> findTypes() {
		return this.indicatorRepository.findIndicatorTypes();
	}

	/**
	 * Adds an indicator.
	 * 
	 * @param indicator the indicator
	 */
	public void addIndicator(Indicator indicator, String username) {
		this.indicatorRepository.addIndicator(indicator, username);
	}

	/**
	 * Updates an indicator.
	 * 
	 * @param indicator the indicator
	 */
	public void updateIndicator(Indicator indicator, String username) {
		this.indicatorRepository.updateIndicator(indicator, username);
	}
}
