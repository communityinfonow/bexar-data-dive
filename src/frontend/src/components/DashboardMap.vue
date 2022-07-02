<template>
	<div class="fill-height">
		<l-map
			v-if="componentInitialized"
			ref="dashboardMap"
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
				:options="options"
				:options-style="style"
			></l-geo-json>
			<l-control
				position="bottomleft"
				class="legend-control"
				v-if="dashboardData && shadingRanges.length"
			>
				<v-card tile outlined :style="{ boxShadow: 'none !important' }">
					<v-card-title class="pb-0 text--primary">
						{{ dashboardData.indicator['name_' + locale] }}
					</v-card-title>
					<v-card-text>
						<v-list
							dense
							class="py-0"
							:style="{ boxShadow: 'none !important' }"
						>
							<v-list-item
								v-for="(range, index) in shadingRanges"
								:key="'range_' + index"
								dense
								class="pl-0"
							>
								<v-list-item-avatar
									size="20"
									:color="shadingColors[index]"
									:style="{ opacity: 0.8 }"
									class="my-0"
								>
								</v-list-item-avatar>
								<span>
									{{ Number(range[0]).toLocaleString() }}
									<span v-if="range[0] !== range[1]">
										&nbsp;-&nbsp;
										{{ Number(range[1]).toLocaleString() }}
									</span>
								</span>
							</v-list-item>
						</v-list>
						<div class="text--primary text-caption">
							Source: {{ dashboardData.source['name_' + locale] }}
						</div>
					</v-card-text>
				</v-card>
			</l-control>
		</l-map>
	</div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import L from 'leaflet'
import { latLng } from 'leaflet'
import { LMap, LTileLayer, LGeoJson, LControl } from 'vue2-leaflet'
import { feature, featureCollection } from '@turf/helpers'
import colorbrewer from 'colorbrewer'
import { ckmeans } from 'simple-statistics'

export default {
	name: 'DashboardMap',
	components: {
		LMap, 
		LTileLayer,
		LGeoJson,
		LControl
	},
	data() {
		return {
			componentInitialized: false,
			mapInitialized: false,
			zoom: 9,
			center: latLng(29.43445, -98.473562383),
			geojson: null,
			refreshOptions: false
		}
	},
	computed: {
		...mapState(['dashboardData', 'locale']),
		options() {
			this.refreshOptions
			return {
				onEachFeature: this.onEachFeature
			}
		},
		style() {
			return () => {
				return { weight: 2, opacity: 1, fillOpacity: 0.8, color: "#666" }
			}
		},
		shadingColors() {
			if (!this.geojson?.features?.length) {
				return []
			}

			let values = this.geojson.features
				.filter(feature => !!feature.properties.value)
				.flatMap(feature => feature.properties.value)
			let uniqueValueCount = [...new Set(values)].length;

			let shadingColors = []
			if (uniqueValueCount < 3) {
				shadingColors = colorbrewer.Blues[3]
					.slice(3 - uniqueValueCount, 3)
					.reverse()
				} else if (uniqueValueCount < 5) {
					shadingColors = colorbrewer.Blues[uniqueValueCount].slice(0).reverse()
				} else {
					shadingColors = colorbrewer.Blues[5].slice(0).reverse()
				}

			return shadingColors
		},
		shadingRanges() {
			if (!this.geojson?.features?.length) {
				return []
			}

			let values = this.geojson.features
				.filter(feature => !!feature.properties.value)
				.flatMap(feature => feature.properties.value)
			let uniqueValueCount = [...new Set(values)].length;

			return ckmeans(values, Math.min(uniqueValueCount, 5))
				.map(cluster => [cluster[0], cluster[cluster.length - 1]])
				.reverse(); //FIXME: do we always want to reverse to shade high values dark?
		}
	},
	watch: {
		dashboardData(newValue) {
			if (this.mapInitialized && newValue) {
				this.drawMap();
			}
		}
	},
	mounted () {
		setTimeout(() => { 
			this.componentInitialized = true;
			if (this.mapInitialized && this.dashboardData) {
				this.drawMap();
			}
		}, 100);
		
	},
	methods: {
		...mapActions(['setDockedTooltip']),
		initializeMap() {
			this.mapInitialized = true;
			if (this.dashboardData) {
				this.drawMap();
			}
		},
		resizeHandler() {
			this.$refs.indicatorMap?.mapObject?.invalidateSize();
		},
		drawMap() {
			this.geojson = featureCollection(this.dashboardData.locationData
				.filter(ld => !!ld.geojson)
				.map(ld => feature(JSON.parse(ld.geojson), 
					{
						locationName: ld.location['name_' + this.locale],
						value: ld.yearData[this.dashboardData.filters.yearFilter.options[0].id]?.value,
						moeLow: ld.yearData[this.dashboardData.filters.yearFilter.options[0].id]?.moeLow,
						moeHigh: ld.yearData[this.dashboardData.filters.yearFilter.options[0].id]?.moeHigh,
						suppressed: false, //TODO: suppression from DB -> UI
					}, 
					{ id: ld.location.id }))
			)
			this.refreshOptions = Math.random(); // force a refresh
			this.$refs.dashboardMap?.mapObject.fitBounds(L.geoJSON(this.geojson).getBounds());
		},
		onEachFeature(feature, layer) {
			let filteredFeature = feature.id === this.dashboardData.filters.locationFilter.options[0].id;
			if (filteredFeature) {
				layer.options.weight = 4;
				layer.options.color = 'orange'; //TODO: what color for filtered geo?
			}
			layer.options.fillColor = this.getLayerShadingColor(feature);

			layer.on('mouseover', (layer) => {
				this.setDockedTooltip({
					value: layer.target.feature.properties.value,
					moeLow: layer.target.feature.properties.moeLow,
					moeHigh: layer.target.feature.properties.moeHigh,
					location: layer.target.feature.properties.locationName,
					year: this.dashboardData.filters.yearFilter.options[0].id,
					indicatorFilters: this.dashboardData.filters.indicatorFilters
				});
			});
			layer.on('mouseout', () => {
				this.setDockedTooltip(null);
			})
		},
		getLayerShadingColor(feature) {
			if (!feature.properties.value || feature.properties.suppressed) {
				return 'transparent'
			}
			
			return this.shadingColors[
				this.shadingRanges.findIndex(
					range => feature.properties.value >= range[0] && feature.properties.value <= range[1])
			];
		}
	},
}
</script>

<style lang="scss" scoped>

</style>