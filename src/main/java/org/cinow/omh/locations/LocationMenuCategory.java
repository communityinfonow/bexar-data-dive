package org.cinow.omh.locations;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.menu.Category;

/**
 * This object represents a location menu category.
 */
public class LocationMenuCategory implements Category<LocationMenuItem> {

	/**
	 * The id.
	 */
	private String id;

	/**
	 * The name (English).
	 */
	private String name_en;

	/**
	 * The name (Spanish).
	 */
	private String name_es;

	/**
	 * The locations.
	 */
	private List<LocationMenuItem> locations;

	/**
	 * Constructor initializes fields.
	 */
	public LocationMenuCategory() {
		this.locations = new ArrayList<>();
	}

	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name_en
	 */
	@Override
	public String getName_en() {
		return this.name_en;
	}

	/**
	 * @param name_en the name_en to set
	 */
	@Override
	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	/**
	 * @return the name_es
	 */
	@Override
	public String getName_es() {
		return this.name_es;
	}

	/**
	 * @param name_es the name_es to set
	 */
	@Override
	public void setName_es(String name_es) {
		this.name_es = name_es;
	}

	/**
	 * @return the locations
	 */
	@Override
	public List<LocationMenuItem> getItems() {
		return this.locations;
	}

	/**
	 * @param locations the locations to set
	 */
	@Override
	public void setItems(List<LocationMenuItem> items) {
		this.locations = items;
	}

	/**
	 * @param location the location to add
	 */
	@Override
	public List<Category<LocationMenuItem>> getSubcategories() {
		// not supported for locations
		return null;
	}

	/**
	 * @param subcategories the subcategories to set
	 */
	@Override
	public void setSubcategories(List<Category<LocationMenuItem>> subcategories) {
		// not supported for locations
	}
}
