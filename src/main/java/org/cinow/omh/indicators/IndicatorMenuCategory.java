package org.cinow.omh.indicators;

import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.menu.Category;

/**
 * This object represents an indicator menu category.
 * 
 * @author brian
 */
public class IndicatorMenuCategory implements Category<IndicatorMenuItem> {
	
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
	 * The subcategories.
	 */
	private List<Category<IndicatorMenuItem>> subcategories;

	/**
	 * The items.
	 */
	private List<IndicatorMenuItem> items;

	/**
	 * Constructor initializes fields.
	 */
	public IndicatorMenuCategory() {
		this.subcategories = new ArrayList<>();
		this.items = new ArrayList<>();
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
	 * @return the subcategories
	 */
	@Override
	public List<Category<IndicatorMenuItem>> getSubcategories() {
		return subcategories;
	}

	/**
	 * @param subcategories the subcategories to set
	 */
	@Override
	public void setSubcategories(List<Category<IndicatorMenuItem>> subcategories) {
		this.subcategories = subcategories;
	}

	/**
	 * @return the items
	 */
	@Override
	public List<IndicatorMenuItem> getItems() {
		return this.items;
	}

	/**
	 * @param items the items to set
	 */
	@Override
	public void setItems(List<IndicatorMenuItem> items) {
		this.items = items;
	}
}
