<template>
	<div 
		:ref="'chart_container_' + this.indicatorId" 
		:id="'chart_container_' + this.indicatorId" 
		style="width: 100%; height: 200px;"
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
import colorbrewer from 'colorbrewer'
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
	},
	data() {
		return {
			chart: null
		}
	},
	computed: {
		...mapState(['locale']),
	},
	watch: {
		locale() {
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
				fontSize: '16px'
			};
			let option = {};
			if (this.data[0].baseFilter) {
				option.legend = { data: this.data.map(d => d.baseFilter['name_' + this.locale])};
			}
			option.yAxis = { 
				type: 'value', 
				splitLine: { show: false },
				splitNumber: 1,
				axisLabel: textStyle
			};
			option.xAxis = { 
				type: 'category', 
				data: Array.from(new Set(this.data.map(d => d.raceFilter['name_' + this.locale]))),
				axisTick: { show: false },
				axisLabel: textStyle
			};
			option.textStyle = textStyle;
			let filters = new Set(this.data.map(d => d.baseFilter?.id || 0))
			option.color = colorbrewer.Blues[Math.max(3, filters.size)].slice(0).reverse()
			option.series = [];
			filters.forEach(filterId => {
				let series = { 
					type: 'bar', 
					cursor: 'default',
					label: { 
						show: true, 
						position: 'top',
						formatter: (o) => {
							let rows = [i18n.t('data.value') +': ' + format(this.indicatorType, o.data.value)];
							if (o.data.moeLow || o.data.moeHigh) {
								rows.push(i18n.t('data.moe_range') 
									+ ': ' 
									+ format(this.indicatorType, o.data.value - o.data.moeLow)
									+ " - "
									+ format(this.indicatorType, o.data.value + o.data.moeHigh))
							}
							return rows.join('\n\n');
						}
					}
				};
				if (filterId !== 0) {
					series.name = this.data.find(d => d.baseFilter.id === filterId).baseFilter['name_' + this.locale]
				}
				series.data = this.data
					.filter(d => (d.baseFilter?.id || 0) === filterId)
					.map(dataPoint => { 
						return { value: dataPoint.value, moeLow: dataPoint.moeLow, moeHigh: dataPoint.moeHigh }; 
					})
				option.series.push(series)
			});
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