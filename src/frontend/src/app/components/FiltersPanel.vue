<template>
	<v-form v-if="filters" ref="filtersForm" v-model="valid">
		<v-card outlined tile id="explore_filters">
			<v-card-title>
				<span class="text-h6 font-weight-light">
					<v-icon color="accent">mdi-filter-variant</v-icon>
					{{ $t('tools.explore.filters') }}
				</span>
			</v-card-title>
			<v-card-text class="pb-0">
				<v-autocomplete
					:label="filters.locationTypeFilter.type['name_' + locale]"
					:placeholder="filters.locationTypeFilter.type['name_' + locale]"
					v-model="selectedLocationType"
					:items="filters.locationTypeFilter.options"
					:item-text="'name_' + locale"
					item-value="id"
					item-disabled="disabled"
					hide-no-data
					flat
					dense
					:rules="[v => !!v || $t('tools.common.make_selection')]"
					@change="selectLocationType"
				>
					<template v-slot:item="{parent, item}">
						<v-list-item-content>
							<v-list-item-title 
								v-html="parent.genFilteredText(item['name_' + locale])"
								:title="item.disabled ? $t('tools.explore.no_data_custom_locations') : null"
							></v-list-item-title>
						</v-list-item-content>
					</template>
				</v-autocomplete>
				<v-autocomplete
					:label="filters.locationFilter.type['name_' + locale]"
					:placeholder="filters.locationFilter.type['name_' + locale]"
					v-model="selectedLocation"
					:items="locationFilterOptions"
					:item-text="'name_' + locale"
					item-value="id"
					item-disabled="disabled"
					hide-no-data
					flat
					dense
					:rules="[v => !!v || $t('tools.common.make_selection')]"
					@change="requestApply"
				>
					<template v-slot:item="{parent, item}">
						<v-list-item-content>
							<v-list-item-title 
								v-html="parent.genFilteredText(item['name_' + locale])"
								:title="item.disabled ? $t('tools.explore.no_data_custom_location') : null"
							></v-list-item-title>
						</v-list-item-content>
					</template>
				</v-autocomplete>
				<v-autocomplete
					:label="filters.yearFilter.type['name_' + locale]"
					:placeholder="filters.yearFilter.type['name_' + locale]"
					v-model="selectedYear"
					:items="yearOptions"
					:item-text="'name_' + locale"
					item-value="name_en"
					item-disabled="disabled"
					hide-no-data
					flat
					dense
					:rules="[v => !!v || $t('tools.common.make_selection')]"
					@change="requestApply"
				>
					<template v-slot:item="{parent, item}">
						<v-list-item-content>
							<v-list-item-title 
								v-html="parent.genFilteredText(item['name_' + locale])"
								:title="item.disabled ? $t('tools.explore.no_data_location_year') : null"
							></v-list-item-title>
						</v-list-item-content>
					</template>
				</v-autocomplete>
				<template v-for="filter in filters.indicatorFilters">
					<v-autocomplete
						:key="filter.type.id"
						:label="filter.type['name_' + locale]"
						:placeholder="filter.type['name_' + locale]"
						v-model="indicatorFilterSelections[filter.type.id]"
						:items="filter.options"
						:item-text="'name_' + locale"
						return-object
						hide-no-data
						flat
						dense
						:rules="[v => !!v || $t('tools.common.make_selection')]"
						@change="selectIndicatorFilter"
						:disabled="!availableFilterCombos[filter.type.id]"
					>
						<template v-slot:item="{parent, item}">
						<v-list-item-content>
							<v-list-item-title 
								v-html="parent.genFilteredText(item['name_' + locale])"
								:title="!availableFilterCombos[filter.type.id] ? $t('tools.common.filter_unavailable') : null"
							></v-list-item-title>
						</v-list-item-content>
					</template>
					</v-autocomplete>
				</template>
			</v-card-text>
			<v-card-actions class="d-flex justify-end">
				<v-btn style="font-size: 14pt;" class="font-weight-bold" color="accent" :disabled="!applyNeeded" @click="applyFilters" data-html2canvas-ignore>
					{{ $t('tools.explore.apply_filters') }}
				</v-btn>
			</v-card-actions>
		</v-card>
	</v-form>
