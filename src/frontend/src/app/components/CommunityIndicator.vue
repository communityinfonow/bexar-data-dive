<template>
	<div :id="'community_indicator_' + this.item.indicator.id" class="pa-8 mb-8 community-indicator" :class="{ 'd-print-none': !item.year }">
		<section>
			<template v-if="!item.indicator.aggregable && community.location.typeId === '7'">
				<h3 class="text-h6 font-weight-bold">
					<span v-if="parentName">{{ parentName }} - </span>
					{{ item.indicator['name_' + locale]}}
				</h3>
				<p>{{ $t('data.not_aggregable') }}</p>
			</template>
			<template v-else-if="item.indicator.hasData && item.year">
				<div class="d-flex justify-space-between">
					<div>
						<h3 class="text-h6 font-weight-bold d-flex">
							<div>
							<span v-if="parentName">{{ parentName }}<span v-if="item.indicator['name_' + locale]"> - </span></span>
							{{ item.indicator['name_' + locale]}}<span v-if="item.indicator['name_' + locale]">:</span>
							</div>
							<v-chip color="secondary" label class="mx-2" v-if="item.indicator['name_' + locale]">
								<span v-if="item.demographicData[0].suppressed" class="text-h6 mb-0">{{ $t('data.suppressed') }}</span>
								<span v-else-if="item.demographicData[0].value === null" class="text-h6 mb-0">{{ $t('data.no_data') }}</span>
								<template v-else>
									<div class="text-h6 font-weight-bold mb-0">{{ formatValue(item.indicatorType.id, item.demographicData[0].value) }}</div>
									<div v-if="item.demographicData[0].moeLow && item.demographicData[0].moeHigh" class="ml-2">{{ $t('data.moe_range') }} {{ formatValue(item.indicatorType.id, item.demographicData[0].moeLow) }} - {{ formatValue(item.indicatorType.id, item.demographicData[0].moeHigh) }}</div> 
								</template>
							</v-chip>
							<indicator-definition v-if="item.indicator['name_' + locale]" :indicator="item.indicator"></indicator-definition>
						</h3>
						<p class="text-subtitle-1 mb-0">
							{{ community.location['name_' + locale]}}, 
							{{ item.source['name_' + locale] }} ({{ item.year }})
						</p>
						<p v-if="item.indicator['name_' + locale] && item.demographicData.filter(i => i.demographicFilter.id !== null).length" :id="'community_indicator_chart_title_' + item.indicator.id" class="text-subtitle-1 mb-4 blue--text">
								{{ $t('tools.community.percentage_of') }}
								<span class="text-normal font-weight-bold">"{{ (parentName ? parentName + ' - ' : '') + item.indicator['name_' + locale] }}"</span>
								{{ $t('tools.community.who_are') }}
								<span v-if="filterType.id === '1'">{{ $t('tools.community.race_ethnicity') }}</span>
								<span v-if="filterType.id === '2'">{{ $t('tools.community.age_group') }}</span>
								<span v-if="filterType.id === '3'">{{ $t('tools.community.sex') }}</span>
								<span v-if="filterType.id === '4'">{{ $t('tools.community.education_level') }}</span>
								<span v-if="filterType.id === '5'">{{ $t('tools.community.income_level') }}</span>
							</p>
					</div>
					<div>
						<button-menu :downloadData="downloadData" :downloadImage="downloadImage" :viewMenu="true" :indicatorId="item.indicator.id" :linkToExplore="true" :linkToTables="true"></button-menu>
					</div>
				</div>
				<v-row>
					<v-col cols="12">
						<template v-if="item.demographicData.filter(i => i.demographicFilter.id !== null).length">
							<community-chart
								:indicatorId="item.indicator.id" 
								:indicatorType=item.indicatorType
								:indicatorName="(parentName ? parentName + ' - ' : '') + item.indicator['name_' + locale]"
								:data="item.demographicData.filter(i => i.demographicFilter.id !== null)" 
								:maxDemographics="maxDemographics" 
								:filterType="filterType"
								:labelsOrLines="labelsOrLines"
							>
							</community-chart>
						</template>
						<p v-else>{{ $t('tools.community.no_disaggregation')}}</p>
					</v-col>
				</v-row>
			</template>
			<template v-else-if="item.indicator.hasData">
				<h3 class="text-h6 font-weight-bold">
					<span v-if="parentName">{{ parentName }} - </span>
					{{ item.indicator['name_' + locale]}}
				</h3>
				<p>{{ $t('tools.community.no_data_geography') }}</p>
			</template>
			<template v-else>
				<h3 class="text-h6 font-weight-bold">
					<span v-if="parentName">{{ parentName }} - </span>
					{{ item.indicator['name_' + locale]}}
				</h3>
				<p>{{ $t('tools.community.working_add_data') }}</p>
			</template>
		</section>
	</div>
