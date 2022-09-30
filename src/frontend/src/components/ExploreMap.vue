<template>
	<div class="fill-height">
		<v-row class="no-gutters flex-wrap flex-column fill-height">
			<explore-tools-panel 
				v-if="filters"
				:draw="drawMap"
				:showLabels="showMapLabels"
				:setShowLabels="setShowMapLabels"
				dataVisualElementId="explore_map"
				dataVisualName="map"
			>
			</explore-tools-panel>
			<v-col cols="auto" class="grow">
				<l-map
					v-if="componentInitialized"
					ref="exploreMap"
					id="explore_map"
					:zoom="zoom"
					:center="center"
					:options="{ zoomDelta: 0.5, zoomSnap: 0.5, preferCanvas: true }"
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
								<span v-if="exploreData.category.parentCategoryId">{{ exploreData.category['name_' + locale] }} -&nbsp;</span>
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
											{{ range[0].label }}
											<span v-if="range[0].value !== range[1].value">
												&nbsp;-&nbsp;
												{{ range[1].label }}
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
					<l-control
						position="bottomright"
						class="layer-control"
						v-if="layers.length"
					>
						<v-expansion-panels data-html2canvas-ignore>
							<v-expansion-panel>
								<v-expansion-panel-header class="text--primary">
									<v-icon color="accent">mdi-layers</v-icon>
									<span class="mx-2">{{ $t('tools.community.community_types') }}</span>
								</v-expansion-panel-header>
								<v-expansion-panel-content>
									<v-radio-group
									v-model="selectedLocationType"
									@change="selectLocationType"
									class="mt-0"
									>
									<v-radio 
										color="accent"
										v-for="layer in layers" 
										:key="layer.id" 
										:value="layer" 
										:label="layer['name_' + locale]">
									</v-radio>
									</v-radio-group>
								</v-expansion-panel-content>
							</v-expansion-panel>
						</v-expansion-panels>
					</l-control>
				</l-map>
			</v-col>
		</v-row>
	</div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import i18n from '@/i18n'
import { latLng } from 'leaflet'
import { LMap, LTileLayer, LGeoJson, LControl } from 'vue2-leaflet'
import { feature, featureCollection } from '@turf/helpers'
import colorbrewer from 'colorbrewer'
import { ckmeans } from 'simple-statistics'
import ExploreToolsPanel from '@/components/ExploreToolsPanel'
import { format } from '@/formatter/formatter'

