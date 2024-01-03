<template>
	<!-- max-height to fix odd extra space below the col that started showing up after the icon button menus were added -->
	<v-col cols="auto" style="max-height: 72px;">
		<v-form v-if="filters" ref="compareForm" v-model="valid">
			<v-row class="mt-2 justify-end">
				<v-col cols="2" v-if="showCompareOptions">
					<v-select
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
				<v-col cols="4" v-if="showCompareOptions">
					<v-autocomplete
						:label="$t('tools.explore.compare_with')"
						dense
						flat
						return-object
						:items="compareWithItems"
						:item-text="(item) => (item['name_' + locale]) + (item.filtered ? (' (' + $t('tools.explore.current_filter_setting') + ')') : '')"
						:item-disabled="(item) => item.filtered"
						v-model="compareWith"
						multiple
						:search-input.sync="compareWithQuery"
						@change="selectCompareWith"
						:rules="[v => compareWith.length <= 10 || $t('tools.explore.compare_limit_exceeded')]"
						height="24px"
					>
					</v-autocomplete>
				</v-col>
				<v-col cols="2" v-if="showCompareOptions">
					<v-btn
						color="red"
						rounded
						dark
						@click="applyComparison"
					>
						{{ $t('tools.explore.compare') }}
					</v-btn>
				</v-col>
				<v-col cols="4" v-if="!showHighlightFilteredLocation">
					<div class="d-flex justify-end">
						<div class="v-input v-input--is-label-active v-input--is-dirty theme--light v-text-field v-text-field--is-booted v-select pt-1 flex-grow-0 flex-shrink-1">
							<label aria-label id="labelsOrLinesLabel" class="v-label v-label--active theme--light" style="left: 0px; right: auto; position: absolute;">{{ $t('tools.common.chart_options') }}</label>
							<v-btn-toggle rounded v-model="labelsOrLines" id="labelsOrLines" aria-labelledby="labelsOrLinesLabel">
								<v-btn 
									v-for="item in [['labels', $t('tools.common.chart_options_labels')], ['lines', $t('tools.common.chart_options_lines')]]" 
									:key="item[0]" 
									:value="item[0]" 
									color="red"
									dark
									small
									:disabled="item[0] == 'lines' && exploreData && !exploreData.indicator.hasMoe"
									:title="item[0] == 'lines' && exploreData && !exploreData.indicator.hasMoe ? $t('tools.common.chart_options_no_moe') : ''"
								>
									{{ item[1] }}
								</v-btn>
							</v-btn-toggle>
						</div>
						<button-menu :downloadData="downloadData" :downloadImage="downloadImage" class="ml-2" style="margin-top: -8px"></button-menu>
					</div>
				</v-col>
				<v-col v-if="showHighlightFilteredLocation" cols="auto" class="d-flex justify-end">
					<template v-if="showHighlightFilteredLocation">
						<v-switch
							inset
							:label="$t('tools.common.highlight_location')"
							v-model="highlight"
							hide-details
							style="margin-top: 2px;"
							class="mr-6"
							color="red"
						></v-switch>
						<v-switch
							inset
							:label="$t('tools.common.labels')"
							style="margin-top: 2px;"
							v-model="labelsOrLines"
							hide-details
							color="red"
						></v-switch>
					</template>
					<button-menu :downloadData="downloadData" :downloadImage="downloadImage" class="ml-2" style="margin-top: -.5em"></button-menu>
				</v-col>
			</v-row>
		</v-form>
	</v-col>
</template>

<script>

import i18n from '@/i18n'
import { mapActions, mapState, mapGetters } from 'vuex'
import router from '@/app/router/index'
import ButtonMenu from '@/app/components/ButtonMenu'
import html2canvas from 'html2canvas'

