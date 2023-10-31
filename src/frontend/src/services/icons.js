export function getCategoryIconPath(categoryId) {
	switch (categoryId) {
	  case '1':
		return '/img/icon_data_civic_social.svg';
	  case '2':
		return '/img/icon_data_criminal_justice.svg';
	  case '3':
		return '/img/icon_data_demographic.svg';
	  case '4':
		return '/img/icon_data_economic.svg'
	  case '5':
		return '/img/icon_data_education.svg';
	  case '6':
		return '/img/icon_data_environment.svg';
	  case '7':
		return '/img/icon_data_health.svg';
	  case '8':
		return '/img/icon_data_housing.svg';
	}

	return '';
  }