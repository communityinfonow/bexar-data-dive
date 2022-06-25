<template>
	<l-map
		ref="communityMap"
		:zoom="zoom"
		:center="center"
		:options="{ zoomDelta: 0.5, zoomSnap: 0.5, preferCanvas: true }"
		:style="{ height: '100%' }"
		v-resize:debounce.100="resizeHandler"
		@ready="initializeMap"
	>
		<l-tile-layer
			url="https://stamen-tiles.a.ssl.fastly.net/toner-background/{z}/{x}/{y}.png"
			:options="{ crossOrigin: 'anonymous' }"
			attribution="Map tiles by <a href='http://stamen.com'>Stamen Design</a>, 
			under <a href='http://creativecommons.org/licenses/by/3.0'>CC BY 3.0</a>. 
			Data by <a href='http://openstreetmap.org'>OpenStreetMap</a>, 
			under <a href='http://www.openstreetmap.org/copyright'>ODbL</a>."
		></l-tile-layer>
		<l-tile-layer
			url="https://stamen-tiles.a.ssl.fastly.net/toner-hybrid/{z}/{x}/{y}.png"
			:options="{ crossOrigin: 'anonymous' }"
			attribution="Map tiles by <a href='http://stamen.com'>Stamen Design</a>, 
			under <a href='http://creativecommons.org/licenses/by/3.0'>CC BY 3.0</a>. 
			Data by <a href='http://openstreetmap.org'>OpenStreetMap</a>, 
			under <a href='http://www.openstreetmap.org/copyright'>ODbL</a>."
		></l-tile-layer>
		<l-geo-json
			v-if="geojson"
			:geojson="geojson"
		></l-geo-json>
	</l-map>
</template>

<script>
import { mapState } from 'vuex'
import { latLng } from 'leaflet'
import { LMap, LTileLayer, LGeoJson } from 'vue2-leaflet'
import { feature, featureCollection } from '@turf/helpers'

export default {
	name: 'DashboardMap',
	components: {
		LMap, 
		LTileLayer,
		LGeoJson
	},
	data() {
		return {
			zoom: 9,
			center: latLng(29.43445, -98.473562383),
			geojson: null
		}
	},
	computed: {
		...mapState(['dashboardData'])
	},
	watch: {
		dashboardData(newValue) {
			if (newValue) {
				this.geojson = featureCollection(newValue.locationData
					.filter(ld => !!ld.geojson)
					.map(ld => feature(JSON.parse(ld.geojson), {}, { id: ld.location.id }))
				)
			}
		}
	},
	methods: {
		initializeMap() {},
		resizeHandler() {
		this.$refs.indicatorMap?.mapObject?.invalidateSize()
		},
	},
}
</script>

<style lang="scss" scoped>

</style>