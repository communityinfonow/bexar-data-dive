<template>
	<v-form v-if="filters" ref="compareForm" v-model="valid">
		<v-row class="mt-2">
			<v-col cols="4">
				<!--TODO: espanol -->
				<v-select
					v-if="showCompareOptions"
					label="Compare by"
					dense
					flat
					return-object
					:items="compareByItems"
					:item-text="'name_' + locale"
					v-model="compareBy"
					@change="selectCompareBy"
					:rules="[v => !!v || 'Please make a selection']"
				>
				</v-select>
			</v-col>
			<v-col cols="4">
				<!--TODO: espanol -->
				<v-autocomplete
					v-if="showCompareOptions"
					label="Compare with"
					dense
					flat
					return-object
					:items="compareWithItems"
					:item-text="'name_' + locale"
					v-model="compareWith"
					:rules="[v => !!v || 'Please make a selection']"
					multiple
					:search-input.sync="compareWithQuery"
					@change="compareWithQuery = ''"
				>
				</v-autocomplete>
			</v-col>
			<v-col cols="2">
				<v-btn
					v-if="showCompareOptions"
					color="primary"
					tile
					@click="applyComparison"
				>
					Compare
				</v-btn>
			</v-col>
			<v-col cols="2" class="d-flex justify-space-between">
				<v-switch
					inset
					:label="$t('tools.common.labels')"
					style="margin-top: 2px;"
					v-model="labels"
				></v-switch>
				<v-btn color="accent" icon @click="downloadImage" data-html2canvas-ignore><v-icon>mdi-download</v-icon></v-btn>
			</v-col>
		</v-row>
	</v-form>
</template>

<script>

import { mapActions, mapState } from 'vuex'
import router from '@/router/index'
import html2canvas from 'html2canvas'

export default {
	name: 'ExploreToolsPanel',
	computed: {
		...mapState(['filters', 'filterSelections', 'locale', 'filters']),
		labels: {
			get() { return this.showLabels },
			set(value) { this.setShowLabels(value) }
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
				router.currentRoute.query.compareWith?.forEach(p => {
					this.compareWith.push(this.compareWithItems.find(i => i.typeId == p.split("_")[0] && i.id == p.split("_")[1]));
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
			this.filters?.indicatorFilters.forEach(filter => {
				this.compareByItems.push(filter.type)
			});
		},
		selectCompareBy() {
			this.compareWith = [];
			this.compareWithItems = [];
			if (this.compareBy?.name_en === 'Location') {
				this.compareWithItems = this.filters?.locationFilter.options
					.filter(o => o.id !== this.filterSelections?.location) || [];
			} else {
				this.compareWithItems = this.filters?.indicatorFilters
					.find(filter => filter.type.name_en === this.compareBy?.name_en)?.options
					.filter(o => o.id !== this.filterSelections?.indicatorFilters[this.compareBy?.id].id) || [];
			}
		},
		validateComparison() {
			this.$refs.compareForm.validate();
		},
		getComparison() {	
			return {
				type: this.compareBy,
				filterOptions: this.compareWith
			};
		},
		applyComparison() { 
			this.validateComparison();
			if (this.valid) {
				this.setCompareSelections(this.getComparison());
			}
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