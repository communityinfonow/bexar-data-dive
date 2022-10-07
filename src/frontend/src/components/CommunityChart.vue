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
import { AriaComponent, LegendComponent, GridComponent } from 'echarts/components';
import { BarChart } from 'echarts/charts';
import { format } from '@/formatter/formatter'

export default {
	name: 'CommunityChart',
	props: {
		indicatorId: {
			type: String
		},
		indicatorType: {
			type: String
		},
		data: {
			type: Array,
		},
		maxDemographics: {
			type: Number
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
		}
	},
	mounted () {
		echarts.use([SVGRenderer, AriaComponent, LegendComponent, GridComponent, BarChart]);
		this.chart = echarts.init(document.getElementById('chart_container_' + this.indicatorId), null, { renderer: 'svg'});
		window.addEventListener('resize', () => {
			this.chart.resize();
		});
		this.drawChart();
	},
	methods: {
		drawChart() {
			let textStyle = {
				fontFamily: '"Roboto", sans-serif !important',
				fontSize: this.smallScreen ? '14px' : '16px'
			};
			let option = {};
			option.grid = { left: 20, right: 20, containLabel: true };
			option.yAxis = { 
				type: 'value', 
				splitLine: { show: false },
				splitNumber: 1,
				axisLabel: textStyle
			};
			let xAxisData = Array.from(new Set(this.data.map(d => '' + (d.raceFilter['name_' + this.locale] || i18n.t('data.all')))));
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
			
			let series = { 
				type: 'bar', 
				cursor: 'default',
				emphasis: {
					disabled: true
				},
				barWidth: '120px',
				label: { 
					show: true, 
					position: 'top',
					formatter: (o) => {
						if (o.data.suppressed) {
							return '{a|' + i18n.t('data.suppressed') + '}';
						} else if (o.data.noData) {
							return '{a|' + i18n.t('data.no_data') + '}';
						}
						let rows = ['{a|' + i18n.t('data.value') +': ' + format(this.indicatorType, o.data.value) + '}'];
						if (o.data.moeLow || o.data.moeHigh) {
							rows.push('{b|' + i18n.t('data.moe_range') 
								+ ': ' 
								+ format(this.indicatorType, o.data.moeLow)
								+ " - "
								+ format(this.indicatorType, o.data.moeHigh) + '}');
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
			};
			
			series.data = this.data
				.map(dataPoint => { 
					return { 
						value: dataPoint.suppressed || dataPoint.value === null ? 0 : dataPoint.value, 
						moeLow: dataPoint.moeLow, 
						moeHigh: dataPoint.moeHigh,
						noData: dataPoint.value === null,
						suppressed: dataPoint.suppressed 
					}; 
				});
			option.series.push(series)
			option.aria = { enabled: true };
			let maxValue = Math.max(...option.series.flatMap(s => s.data).map(d => d.value));
			let rounder = 1;
			for (let i = 1; i < Math.floor(maxValue).toString().length; i++) {
				rounder = rounder * 10;
			}
			let axisMax = Math.ceil(maxValue / rounder) * rounder;
			option.yAxis.interval = axisMax;
			option.yAxis.max = axisMax;
			this.chart.setOption(option);
		}
	},
}
</script>

<style lang="scss" scoped>

</style>