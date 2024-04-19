<template>
	<v-menu offset-y min-width="300px">
		<template v-slot:activator="{ on }">
			<v-btn icon data-html2canvas-ignore v-on="on" :aria-label="$t('featured_card.view')" :x-small="size === 'x-small'">
				<v-img src="/img/icon_ux_menu_view__active.svg" height="24px" width="24px"></v-img>
			</v-btn>
		</template>
		<v-list>
			<v-list-item v-if="linkToCommunity" :href="communityUrl">
				<v-list-item-title>{{ $t('tools.community.name') }}</v-list-item-title>
			</v-list-item>
			<v-list-item v-if="linkToExplore" :href="exploreUrl">
				<v-list-item-title>{{ $t('tools.explore.name') }}</v-list-item-title>
			</v-list-item>
			<v-list-item v-if="linkToTables" :href="tablesUrl">
				<v-list-item-title >{{ $t('tools.tables.name') }}</v-list-item-title>
			</v-list-item>
		</v-list>
	</v-menu>
</template>

<script>
import { mapState } from 'vuex'
export default {
	name: 'ViewMenu',
	props: {
		indicatorId: {
			type: String,
			required: true
		},
		locationId: {
			type: String,
			required: true
		},
		locationTypeId: {
			type: String,
			required: true
		},
		year: {
			type: String,
			required: false
		},
		raceId: {
			type: String,
			required: false
		},
		ageId: {
			type: String,
			required: false
		},
		sexId: {
			type: String,
			required: false
		},
		educationId: {
			type: String,
			required: false
		},
		incomeId: {
			type: String,
			required: false
		},
		linkToCommunity: {
			type: Boolean,
			default: false
		},
		linkToExplore: {
			type: Boolean,
			default: false
		},
		linkToTables: {
			type: Boolean,
			default: false
		},
		size: {
			type: String,
			default: '24px'
		
		}
	},
	computed: {
		...mapState(['locale']),
		communityUrl() {
			return '/community?lang=' + this.$router.currentRoute.query.lang + '&locationType=' + this.locationTypeId + '&location=' + this.locationId + '&filterType=1'
		},
		exploreUrl() {
			let url = '/explore?lang=' + this.$router.currentRoute.query.lang 
				+ '&locationType=' + this.locationTypeId 
				+ '&location=' + this.locationId 
				+ '&indicator=' + this.indicatorId
			if (this.year) {
				url += '&year=' + this.year
			}
			if (this.raceId) {
				url += '&filter_1=' + this.raceId
			}
			if (this.ageId) {
				url += '&filter_2=' + this.ageId
			}
			if (this.sexId) {
				url += '&filter_3=' + this.sexId
			}
			if (this.educationId) {
				url += '&filter_4=' + this.educationId
			}
			if (this.incomeId) {
				url += '&filter_5=' + this.incomeId
			}

			return url
		},
		tablesUrl() {
			let url = '/tables?lang=' + this.$router.currentRoute.query.lang 
				+ '&indicator=' + this.indicatorId 
				+ '&locations=' + this.locationTypeId + '_' + this.locationId
			if (this.year) {
				url += '&years=' + this.year
			}
			if (this.raceId) {
				url += '&races=' + this.raceId
			}
			if (this.ageId) {
				url += '&ages=' + this.ageId
			}
			if (this.sexId) {
				url += '&sexes=' + this.sexId
			}
			if (this.educationId) {
				url += '&educations=' + this.educationId
			}
			if (this.incomeId) {
				url += '&incomes=' + this.incomeId
			}

			return url
		},
	},
}
</script>

<style lang="scss" scoped>
</style>