<template>
	<div class="fill-height">
		<v-row class="no-gutters flex-wrap flex-column fill-height">
			<v-col cols="auto">
				<explore-tools-panel 
					v-if="filters"
					:draw="drawChart"
					:showLabels="showTrendLabels"
					:setShowLabels="setShowTrendLabels"
				>
				</explore-tools-panel>
			</v-col>
			<v-col cols="auto" class="grow">
				<div
					ref="trend_chart_container" 
					id="trend_chart_container" 
					style="width: 100%; height: 100%;"
				>
				</div>
			</v-col>
		</v-row>
	</div>
</template>

<script>

import { mapActions, mapState } from 'vuex'
import i18n from '@/i18n'
import * as echarts from 'echarts/core';
import { SVGRenderer } from 'echarts/renderers';
import { AriaComponent, LegendComponent, GridComponent } from 'echarts/components';
import { LineChart, BarChart } from 'echarts/charts';
import ExploreToolsPanel from '@/components/ExploreToolsPanel'
import { format } from '@/formatter/formatter'

export default {
	name: 'ExploreTrendChart',
	components: {
		ExploreToolsPanel
	},
	props: {
		tab: {
			type: String,
		},
	},
	data() {
		return {
			chart: null
		}
	},
	computed: {
		...mapState(['locale', 'exploreData', 'filters', 'showTrendLabels']),
	},
	watch: {
		locale() {
			this.drawChart()
		},
		exploreData(newValue) {
			if (newValue) {
				this.drawChart();
			}
		},
		showTrendLabels() {
			this.drawChart();
		}
	},
	mounted () {
		setTimeout(() => { 
			echarts.use([SVGRenderer, AriaComponent, LegendComponent, GridComponent, LineChart, BarChart]);
			this.chart = echarts.init(document.getElementById('trend_chart_container'), null, { renderer: 'svg'});
			this.chart.on('mouseover', (params) => {
				if (params.componentType === 'series') {
					this.setDockedTooltip({
						value: params.data.value,
						noData: params.data.noData,
						suppressed: params.data.suppressed,
						moeLow: params.data.moeLow,
						moeHigh: params.data.moeHigh,
						location: this.exploreData.filters.locationFilter.options[0]['name_' + this.locale],
						year: params.name,
						indicatorFilters: this.exploreData.filters.indicatorFilters
					});
				}
			});
			this.chart.on('mouseout', (params) => {
				if (params.componentType === 'series') {
					this.setDockedTooltip(null);
				}
			});
			window.addEventListener('resize', () => {
				this.chart.resize();
			});
			if (this.exploreData) {
				this.drawChart();
			}
		}, 100);
		
	},
	methods: {
		...mapActions(['setDockedTooltip', 'setShowTrendLabels']),
		drawChart() {
			let textStyle = {
				fontFamily: '"Roboto", sans-serif !important',
				fontSize: '16px'
			};
			let option = {};
			option.yAxis = { 
				type: 'value', 
				splitLine: { show: false },
				splitNumber: 1,
				axisLabel: textStyle
			};
			let trendYears = Array.from(new Set([...this.exploreData.locationData.flatMap(ld => Object.keys(ld.yearData))]));
			trendYears.sort();
			option.xAxis = { 
				type: 'category', 
				data: trendYears,
				axisTick: { show: false },
				axisLabel: textStyle
			};
			option.color = '#3b5a98';
			let yearData = this.exploreData.locationData.find(ld => 
						ld.location.id === this.exploreData.filters.locationFilter.options[0].id && 
						ld.location.typeId === this.exploreData.filters.locationTypeFilter.options[0].id).yearData;
			option.series = [{
				// first series for valid values
				data: trendYears
					.map(ty => {
						let yd = yearData[ty]; 
						return { 
							value: yd?.value, 
							noData: !yd?.value,
							suppressed: yd?.suppressed,
							moeLow: yd?.moeLow, 
							moeHigh: yd?.moeHigh
						}; 
					}),
				type: 'line',
				symbol: 'circle',
				symbolSize: 12,
				label: {
					show: this.showTrendLabels,
					position: 'top',
					formatter: (o) => {
						if (o.data.suppressed) {
							return '';
						} else if (!o.data.value) {
							return '';
						} else if (this.showTrendLabels) {
							let rows = ['{a|' + i18n.t('data.value') +': ' + format(this.exploreData.indicator.typeId, o.data.value) + '}'];
							if (o.data.moeLow || o.data.moeHigh) {
								rows.push('{b|' + i18n.t('data.moe_range') 
									+ ': ' 
									+ format(this.exploreData.indicator.typeId, o.data.moeLow)
									+ " - "
									+ format(this.exploreData.indicator.typeId, o.data.moeHigh) + '}');
							}
							return rows.join('\n');
						} else {
							return '';
						}
					},
					rich: { 
						a: {
							align: 'center',
							fontSize: '16px',
							lineHeight: '20',
							color: '#333333'
						},
						b: {
							align: 'center',
							fontSize: '14px',
							lineHeight: '16',
							color: '#666666'
						}
					}
				}
			},
			{
				// second series for nulls/no data
				data: trendYears
					.map(ty => {
						let yd = yearData[ty];
						if (yd?.value && !yd.suppressed) {
							return null;
						}
						return { 
							value: 0, 
							noData: !yd?.value,
							suppressed: yd?.suppressed,
							moeLow: null, 
							moeHigh: null
						}; 
					}),
				type: 'bar',
				label: {
					show: true,
					position: 'top',
					formatter: (o) => {
						return o.data.suppressed ? i18n.t('data.suppressed') : (!o.data.value ? i18n.t('data.no_data') : o.data.value)
					}
				}
			}];
			option.aria = { enabled: true };

			this.chart.setOption(option);
		}
	}
}
</script>

<style lang="scss" scoped>

</style>