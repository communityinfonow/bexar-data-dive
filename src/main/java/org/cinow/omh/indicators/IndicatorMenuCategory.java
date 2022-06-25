package org.cinow.omh.indicators;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.menu.Category;

public class IndicatorMenuCategory implements Category<IndicatorMenuItem> {
	
	private String id;
	private String name_en;
	private String name_es;
	private List<IndicatorMenuItem> indicators;

	public IndicatorMenuCategory() {
		this.indicators = new ArrayList<>();
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
	public List<IndicatorMenuItem> getItems() {
		return this.indicators;
	}

	@Override
	public void setItems(List<IndicatorMenuItem> items) {
		this.indicators = items;
	}
}
