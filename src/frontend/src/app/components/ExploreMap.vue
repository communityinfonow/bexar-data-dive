<template>
	<div class="fill-height">
		<v-row class="no-gutters flex-wrap flex-column fill-height">
			<explore-tools-panel 
				v-if="filters"
				:draw="drawMap"
				:showHighlightFilteredLocation="true"
				:highlightFilteredLocation="highlightFilteredLocation"
				:setHighlightFilteredLocation="setHighlightFilteredLocation"
				:showLabels="showMapLabels"
				:setLabelsOrLinesOption="setShowMapLabels"
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
						url="https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png"
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
									{{ $t('tools.explore.docked_tooltip.source') }}: {{ exploreData.source['name_' + locale] }}
								</div>
							</v-card-text>
						</v-card>
					</l-control>
					<l-control
						position="bottomright"
						class="layer-control d-flex flex-column"
						v-if="layers && layers.length"
					>
						<v-expansion-panels accordion class="report-control">
							<v-expansion-panel v-if="indicator.showReport">
								<v-expansion-panel-header class="text--primary" data-html2canvas-ignore>
									<div>
										<v-icon color="accent">mdi-file-document-outline</v-icon>
										<span class="mx-2">{{ $t('tools.explore.report') }}</span>
									</div>
								</v-expansion-panel-header>
								<v-expansion-panel-content v-if="reportData" :style="{ 'overflow': 'hidden', 'display': 'flex', 'flex-direction': 'column' }">
									<span class="text-subtitle-1">{{ reportData.location.properties.locationName }}</span>
									<v-btn icon color="accent" class="ml-2" @click="downloadReport">
										<v-icon>mdi-download</v-icon>
									</v-btn>
									<v-simple-table fixed-header :height="this.reportHeight">
										<thead>
											<tr>
												<th>{{ $t('tools.common.download.headers.indicator') }}</th>
												<th>{{ $t('tools.common.download.headers.year') }}</th>
												<th>{{ $t('data.value') }}</th>
												<th>{{ $t('data.moe') }}</th>
											</tr>
										</thead>
										<tbody>
											<tr v-for="(entry, idx) in reportData.data" :key="idx">
												<td>{{ entry['indicatorName_' + locale] }}</td>
												<td>{{ entry.year_ }}</td>
												<td class="text-end">
													<span v-if="entry.indicatorValue && entry.indicatorName_en === 'Median Household Income'">
														{{ '$' + entry.indicatorValue.toLocaleString() }}
													</span>
													<span v-else-if="entry.indicatorValue && entry.indicatorName_en === 'Retail Food Environment Index'">
														{{ entry.indicatorValue.toFixed(1).toLocaleString() }}
													</span>
													<span v-else-if="entry.indicatorValue">
														{{ entry.indicatorValue.toFixed(1).toLocaleString() + '%' }}
													</span>
													<span v-else>
														{{ $t('data.no_data') }}
													</span>
												</td>
												<td class="text--secondary">
													<span v-if="entry.moe && entry.indicatorName_en === 'Median Household Income'">
														&#177; {{ '$' + entry.moe.toLocaleString() }}
													</span>
													<span v-else-if="entry.moe && entry.indicatorName_en === 'Retail Food Environment Index'">
														{{ entry.moe.toFixed(1).toLocaleString() }}
													</span>
													<span v-else-if="entry.moe">
														&#177; {{ entry.moe.toFixed(1).toLocaleString() + '%' }}
													</span>
												</td>
											</tr>
										</tbody>
									</v-simple-table>
								</v-expansion-panel-content>
							</v-expansion-panel>
							<v-expansion-panel v-if="indicator.showPoints">
								<v-expansion-panel-header class="text--primary" data-html2canvas-ignore>
									<div>
										<v-icon color="accent">mdi-circle</v-icon>
										<span class="mx-2">{{ $t('tools.explore.locations') }}</span>
									</div>
								</v-expansion-panel-header>
								<v-expansion-panel-content class="location-options">
									<div v-for="pointType in pointTypes" :key="pointType.id">
										<v-checkbox 
											color="accent"
											:value="pointType" 
											:label="pointType['name_' + locale] + ' (' + pointType.year + ')'"
											v-model="selectedPointTypes"
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
										<v-icon color="accent">mdi-layers</v-icon>
										<span class="mx-2">{{ $t('tools.community.community_types') }}</span>
									</div>
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
import ExploreToolsPanel from '@/app/components/ExploreToolsPanel'
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
			filteredLocationGeojson: null,
			refreshOptions: false,
			selectedLocationType: null,
			pointCollections: [],
			selectedPointTypes: [],
			pointsGeojson: featureCollection([]),
			reportData: null
		}
	},
	computed: {
		...mapState(['exploreData', 'locale', 'filterSelections', 'showMapLabels', 'highlightFilteredLocation', 'exploreTab', 'customLocations', 'indicator']),
		...mapGetters(['locationMenu', 'filters']),
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
		pointTypes() {
			return this.pointCollections?.map(pc => pc.pointType)
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
		reportHeight() {
			return this.$refs.exploreMap?.$el?.offsetHeight - 280 + 'px';
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
			this.pointsGeojson = featureCollection([])
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
			this.getReportData(newValue);
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
				this.pointsGeojson = featureCollection([])
				this.selectedPointTypes = []
			}
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
			if (this.indicator?.showPoints) {
				this.getPoints()
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
		...mapActions(['setDockedTooltip', 'setFilterSelections', 'setShowMapLabels', 'setHighlightFilteredLocation']),
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
		togglePointType(pointType) {
			if (!this.selectedPointTypes.some(pt => pt.id === pointType)) {
				this.pointsGeojson.features = this.pointsGeojson.features.filter(f => f.properties.typeId !== pointType);
			} else {
				this.pointsGeojson.features = this.pointsGeojson.features.concat(this.pointCollections
					.find(pc => pc.pointType.id === pointType)
					.points
					.map(p => {
						return {
							type: 'Feature',
							geometry: JSON.parse(p.geojson),
							properties: {
								...JSON.parse(p['featureProperties_' + this.locale]),
								id: p.id,
								typeId: pointType,
								valueLabel_en: p.valueLabel_en,
								valueLabel_es: p.valueLabel_es,
								value: p.value
							}
						}
					})
				);
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
			axios.get('/api/points').then(response => {
				this.pointCollections = response.data
			})
		},
		getReportData(location) {
			if (location && this.selectedLocationType) {
				axios.get('/api/location-report', { params: { locationTypeId: this.selectedLocationType.id, locationId: location.id } }).then(response => {
					this.reportData = { location: location, data: null };
					this.reportData.data = response.data;
				});
			}
		},
		downloadReport() {
			let fileName = this.reportData.location.properties.locationName + '.csv';
			let csv = [i18n.t('tools.common.download.headers.indicator'), i18n.t('tools.common.download.headers.year'), i18n.t('data.value'), i18n.t('data.moe')].join(',') + '\n'
				+ Object.entries(this.reportData.data).map(i => i[1]).map(i => {
						let value = '';
						let moe = '';
						if (i.indicatorValue && i.indicatorName_en === 'Median Household Income') {
							value = '"$' + i.indicatorValue.toLocaleString() + '"';
						} else if (i.indicatorValue && i.indicatorName_en === 'Retail Food Environment Index') {
							value = '"' + i.indicatorValue.toLocaleString() + '"';
						} else if (i.indicatorValue) {
							value = '"' + i.indicatorValue.toFixed(1).toLocaleString() + '%' + '"';
						}
						if (i.moe && i.indicatorName_en === 'Median Household Income') {
							moe = '"' + '$' + i.moe.toLocaleString() + '"';
						} else if (i.moe && i.indicatorName_en === 'Retail Food Environment Index') {
							moe = '"' + i.moe.toLocaleString() + '"';
						} else if (i.moe) {
							moe = '"' + i.moe.toFixed(1).toLocaleString() + '%' + '"';
						}
						return [i['indicatorName_' + this.locale], i.year_, value, moe].join(',');
					}).join('\n');
			let downloadLink = document.createElement('a');
			downloadLink.download = fileName;
			downloadLink.href = 'data:text/csv;charset=utf-8,' + encodeURIComponent(csv);
			downloadLink.click();
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

	::v-deep .v-expansion-panels {
		flex-direction: column;
	}
</style>