</template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import router from '@/app/router/index'

export default {
	name: 'FiltersPanel',
	data() {
		return {
			applyNeeded: false,
			selectedLocationType: null,
			selectedLocation: null,
			selectedYear: null,
			indicatorFilterSelections: {},
			valid: true,
			availableFilterCombos: {}
		}
	},
	computed: {
		...mapState(['locale', 'filterSelections']),
		...mapGetters(['filters']),
		locationFilterOptions() {
			return this.filters.locationFilter.options
				.filter(option => option.typeId === this.selectedLocationType)
		},
		yearOptions() {
			return this.filters.yearFilter.options.map(o => {
				return {
					...o,
					disabled: this.filters?.locationTypeYears[this.selectedLocationType].indexOf(o.id) === -1
				}
			})
		}
	},
	watch: {
		filters() {
			this.initFilters();
			this.$nextTick(() => this.applyFilters());
		},
		filterSelections(newValue) {
			this.selectedLocationType = newValue?.locationType;
			this.selectedLocation = newValue?.location;
		},
		indicatorFilterSelections() {
			this.selectIndicatorFilter();
		}
	},
	methods: {
		...mapActions(['setFilterSelections']),
		initFilters() {
			this.applyNeeded = false;
			this.indicatorFilterSelections = {}
			this.selectedLocationType = router.currentRoute.query.locationType || this.filters?.locationTypeFilter.options[0]?.id
			this.selectedLocation = router.currentRoute.query.location || this.filters?.locationFilter.options.find(o => o.typeId == this.selectedLocationType)?.id
			this.selectedYear = router.currentRoute.query.year || this.filters?.yearFilter.options[0]?.name_en
			this.filters?.indicatorFilters.forEach((filter) => {
				let option = filter.options[0];
				if (router.currentRoute.query['filter_' + filter.type.id]) {
					option = filter.options.find(o => o.id == router.currentRoute.query['filter_' + filter.type.id]);
				} 
				this.indicatorFilterSelections[filter.type.id] = option 
			});
		},
		requestApply() {
			if (this.yearOptions.find(y => y.id === this.selectedYear).disabled) {
				this.selectedYear = this.yearOptions.find(y => !y.disabled).id;
			}
			this.applyNeeded = true;
		},
		selectLocationType() {
			this.selectedLocation = this.filters.locationFilter.options
				.filter(option => option.typeId === this.selectedLocationType)[0]?.id
			this.requestApply();
		},
		selectIndicatorFilter() {
			let combos = {};
			this.filters?.indicatorFilters.forEach(filter => {
				let setFilters = Object.entries(this.indicatorFilterSelections || {})
					.filter(e => e[1].id !== null)
					.map(e => e[0]);

				combos[filter.type.id] = !setFilters.length || !!filter.compatibleFilterTypeIds.some(fc => setFilters.every(sf => fc.includes(sf)));
			});
			this.availableFilterCombos = combos;
			this.requestApply();
		},
		validateFilters() {
			this.$refs.filtersForm?.validate()
		},
		getFilterSelections() {	
			return {
				locationType: this.selectedLocationType,
				location: this.selectedLocation,
				year: this.selectedYear,
				indicatorFilters: this.indicatorFilterSelections
			};
		},
		applyFilters() { 
			this.validateFilters();
			if (this.valid) {
				this.setFilterSelections(this.getFilterSelections());
				this.applyNeeded = false;
			}
		}
	},
}
</script>

<style lang="scss" scoped>
	::v-deep .v-list-item--disabled {
		pointer-events: all;
	}
</style>