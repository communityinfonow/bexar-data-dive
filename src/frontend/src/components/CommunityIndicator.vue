<template>
	<div :id="'community_indicator_' + this.item.indicator.id" class="pl-4" :class="{ 'd-print-none': !item.year }">
		<section class="mb-8">
			<template v-if="item.year">
				<div class="d-flex justify-space-between">
					<div>
						<h3 class="text-h6">
							<span v-if="parentName">{{ parentName }} - </span>
							{{ item.indicator['name_' + locale]}}:
							<span v-if="item.demographicData[0].suppressed" class="text-h6 mb-0">{{ $t('data.suppressed') }}</span>
							<span v-else-if="item.demographicData[0].value === null" class="text-h6 mb-0">{{ $t('data.no_data') }}</span>
							<span v-else class="text-h6 mb-0">{{ formatValue(item.indicatorType.id, item.demographicData[0].value) }}</span>
						</h3>
						<p class="text-subtitle-1 mb-4">
							{{ community.location['name_' + locale]}}, 
							{{ item.source['name_' + locale] }} ({{ item.year }})
						</p>
					</div>
					<download-menu :downloadData="downloadData" :downloadImage="downloadImage"></download-menu>
				</div>
				<v-row>
					<v-col cols="12">
						<community-chart 
							:indicatorId="item.indicator.id" 
							:indicatorType=item.indicatorType.id 
							:data="item.demographicData" 
							:maxDemographics="maxDemographics" 
						>
						</community-chart>
					</v-col>
				</v-row>
			</template>
			<template v-else>
				<h3 class="text-h6">
					{{ item.indicator['name_' + locale]}}
				</h3>
				<p>Sorry, no data is available for this indicator</p>
			</template>
		</section>
	</div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import CommunityChart from '@/components/CommunityChart'
import { format } from '@/formatter/formatter'
import html2canvas from 'html2canvas'
import DownloadMenu from '@/components/DownloadMenu'

export default {
	name: 'CommunityView',
	components: {
		CommunityChart,
		DownloadMenu
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
			console.log('TODO');
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
	
</style>