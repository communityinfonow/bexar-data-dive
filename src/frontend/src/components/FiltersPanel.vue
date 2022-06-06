<template>
	<v-form v-if="filters" ref="filtersForm" v-model="valid">
		<v-card outlined tile>
			<v-card-title >
				<span class="text-h6 font-weight-light">{{ $t('tools.dashboard.filters') }}</span>
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
						item-value="id"
						hide-no-data
						flat
						:rules="[v => !!v || 'Please make a selection']"
					></v-autocomplete>
				</template>
			</v-card-text>
			<v-card-actions>
				<v-btn text color="primary" @click="applyFilters">
					{{ $t('tools.dashboard.apply_filters') }}
				</v-btn>
			</v-card-actions>
		</v-card>
	</v-form>
</template>

<script>
import { mapState } from 'vuex'
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
		...mapState(['filters', 'locale']),
		locationFilterOptions() {
			return this.filters.locationFilter.options
				.filter(option => option.typeId === this.selectedLocationType)
		}
	},
	watch: {
		filters(newValue) {
			this.indicatorFilterSelections = {}
			this.selectedLocationType = newValue.locationTypeFilter.options[0].id
			this.selectedLocation = newValue.locationFilter.options[0].id
			this.selectedYear = newValue.yearFilter.options[0].name_en
			newValue.indicatorFilters.forEach((filter) => {
				this.indicatorFilterSelections[filter.type.id] = filter.options[0].id
			})
		}
	},
	methods: {
		selectLocationType() {
			this.selectedLocation = this.filters.locationFilter.options
				.filter(option => option.typeId === this.selectedLocationType)[0]?.id
		},
		validateFilters() {
			this.$refs.filtersForm.validate()
		},
		applyFilters() { 
			this.validateFilters()
			if (this.valid) {
				//TODO
			}
		}
	},
}
</script>

<style lang="scss" scoped>

</style>