<template>
	<div class="fill-height">
		<l-map
			v-if="componentInitialized"
			ref="exploreMap"
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
				v-if="exploreData && shadingRanges.length"
			>
				<v-card tile outlined :style="{ boxShadow: 'none !important' }">
					<v-card-title class="pb-0 text--primary">
						{{ exploreData.indicator['name_' + locale] }}
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
							Source: {{ exploreData.source['name_' + locale] }}
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
	name: 'ExploreMap',
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
		...mapState(['exploreData', 'locale']),
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
			console.log(this.geojson?.features)
			if (!this.geojson?.features?.length 
					|| !this.geojson?.features?.find(f => f.properties.value && !f.properties.suppressed)) {
				return []
			}

			let values = this.geojson.features
				.filter(feature => !!feature.properties.value)
				.flatMap(feature => feature.properties.value)
			let uniqueValueCount = [...new Set(values)].length;

			return ckmeans(values, Math.min(uniqueValueCount, 5))
				.map(cluster => [cluster[0], cluster[cluster.length - 1]])
				.reverse();
		}
	},
	watch: {
		exploreData(newValue) {
			if (this.mapInitialized && newValue) {
				this.drawMap();
			}
		}
	},
	mounted () {
		setTimeout(() => { 
			this.componentInitialized = true;
			if (this.mapInitialized && this.exploreData) {
				this.drawMap();
			}
		}, 100);
		
	},
	methods: {
		...mapActions(['setDockedTooltip']),
		initializeMap() {
			this.mapInitialized = true;
			if (this.exploreData) {
				this.drawMap();
			}
		},
		resizeHandler() {
			this.$refs.indicatorMap?.mapObject?.invalidateSize();
		},
		drawMap() {
			this.geojson = featureCollection(this.exploreData.locationData
				.filter(ld => !!ld.geojson)
				.map(ld => feature(JSON.parse(ld.geojson), 
					{
						locationName: ld.location['name_' + this.locale],
						value: ld.yearData[this.exploreData.filters.yearFilter.options[0].id]?.value,
						noData: !ld.yearData[this.exploreData.filters.yearFilter.options[0].id]?.value,
						moeLow: ld.yearData[this.exploreData.filters.yearFilter.options[0].id]?.moeLow,
						moeHigh: ld.yearData[this.exploreData.filters.yearFilter.options[0].id]?.moeHigh,
						suppressed: ld.yearData[this.exploreData.filters.yearFilter.options[0].id]?.suppressed
					}, 
					{ id: ld.location.id }))
			)
			this.refreshOptions = Math.random(); // force a refresh
			this.$refs.exploreMap?.mapObject.fitBounds(L.geoJSON(this.geojson).getBounds());
		},
		onEachFeature(feature, layer) {
			let filteredFeature = feature.id === this.exploreData.filters.locationFilter.options[0]?.id;
			if (filteredFeature) {
				layer.options.weight = 4;
				layer.options.color = 'orange';
			}
			layer.options.fillColor = this.getLayerShadingColor(feature);

			layer.on('mouseover', (layer) => {
				this.setDockedTooltip({
					value: layer.target.feature.properties.value,
					suppressed: layer.target.feature.properties.suppressed,
					noData: layer.target.feature.properties.noData,
					moeLow: layer.target.feature.properties.moeLow,
					moeHigh: layer.target.feature.properties.moeHigh,
					location: layer.target.feature.properties.locationName,
					year: this.exploreData.filters.yearFilter.options[0].id,
					indicatorFilters: this.exploreData.filters.indicatorFilters
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