package org.cinow.omh.locations;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.menu.Menu;

public class LocationMenu implements Menu<LocationMenuCategory> {

	private List<LocationMenuCategory> categories;

	public LocationMenu() {
		this.categories = new ArrayList<LocationMenuCategory>();
	}

	@Override
	public List<LocationMenuCategory> getCategories() {
		return this.categories;
	}

	@Override
	public void setCategories(List<LocationMenuCategory> categories) {
		this.categories = categories;
	}
}
