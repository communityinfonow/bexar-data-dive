package org.cinow.omh.explore;

public class Point {

	private String id;
	private String valueLabel_en;
	private String valueLabel_es;
	private int value;
	private String featureProperties_en;
	private String featureProperties_es;
	private String geojson;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getGeojson() {
		return geojson;
	}
	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}
	public String getFeatureProperties_en() {
		return featureProperties_en;
	}
	public void setFeatureProperties_en(String featureProperties_en) {
		this.featureProperties_en = featureProperties_en;
	}
	public String getFeatureProperties_es() {
		return featureProperties_es;
	}
	public void setFeatureProperties_es(String featureProperties_es) {
		this.featureProperties_es = featureProperties_es;
	}
	public String getValueLabel_en() {
		return valueLabel_en;
	}
	public void setValueLabel_en(String valueLabel_en) {
		this.valueLabel_en = valueLabel_en;
	}
	public String getValueLabel_es() {
		return valueLabel_es;
	}
	public void setValueLabel_es(String valueLabel_es) {
		this.valueLabel_es = valueLabel_es;
	}
	
}
