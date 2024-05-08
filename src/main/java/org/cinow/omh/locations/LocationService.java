package org.cinow.omh.locations;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import org.cinow.omh.community.CommunityLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Service class for locations.
 * 
 * @author brian
 */
@Service
public class LocationService {

	/**
	 * The location type repository.
	 */
	@Autowired
	private LocationTypeRepository locationTypeRepository;

	/**
	 * The location repository.
	 */
	@Autowired
	private LocationRepository locationRepository;

	@Value("${locationiq.apikey}")
	private String locationiqApiKey;
	
	/**
	 * Build the location menu.
	 * 
	 * @return the location menu
	 */
	public LocationMenu buildLocationMenu() {
		LocationMenu locationMenu = new LocationMenu();
		List<LocationType> locationTypes = this.locationTypeRepository.findLocationTypes();
		locationTypes.stream().forEach(type -> {
			LocationMenuCategory category = new LocationMenuCategory();
			category.setId(type.getId());
			category.setName_en(type.getName_en());
			category.setName_es(type.getName_es()); 
			List<Location> locations = this.locationRepository.findLocationByType(type.getId());
			locations.stream().forEach(location -> {
				LocationMenuItem item = new LocationMenuItem();
				item.setId(location.getId());
				item.setCategoryId(type.getId());
				item.setName_en(location.getName_en());
				item.setName_es(location.getName_es());
				item.setHasData(true);
				category.getItems().add(item);
			});
			locationMenu.getCategories().add(category);
		});

		return locationMenu;
	}


	/**
	 * Save a custom location.
	 * 
	 * @param location the location
	 */
	public void saveCustomLocation(CustomLocation location) {
		this.locationRepository.saveCustomLocation(location);
	}

	public CustomLocation getCustomLocation(String id) {
		Location location = this.locationRepository.findCustomLocation(id);
		CustomLocation customLocation = new CustomLocation();
		customLocation.setId(id);
		customLocation.setTypeId(this.locationRepository.findCustomLocationType(id));
		customLocation.setName(location.getName_en());
		customLocation.setIds(this.locationRepository.findCustomLocationIds(id));
		customLocation.setGeojson(this.locationRepository.findCustomLocationGeojson(id));
		
		return customLocation;
	}

	/**
	 * Find address matches.
	 * 
	 * @param address
	 * @return the address matches in JSON format
	 * @throws URISyntaxException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public String findAddressMatches(String address) throws URISyntaxException, IOException, InterruptedException {
		if (!StringUtils.hasText(address)) {
			return null;
		}
		String formattedAddress = !address.contains("TX") ? address + " Bexar County, TX" : address;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI("https://us1.locationiq.com/v1/search?key=" + locationiqApiKey 
					+ "&format=json&addressdetails=1&normalizeaddress=1&q=" + URLEncoder.encode(formattedAddress, "UTF-8")))
				.GET()
				.build();
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		return response.body();
	}

	/**
	 * Lookup an address.
	 * 
	 * @param address the address
	 * @return the address lookup result
	 */
	public List<CommunityLocation> findCommunitiesByPoint(String lat, String lng) {
		return this.locationRepository.findCommunitiesByPoint(lat, lng);
	}
}
