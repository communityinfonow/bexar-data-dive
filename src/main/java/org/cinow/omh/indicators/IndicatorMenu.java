package org.cinow.omh.indicators;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.menu.Menu;

public class IndicatorMenu implements Menu<IndicatorMenuCategory> {
	
	private List<IndicatorMenuCategory> categories;

	public IndicatorMenu() {
		this.categories = new ArrayList<IndicatorMenuCategory>();
	}

	@Override
	public List<IndicatorMenuCategory> getCategories() {
		return this.categories;
	}

	@Override
	public void setCategories(List<IndicatorMenuCategory> categories) {
		this.categories = categories;
	}
}
