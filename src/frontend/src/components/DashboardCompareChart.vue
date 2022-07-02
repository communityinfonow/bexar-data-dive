<template>
	<div class="fill-height">
		<v-row class="no-gutters flex-wrap flex-column fill-height">
			<v-col cols="auto">
				<dashboard-tools-panel></dashboard-tools-panel>
			</v-col>
			<v-col cols="auto" class="grow">
				<div 
					ref="compare_chart_container" 
					id="compare_chart_container" 
					style="width: 100%; height: 100%;"
				>
				</div>
			</v-col>
		</v-row>
	</div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import * as echarts from 'echarts/core';
import { SVGRenderer } from 'echarts/renderers';
import { AriaComponent, LegendComponent, GridComponent } from 'echarts/components';
import { BarChart } from 'echarts/charts';
import colorbrewer from 'colorbrewer'
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
		...mapState(['locale', 'dashboardData', 'compareSelections']),
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
						value: params.data.value,
						noData: params.data.noData,
						moeLow: params.data.moeLow,
						moeHigh: params.data.moeHigh,
						location: params.data.location,
						year: this.dashboardData.filters.yearFilter.options[0].id,
						indicatorFilters: params.data.indicatorFilters
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
			let xAxisData = [];
			if (this.dashboardData.compareData) {
				xAxisData.push(this.compareSelections.type.name_en === 'Location' 
					? this.dashboardData.filters.locationFilter.options[0]['name_' + this.locale]
					: this.dashboardData.filters.indicatorFilters.find(f => f.type.id === this.compareSelections.type.id).options[0]['name_' + this.locale])
				xAxisData.push(...this.compareSelections.filterOptions.map(o => o['name_' + this.locale]))
			} else {
				xAxisData.push('')
			}
			option.xAxis = { 
				type: 'category', 
				data: xAxisData, //TODO: this will change if a comparison is selected
				axisTick: { show: false },
				axisLabel: textStyle,
				name: this.compareSelections ? '' : this.dashboardData.indicator['name_' + this.locale],
				nameLocation: 'center',
				nameTextStyle: textStyle
			};
			option.color = colorbrewer.Blues[3][2];
			let seriesData = [];
			let filteredLocation = this.dashboardData.locationData.find(ld => 
						ld.location.id === this.dashboardData.filters.locationFilter.options[0].id && 
						ld.location.typeId === this.dashboardData.filters.locationTypeFilter.options[0].id);
			seriesData.push({ 
				value: filteredLocation.yearData[this.dashboardData.filters.yearFilter.options[0].id]?.value || 0,
				noData: filteredLocation.yearData[this.dashboardData.filters.yearFilter.options[0].id],
				moeLow: filteredLocation.yearData[this.dashboardData.filters.yearFilter.options[0].id]?.moeLow, 
				moeHigh: filteredLocation.yearData[this.dashboardData.filters.yearFilter.options[0].id]?.moeHigh,
				location: filteredLocation.location['name_' + this.locale] ,
				indicatorFilters: this.dashboardData.filters.indicatorFilters
			});
			if (this.dashboardData.compareData) {
				seriesData.push(...this.dashboardData.compareData.map((cd, index) => {
					let compareIndicatorFilters = JSON.parse(JSON.stringify(this.dashboardData.filters.indicatorFilters));
					if (this.compareSelections.type.name && this.compareSelections.type.name !== 'Location') {
						compareIndicatorFilters.find(f => f.type.id === this.compareSelections.type.id).options[0] = this.compareSelections.filterOptions[index];
					}
					return  { 
						value: cd.yearData[this.dashboardData.filters.yearFilter.options[0].id]?.value || 0,
						noData: !cd.yearData[this.dashboardData.filters.yearFilter.options[0].id],
						moeLow: cd.yearData[this.dashboardData.filters.yearFilter.options[0].id]?.moeLow,
						moeHigh: cd.yearData[this.dashboardData.filters.yearFilter.options[0].id]?.moeHigh,
						location: cd.location['name_' + this.locale],
						indicatorFilters: compareIndicatorFilters
					};
			 	}))
			}
			option.series = {
				data: seriesData,
				type: 'bar',
				label: {
					show: true,
					position: 'top',
					formatter: (o) => {
						return !o.data.value ? 'No Data' : ''
					}
				}
			};
			option.aria = { enabled: true };

			this.chart.setOption(option);
			
		}
	}
}
</script>

<style lang="scss" scoped>

</style>