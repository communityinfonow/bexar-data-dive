<template>
	<div class="location-report">
		<div class="d-flex">
			<span class="text-subtitle-1">{{ reportData.location.properties.locationName }}</span>
			<v-btn icon color="green" class="ml-2" @click="downloadReport">
				<v-icon>mdi-download</v-icon>
			</v-btn>
			<v-spacer></v-spacer>
			<v-btn v-if="layout === 'gallery'" icon @click="closeDialog">
				<v-icon>mdi-close</v-icon>
			</v-btn>
		</div>
		<v-simple-table fixed-header :height="this.reportHeight">
			<thead>
				<tr>
					<th>{{ $t('tools.common.download.headers.indicator') }}</th>
					<th>{{ $t('tools.common.download.headers.year') }}</th>
					<th>{{ $t('data.value') }}</th>
					<th>{{ $t('data.moe') }}</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="(entry, idx) in reportData.data" :key="idx">
					<td>{{ entry['indicatorName_' + locale] }}</td>
					<td>{{ entry.year_ }}</td>
					<td class="text-end">
						<span v-if="entry.indicatorValue && entry.indicatorName_en === 'Median Household Income'">
							{{ '$' + entry.indicatorValue.toLocaleString() }}
						</span>
						<span v-else-if="entry.indicatorValue && entry.indicatorName_en === 'Retail Food Environment Index'">
							{{ entry.indicatorValue.toFixed(1).toLocaleString() }}
						</span>
						<span v-else-if="entry.indicatorValue">
							{{ entry.indicatorValue.toFixed(1).toLocaleString() + '%' }}
						</span>
						<span v-else>
							{{ $t('data.no_data') }}
						</span>
					</td>
					<td class="text--secondary">
						<span v-if="entry.moe && entry.indicatorName_en === 'Median Household Income'">
							&#177; {{ '$' + entry.moe.toLocaleString() }}
						</span>
						<span v-else-if="entry.moe && entry.indicatorName_en === 'Retail Food Environment Index'">
							{{ entry.moe.toFixed(1).toLocaleString() }}
						</span>
						<span v-else-if="entry.moe">
							&#177; {{ entry.moe.toFixed(1).toLocaleString() + '%' }}
						</span>
					</td>
				</tr>
			</tbody>
		</v-simple-table>
	</div>
</template>

<script>
import { mapState } from 'vuex'
import i18n from '@/i18n'
export default {
	name: 'LocationReport',
	props: {
		layout: {
			type: String
		},
		closeDialog: {
			type: Function
		},
	},
	computed: {
		...mapState(['locale', 'reportData']),
		reportHeight() {
			return document.querySelector('#explore_map').offsetHeight - 280 + 'px';
		}
	},
	methods: {
		downloadReport() {
			let fileName = this.reportData.location.properties.locationName + '.csv';
			let csv = [i18n.t('tools.common.download.headers.indicator'), i18n.t('tools.common.download.headers.year'), i18n.t('data.value'), i18n.t('data.moe')].join(',') + '\n'
				+ Object.entries(this.reportData.data).map(i => i[1]).map(i => {
						let value = '';
						let moe = '';
						if (i.indicatorValue && i.indicatorName_en === 'Median Household Income') {
							value = '"$' + i.indicatorValue.toLocaleString() + '"';
						} else if (i.indicatorValue && i.indicatorName_en === 'Retail Food Environment Index') {
							value = '"' + i.indicatorValue.toLocaleString() + '"';
						} else if (i.indicatorValue) {
							value = '"' + i.indicatorValue.toFixed(1).toLocaleString() + '%' + '"';
						}
						if (i.moe && i.indicatorName_en === 'Median Household Income') {
							moe = '"' + '$' + i.moe.toLocaleString() + '"';
						} else if (i.moe && i.indicatorName_en === 'Retail Food Environment Index') {
							moe = '"' + i.moe.toLocaleString() + '"';
						} else if (i.moe) {
							moe = '"' + i.moe.toFixed(1).toLocaleString() + '%' + '"';
						}
						return [i['indicatorName_' + this.locale], i.year_, value, moe].join(',');
					}).join('\n');
			let downloadLink = document.createElement('a');
			downloadLink.download = fileName;
			downloadLink.href = 'data:text/csv;charset=utf-8,' + encodeURIComponent(csv);
			downloadLink.click();
		}
	},
}
</script>

<style lang="scss" scoped>
.v-dialog .location-report {
	padding: 1em;
}
.location-report {
	background-color: white;
}
</style>