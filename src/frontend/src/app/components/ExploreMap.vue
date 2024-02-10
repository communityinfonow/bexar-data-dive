<template>
	<div class="fill-height">
		<v-row class="no-gutters flex-wrap flex-column fill-height">
			<explore-tools-panel 
				v-if="filters && filterSelections && exploreData && layout !== 'gallery'"
				:draw="drawMap"
				:showHighlightFilteredLocation="true"
				:highlightFilteredLocation="highlightFilteredLocation"
				:setHighlightFilteredLocation="setHighlightFilteredLocation"
				:showLabels="showMapLabels"
				:setLabelsOrLinesOption="setShowMapLabels"
				dataVisualElementId="explore_map"
				dataVisualName="map"
				:layout="layout"
			>
			</explore-tools-panel>
			<v-col cols="auto" class="grow">
				<div class="explore-map" :class="{ 'full-height': fullHeight }">
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
							url="https://tiles.stadiamaps.com/tiles/stamen_toner_background/{z}/{x}/{y}{r}.png"
							:options="{ crossOrigin: 'anonymous' }"
							:attribution="$t('tools.common.map_attribution')"
						></l-tile-layer>
						<l-tile-layer
							url="https://tiles.stadiamaps.com/tiles/stamen_toner_lines/{z}/{x}/{y}{r}.png"
							:options="{ crossOrigin: 'anonymous' }"
							:attribution="$t('tools.common.map_attribution')"
						></l-tile-layer>
						<l-tile-layer
							url="https://tiles.stadiamaps.com/tiles/stamen_toner_labels/{z}/{x}/{y}{r}.png"
							:options="{ crossOrigin: 'anonymous' }"
							:attribution="$t('tools.common.map_attribution')"
						></l-tile-layer>
						<l-geo-json
							v-if="geojson"
							:geojson="geojson"
							:options="options"
							:options-style="style"
						></l-geo-json>
						<l-geo-json
							v-if="pointsGeojson"
							:geojson="pointsGeojson"
							:options="pointsOptions"
						>
						</l-geo-json>
						<l-geo-json
							v-if="highlightFilteredLocation && filteredLocationGeojson"
							ref="filteredLocation"
							:geojson="filteredLocationGeojson"
							:options="filteredLocationOptions"
							:options-style="style"
						></l-geo-json>
						<l-control
							position="bottomleft"
							class="legend-control"
							v-if="exploreData && shadingRanges.length"
						>
							<v-card tile outlined :style="{ boxShadow: 'none !important' }">
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
											:style="{ 'min-height': layout === 'tabs' ? 'auto' : '28px !important' }"
										>
											<v-list-item-avatar
												size="20"
												:color="shadingColors[index]"
												:style="{ opacity: 0.8 }"
												class="my-0"
											>
											</v-list-item-avatar>
											<span style="{ 'font-size': this.layout === 'gallery' ? '14px' : 'default' }">
												{{ range[0].label }}
												<span v-if="range[0].value !== range[1].value">
													&nbsp;-&nbsp;
													{{ range[1].label }}
												</span>
											</span>
										</v-list-item>
									</v-list>
								</v-card-text>
							</v-card>
						</l-control>
						<l-control
							position="bottomright"
							class="layer-control d-flex flex-column"
							v-if="layout === 'tabs' && layers && layers.length"
						>
							<v-expansion-panels accordion class="report-control">
								<v-expansion-panel v-if="indicator.showReport">
									<v-expansion-panel-header class="text--primary" data-html2canvas-ignore>
										<div>
											<v-icon color="green">mdi-file-document-outline</v-icon>
											<span class="mx-2">{{ $t('tools.explore.report') }}</span>
										</div>
									</v-expansion-panel-header>
									<v-expansion-panel-content v-if="reportData" :style="{ 'overflow': 'hidden', 'display': 'flex', 'flex-direction': 'column' }">
										<location-report></location-report>
									</v-expansion-panel-content>
								</v-expansion-panel>
								<v-expansion-panel v-if="indicator.showPoints">
									<v-expansion-panel-header class="text--primary" data-html2canvas-ignore>
										<div>
											<v-icon color="green">mdi-circle</v-icon>
											<span class="mx-2">{{ $t('tools.explore.locations') }}</span>
										</div>
									</v-expansion-panel-header>
									<v-expansion-panel-content class="location-options">
										<div v-for="pointType in pointTypes" :key="pointType.id">
											<v-checkbox 
												color="green"
												:value="pointType" 
												:label="pointType['name_' + locale] + ' (' + pointType.year + ')'"
												v-model="mapPointTypes"
												@change="togglePointType(pointType.id)"
												hide-details
												class="mt-0"
											>
												<template v-slot:append>
													<v-icon :style="{ 'color':pointType.color }">mdi-circle</v-icon>
												</template>
											</v-checkbox>
											<div 
												v-if="selectedPointTypes.some(pt => pt.id === pointType.id)
													&& pointScales 
													&& pointScales.find(ps => ps.pointType.id === pointType.id)
													&& pointScales.find(ps => ps.pointType.id === pointType.id).min 
														!== pointScales.find(ps => ps.pointType.id === pointType.id).max"
												class="d-flex align-center justify-space-between px-10 text-center"
												
											>
												<div
													v-for="(value, index) in [
														pointScales.find(ps => ps.pointType.id === pointType.id).min, 
														pointScales.find(ps => ps.pointType.id === pointType.id).mid, 
														pointScales.find(ps => ps.pointType.id === pointType.id).max
													]"
													:key="'range_' + index"
												>
													<v-icon
														:size="pointScales.find(ps => ps.pointType.id === pointType.id).scale(value)"
														:style="{ 'color': pointType.color }"
													>
														mdi-circle
													</v-icon>
													<br>
													<span>
														{{ value }}
													</span>
												</div>
											</div>
											<div v-if="selectedPointTypes.some(pt => pt.id === pointType.id) && pointCollections.find(pc => pc.pointType.id === pointType.id).points[0]['valueLabel_' + locale]" class="text-center mb-2">{{ pointCollections.find(pc => pc.pointType.id === pointType.id).points[0]['valueLabel_' + locale] }}</div>
										</div>
									</v-expansion-panel-content>
								</v-expansion-panel>
								<v-expansion-panel data-html2canvas-ignore>
									<v-expansion-panel-header class="text--primary">
										<div>
											<v-icon color="green">mdi-layers</v-icon>
											<span class="mx-2">{{ $t('tools.community.community_types') }}</span>
										</div>
									</v-expansion-panel-header>
									<v-expansion-panel-content>
										<v-radio-group
										v-model="selectedLocationType"
										@change="selectLocationType(selectedLocationType)"
										class="mt-0"
										>
										<v-radio 
											color="green"
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
				</div>
				<div v-if="showChart" id="location_rank_chart_container" class="location-rank-chart-container"></div>
			</v-col>
		</v-row>
	</div>
