package org.cinow.omh.sources;

/**
 * Represents a data source.
 * 
 * @author brian
 */
public class Source {
	
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
	 * The url.
	 */
	private String url;

	/**
	 * The data source's required trend interval.
	 */
	private int trendInterval;

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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the trendInterval
	 */
	public int getTrendInterval() {
		return trendInterval;
	}

	/**
	 * @param trendInterval the trendInterval to set
	 */
	public void setTrendInterval(int trendInterval) {
		this.trendInterval = trendInterval;
	}
}
