<template>
	<div class="fill-height">
		<div
			ref="trend_chart_container" 
			id="trend_chart_container" 
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
import { LineChart } from 'echarts/charts';
import colorbrewer from 'colorbrewer'
import { format } from '@/formatter/formatter'

export default {
	name: 'DashboardTrendChart',
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
		...mapState(['locale', 'dashboardData', 'filters']),
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
			echarts.use([SVGRenderer, AriaComponent, LegendComponent, GridComponent, LineChart]);
			this.chart = echarts.init(document.getElementById('trend_chart_container'), null, { renderer: 'svg'});
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
				type: 'category', 
				data: this.filters.yearFilter.options.map(o => o.id).reverse(), // filter dropdowns are desc, but location year values are asc
				axisTick: { show: false },
				axisLabel: textStyle
			};
			option.color = colorbrewer.Blues[3][2];
			option.series = {
				data: Object.values(this.dashboardData.locationData.find(ld => 
						ld.location.id === this.dashboardData.filters.locationFilter.options[0].id && 
						ld.location.typeId === this.dashboardData.filters.locationTypeFilter.options[0].id).yearData)
					.map(yd => yd.value),
				type: 'line'
			};
			option.aria = { enabled: true };

			this.chart.setOption(option);
		}
	}
}
</script>

<style lang="scss" scoped>

</style>