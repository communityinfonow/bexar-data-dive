<template>
	<div class="my-2">
		<v-card outlined tile class="fill-height docked-tooltip">
			<v-card-title class="docked-tooltip-title white--text">
				{{ $t('tools.explore.docked_tooltip.now_viewing')}}
				<span v-if="activeTab === 0 && dockedTooltip">: {{ dockedTooltip.location }}</span>
				<span v-else-if="activeTab !== 0">: {{ filteredLocation }}</span>
			</v-card-title>
			<v-card-text v-if="!dockedTooltip">
				<div class="text-subtitle-1 pt-2">{{ helpMessage }}</div>
			</v-card-text>
			<v-card-text v-else>
				<div class="text-h6 pt-2">
					<span v-if="exploreData.category.parentCategoryId">{{ exploreData.category['name_' + locale] }} - </span>
					{{ exploreData.indicator['name_' + locale] }}: {{ valueFormatted }}
				</div>
				<ul class="text-body-1">
					<li v-if="!dockedTooltip.noData">Range: {{  rangeFormatted }}</li>
					<li>Source: {{ exploreData.source['name_' + locale] }}</li>
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
		activeTab: {
			type: Number
		}
	},
	computed: {
		...mapState(['locale', 'exploreData', 'dockedTooltip']),
		filteredLocation() {
			return this.exploreData?.filters.locationFilter.options[0]['name_' + this.locale]
		},
		valueFormatted() {
			if (this.dockedTooltip.suppressed) {
				return i18n.t('data.suppressed');
			} else if (this.dockedTooltip.noData) {
				return i18n.t('data.no_data');
			}

			return format(this.exploreData.indicator.typeId, this.dockedTooltip.value);
		},
		rangeFormatted() {
			return format(this.exploreData.indicator.typeId, this.dockedTooltip.moeLow)
				+ " - "
				+ format(this.exploreData.indicator.typeId, this.dockedTooltip.moeHigh);
		}
	},
}
</script>

<style lang="scss" scoped>
	.docked-tooltip {
		border: 1px solid rgba($color-secondary, 1);
	}
	.docked-tooltip-title {
		color: white;
		background: rgba($color-secondary, 1);
		
	}
</style>