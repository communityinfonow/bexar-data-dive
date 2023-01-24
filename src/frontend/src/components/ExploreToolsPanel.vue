<template>
	<!-- max-height to fix odd extra space below the col that started showing up after the icon button menus were added -->
	<v-col cols="auto" style="max-height: 72px;">
		<v-form v-if="filters" ref="compareForm" v-model="valid">
			<v-row class="mt-2">
				<v-col cols="3" xl="4">
					<!-- TODO: try a custom v-item slot to allow for disabled items with tooltips rather than removing unavailable items -->
					<v-select
						v-if="showCompareOptions"
						:label="$t('tools.explore.compare_by')"
						dense
						flat
						return-object
						:items="availableCompareByItems"
						:item-text="'name_' + locale"
						v-model="compareBy"
						@change="selectCompareBy"
						:rules="[v => !!v || $t('tools.common.make_selection')]"
					>
					</v-select>
				</v-col>
				<v-col cols="3" xl="4">
					<v-autocomplete
						v-if="showCompareOptions"
						:label="$t('tools.explore.compare_with')"
						dense
						flat
						return-object
						:items="compareWithItems"
						:item-text="'name_' + locale"
						v-model="compareWith"
						:rules="[v => !!v.length || $t('tools.common.make_selection')]"
						multiple
						:search-input.sync="compareWithQuery"
						@change="compareWithQuery = ''"
					>
					</v-autocomplete>
				</v-col>
				<v-col cols="2" xl="1">
					<v-btn
						v-if="showCompareOptions"
						color="primary"
						tile
						@click="applyComparison"
					>
						{{ $t('tools.explore.compare') }}
					</v-btn>
				</v-col>
				<v-col cols="4" xl="3">
					<div class="d-flex justify-end">
					<v-switch
						inset
						:label="$t('tools.common.labels')"
						style="margin-top: 2px;"
						v-model="labels"
						hide-details
					></v-switch>
					<div>
						<download-menu :downloadData="downloadData" :downloadImage="downloadImage"></download-menu>
						<share-menu></share-menu>
						<about-menu indicator tool :indicatorId="indicatorId"></about-menu>
					</div>
					</div>
				</v-col>
			</v-row>
		</v-form>
	</v-col>
</template>

<script>

import i18n from '@/i18n'
import { mapActions, mapState } from 'vuex'
import router from '@/router/index'
import DownloadMenu from '@/components/DownloadMenu'
import ShareMenu from '@/components/ShareMenu'
import AboutMenu from '@/components/AboutMenu'
import html2canvas from 'html2canvas'

