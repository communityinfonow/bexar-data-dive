package org.cinow.omh.locations;

import java.util.List;

import org.cinow.omh.community.CommunityLocation;

public class AddressLookupResult {
	
	public String address;
	public String geojson;
	public List<CommunityLocation> locations;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGeojson() {
		return geojson;
	}
	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}
	public List<CommunityLocation> getLocations() {
		return locations;
	}
	public void setLocations(List<CommunityLocation> locations) {
		this.locations = locations;
	}

	
}