</template>

<script>
import i18n from '@/i18n'
import { mapActions, mapState } from 'vuex'
import CommunityChart from '@/app/components/CommunityChart'
import { format } from '@/services/formatter'
import html2canvas from 'html2canvas'
import ButtonMenu from '@/app/components/ButtonMenu'
import IndicatorDefinition from '@/app/components/IndicatorDefinition'

export default {
	name: 'CommunityIndicator',
	components: {
		CommunityChart,
		IndicatorDefinition,
		ButtonMenu
	},
	props: {
		item: {
			type: Object
		},
		parentName: {
			type: String
		},
		maxDemographics: {
			type: Number
		},
		filterType: {
			type: Object
		},
		labelsOrLines: {
			type: String
		}
	},
	computed: {
		...mapState(['community', 'locale'])
	},
	methods: {
		...mapActions(['setLoading']),
		formatValue(type, value) {
			return format(type, value)
		},
		downloadData() {
			let csv = [
				i18n.t('tools.common.download.headers.indicator'), 
				i18n.t('tools.common.download.headers.source'), 
				i18n.t('tools.common.download.headers.location_id'),
				i18n.t('tools.common.download.headers.location'), 
				i18n.t('tools.common.download.headers.year'),
				i18n.t('tools.common.download.headers.race'),
				i18n.t('tools.common.download.headers.value'),
				i18n.t('tools.common.download.headers.range')
			].join(',');
			csv += this.item.demographicData.map(data => {
				return '\n"' + ((this.parentName ? this.parentName + ' - ' : '') + this.item.indicator['name_' + this.locale])+ '",'
					+ '"' + this.item.source['name_' + this.locale] + '",'
					+ '"' + this.community.location.id + '",'
					+ '"' + this.community.location['name_' + this.locale] + '",'
					+ this.item.year + ','
					+ '"' + (data.demographicFilter['name_' + this.locale] || i18n.t('data.all')) + '",'
					+ (data.suppressed ? i18n.t('data.suppressed') : data.value === null ? i18n.t('data.no_data') : data.value) + ','
					+ (data.moeLow && data.moeHigh ? (data.moeLow + ' - ' + data.moeHigh) : '');
				});
			
			let downloadLink = document.createElement('a');
			downloadLink.download = 'community_data.csv';
			downloadLink.href = 'data:text/csv;charset=utf-8,' + encodeURIComponent(csv);
			downloadLink.click();
		},
		downloadImage() {
			this.setLoading(true);
			// scale is needed to fix a background color quirk with html2canvas
			// so we scale the dom elements up 2x during processing, 
			// then scale them back down to original size in the final canvas
			html2canvas(document.querySelector('header'), { scale: 2 }).then((headerCanvas) => {
				html2canvas(document.querySelector('#community_indicator_' + this.item.indicator.id), { scale: 2 }).then((chartCanvas) => {
					let imageCanvas = document.createElement('canvas');
					imageCanvas.width = headerCanvas.width / 2;
					imageCanvas.height = (headerCanvas.height + chartCanvas.height) / 2;
					imageCanvas.getContext('2d').scale(0.5, 0.5);
					imageCanvas.getContext('2d').fillStyle = 'white';
					imageCanvas.getContext('2d').fillRect(0, 0, imageCanvas.width * 2, imageCanvas.height * 2);
					imageCanvas.getContext('2d').drawImage(headerCanvas, 0, 0);
					imageCanvas.getContext('2d').drawImage(chartCanvas, 0, headerCanvas.height);

					let imageLink = document.createElement('a');
					imageLink.download = 'community_chart.png';
					imageLink.href = imageCanvas.toDataURL('image/png').replace('image/png', 'image/octet-stream');
					imageLink.click();

					this.setLoading(false);
				});
			});
		}
	},
}
</script>

<style lang="scss" scoped>
	.community-indicator {
		background-color: $color-grey;
		border-radius: 2em;
	}
	::v-deep .v-chip .v-chip__content {
		align-items: baseline;
		flex-direction: row;
	}
</style>