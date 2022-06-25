package org.cinow.omh.locations;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.menu.Category;

public class LocationMenuCategory implements Category<LocationMenuItem> {

	private String id;
	private String name_en;
	private String name_es;
	private List<LocationMenuItem> locations;

	public LocationMenuCategory() {
		this.locations = new ArrayList<>();
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
	public List<LocationMenuItem> getItems() {
		return this.locations;
	}

	@Override
	public void setItems(List<LocationMenuItem> items) {
		this.locations = items;
	}
}