export default {
	name: 'ExploreMap',
	components: {
		LMap, 
		LTileLayer,
		LGeoJson,
		LControl,
		ExploreToolsPanel
	},
	data() {
		return {
			componentInitialized: false,
			mapInitialized: false,
			zoom: 10,
			center: latLng(29.43445, -98.473562383),
			geojson: null,
			refreshOptions: false,
			selectedLocationType: null
		}
	},
	computed: {
		...mapState(['exploreData', 'locale', 'locationMenu', 'filters', 'filterSelections', 'showMapLabels', 'exploreTab']),
		layers() {
			return this.locationMenu?.categories?.map(locationType => {
				return {
					id: locationType.id,
					name_en: locationType.name_en,
					name_es: locationType.name_es
				};
			});
		},
		options() {
			this.refreshOptions;
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
			if (uniqueValueCount < 5) {
				shadingColors = colorbrewer.Blues[5]
					.slice(5 - uniqueValueCount, 5)
					.reverse()
			} else {
				shadingColors = colorbrewer.Blues[5].slice(0).reverse()
			}

			return shadingColors
		},
		shadingRanges() {
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
				.reverse()
				.map(range => {
					return [{ value: range[0], label: format(this.exploreData.indicator.typeId, range[0]) },
					 { value: range[1], label: format(this.exploreData.indicator.typeId, range[1]) }];
				});
		}
	},
	watch: {
		exploreData(newValue) {
			if (this.mapInitialized && newValue) {
				this.drawMap();
			}
		},
    	locale() {
			this.drawMap()
		},
		exploreTab(newValue) {
			if (newValue === 'map') {
				window.setTimeout(() => {
					this.resizeHandler();
				}, 100);
			}
		},
		layers(newValue) {
			this.selectedLocationType = newValue[0];
		},
		filterSelections(newValue) {
			this.selectedLocationType = this.layers.find(l => l.id === newValue.locationType);
		},
		showMapLabels() {
			this.drawMap();
		}
	},
	mounted () {
		setTimeout(() => { 
			this.componentInitialized = true;
			if (this.filterSelections) {
				this.selectedLocationType = this.layers.find(l => l.id === this.filterSelections.locationType);
			}
			if (this.mapInitialized && this.exploreData) {
				this.drawMap();
			}
		}, 100);
		
	},
	methods: {
		...mapActions(['setDockedTooltip', 'setFilterSelections', 'setShowMapLabels']),
		initializeMap() {
			this.mapInitialized = true;
			if (this.exploreData) {
				this.drawMap();
			}
		},
		resizeHandler() {
			if (this.exploreTab === 'map') {
				this.$refs.exploreMap?.mapObject?.invalidateSize();
			}
		},
		selectLocationType() {
			let newFilterSelections = JSON.parse(JSON.stringify(this.filterSelections));
			newFilterSelections.locationType = this.selectedLocationType.id;
			newFilterSelections.location = this.filters.locationFilter.options.filter(o => o.typeId === this.selectedLocationType.id)[0].id;
			this.setFilterSelections(newFilterSelections);
		},
		selectLocation(location) {
			let newFilterSelections = JSON.parse(JSON.stringify(this.filterSelections));
			newFilterSelections.locationType = this.selectedLocationType.id;
			newFilterSelections.location = location;
			this.setFilterSelections(newFilterSelections);
		},
		drawMap() {
			this.setDefaultDockedTooltip();
			this.geojson = featureCollection(this.exploreData.locationData
				.filter(ld => !!ld.geojson)
				.map(ld => feature(JSON.parse(ld.geojson), 
					{
						locationName: ld.location['name_' + this.locale],
						value: ld.yearData[this.exploreData.filters.yearFilter.options[0].id]?.value,
						noData: ld.yearData[this.exploreData.filters.yearFilter.options[0].id]?.value === null,
						moeLow: ld.yearData[this.exploreData.filters.yearFilter.options[0].id]?.moeLow,
						moeHigh: ld.yearData[this.exploreData.filters.yearFilter.options[0].id]?.moeHigh,
						suppressed: ld.yearData[this.exploreData.filters.yearFilter.options[0].id]?.suppressed
					}, 
					{ id: ld.location.id }))
			)
			this.refreshOptions = Math.random(); // force a refresh
		},
		onEachFeature(feature, layer) {
			let filteredFeature = feature.id === this.exploreData.filters.locationFilter.options[0]?.id;
			if (filteredFeature) {
				layer.options.weight = 4;
				layer.options.color = 'orange';
			}
			layer.options.fillColor = this.getLayerShadingColor(feature);
			if (this.showMapLabels) {
				layer.bindTooltip(layer.feature.properties.locationName.replace('Zip Code', 'Zip').replace('Census Tract', 'Tract') 
						+ '<br>' 
						+ (layer.feature.properties.suppressed ? i18n.t('data.suppressed') : format(this.exploreData.indicator.typeId, layer.feature.properties.value)), 
					{
						className: 'location-label',
						permanent: true, 
						direction: 'center'
					}
				);
			}

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
				this.setDefaultDockedTooltip()
			});
			layer.on('click', (e) => {
				this.selectLocation(e.target.feature.id);
			});
		},
		getLayerShadingColor(feature) {
			if (!feature.properties.value || feature.properties.suppressed) {
				return 'transparent'
			}
			
			return this.shadingColors[
				this.shadingRanges.findIndex(
					range => feature.properties.value >= range[0].value && feature.properties.value <= range[1].value)
			];
		},
		setDefaultDockedTooltip() {
			this.setDockedTooltip({
					value: null,
					suppressed: null,
					noData: null,
					moeLow: null,
					moeHigh: null,
					location: null,
					year: this.exploreData.filters.yearFilter.options[0].id,
					indicatorFilters: this.exploreData.filters.indicatorFilters
				});
		}
	},
}
</script>

<style lang="scss" scoped>
	::v-deep .leaflet-tooltip.location-label {
		background: none;
		border: none;
		box-shadow: none;
		font-weight: bold;
		opacity: 1 !important;
		color: #000;
		font-size: 14px;
		text-shadow: -2px -2px 2px #fff, 2px -2px 2px #fff, -2px 2px 2px #fff, 2px 2px 2px #fff;
		text-align: center;
	}
</style>