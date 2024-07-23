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
import store from '@/app/store/index.js'
import CommunityIndicator from '@/app/components/CommunityIndicator'
import { mapActions, mapState } from 'vuex'
export default {
	name: 'EmbedCommunityIndicator',
	store,
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
			return this.community?.indicatorData
				.flatMap(c => c.indicators.concat(c.subcategories?.flatMap(sc => sc.indicators) || []))
				.find(i => i.indicator.id === this.indicatorId)
		},
		parentName() {
			let name = null
			this.community?.indicatorData
				.map(c => c.subcategories)
				.find(sc => {
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
	mounted() {
		this.getLocationMenu()
		this.getIndicatorMenu()
		console.log('loading data')
		this.getCommunityData({ community: { id: this.locationId, categoryId: this.locationTypeId }, filterType: this.compareById })
	},
	methods: {
		...mapActions(['getLocationMenu', 'getIndicatorMenu', 'getCommunityData'])
	},
}
</script>

<style lang="scss" scoped>

</style>