<template>
	<div class="fill-height">
		<dashboard-tools-panel></dashboard-tools-panel>
		<div 
			ref="compare_chart_container" 
			id="compare_chart_container" 
			style="width: 100%; height: 100%;"
		>
		</div>
	</div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import * as echarts from 'echarts/core';
import { SVGRenderer } from 'echarts/renderers';
import { AriaComponent, LegendComponent, GridComponent } from 'echarts/components';
import { BarChart } from 'echarts/charts';
import { format } from '@/formatter/formatter'
import DashboardToolsPanel from '@/components/DashboardToolsPanel'

export default {
	name: 'DashboardCompareChart',
	components: {
		DashboardToolsPanel
	},
	data() {
		return {
			chart: null
		}
	},
	computed: {
		...mapState(['locale', 'dashboardData']),
	},
	watch: {
		locale() {
			this.drawChart()
		},
		dashboardData(newValue) {
			if (newValue) {
				this.drawChart();
			}
		}
	},
	mounted () {
		setTimeout(() => { 
			echarts.use([SVGRenderer, AriaComponent, LegendComponent, GridComponent, BarChart]);
			this.chart = echarts.init(document.getElementById('compare_chart_container'), null, { renderer: 'svg'});
			this.chart.on('mouseover', (params) => {
				if (params.componentType === 'series') {
					this.setDockedTooltip({
						primaryName: params.name,
						secondaryName: this.dashboardData.indicator['name_' + this.locale],
						value: format(this.dashboardData.indicator.typeId, params.value)
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
			if (this.dashboardData) {
				this.drawChart();
			}
		}, 100);
	},
	methods: {
		...mapActions(['setDockedTooltip']),
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
				//data: Array.from(new Set(this.data.map(d => d.raceFilter['name_' + this.locale]))),
				data: [this.dashboardData.indicator['name_' + this.locale]],
				axisTick: { show: false },
				axisLabel: textStyle
			};
			option.series = {
				data: [this.dashboardData.locationData.find(ld => 
						ld.location.id === this.dashboardData.filters.locationFilter.options[0].id && 
						ld.location.typeId === this.dashboardData.filters.locationTypeFilter.options[0].id)
					.yearData[this.dashboardData.filters.yearFilter.options[0].id].value],
				type: 'bar'
			};
			option.aria = { enabled: true };

			this.chart.setOption(option);
			
		}
	}
}
</script>

<style lang="scss" scoped>

</style>