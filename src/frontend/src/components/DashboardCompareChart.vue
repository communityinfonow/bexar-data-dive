<template>
	<div 
		ref="'compare_chart_container'" 
		id="'compare_chart_container'" 
		style="width: 100%; height: 100%;"
	>
	</div>
</template>

<script>
import { mapState } from 'vuex'
//import i18n from '@/i18n'
import * as echarts from 'echarts/core';
import { SVGRenderer } from 'echarts/renderers';
import { AriaComponent, LegendComponent, GridComponent } from 'echarts/components';
import { BarChart } from 'echarts/charts';
//import colorbrewer from 'colorbrewer'
//import { format } from '@/formatter/formatter'

export default {
	name: 'DashboardCompareChart',
	components: {
		
	},
	data() {
		return {
			data: null,
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
		//this.drawChart();
	},
	methods: {
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
			option.xAxis = { 
				//TODO: needs refactored for this chart's data
				type: 'category', 
				data: Array.from(new Set(this.data.map(d => d.raceFilter['name_' + this.locale]))),
				axisTick: { show: false },
				axisLabel: textStyle
			};
		}
	}
}
</script>

<style lang="scss" scoped>

</style>