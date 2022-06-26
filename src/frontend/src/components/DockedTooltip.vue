<template>
	<div class="my-2">
		<v-card outlined tile class="fill-height">
			<v-card-title>
				{{ $t('tools.dashboard.docked_tooltip.now_viewing')}}
			</v-card-title>
			<v-card-subtitle v-if="!dockedTooltip">
				{{ helpMessage }}
			</v-card-subtitle>
			<v-card-text v-else>
				<div class="text-h5">{{ dockedTooltip.primaryName }}</div>
				<div class="text-subtitle-1">{{ dockedTooltip.secondaryName }}: {{ dockedTooltip.value }}</div>
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
		...mapState(['dockedTooltip'])
	},
}
</script>

<style lang="scss" scoped>

</style>