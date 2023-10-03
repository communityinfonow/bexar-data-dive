package org.cinow.omh.indicators;

import org.cinow.omh.menu.Item;

/**
 * This object represents an indicator menu item.
 * 
 * @author brian
 */
public class IndicatorMenuItem implements Item {

	/**
	 * The id.
	 */
	private String id;

	/**
	 * The category id.
	 */
	private String categoryId;

	/**
	 * The name (English).
	 */
	private String name_en;

	/**
	 * The name (Spanish).
	 */
	private String name_es;

	/**
	 * The description (English).
	 */
	private String description_en;

	/**
	 * The description (Spanish).
	 */
	private String description_es;

	/**
	 * Whether the indicator has data yet.
	 */
	private boolean hasData;

	/**
	 * Whether the indicator may be aggregated.
	 */
	private boolean aggregable;

	/**
	 * Whether to show points for this indicator.
	 */
	private boolean showPoints;

	/**
	 * Whether to show a report for this indicator.
	 */
	private boolean showReport;

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
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
	 * @return the description_en
	 */
	@Override
	public String getDescription_en() {
		return description_en;
	}

	/**
	 * @param description_en the description_en to set
	 */
	@Override
	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}

	/**
	 * @return the description_es
	 */
	@Override
	public String getDescription_es() {
		return description_es;
	}

	/**
	 * @param description_es the description_es to set
	 */
	@Override
	public void setDescription_es(String description_es) {
		this.description_es = description_es;
	}

	public boolean isHasData() {
		return hasData;
	}

	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}

	public boolean isAggregable() {
		return aggregable;
	}

	public void setAggregable(boolean aggregable) {
		this.aggregable = aggregable;
	}

	public boolean isShowPoints() {
		return showPoints;
	}

	public void setShowPoints(boolean showPoints) {
		this.showPoints = showPoints;
	}

	public boolean isShowReport() {
		return showReport;
	}

	public void setShowReport(boolean showReport) {
		this.showReport = showReport;
	}
}
