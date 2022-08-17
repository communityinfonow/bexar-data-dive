<template>
	<v-form v-if="filters" ref="filtersForm" v-model="valid">
		<v-card outlined tile>
			<v-card-title >
				<span class="text-h6 font-weight-light">
					<v-icon color="accent">mdi-filter-variant</v-icon>
					{{ $t('tools.explore.filters') }}
				</span>
			</v-card-title>
			<v-card-text>
				<v-autocomplete
					:label="filters.locationTypeFilter.type['name_' + locale]"
					:placeholder="filters.locationTypeFilter.type['name_' + locale]"
					v-model="selectedLocationType"
					:items="filters.locationTypeFilter.options"
					:item-text="'name_' + locale"
					item-value="id"
					hide-no-data
					flat
					dense
					:rules="[v => !!v || 'Please make a selection']"
					@change="selectLocationType"
				></v-autocomplete>
				<v-autocomplete
					:label="filters.locationFilter.type['name_' + locale]"
					:placeholder="filters.locationFilter.type['name_' + locale]"
					v-model="selectedLocation"
					:items="locationFilterOptions"
					:item-text="'name_' + locale"
					item-value="id"
					hide-no-data
					flat
					dense
					:rules="[v => !!v || 'Please make a selection']"
				></v-autocomplete>
				<v-autocomplete
					:label="filters.yearFilter.type['name_' + locale]"
					:placeholder="filters.yearFilter.type['name_' + locale]"
					v-model="selectedYear"
					:items="filters.yearFilter.options"
					:item-text="'name_' + locale"
					item-value="name_en"
					hide-no-data
					flat
					dense
					:rules="[v => !!v || 'Please make a selection']"
				></v-autocomplete>
				<template v-for="filter in filters.indicatorFilters">
					<v-autocomplete
						:key="filter.id"
						:label="filter.type['name_' + locale]"
						:placeholder="filter.type['name_' + locale]"
						v-model="indicatorFilterSelections[filter.type.id]"
						:items="filter.options"
						:item-text="'name_' + locale"
						return-object
						hide-no-data
						flat
						dense
						:rules="[v => !!v || 'Please make a selection']"
					></v-autocomplete>
				</template>
			</v-card-text>
			<v-card-actions>
				<v-btn color="accent" @click="applyFilters">
					{{ $t('tools.explore.apply_filters') }}
				</v-btn>
			</v-card-actions>
		</v-card>
	</v-form>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import router from '@/router/index'

export default {
	name: 'FiltersPanel',
	data() {
		return {
			selectedLocationType: null,
			selectedLocation: null,
			selectedYear: null,
			indicatorFilterSelections: {},
			valid: true
		}
	},
	computed: {
		...mapState(['filters', 'locale', 'filterSelections']),
		locationFilterOptions() {
			return this.filters.locationFilter.options
				.filter(option => option.typeId === this.selectedLocationType)
		},
	},
	watch: {
		filters() {
			this.initFilters();
			this.$nextTick(() => this.applyFilters());
		},
		filterSelections(newValue) {
			this.selectedLocationType = newValue.locationType;
			this.selectedLocation = newValue.location;
		}
	},
	methods: {
		...mapActions(['setFilterSelections']),
		initFilters() {
			this.indicatorFilterSelections = {}
			this.selectedLocationType = router.currentRoute.query.locationType || this.filters?.locationTypeFilter.options[0].id
			this.selectedLocation = router.currentRoute.query.location || this.filters?.locationFilter.options.find(o => o.typeId == this.selectedLocationType).id
			this.selectedYear = router.currentRoute.query.year || this.filters?.yearFilter.options[0].name_en
			this.filters?.indicatorFilters.forEach((filter) => {
				let option = filter.options[0];
				if (router.currentRoute.query['filter_' + filter.type.id]) {
					option = filter.options.find(o => o.id == router.currentRoute.query['filter_' + filter.type.id]);
				} 
				this.indicatorFilterSelections[filter.type.id] = option 
			});
		},
		selectLocationType() {
			this.selectedLocation = this.filters.locationFilter.options
				.filter(option => option.typeId === this.selectedLocationType)[0]?.id
		},
		validateFilters() {
			this.$refs.filtersForm.validate()
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
				this.setFilterSelections(this.getFilterSelections())
			}
		}
	},
}
</script>

<style lang="scss" scoped>

</style>