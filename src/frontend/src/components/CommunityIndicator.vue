<template>
	<div>
	<h3 class="text-h6">{{ item.indicator['name_' + locale]}}</h3>
	<section class="mb-8">
	<template v-if="item.year">
		<p class="text-subtitle-1 mb-4">{{ item.source['name_' + locale] }} ({{ item.year }})</p>
		<v-row>
		<v-col cols="3">
			<p v-if="item.demographicData[0].suppressed" class="text-h4 mb-0">{{ $t('data.suppressed') }}</p>
			<p v-else-if="item.demographicData[0].value === null" class="text-h4 mb-0">{{ $t('data.no_data') }}</p>
			<p v-else class="text-h4 mb-0">{{ formatValue(item.indicatorType.id, item.demographicData[0].value) }}</p>
			<p class="text-subtitle-1" v-if="item.indicator.baseFilterTypeId">{{ item.demographicData[0].baseFilter['name_' + locale] }}</p>
		</v-col>
		<v-col cols="9">
			<community-chart :indicatorId="item.indicator.id" :indicatorType=item.indicatorType.id :data="item.demographicData"></community-chart>
		</v-col>
		</v-row>
	</template>
	<template v-else>
		<p>Sorry, no data is available for this indicator</p>
	</template>
	</section>
	</div>
</template>

<script>
import { mapState } from 'vuex'
import CommunityChart from '@/components/CommunityChart'
import { format } from '@/formatter/formatter'

export default {
	name: 'CommunityView',
	components: {
		CommunityChart
	},
	props: {
		item: {
			type: Object
		},
	},
	computed: {
		...mapState(['locale'])
	},
	methods: {
		formatValue(type, value) {
			return format(type, value)
		}
	},
}
</script>

<style lang="scss" scoped>

</style>