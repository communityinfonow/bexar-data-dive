<template>
	<div class="my-2">
		<v-card outlined tile class="fill-height">
			<v-card-title>
				{{ $t('tools.dashboard.docked_tooltip.now_viewing')}}
			</v-card-title>
			<v-card-text v-if="!dockedTooltipValue">
				<div class="text-subtitle-1">{{ helpMessage }}</div>
			</v-card-text>
			<v-card-text v-else>
				<div class="text-h6">{{ dashboardData.indicator['name_' + locale] }}: {{ dockedTooltipValue }}</div>
				<ul class="text-body-1">
					<li>Source: {{ dashboardData.source['name_' + locale] }}</li>
					<li>Location: {{ dashboardData.filters.locationFilter.options[0]['name_' + locale] }}</li>
					<li>Year: {{ dashboardData.filters.yearFilter.options[0].id }}</li>
					<li v-for="filter in dashboardData.filters.indicatorFilters" :key="filter.type.id">
						{{ filter.type['name_' + locale] }}: {{ filter.options[0]['name_' + locale] }}
					</li>
				</ul>
			</v-card-text>
		</v-card>
	</div>
</template>

<script>
import { mapState } from 'vuex'

export default {
	name: 'DockedTooltip',
	props: {
		helpMessage: {
			type: String
		},
	},
	computed: {
		...mapState(['locale', 'dashboardData', 'dockedTooltipValue'])
	},
}
</script>

<style lang="scss" scoped>

</style>