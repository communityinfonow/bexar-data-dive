<template>
	<div v-if="exploreData" style="height: 600px;">
		<explore-compare-chart></explore-compare-chart>
	</div>
</template>

<script>
import ExploreCompareChart from '@/app/components/ExploreCompareChart'
import { mapActions, mapState } from 'vuex'
export default {
	name: 'ExploreCompareChartWebComponent',
	components: { ExploreCompareChart },
	props: {
		locale: {
			type: String
		},
		indicatorId: {
			type: String
		},
		locationId: {
			type: String
		},
		locationTypeId: {
			type: String
		},
		year: {
			type: String
		},
		filters: {
			type: String
		}
	},
	data() {
		return {
			
		}
	},
	computed: {
		...mapState(['locationMenu', 'indicatorMenu', 'exploreData']),
	},
	watch: {
		indicatorMenu(newValue) {
			this.setIndicator(newValue.categories.flatMap(c => c.items.concat(c.subcategories.flatMap(s => s.items))).find(i => i.id === this.indicatorId))
			this.setFilterSelections({
				year: this.year,
				location: this.locationId,
				locationType: this.locationTypeId,
				indicatorFilters: { ...this.filters.split(',').map(f => f.split('_')).reduce((acc, [key, value]) => ({ ...acc, [key]: { id: value } }), {}) }
			})
		}
	},
	mounted() {
		this.getLocationMenu()
		this.getIndicatorMenu()
		this.setLocale(this.locale)
	},
	methods: {
		...mapActions(['getLocationMenu', 'getIndicatorMenu', 'setLocale', 'setIndicator', 'setFilterSelections'])
	},
}
</script>

<style lang="scss" scoped>

</style>