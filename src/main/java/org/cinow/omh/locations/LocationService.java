package org.cinow.omh.locations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
