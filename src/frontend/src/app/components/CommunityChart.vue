<template>
	<div 
		:ref="'chart_container_' + this.indicatorId" 
		:id="'chart_container_' + this.indicatorId" 
		:style="{ width: '100%', height: '400px' }"
	>
	</div>
</template>

<script>
import { mapState } from 'vuex'
import i18n from '@/i18n'
import * as echarts from 'echarts/core';
import { SVGRenderer } from 'echarts/renderers';
import { AriaComponent, LegendComponent, GridComponent, TitleComponent } from 'echarts/components';
import { BarChart, CustomChart } from 'echarts/charts';
import { format } from '@/formatter/formatter'

export default {
	name: 'CommunityChart',
	props: {
		indicatorId: {
			type: String
		},
		indicatorType: {
			type: Object
		},
		indicatorName: {
			type: String
		},
		data: {
			type: Array,
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
	data() {
		return {
			chart: null
		}
	},
	computed: {
		...mapState(['locale']),
		smallScreen() {
			return document.body.clientWidth <= 1440;
		}
	},
	watch: {
		locale() {
			this.drawChart()
		},
		data() {
			this.drawChart()
		},
		labelsOrLines() {
			this.drawChart()
		}
	},
	mounted () {
		echarts.use([SVGRenderer, AriaComponent, LegendComponent, GridComponent, BarChart, CustomChart, TitleComponent]);
		window.addEventListener('resize', () => {
			this.chart.resize();
		});
		this.drawChart();
	},
	methods: {
		drawChart() {
			if (this.chart) {
				this.chart.dispose();
			}
			this.chart = echarts.init(document.getElementById('chart_container_' + this.indicatorId), null, { renderer: 'svg'});
			let textStyle = {
				fontFamily: '"Roboto", sans-serif !important',
				fontSize: this.smallScreen ? '14px' : '16px'
			};
			let option = {};
			option.grid = { left: 40, right: 20, containLabel: true };
			option.yAxis = { 
				type: 'value', 
				splitLine: { show: false },
				splitNumber: 1,
				axisLabel: textStyle
			};
			let xAxisData = Array.from(new Set(this.data.map(d => '' + (d.demographicFilter['name_' + this.locale] || i18n.t('data.all')))));
			if (xAxisData.length < this.maxDemographics) {
				xAxisData = xAxisData.concat(...Array.from(Array(this.maxDemographics - xAxisData.length))).map(d => d || '')
			}
			option.xAxis = [{ 
				type: 'category', 
				data: xAxisData,
				axisTick: { show: false },
				axisLabel: { ...textStyle, interval: 0, width: '80', overflow: 'break', lineHeight: 20, color: '#333333' }
			}];
			option.textStyle = textStyle;
			option.color = '#3b5a98';
			option.series = [];
			
			let series = [
				{ 
					type: 'bar', 
					cursor: 'default',
					emphasis: {
						disabled: true
					},
					barWidth: '120px',
					label: { 
						show: this.labelsOrLines === 'labels', 
						position: 'top',
						formatter: (o) => {
							if (o.data.suppressed) {
								return '{a|' + i18n.t('data.suppressed') + '}';
							} else if (o.data.noData) {
								return '{a|' + i18n.t('data.no_data') + '}';
							}
							let rows = ['{a|' + i18n.t('data.value') +': ' + format(this.indicatorType.id, o.data.value) + '}'];
							if (o.data.moeLow || o.data.moeHigh) {
								rows.push('{b|' + i18n.t('data.moe_range') 
									+ ': ' 
									+ format(this.indicatorType.id, o.data.moeLow)
									+ " - "
									+ format(this.indicatorType.id, o.data.moeHigh) + '}');
							}
							return rows.join('\n');
						},
						rich: { 
							a: {
								align: 'center',
								fontSize: this.smallScreen ? '12px' : '16px',
								lineHeight: '20',
								color: '#333333'
							},
							b: {
								align: 'center',
								fontSize: this.smallScreen ? '10px' : '14px',
								lineHeight: '16',
								color: '#666666'
							}
						}
					}
				}
			];
			if (this.labelsOrLines === 'lines') {
				series.push(
					{
						type: 'custom',
						name: 'error',
						renderItem: function(params, api) {
							let xValue = api.value(0);
							let highPoint = api.coord([xValue, api.value(1)]) || 0;
							let lowPoint = api.coord([xValue, api.value(2)]) || 0;
							let halfWidth = api.size([1, 0])[0] * 0.1;
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
					}
				);
			}
			
			let seriesData = this.data
				.map(dataPoint => { 
					return { 
						value: dataPoint.suppressed || dataPoint.value === null ? 0 : dataPoint.value, 
						moeLow: dataPoint.moeLow, 
						moeHigh: dataPoint.moeHigh,
						noData: dataPoint.value === null,
						suppressed: dataPoint.suppressed 
					}; 
				});
			series[0].data = seriesData;
			if (this.labelsOrLines === 'lines') {
				series[1].data = seriesData.map((d, i) => [xAxisData[i], d.moeHigh, d.moeLow]);
			}
			option.series = series;
			option.aria = { enabled: true };
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
			this.chart.setOption(option);
		}
	},
}
</script>

<style lang="scss" scoped>

</style>