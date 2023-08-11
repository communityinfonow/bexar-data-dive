<template>
	<div class="fill-height">
		<v-row class="no-gutters flex-wrap flex-column fill-height">
			<explore-tools-panel 
				v-if="filters"
				:labelsOrLinesOption="trendLabelsOrLines"
				:setLabelsOrLinesOption="setTrendLabelsOrLines"
				dataVisualElementId="trend_chart_container"
				dataVisualName="trend_chart"
			>
			</explore-tools-panel>
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

import { mapActions, mapState, mapGetters } from 'vuex'
import i18n from '@/i18n'
import * as echarts from 'echarts/core';
import { SVGRenderer } from 'echarts/renderers';
import { AriaComponent, LegendComponent, GridComponent } from 'echarts/components';
import { LineChart, BarChart, CustomChart } from 'echarts/charts';
import ExploreToolsPanel from '@/app/components/ExploreToolsPanel'
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
		...mapState(['locale', 'exploreData', 'trendLabelsOrLines', 'exploreTab']),
		...mapGetters(['filters']),
	},
	watch: {
		locale() {
			this.drawChart()
		},
		trendLabelsOrLines() {
			this.drawChart()
		},
		exploreTab(newValue) {
			if (newValue === 'trend') {
				window.setTimeout(() => this.chart?.resize(), 100);
			}
		},
		exploreData(newValue) {
			if (newValue && this.chart) {
				this.drawChart();
			}
		},
		showTrendLabels() {
			this.drawChart();
		}
	},
	mounted () {
		setTimeout(() => { 
			echarts.use([SVGRenderer, AriaComponent, LegendComponent, GridComponent, LineChart, BarChart, CustomChart]);
			this.chart = echarts.init(document.getElementById('trend_chart_container'), null, { renderer: 'svg'});
			window.addEventListener('resize', () => {
				if (this.exploreTab === 'trend') {
					this.chart.resize();
				}
			});
			if (this.exploreData) {
				this.drawChart();
			}
		}, 100);
		
	},
	methods: {
		...mapActions(['setDockedTooltip', 'setTrendLabelsOrLines']),
		drawChart() {
			if (this.chart) {
				this.chart.dispose();
			}
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
			let textStyle = {
				fontFamily: '"Roboto", sans-serif !important',
				fontSize: '16px'
			};
			let option = {};
			option.grid = { left: 40, right: 20, containLabel: true };
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
							noData: yd?.value === null,
							suppressed: yd?.suppressed,
							moeLow: yd?.moeLow, 
							moeHigh: yd?.moeHigh
						}; 
					}),
				type: 'line',
				cursor: 'default',
				emphasis: {
					disabled: true
				},
				symbol: 'circle',
				symbolSize: 12,
				label: {
					show: this.trendLabelsOrLines === 'labels',
					position: 'top',
					formatter: (o) => {
						if (o.data.suppressed) {
							return '';
						} else if (o.data.noData) {
							return '';
						} else if (this.trendLabelsOrLines === 'labels') {
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
						if (yd?.value !== null && !yd?.suppressed) {
							return null;
						}
						return { 
							value: 0, 
							noData: yd?.value === null,
							suppressed: yd?.suppressed,
							moeLow: null, 
							moeHigh: null
						}; 
					}),
				type: 'bar',
				cursor: 'default',
				label: {
					show: true,
					position: 'top',
					formatter: (o) => {
						return o.data.suppressed ? i18n.t('data.suppressed') : (!o.data.value ? i18n.t('data.no_data') : o.data.value)
					}
				}
			}];
			if (this.trendLabelsOrLines === 'lines') {
				option.series.push({
					// third series for moe lines
					data: trendYears
						.map(ty => {
							let yd = yearData[ty]; 
							return [ty, yd?.moeLow, yd?.moeHigh]; 
						}),
					type: 'custom',
					name: 'error',
					renderItem: function(params, api) {
						let xValue = api.value(0);
						let highPoint = api.coord([xValue, api.value(1)]) || 0;
						let lowPoint = api.coord([xValue, api.value(2)]) || 0;
						console.log(api.value(0) + ' (' + api.value(1) + '-' + api.value(2) + ')')
						let halfWidth = api.size([1, 0])[0] * 0.05;
						let style = {
							stroke: '#3aa38f',
							fill: null,
							lineWidth: 2
						};
						return {
							type: 'group',
							children: [
								{
									type: 'line',
									transition: ['shape'],
									shape: {
										x1: highPoint[0] - halfWidth,
										y1: highPoint[1],
										x2: highPoint[0] + halfWidth,
										y2: highPoint[1]
									},
									style: style
								},
								{
									type: 'line',
									transition: ['shape'],
									shape: {
										x1: highPoint[0],
										y1: highPoint[1],
										x2: lowPoint[0],
										y2: lowPoint[1]
									},
									style: style
								},
								{
									type: 'line',
									transition: ['shape'],
									shape: {
										x1: lowPoint[0] - halfWidth,
										y1: lowPoint[1],
										x2: lowPoint[0] + halfWidth,
										y2: lowPoint[1]
									},
									style: style
								}
							]
						}
					},
					encode: {
						x: 0,
						y: [1, 2]
					},
					z: 100
				});
			}
			let allValues = option.series[0].data.map(d => d.value);
			if (this.labelsOrLines === 'lines') {
				allValues = allValues.concat(...option.series[0].data.map(d => d.moeHigh || 0));
				allValues = allValues.concat(...option.series[0].data.map(d => d.moeLow || 0));
			}
			let maxValue = Math.max(...allValues);
			let minValue = Math.min(0, ...allValues);
			let rounder = 1;
			for (let i = 1; i < Math.floor(maxValue).toString().length; i++) {
				rounder = rounder * 10;
			}
			let axisMax = Math.ceil(maxValue / rounder) * rounder;
			let axisMin = Math.floor(minValue / rounder) * rounder;
			option.yAxis.interval = axisMax;
			option.yAxis.max = axisMax;
			option.yAxis.min = axisMin;
			option.aria = { enabled: true };

			this.chart.setOption(option);
		}
	}
}
</script>

<style lang="scss" scoped>

</style>