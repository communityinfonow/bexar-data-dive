<template>
	<div :ref="'chart_container_' + this.indicatorId" style="width: 100%; height: 200px;">

	</div>
</template>

<script>
import { mapState } from 'vuex'
import * as echarts from 'echarts/core';
import { SVGRenderer } from 'echarts/renderers';
import { LegendComponent, GridComponent } from 'echarts/components';
import { BarChart } from 'echarts/charts';
import colorbrewer from 'colorbrewer'
import { format } from '@/formatter/formatter'

export default {
	name: 'MyCommunityView',
	props: {
		indicatorId: {
			type: Number
		},
		indicatorType: {
			type: String
		},
		data: {
			type: Array,
		},
	},
	computed: {
    ...mapState(['locale']),
  },
	mounted () {
		echarts.use([SVGRenderer, LegendComponent, GridComponent, BarChart]);
		let chart = echarts.init(this.$refs['chart_container_' + this.indicatorId], null, { renderer: 'svg'});
		let option = {};
		if (this.data[0].baseFilter) {
			option.legend = { data: this.data.map(d => d.baseFilter['name_' + this.locale])};
		}
		option.yAxis = { 
			type: 'value', 
			splitLine: { show: false }
		};
		option.xAxis = { 
			type: 'category', 
			data: Array.from(new Set(this.data.map(d => d.raceFilter['name_' + this.locale]))),
			axisTick: { show: false }
		};
		let filters = new Set(this.data.map(d => d.baseFilter?.id || 0))
		option.color = colorbrewer.Blues[Math.max(3, filters.size)].slice(0).reverse()
		option.series = [];
		filters.forEach(filterId => {
			let series = { 
				type: 'bar', 
				label: { 
					show: true, 
					position: 'top',
					formatter: (o) => {
						let rows = ['Value: ' + format(this.indicatorType, o.data.value)];
						if (o.data.moeLow || o.data.moeHigh) {
							if (o.data.moeLow === o.data.moeHigh) {
								rows.push('MOE: ' + format(this.indicatorType, o.data.moeLow))
							} else {
								rows.push('MOE (high): ' + format(this.indicatorType, o.data.moeHigh));
								rows.push('MOE (high): ' + format(this.indicatorType, o.data.moeLow));
							}
						}
						return rows.join('\n');
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
		chart.setOption(option);
	},
}
</script>

<style lang="scss" scoped>

</style>