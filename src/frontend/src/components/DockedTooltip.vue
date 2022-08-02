<template>
	<div class="my-2">
		<v-card outlined tile class="fill-height">
			<v-card-title>
				{{ $t('tools.explore.docked_tooltip.now_viewing')}}
			</v-card-title>
			<v-card-text v-if="!dockedTooltip">
				<div class="text-subtitle-1">{{ helpMessage }}</div>
			</v-card-text>
			<v-card-text v-else>
				<div class="text-h6">
					<span v-if="exploreData.category.parentCategoryId">{{ exploreData.category['name_' + locale] }} - </span>
					{{ exploreData.indicator['name_' + locale] }}: {{ valueFormatted }}
				</div>
				<ul class="text-body-1">
					<li v-if="!dockedTooltip.noData">Range: {{  rangeFormatted }}</li>
					<li>Source: {{ exploreData.source['name_' + locale] }}</li>
					<li>Location: {{ dockedTooltip.location }}</li>
					<li>Year: {{ dockedTooltip.year }}</li>
					<li v-for="filter in dockedTooltip.indicatorFilters" :key="filter.type.id">
						{{ filter.type['name_' + locale] }}: {{ filter.options[0]['name_' + locale] }}
					</li>
				</ul>
			</v-card-text>
		</v-card>
	</div>
</template>

<script>
import { mapState } from 'vuex'
import i18n from '@/i18n'
import { format } from '@/formatter/formatter'

export default {
	name: 'DockedTooltip',
	props: {
		helpMessage: {
			type: String
		},
	},
	computed: {
		...mapState(['locale', 'exploreData', 'dockedTooltip']),
		valueFormatted() {
			if (this.dockedTooltip.suppressed) {
				return i18n.t('data.suppressed');
			} else if (this.dockedTooltip.noData) {
				return i18n.t('data.no_data');
			}

			return format(this.exploreData.indicator.typeId, this.dockedTooltip.value);
		},
		rangeFormatted() {
			return format(this.exploreData.indicator.typeId, this.dockedTooltip.value - this.dockedTooltip.moeLow)
				+ " - "
				+ format(this.exploreData.indicator.typeId, this.dockedTooltip.value + this.dockedTooltip.moeHigh);
		}
	},
}
</script>

<style lang="scss" scoped>

</style>