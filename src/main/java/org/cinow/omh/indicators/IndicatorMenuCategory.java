package org.cinow.omh.indicators;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.menu.Category;

public class IndicatorMenuCategory implements Category<IndicatorMenuItem> {
	
	private String id;
	private String name_en;
	private String name_es;
	private List<Category<IndicatorMenuItem>> subcategories;
	private List<IndicatorMenuItem> items;

	public IndicatorMenuCategory() {
		this.subcategories = new ArrayList<>();
		this.items = new ArrayList<>();
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName_en() {
		return this.name_en;
	}

	@Override
	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	@Override
	public String getName_es() {
		return this.name_es;
	}

	@Override
	public void setName_es(String name_es) {
		this.name_es = name_es;
	}

	@Override
	public List<Category<IndicatorMenuItem>> getSubcategories() {
		return subcategories;
	}

	@Override
	public void setSubcategories(List<Category<IndicatorMenuItem>> subcategories) {
		this.subcategories = subcategories;
	}

	@Override
	public List<IndicatorMenuItem> getItems() {
		return this.items;
	}

	@Override
	public void setItems(List<IndicatorMenuItem> items) {
		this.items = items;
	}
}