export default {
	name: 'ExploreToolsPanel',
	components: {
		ButtonMenu
	},
	computed: {
		...mapState(['filterSelections', 'locale', 'exploreData', 'indicator', 'indicatorMenu']),
		...mapGetters(['filters']),
		highlight: {
			get() { return this.highlightFilteredLocation },
			set(value) { this.setHighlightFilteredLocation(value) }
		},
		labelsOrLines: {
			get() { return this.labelsOrLinesOption },
			set(value) { this.setLabelsOrLinesOption(value) }
		},
		indicatorId() {
			return this.exploreData?.indicator?.id
		},
		availableCompareByItems() {
			let items = [];
			let subcategory = this.indicatorMenu.categories.find(c => c.id === '3').subcategories.find(sc => sc.id === this.indicator.categoryId);
			if (subcategory) {
				items.push({
					id: "i",
						name_en: "Indicator",
						name_es: "Indicador"
				});
			}
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
		showHighlightFilteredLocation: {
			type: Boolean
		},
		highlightFilteredLocation: {
			type: Boolean
		},
		setHighlightFilteredLocation: {
			type: Function
		},
		labelsOrLinesOption: {
			type: String
		},
		setLabelsOrLinesOption: {
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
			compareWithSelectAll: false,
			compareCounter: 0,
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
				this.compareWith = this.compareWithItems.filter(w => prev.includes(w.id) && !w.filtered);
				this.applyComparison();
			}
		},
		locale() {
			if (this.compareWithItems?.find(i => i.id === 0)) {
				this.compareWithItems.find(i => i.id === 0).name_en = i18n.t('tools.tables.select_all');
				this.compareWithItems.find(i => i.id === 0).name_es = i18n.t('tools.tables.select_all');
			}
		}
	},
	beforeRouteEnter(to, from, next) {
		next(vm => {
			vm.init();
		});
	},
	mounted () {
		this.init()
	},
	methods: {
		...mapActions(['setCompareSelections', 'setLoading']),
		init() {
			this.initializeCompareByItems();
			if (router.currentRoute.query.compareBy) {
				this.compareBy = this.compareByItems.find(i => i.id == router.currentRoute.query.compareBy);
				this.selectCompareBy();
				if (router.currentRoute.query.compareBy === 'i') {
					[].concat(router.currentRoute.query.compareWith).forEach(p => {
						let indicator = this.compareWithItems.find(i => i.id == p && !i.filtered);
						if (indicator) {
							this.compareWith.push(indicator);
						}
					});
				} else if (router.currentRoute.query.compareBy === 'l') {
					[].concat(router.currentRoute.query.compareWith).forEach(p => {
						let location = this.compareWithItems.find(i => i.typeId == p.split("_")[0] && i.id == p.split("_")[1] && !i.filtered);
						if (location) {
							this.compareWith.push(location);
						}
					});
				} else if (router.currentRoute.query.compareBy === 'y') {
					[].concat(router.currentRoute.query.compareWith).forEach(p => {
						let year = this.compareWithItems.find(i => i.id == p && !i.filtered);
						if (year) {
							this.compareWith.push(year);
						}
					});
				} else {
					[].concat(router.currentRoute.query.compareWith).forEach(p => {
						let item = this.compareWithItems.find(i => i.id == p && !i.filtered);
						if (item) {
							this.compareWith.push(item);
						}
					});
				}
				this.selectCompareWith();
				this.applyComparison();
			}
		},
		initializeCompareByItems() {
			this.compareByItems = [];
			let subcategory = this.indicatorMenu.categories.find(c => c.id === '3').subcategories.find(sc => sc.id === this.indicator.categoryId);
			if (subcategory) {
				this.compareByItems.push({
					id: "i",
						name_en: "Indicator",
						name_es: "Indicador"
				});
			}
			this.compareByItems.push(this.filters?.locationFilter.type);
			this.compareByItems.push(this.filters?.yearFilter.type);
			this.filters?.indicatorFilters.forEach(filter => {
				this.compareByItems.push(filter.type)
			});
		},
		selectCompareBy() {
			this.compareWith = [];
			this.compareWithItems = [];
			this.compareWithSelectAll = false;
			if (this.compareBy?.id === 'i') {
				this.compareWithItems = [{id: 0, name_en: i18n.t('tools.tables.select_all'), name_es: i18n.t('tools.tables.select_all')}]
					.concat(this.indicatorMenu.categories
						.find(c => c.id === '3').subcategories
						.find(sc => sc.id === this.indicator.categoryId).items
						.filter(i => i.id !== this.indicator.id));
			} else if (this.compareBy?.id === 'l') {
				let typeIdsWithData = this.filters?.locationTypeFilter.options.map(o => o.id);
				this.compareWithItems = this.filters?.locationFilter.options
					.filter(o => typeIdsWithData.indexOf(o.typeId) !== -1) || [];
				this.compareWithItems.forEach(i => i.filtered = (i.id === this.filterSelections?.location));
			} else if (this.compareBy?.id === 'y') {
				this.compareWithItems = [{id: 0, name_en: i18n.t('tools.tables.select_all'), name_es: i18n.t('tools.tables.select_all')}].concat(this.filters?.yearFilter.options
					.filter(o => (Number(this.filterSelections?.year) - Number(o.id)) % this.exploreData.source.trendInterval === 0) || []);
				this.compareWithItems.forEach(i => i.filtered = (i.id === this.filterSelections?.year));
			} else {
				this.compareWithItems = [{id: 0, name_en: i18n.t('tools.tables.select_all'), name_es: i18n.t('tools.tables.select_all')}].concat(this.filters?.indicatorFilters
					.find(filter => filter.type.name_en === this.compareBy?.name_en)?.options || []);
				this.compareWithItems.forEach(i => i.filtered = (i.id === this.filterSelections?.indicatorFilters[this.compareBy?.id]?.id));
			}
		},
		selectCompareWith() {
			this.compareWithQuery = '';
			if (this.compareWith.find(i => i?.id === 0) || this.compareWith.length === this.compareWithItems.length - 2) {
				if (this.compareWithSelectAll) {
					this.compareWith = [];
					this.compareWithItems.find(i => i.id === 0).name_en = i18n.t('tools.tables.select_all');
					this.compareWithItems.find(i => i.id === 0).name_es = i18n.t('tools.tables.select_all');
					this.compareWithSelectAll = false;
				} else {
					this.compareWith = this.compareWithItems.filter(i => i.id !== 0 && !i.filtered);
					this.compareWithItems.find(i => i.id === 0).name_en = i18n.t('tools.tables.clear_selections');
					this.compareWithItems.find(i => i.id === 0).name_es = i18n.t('tools.tables.clear_selections');
					this.compareWithSelectAll = true;
				}
			} else {
				if (this.compareWithItems.find(i => i.id === 0)) {
					this.compareWithItems.find(i => i.id === 0).name_en = i18n.t('tools.tables.select_all');
					this.compareWithItems.find(i => i.id === 0).name_es = i18n.t('tools.tables.select_all');
				}
				this.compareWithSelectAll = false;
			}
		},
		validateComparison() {
			this.$refs.compareForm.validate();
		},
		getComparison() {
			// manually remove the indicator filter that is already applied from the comparison
			// even with several safeguards in place, it's still ending up in the comparison 
			// on subsequent view loads after browser back button use
			let cw = JSON.parse(JSON.stringify(this.compareWith));
			if (this.filterSelections?.indicatorFilters[this.compareBy.id]?.id) {
				cw = cw.filter(i => i.id !== this.filterSelections?.indicatorFilters[this.compareBy.id]?.id);
			}
			return {
				type: this.compareBy,
				options: cw
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
				i18n.t('tools.common.download.headers.location_id'),
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
						+ '"' + data.location.id + '",'
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
						+ '"' + data.location.id + '",'
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
					+ '"' + data.location.id + '",'
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
							+ '"' + comp.location.id + '",'
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
							+ '"' + data.location.id + '",'
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
							+ '"' + data.location.id + '",'
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
							return el.classList.contains('leaflet-control-zoom')
							 || el.matches('.location-options .mdi-checkbox-marked')
							 || (el.matches('.location-options .v-input--checkbox') && !el.querySelector('input:checked'));
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
::v-deep .v-select__selections {
	position: absolute;
	top: 0;
	left: 0;
	max-height: 24px;
	overflow: hidden;
}
</style>