package org.cinow.omh.indicators;

import org.cinow.omh.sources.Source;

/**
 * This object represents an indicator.
 * 
 * @author brian
 */
public class Indicator {
	
	/**
	 * The id.
	 */
	private String id;

	/**
	 * The type id.
	 */
	private String typeId;

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
	 * Whether the indicator has MOEs.
	 */
	private boolean hasMoe;

	/**
	 * The source.
	 */
	private Source source;

	/**
	 * Whether the indicator is featured.
	 */
	private boolean featured;

	/**
	 * Whether the indicator displays.
	 */
	private boolean display;

	/**
	 * The rate per (for rate indicator types).
	 */
	private int ratePer;

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
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
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
	public String getName_en() {
		return name_en;
	}

	/**
	 * @param name_en the name_en to set
	 */
	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	/**
	 * @return the name_es
	 */
	public String getName_es() {
		return name_es;
	}

	/**
	 * @param name_es the name_es to set
	 */
	public void setName_es(String name_es) {
		this.name_es = name_es;
	}

	/**
	 * @return the description_en
	 */
	public String getDescription_en() {
		return description_en;
	}

	/**
	 * @param description_en the description_en to set
	 */
	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}

	/**
	 * @return the description_es
	 */
	public String getDescription_es() {
		return description_es;
	}

	/**
	 * @param description_es the description_es to set
	 */
	public void setDescription_es(String description_es) {
		this.description_es = description_es;
	}

	/**
	 * @return the hasMoe
	 */
	public boolean isHasMoe() {
		return hasMoe;
	}

	/**
	 * @param hasMoe the hasMoe to set
	 */
	public void setHasMoe(boolean hasMoe) {
		this.hasMoe = hasMoe;
	}

	/**
	 * @return the hasData
	 */
	public boolean isHasData() {
		return hasData;
	}

	/**
	 * @param hasData the hasData to set
	 */
	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}

	/**
	 * @return the source
	 */
	public Source getSource() {
		return this.source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Source source) {
		this.source = source;
	}

	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return this.source != null ? this.source.getId() : null;
	}

	/**
	 * @return the featured
	 */
	public boolean isFeatured() {
		return featured;
	}

	/**
	 * @param featured the featured to set
	 */
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	/**
	 * @return the display
	 */
	public boolean isDisplay() {
		return display;
	}

	/**
	 * @param display the display to set
	 */
	public void setDisplay(boolean display) {
		this.display = display;
	}

	/**
	 * @return the ratePer
	 */
	public int getRatePer() {
		return ratePer;
	}

	/**
	 * @param ratePer the ratePer to set
	 */
	public void setRatePer(int ratePer) {
		this.ratePer = ratePer;
	}

	/**
	 * @return the aggregable
	 */
	public boolean isAggregable() {
		return aggregable;
	}

	/**
	 * @param aggregable the aggregable to set
	 */
	public void setAggregable(boolean aggregable) {
		this.aggregable = aggregable;
	}

	/**
	 * @return the showPoints
	 */
	public boolean isShowPoints() {
		return showPoints;
	}

	/**
	 * @param showPoints the showPoints to set
	 */
	public void setShowPoints(boolean showPoints) {
		this.showPoints = showPoints;
	}

	/**
	 * @return the showReport
	 */
	public boolean isShowReport() {
		return showReport;
	}

	/**
	 * @param showReport the showReport to set
	 */
	public void setShowReport(boolean showReport) {
		this.showReport = showReport;
	}
}
