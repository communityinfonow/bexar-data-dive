package org.cinow.omh.indicators;

import org.cinow.omh.menu.Item;

public class IndicatorMenuItem implements Item {

	private long id;
	private long categoryId;
	private String name_en;
	private String name_es;

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
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
}
