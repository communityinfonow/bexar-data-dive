import http from 'k6/http'
import { check, sleep } from 'k6'

export let options = {
	discardResponseBodies: true,
	scenarios: {
	  loadDashboard: {
		executor: 'ramping-vus',
		startVUs: 0,
		stages: [
		  { duration: '30s', target: 50 }, // simulate ramp-up of traffic from 1 to 50 users over 1 minute
		  { duration: '1m', target: 50 }, // stay at 50 users for 1 minute
		  { duration: '30s', target: 100 }, // simulate ramp-up of traffic from 50 to 100 users over 1 minute
		  { duration: '1m', target: 100 }, // stay at 100 users for 1 minute
		  { duration: '30s', target: 0 }, // simulate ramp-down of traffic from 100 to 0 users over 1 minute
		],
		gracefulRampDown: '120s',
		gracefulStop: '120s'
	  }
	},
	thresholds: {
	  'http_req_duration{endpoint:indicator-menu}': ['p(95)<1000'], // 95% of requests must complete below 1s
	  'http_req_duration{endpoint:location-menu}': ['p(95)<1000'], // 95% of requests must complete below 1s
	  'http_req_duration{endpoint:community-locations}': ['p(95)<2000'], // 95% of requests must complete below 2s
	  'http_req_duration{endpoint:community-location}': ['p(95)<2000'], // 95% of requests must complete below 2s
	  'http_req_duration{endpoint:community-data}': ['p(95)<3000'], // 95% of requests must complete below 3s
	  'http_req_duration{endpoint:indicator-source}': ['p(95)<1000'], // 95% of requests must complete below 1s
	  'http_req_duration{endpoint:filters}': ['p(95)<2000'], // 95% of requests must complete below 2s
	  'http_req_duration{endpoint:explore-data}': ['p(95)<3000'], // 95% of requests must complete below 3s
	  'http_req_duration{endpoint:tables-data}': ['p(95)<5000'], // 95% of requests must complete below 5s
	}
  }
  
  const BASE_URL = 'https://omh-dev.cinow.info'

export default () => {
	// first load the menus and wait 1-3 seconds
	let indicatorMenuResponse = http.get(
		`${BASE_URL}/api/indicator-menu`,
		{
			tags: { endpoint: 'indicator-menu' }
		}
	)
	check(indicatorMenuResponse, {
		'loaded indicator menu': resp => resp.status === 200
	})
	let locationMenuResponse = http.get(
		`${BASE_URL}/api/location-menu`,
		{
			tags: { endpoint: 'location-menu' }
		}
	)
	check(locationMenuResponse, {
		'loaded location menu': resp => resp.status === 200
	})
	sleep(Math.floor(Math.random() * (3 - 1 + 1) + 1))
	
	// then load the community locations and wait another 1-3 seconds
	let locationCommunityLocationsResponse = http.get(
		`${BASE_URL}/api/community-locations?locationType=4`,
		{
			tags: { endpoint: 'community-locations' }
		}
	)
	check(locationCommunityLocationsResponse, {
		'loaded community locations': resp => resp.status === 200
	})
	sleep(Math.floor(Math.random() * (3 - 1 + 1) + 1))

	// then load community location and data and wait another 10-30 seconds
	let locationCommunityLocationResponse = http.get(
		`${BASE_URL}/api/community-location?location=15&locationType=4`,
		{
			tags: { endpoint: 'community-location' }
		}
	)
	check(locationCommunityLocationResponse, {
		'loaded community location': resp => resp.status === 200
	})
	let locationCommunityDataResponse = http.get(
		`${BASE_URL}/api/community-data?location=15&locationType=4`,
		{
			tags: { endpoint: 'community-data' }
		}
	)
	check(locationCommunityDataResponse, {
		'loaded community data': resp => resp.status === 200
	})
	sleep(Math.floor(Math.random() * (30 - 10 + 1) + 30))

	// then load featured indicators and wait another 1-3 seconds
	let featuredIndicatorsResponse = http.get(
		`${BASE_URL}/api/featured-indicators`,
		{
			tags: { endpoint: 'featured-indicators' }
		}
	)
	check(featuredIndicatorsResponse, {
		'featured indicators': resp => resp.status === 200
	})
	sleep(Math.floor(Math.random() * (3 - 1 + 1) + 1))

	// then load the explore data and wait another 10-30 seconds
	let indicatorSourceResponse = http.get(
		`${BASE_URL}/api/indicator-source?indicator=30`,
		{
			tags: { endpoint: 'indicator-source' }
		}
	)
	check(indicatorSourceResponse, {
		'loaded indicator source': resp => resp.status === 200
	})
	let filtersResponse = http.get(
		`${BASE_URL}/api/filters?indicator=30`,
		{
			tags: { endpoint: 'filters' }
		}
	)
	check(filtersResponse, {
		'loaded filters': resp => resp.status === 200
	})
	let exploreDataResponse = http.post(
		`${BASE_URL}/api/explore-data`,
		JSON.stringify({"indicator":"30","filters":{"locationType":"1","location":"48029","year":"2020","indicatorFilters":{"2":{"id":null,"typeId":null,"name_en":"All","name_es":"Todos"},"3":{"id":null,"typeId":null,"name_en":"All","name_es":"Todos"},"4":{"id":null,"typeId":null,"name_en":"All","name_es":"Todos"},"5":{"id":null,"typeId":null,"name_en":"All","name_es":"Todos"}}},"comparisons":null}),
		{
			headers: {
				'Content-Type': 'application/json',
			},
			tags: { endpoint: 'explore-data' }
		}
	)
	check(exploreDataResponse, {
		'loaded explore data': resp => resp.status === 200
	})

	sleep(Math.floor(Math.random() * (30 - 10 + 1) + 30))

	// then load featured indicators and wait another 1-3 seconds
	let featuredIndicatorsResponse2 = http.get(
		`${BASE_URL}/api/featured-indicators`,
		{
			tags: { endpoint: 'featured-indicators' }
		}
	)
	check(featuredIndicatorsResponse2, {
		'featured indicators 2': resp => resp.status === 200
	})
	sleep(Math.floor(Math.random() * (3 - 1 + 1) + 1))

	// then load the explore data and wait another 10-30 seconds
	let indicatorSourceResponse2 = http.get(
		`${BASE_URL}/api/indicator-source?indicator=30`,
		{
			tags: { endpoint: 'indicator-source' }
		}
	)
	check(indicatorSourceResponse2, {
		'loaded indicator source 2': resp => resp.status === 200
	})
	let filtersResponse2 = http.get(
		`${BASE_URL}/api/filters?indicator=30`,
		{
			tags: { endpoint: 'filters' }
		}
	)
	check(filtersResponse2, {
		'loaded filters 2': resp => resp.status === 200
	})
	let tablesDataResponse = http.post(
		`${BASE_URL}/api/tables-data`,
		JSON.stringify({"indicator":"30","search":"","page":1,"perPage":50,"sortBy":"","sortDesc":false,"lang":"en"}),
		{
			headers: {
				'Content-Type': 'application/json',
			},
			tags: { endpoint: 'tables-data' }
		}
	)
	check(tablesDataResponse, {
		'loaded tables data': resp => resp.status === 200
	})

	sleep(Math.floor(Math.random() * (30 - 10 + 1) + 30))
}