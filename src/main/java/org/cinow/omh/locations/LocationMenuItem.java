package org.cinow.omh.locations;

import org.cinow.omh.menu.Item;

public class LocationMenuItem implements Item {

	private String id;
	private String categoryId;
	private String name_en;
	private String name_es;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
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
