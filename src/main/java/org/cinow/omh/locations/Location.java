package org.cinow.omh.locations;

import org.cinow.omh.menu.Item;

public class Location implements Item {
	
	private String id;
	private String typeId;
	private String name_en;
	private String name_es;
	private String description_en;
	private String description_es;
	
	@Override
	public String getCategoryId() {
		return typeId;
	}

	@Override
	public void setCategoryId(String id) {
		this.typeId = id;
	}
	
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

	@Override
	public String getDescription_en() {
		return description_en;
	}

	@Override
	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}

	@Override
	public String getDescription_es() {
		return description_es;
	}

	@Override
	public void setDescription_es(String description_es) {
		this.description_es = description_es;
	}
}
