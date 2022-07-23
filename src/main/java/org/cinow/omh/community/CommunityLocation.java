package org.cinow.omh.community;

public class CommunityLocation {
	
	private String id;
	private String typeId;
	private String name_en;
	private String name_es;
	private String geojson;

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
	public String getGeojson() {
		return geojson;
	}
	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}
}
