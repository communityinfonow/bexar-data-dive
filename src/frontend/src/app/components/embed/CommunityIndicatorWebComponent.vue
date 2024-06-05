<template>
	<div v-if="item">
		<community-indicator
			:locale="locale"
			:item="item"
			:parentName="parentName"
			:maxDemographics="maxDemographics"
			:filterType="{ id: compareById }"
			:labelsOrLines="labelsOrLines"
		>
		</community-indicator>
	</div>
</template>

<script>
import CommunityIndicator from '@/app/components/CommunityIndicator'
import { mapActions, mapState } from 'vuex'
export default {
	name: 'CommunityIndicatorWebComponent',
	components: { CommunityIndicator },
	props: {
		locale: {
			type: String
		},
		locationId: {
			type: String
		},
		locationTypeId: {
			type: String
		},
		indicatorId: {
			type: String
		},
		compareById: {
			type: String
		},
		labelsOrLines: {
			type: String
		}
	},
	data() {
		return {
			
		}
	},
	computed: {
		...mapState(['locationMenu', 'indicatorMenu', 'community']),
		item() {
			if (this.community?.indicatorData) {
				console.log(this.indicatorId)
				console.log(this.community?.indicatorData
					.flatMap(c => c.indicators.concat(c.subcategories?.flatMap(sc => sc.indicators) || []))
					.map(i => i.indicator?.id))
			}
			return this.community?.indicatorData
				.flatMap(c => c.indicators.concat(c.subcategories?.flatMap(sc => sc.indicators) || []))
				.find(i => i.indicator.id === this.indicatorId)
		},
		parentName() {
			let name = null
			this.community?.indicatorData
				.map(c => c.subcategories)
				.find(sc => {
					console.log(sc)
					if (sc?.flatMap(i => i.id).includes(this.indicatorId)) {
						name = sc.name
					}
				})

			return name;
		},
		maxDemographics() {
			return this.community ? Math.max(...this.community.indicatorData.flatMap(id => id.indicators.concat(id.subcategories.flatMap(sc => sc.indicators))).map(i => i.demographicData.length)) : 0;
		},
	},
	watch: {
		locationMenu(newValue) {
			if (newValue) {
				this.getCommunityData({ community: newValue.categories.find(c => c.id === this.locationTypeId).items.find(l => l.id === this.locationId), filterType: this.compareById })
			}
		}
	},
	mounted() {
		this.getLocationMenu()
		this.getIndicatorMenu()
	},
	methods: {
		...mapActions(['getLocationMenu', 'getIndicatorMenu', 'getCommunityData'])
	},
}
</script>

<style lang="scss" scoped>

</style>