<template>
	<v-menu offset-y min-width="300px">
		<template v-slot:activator="{ on }">
			<v-btn icon data-html2canvas-ignore v-on="on" :aria-label="$t('featured_card.view')" :x-small="size === 'x-small'">
				<v-img src="/img/icon_ux_menu_view__active.svg" height="24px" width="24px"></v-img>
			</v-btn>
		</template>
		<v-list>
			<v-list-item v-if="linkToExplore" :to="exploreUrl">
				<v-list-item-title>{{ $t('tools.explore.name') }}</v-list-item-title>
			</v-list-item>
			<v-list-item v-if="linkToTables" :to="tablesUrl">
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
		exploreUrl() {
			return '/explore?lang=' + this.$router.currentRoute.query.lang + '&locationType=' + this.$router.currentRoute.query.locationType + '&locationId=' + this.$router.currentRoute.query.location + '&indicator=' + this.indicatorId
		},
		tablesUrl() {
			return '/tables?lang=' + this.$router.currentRoute.query.lang + '&indicator=' + this.indicatorId
		},
	},
}
</script>

<style lang="scss" scoped>
</style>