package org.cinow.omh.indicators;

import org.cinow.omh.menu.Item;

public class Indicator implements Item, IndicatorDetails {
	
	private String id;
	private String typeId;
	private String categoryId;
	private String name_en;
	private String name_es;
	private String description_en;
	private String description_es;
	private String baseFilterTypeId;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getName_en() {
		return name_en;
	}
	public void setName_en(String name_en) {
		this.name_en = name_en;
	}
	public String getName_es() {
		return name_es;
	}
	public void setName_es(String name_es) {
		this.name_es = name_es;
	}
	public String getDescription_en() {
		return description_en;
	}
	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}
	public String getDescription_es() {
		return description_es;
	}
	public void setDescription_es(String description_es) {
		this.description_es = description_es;
	}
	public String getBaseFilterTypeId() {
		return baseFilterTypeId;
	}
	public void setBaseFilterTypeId(String baseFilterTypeId) {
		this.baseFilterTypeId = baseFilterTypeId;
	}	
}