export default {
	name: 'ExploreToolsPanel',
	components: {
		DownloadMenu,
		ShareMenu,
		AboutMenu
	},
	computed: {
		...mapState(['filters', 'filterSelections', 'locale', 'filters', 'exploreData']),
		labels: {
			get() { return this.showLabels },
			set(value) { this.setShowLabels(value) }
		},
		indicatorId() {
			return this.exploreData?.indicator?.id
		},
		availableCompareByItems() {
			let items = [];
			items.push(this.filters?.locationFilter.type);
			items.push(this.filters?.yearFilter.type);
			this.filters?.indicatorFilters.forEach(filter => {
				let setFilters = Object.entries(this.filterSelections.indicatorFilters || {})
					.filter(e => e[1].id !== null)
					.map(e => e[0]);

				if (!setFilters.length || !!filter.compatibleFilterTypeIds.some(fc => setFilters.every(sf => fc.includes(sf)))) {
					items.push(filter.type);
				}
			});

			return items;
		}
	},
	props: {
		showCompareOptions: {
			type: Boolean,
			default: false
		},
		showLabels: {
			type: Boolean
		},
		setShowLabels: {
			type: Function
		},
		dataVisualElementId: {
			type: String
		},
		dataVisualName: {
			type: String
		}
	},
	data() {
		return {
			compareByItems: [],
			compareBy: null,
			compareWithQuery: '',
			compareWithItems: [],
			compareWith: [],
			valid: true
		}
	},
	watch: {
		filters() {
			this.compareBy = null;
			this.compareWithQuery = '';
			this.compareWith = [];
			this.valid = true;
			this.initializeCompareByItems();
			this.$refs.compareForm.resetValidation();
		},
		filterSelections() {
			if (this.compareBy) {
				let prev = this.compareWith.map(w => w.id);
				this.selectCompareBy();
				this.compareWith = this.compareWithItems.filter(w => prev.includes(w.id));
				this.applyComparison();
			}
		}
	},
	
	mounted () {
		this.initializeCompareByItems();
		if (router.currentRoute.query.compareBy) {
			if (router.currentRoute.query.compareBy === 'l') {
				this.compareBy = this.compareByItems[0];
				this.selectCompareBy();
				[].concat(router.currentRoute.query.compareWith).forEach(p => {
					this.compareWith.push(this.compareWithItems.find(i => i.typeId == p.split("_")[0] && i.id == p.split("_")[1]));
				});
			} else if (router.currentRoute.query.compareBy === 'y') {
				this.compareBy = this.compareByItems[1];
				this.selectCompareBy();
				[].concat(router.currentRoute.query.compareWith).forEach(p => {
					this.compareWith.push(this.compareWithItems.find(i => i.id == p));
				});
			} else {
				this.compareBy = this.compareByItems.find(i => i.id == router.currentRoute.query.compareBy);
				this.selectCompareBy();
				[].concat(router.currentRoute.query.compareWith).forEach(p => {
					this.compareWith.push(this.compareWithItems.find(i => i.id == p));
				});
			}
			this.applyComparison();
		}
	},
	methods: {
		...mapActions(['setCompareSelections', 'setLoading']),
		initializeCompareByItems() {
			this.compareByItems = [];
			this.compareByItems.push(this.filters?.locationFilter.type);
			this.compareByItems.push(this.filters?.yearFilter.type);
			this.filters?.indicatorFilters.forEach(filter => {
				this.compareByItems.push(filter.type)
			});
		},
		selectCompareBy() {
			this.compareWith = [];
			this.compareWithItems = [];
			if (this.compareBy?.id === 'l') {
				let typeIdsWithData = this.filters?.locationTypeFilter.options.map(o => o.id);
				this.compareWithItems = this.filters?.locationFilter.options
					.filter(o => o.id !== this.filterSelections?.location && typeIdsWithData.indexOf(o.typeId) !== -1) || [];
			} else if (this.compareBy?.id === 'y') { 
				this.compareWithItems = this.filters?.yearFilter.options
					.filter(o => o.id !== this.filterSelections?.year
						&& (Number(this.filterSelections?.year) - Number(o.id)) % this.exploreData.source.trendInterval === 0) || [];
			} else {
				this.compareWithItems = this.filters?.indicatorFilters
					.find(filter => filter.type.name_en === this.compareBy?.name_en)?.options
					.filter(o => o.id !== this.filterSelections?.indicatorFilters[this.compareBy?.id]?.id) || [];
			}
		},
		validateComparison() {
			this.$refs.compareForm.validate();
		},
		getComparison() {	
			return {
				type: this.compareBy,
				options: this.compareWith
			};
		},
		applyComparison() { 
			this.validateComparison();
			if (this.valid) {
				this.setCompareSelections(this.getComparison());
			}
		},
		downloadData() {
			let fileName = '';
			let csv = [
				i18n.t('tools.common.download.headers.indicator'), 
				i18n.t('tools.common.download.headers.source'), 
				i18n.t('tools.common.download.headers.location_type'), 
				i18n.t('tools.common.download.headers.location'), 
				i18n.t('tools.common.download.headers.year')
			].join(',');
			csv += this.filters.indicatorFilters.map(f => ',' + f.type['name_' + this.locale]).join('');
			csv += ',' + [
				i18n.t('tools.common.download.headers.value'),
				i18n.t('tools.common.download.headers.range')
			].join(',');
			if (this.dataVisualName === 'map') {
				fileName = 'explore_map_data.csv';
				csv += this.exploreData.locationData.map((data) => {
					let yearData = data.yearData[this.filterSelections.year];
					if (!yearData) {
						return '';
					}
					return '\n' 
						+ '"' + ((this.exploreData.category ? this.exploreData.category['name_' + this.locale] + ' - ' : '') + this.exploreData.indicator['name_' + this.locale]) + '",'
						+ '"' + this.exploreData.source['name_' + this.locale] + '",'
						+ '"' + data.locationType['name_' + this.locale] + '",'
						+ '"' + data.location['name_' + this.locale] + '",'
						+ this.filterSelections.year + ','
						+ this.filters.indicatorFilters.map(f => '"' + this.filterSelections.indicatorFilters[f.type.id]['name_' + this.locale] + '"').join(',') + ','
						+ (yearData.suppressed ? i18n.t('data.suppressed') : yearData.value === null ? i18n.t('data.no_data') : yearData.value) + ','
            			+ (yearData.moeLow || yearData.moeHigh ? (yearData.moeLow + ' - ' + yearData.moeHigh) : '');
				}).join('');
			} else if (this.dataVisualName === 'trend_chart') {
				fileName = 'explore_trend_data.csv';
				let data = this.exploreData.locationData.find(data => data.location.id === this.filterSelections.location);
				csv += Object.entries(data.yearData).map(([year, values]) => {
					return '\n'
						+ '"' + ((this.exploreData.category ? this.exploreData.category['name_' + this.locale] + ' - ' : '') + this.exploreData.indicator['name_' + this.locale]) + '",'
						+ '"' + this.exploreData.source['name_' + this.locale] + '",'
						+ '"' + data.locationType['name_' + this.locale] + '",'
						+ '"' + data.location['name_' + this.locale] + '",'
						+ year + ','
						+ this.filters.indicatorFilters.map(f => '"' + this.filterSelections.indicatorFilters[f.type.id]['name_' + this.locale] + '"').join(',') + ','
						+ (values.suppressed ? i18n.t('data.suppressed') : values.value === null ? i18n.t('data.no_data') : values.value) + ','
            			+ (values.moeLow || values.moeHigh ? (values.moeLow + ' - ' + values.moeHigh) : '');
				})
			} else if (this.dataVisualName === 'compare_chart') {
				fileName = 'explore_compare_data.csv';
				let data = this.exploreData.locationData.find(data => data.location.id === this.filterSelections.location);
				let yearData = data.yearData[this.filterSelections.year];
				csv += '\n'
					+ '"' + ((this.exploreData.category ? this.exploreData.category['name_' + this.locale] + ' - ' : '') + this.exploreData.indicator['name_' + this.locale]) + '",'
					+ '"' + this.exploreData.source['name_' + this.locale] + '",'
					+ '"' + data.locationType['name_' + this.locale] + '",'
					+ '"' + data.location['name_' + this.locale] + '",'
					+ this.filterSelections.year + ','
					+ this.filters.indicatorFilters.map(f => '"' + this.filterSelections.indicatorFilters[f.type.id]['name_' + this.locale] + '"').join(',') + ','
					+ (yearData.suppressed ? i18n.t('data.suppressed') : yearData.value === null ? i18n.t('data.no_data') : yearData.value) + ','
					+ (yearData.moeLow || yearData.moeHigh ? (yearData.moeLow + ' - ' + yearData.moeHigh) : '');
				if (this.compareBy?.id === 'l') {
					this.exploreData.compareData.forEach(comp => {
						let compYearData = comp.yearData[this.filterSelections.year];
						csv += '\n'
							+ '"' + ((this.exploreData.category ? this.exploreData.category['name_' + this.locale] + ' - ' : '') + this.exploreData.indicator['name_' + this.locale]) + '",'
							+ '"' + this.exploreData.source['name_' + this.locale] + '",'
							+ '"' + comp.locationType['name_' + this.locale] + '",'
							+ '"' + comp.location['name_' + this.locale] + '",'
							+ this.filterSelections.year + ','
							+ this.filters.indicatorFilters.map(f => '"' + this.filterSelections.indicatorFilters[f.type.id]['name_' + this.locale] + '"').join(',') + ','
							+ (compYearData.suppressed ? i18n.t('data.suppressed') : compYearData.value === null ? i18n.t('data.no_data') : compYearData.value) + ','
							+ (compYearData.moeLow || compYearData.moeHigh ? (compYearData.moeLow + ' - ' + compYearData.moeHigh) : '');
					});
				} else if (this.compareBy?.id === 'y') {
					this.compareWith.forEach(year => {
						let compYearData = data.yearData[year.id] || {};
						csv += '\n'
							+ '"' + ((this.exploreData.category ? this.exploreData.category['name_' + this.locale] + ' - ' : '') + this.exploreData.indicator['name_' + this.locale]) + '",'
							+ '"' + this.exploreData.source['name_' + this.locale] + '",'
							+ '"' + data.locationType['name_' + this.locale] + '",'
							+ '"' + data.location['name_' + this.locale] + '",'
							+ year.id + ','
							+ this.filters.indicatorFilters.map(f => '"' + this.filterSelections.indicatorFilters[f.type.id]['name_' + this.locale] + '"').join(',') + ','
							+ (compYearData.suppressed ? i18n.t('data.suppressed') : !compYearData.value ? i18n.t('data.no_data') : compYearData.value) + ','
							+ (compYearData.moeLow || compYearData.moeHigh ? (compYearData.moeLow + ' - ' + compYearData.moeHigh) : '');
					});
				} else {
					this.exploreData.compareData?.forEach((comp, index) => {
						let compYearData = comp.yearData[this.filterSelections.year];
						csv += '\n'
							+ '"' + ((this.exploreData.category ? this.exploreData.category['name_' + this.locale] + ' - ' : '') + this.exploreData.indicator['name_' + this.locale]) + '",'
							+ '"' + this.exploreData.source['name_' + this.locale] + '",'
							+ '"' + data.locationType['name_' + this.locale] + '",'
							+ '"' + data.location['name_' + this.locale] + '",'
							+ this.filterSelections.year + ','
							+ this.filters.indicatorFilters.map(f => {
									if (f.type.id === this.compareBy?.id) {
										return '"' + this.compareWith[index]['name_' + this.locale] + '"'; 
									} else {
										return '"' + this.filterSelections.indicatorFilters[f.type.id]['name_' + this.locale] + '"';
									}
								}).join(',') + ','
							+ (compYearData.suppressed ? i18n.t('data.suppressed') : compYearData.value === null ? i18n.t('data.no_data') : compYearData.value) + ','
							+ (compYearData.moeLow || compYearData.moeHigh ? (compYearData.moeLow + ' - ' + compYearData.moeHigh) : '');
					});
				}
				console.log(csv);
			}
			let downloadLink = document.createElement('a');
			downloadLink.download = fileName;
			downloadLink.href = 'data:text/csv;charset=utf-8,' + encodeURIComponent(csv);
			downloadLink.click();
		},
		downloadImage() {
			this.setLoading(true);
			// scale is needed to fix a background color quirk with html2canvas
			// so we scale the dom elements up 2x during processing, 
			// then scale them back down to original size in the final canvas
			let exploreIndicator = document.querySelector('#explore_indicator').cloneNode(true);
			exploreIndicator.id = 'explore_indicator_download';
			exploreIndicator.style.position = 'absolute';
			exploreIndicator.style.top = '-400px';
			document.querySelector('main').appendChild(exploreIndicator);
			document.querySelector('#explore_indicator_download h2').innerHTML = ''
				+ this.filters.locationFilter.options.find(o => o.typeId == this.filterSelections.locationType && o.id == this.filterSelections.location)['name_' + this.locale]
				+ ', '
				+ document.querySelector('#explore_indicator_download h2').innerHTML
				+ ' (' + this.filterSelections.year + ')';
			document.querySelector('#explore_indicator_download').innerHTML += ''
				+ '<h2 class="text-subtitle-1 mb-2">'
				+ this.filters.indicatorFilters.map(f => f.type['name_' + this.locale] + ': ' + this.filterSelections.indicatorFilters[f.type.id]['name_' + this.locale]).join(', ')
				+ '</h2>';
			html2canvas(document.querySelector('header'), { scale: 2 }).then((headerCanvas) => {
				html2canvas(document.querySelector('#explore_indicator_download'), { scale: 2 }).then((indicatorCanvas) => {
					html2canvas(document.querySelector('#' + this.dataVisualElementId), { 
						scale: 2.666, 
						useCORS: true,
						ignoreElements: (el) => {
							return el.classList.contains('leaflet-control-zoom');
						}
					}).then((chartCanvas) => {
						let imageCanvas = document.createElement('canvas');
						imageCanvas.width = headerCanvas.width / 2;
						imageCanvas.height = (headerCanvas.height + indicatorCanvas.height + chartCanvas.height) / 2;
						imageCanvas.getContext('2d').scale(0.5, 0.5);
						imageCanvas.getContext('2d').fillStyle = 'white';
						imageCanvas.getContext('2d').fillRect(0, 0, imageCanvas.width * 2, imageCanvas.height * 2);
						imageCanvas.getContext('2d').drawImage(headerCanvas, 0, 0);
						imageCanvas.getContext('2d').drawImage(indicatorCanvas, 0, headerCanvas.height);
						imageCanvas.getContext('2d').drawImage(chartCanvas, 0, headerCanvas.height + indicatorCanvas.height);

						let imageLink = document.createElement('a');
						imageLink.download = this.dataVisualName + '.png';
						imageLink.href = imageCanvas.toDataURL('image/png').replace('image/png', 'image/octet-stream');
						imageLink.click();

						document.querySelector('main').removeChild(exploreIndicator);
						this.setLoading(false);
					});
				});
			});
		}
	},
}
</script>

<style lang="scss" scoped>

</style>