</template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import i18n from '@/i18n'
import axios from 'axios'
import L from 'leaflet'
import { latLng } from 'leaflet'
import { LMap, LTileLayer, LGeoJson, LControl } from 'vue2-leaflet'
import { feature, featureCollection, multiPolygon } from '@turf/helpers'
import colorbrewer from 'colorbrewer'
import { ckmeans } from 'simple-statistics'
import {scaleLinear} from 'd3-scale'
import * as echarts from 'echarts/core';
import { SVGRenderer } from 'echarts/renderers';
import { AriaComponent, LegendComponent, GridComponent, TooltipComponent } from 'echarts/components';
import { BarChart } from 'echarts/charts';
import ExploreToolsPanel from '@/app/components/ExploreToolsPanel'
import LocationReport from './LocationReport.vue'
import { format } from '@/services/formatter'

export default {
	name: 'ExploreMap',
	components: {
		LMap, 
		LTileLayer,
		LGeoJson,
		LControl,
		ExploreToolsPanel,
		LocationReport
	},
	props: {
		layout: {
			type: String,
			default: 'tabs'
		}
	},
	data() {
		return {
			componentInitialized: false,
			mapInitialized: false,
			zoom: 10,
			center: latLng(29.43445, -98.473562383),
			geojson: null,
			filteredLocationGeojson: null,
			refreshOptions: false,
			selectedLocationType: null,
			mapPointTypes: [],
			chart: null,
			fullHeight: true
		}
	},
	computed: {
		...mapState(['exploreData', 'locale', 'filterSelections', 'showMapLabels', 'highlightFilteredLocation', 'exploreTab', 'customLocations', 'indicator', 'pointCollections', 'selectedPointTypes', 'pointsGeojson', 'reportData']),
		...mapGetters(['locationMenu', 'filters', 'pointTypes']),
		layers() {
			return this.filters?.locationTypeFilter?.options?.map(option => {
				return {
					id: option.id,
					name_en: option.name_en,
					name_es: option.name_es
				};
			});
		},
		options() {
			this.refreshOptions;
			return {
				onEachFeature: this.onEachFeature
			}
		},
		pointsOptions() {
			this.refreshOptions;
			return {
				pointToLayer: this.pointToLayer,
			}
		},
		filteredLocationOptions() {
			this.refreshOptions;
			return {
				onEachFeature: this.onEachFilteredLocationFeature
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
		},
		pointScales() {
			return this.pointCollections?.filter(pc => pc.points.some(p => p.value))
				.map(pc => {
					let values = pc.points.map(p => p.value);
					let min = Math.min(...values);
					let max = Math.max(...values);
					let scale = scaleLinear()
						.domain([min, max])
						.range(min === max ? [8, 8] : [8, 80]);
					return {
						pointType: pc.pointType,
						scale: scale,
						min: min,
						max: max,
						mid: Math.floor((min + max) / 2)
					}
				})
		},
		showChart() {
			return this.exploreData && this.exploreData.locationData && this.exploreData.locationData.length > 1;
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
			this.setPointsGeojson(featureCollection([]))
			this.pointTypes.forEach(pt => {
				this.togglePointType(pt.id)
			})
		},
		exploreTab(newValue) {
			if (newValue === 'map') {
				window.setTimeout(() => {
					this.resizeHandler();
				}, 100);
			}
		},
		layers(newValue) {
			if (newValue && newValue.length) {
				this.selectedLocationType = newValue[0];
			} else {
				this.selectedLocationType = null;
			}
		},
		filterSelections(newValue) {
			this.selectedLocationType = this.layers?.find(l => l.id === newValue.locationType);
			
		},
		filteredLocationGeojson(newValue) {
			if (this.selectedLocationType) {
				this.getReportData({location: newValue, locationType: this.selectedLocationType.id});
			}
		},
		showMapLabels() {
			this.drawMap();
		},
		highlightFilteredLocation() {
			this.drawMap();
		},
		indicator(newValue, oldValue) {
			if (newValue.id !== oldValue?.id && newValue.showPoints && !oldValue?.showPoints) {
				this.getPoints()
			} else if (!newValue.showPoints) {
				this.setPointsGeojson(featureCollection([]))
				this.setSelectedPointTypes([])
			}
		},
		selectedPointTypes(newValue) {
			this.mapPointTypes = newValue
		}
	},
	mounted () {
		setTimeout(() => { 
			this.componentInitialized = true;
			echarts.use([SVGRenderer, AriaComponent, LegendComponent, GridComponent, TooltipComponent, BarChart]);
			if (this.filterSelections) {
				this.selectedLocationType = this.layers.find(l => l.id === this.filterSelections.locationType);
			}
			if (this.mapInitialized && this.exploreData) {
				this.drawMap();
			}
			if (this.indicator?.showPoints) {
				this.getPoints()
				this.mapPointTypes = this.selectedPointTypes
			}
		}, 100);
		
	},
	updated() {
		this.$nextTick(() => {
			if (this.$refs.filteredLocation) {
				this.$refs.filteredLocation.mapObject.bringToFront();
			}
		});
	},
	methods: {
		...mapActions(['setDockedTooltip', 'selectLocationType', 'setFilterSelections', 'setShowMapLabels', 'setHighlightFilteredLocation', 'setPointCollections', 'setSelectedPointTypes', 'togglePointType', 'getReportData', 'setPointsGeojson']),
		initializeMap() {
			this.mapInitialized = true;
			if (this.exploreData) {
				this.drawMap();
			}
		},
		resizeHandler() {
			if (this.exploreTab === 'map' || this.layout === 'gallery') {
				this.$refs.exploreMap?.mapObject?.invalidateSize();
			}
		},
		selectLocation(location) {
			let newFilterSelections = JSON.parse(JSON.stringify(this.filterSelections));
			newFilterSelections.locationType = this.selectedLocationType.id;
			// swap to the location type the custom location is built from if selecting a standard location while the currently filtered location is custom
			if (this.selectedLocationType.id === '7' && !isNaN(Number(location))) {
				newFilterSelections.locationType = this.customLocations.find(cl => cl.id === this.filterSelections.location).typeId;
			}
			newFilterSelections.location = location;
			this.setFilterSelections(newFilterSelections);
		},
		drawMap() {
			this.fullHeight = this.exploreData?.filters?.locationTypeFilter?.options[0]?.id === '1'
			
			this.$nextTick(() => {
				this.resizeHandler();
				if (!this.exploreData) {
					this.geojson = null;
					this.filteredLocationGeojson = null;
					return;
				}
				this.setDefaultDockedTooltip();
				this.geojson = featureCollection(this.exploreData.locationData
					.filter(ld => !!ld.geojson)
					.map(ld => feature(this.getLocationDataGeojson(ld), 
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
				this.filteredLocationGeojson = this.geojson.features.find(f => f.id === this.exploreData.filters.locationFilter.options[0]?.id);
				this.refreshOptions = Math.random(); // force a refresh
				this.drawChart();
			});
		},
		getLocationDataGeojson(locationData) {
			let geojson = JSON.parse(locationData.geojson);
			if (geojson.type === 'FeatureCollection') {
				geojson = multiPolygon(geojson.features
						.map(clf => clf.geometry)
						.reduce((acc, cur) => acc.concat(cur.coordinates), [])).geometry;
			}

			return geojson;
		},
		onEachFeature(feature, layer) {
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
					year: this.exploreData?.filters?.yearFilter.options[0].id,
					indicatorFilters: this.exploreData?.filters?.indicatorFilters
				});
			});
			layer.on('mouseout', () => {
				this.setDefaultDockedTooltip()
			});
			layer.on('click', (e) => {
				this.selectLocation(e.target.feature.id);
			});
		},
		onEachFilteredLocationFeature(feature, layer) {
			layer.options.weight = 4;
			layer.options.color = 'orange';
			layer.options.fillColor = 'transparent';
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
					year: this.exploreData?.filters?.yearFilter.options[0].id,
					indicatorFilters: this.exploreData?.filters?.indicatorFilters
				});
			});
			layer.on('mouseout', () => {
				this.setDefaultDockedTooltip()
			});
			layer.on('click', (e) => {
				this.selectLocation(e.target.feature.id);
			});
		},
		pointToLayer(feature, latlng) {
			let size = 8;
			if (this.pointScales.find(ps => ps.pointType.id === feature.properties.typeId)) {
				size = this.pointScales.find(ps => ps.pointType.id === feature.properties.typeId).scale(feature.properties.value);
			}
			return L.marker(latlng, {
				icon: L.divIcon({
					className: 'dive-point',
					html: '<div class="dive-point-icon" style="opacity: 0.8; background-color: ' + this.pointTypes.find(pt => pt.id === feature.properties.typeId).color  + '; width: ' + size + 'px; height: ' + size + 'px; border-radius: 50%;"></div>',
					tooltipAnchor: [size/2, size/2]
				})
			}).bindTooltip(
				Object.entries(feature.properties).filter(([key]) => key !== 'id' && key !== 'typeId' && key !== 'valueLabel_en' && key !== 'valueLabel_es' && key !== 'value').map(([key, value]) => key + ': ' + (!isNaN(Number(value)) ? Number(value).toLocaleString() : value)).join('<br>')
				+ (feature.properties.value ? '<br>' + feature.properties['valueLabel_' + this.locale] + ': ' + Number(feature.properties.value).toLocaleString() : ''),
				
			);
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
					year: this.exploreData?.filters?.yearFilter.options[0]?.id,
					indicatorFilters: this.exploreData?.filters?.indicatorFilters
				});
		},
		getPoints() {
			if (!this.pointCollections) {
				axios.get('/api/points').then(response => {
					this.setPointCollections(response.data)
					for (let pointType of this.selectedPointTypes) {
						this.togglePointType(pointType.id)
					}
				})
			}
		},
		drawChart() {
			if (!this.showChart) {
				return;
			}
			// wait for the chart container to be rendered before drawing the chart
			this.$nextTick(() => {
				this.drawChartInternal();
			});
		},
		drawChartInternal() {
			let filteredLocation = this.exploreData.locationData.find(ld => 
					ld.location.id === this.exploreData.filters.locationFilter.options[0].id && 
					ld.location.typeId === this.exploreData.filters.locationTypeFilter.options[0].id);
			if (!filteredLocation) {
				this.chart = null;
				return;
			} else if (this.chart){
				this.chart.dispose();
			}
			this.chart = echarts.init(document.getElementById('location_rank_chart_container'), null, { renderer: 'svg'});
			let textStyle = {
				fontFamily: '"Roboto", sans-serif !important',
				fontSize: this.layout === 'gallery' ? '12px' : this.smallScreen ? '14px' : '16px'
			};
			let option = {};
			option.animation = false;
			option.tooltip = {
				borderColor: '#333',
				position: 'top',
			};
			option.grid = { left: 40, right: 20, top: 20, bottom: 5,containLabel: true };
			option.yAxis = { 
				type: 'value', 
				splitLine: { show: false },
				splitNumber: 1,
				axisLabel: textStyle
			};
			option.xAxis = { 
				type: 'category', 
				data: this.exploreData.locationData.map(l => l.location['name_' + this.locale]),
				axisTick: { show: false },
				axisLabel: { show: false }
			};
			let seriesData = this.exploreData.locationData
				.map(ld => {
					let year = this.exploreData.filters.yearFilter.options[0].id;
					let value = ld.yearData[year]?.value || 0;
					let filteredLocation = this.filteredLocationGeojson && ld.location.id === this.filteredLocationGeojson.id;
					let maxValue = Math.max(...this.exploreData.locationData.map(ld => ld.yearData[year]?.value || 0));
					let suppressed = ld.yearData[year]?.suppressed;
					let noData = !ld.yearData[year] || ld.yearData[year]?.value === null;
					let color = suppressed || noData ? 'transparent' : this.shadingColors[
						this.shadingRanges.findIndex(
							range => value >= range[0].value && value <= range[1].value)
						];
					let borderColor = suppressed || noData ? 'transparent' : filteredLocation && this.highlightFilteredLocation ? 'orange' : '#666';
					return  { 
						value: suppressed || noData ? maxValue : value,
						suppressed: suppressed,
						noData: noData,
						moeLow: ld.yearData[year]?.moeLow,
						moeHigh: ld.yearData[year]?.moeHigh,
						location: ld.location['name_' + this.locale],
						itemStyle: {
							color: color,
							borderColor: borderColor,
							borderWidth: filteredLocation && this.highlightFilteredLocation ? 4 : 1
						}
					};
				})
				.sort((a, b) => {
					if (a.value === null || a.suppressed || a.noData) {
						return -1;
					} else if (b.value === null || b.suppressed || b.noData) {
						return 1;
					} else {
						return a.value - b.value;
					}
				
				});
			option.series = [{
				data: seriesData,
				type: 'bar',
				barCategoryGap: -1,
				tooltip: {
					formatter: (params) => {
						return params.data.location + '<br>' + (params.data.suppressed ? i18n.t('data.suppressed') : format(this.exploreData.indicator.typeId, params.data.value));
					}
				},
				click: (params) => {
					this.selectLocation(this.exploreData.locationData.find(ld => ld.location['name_' + this.locale] === params.data.location).location.id);
				}
			}];
			this.chart.on('click', (params) => {
				if (params.data.location) {
					this.selectLocation(this.exploreData.locationData.find(ld => ld.location['name_' + this.locale] === params.data.location).location.id);
				}
			});
			this.chart.setOption(option);
		}
	},
}
</script>

<style lang="scss" scoped>
	::v-deep .explore-map {
		height: 85%;
		overflow: hidden;
	}
	::v-deep .explore-map.full-height {
		height: 100%;
	}
	.location-rank-chart-container {
		height: 15%;
	}
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

	::v-deep .v-expansion-panels {
		flex-direction: column;
	}
</style>