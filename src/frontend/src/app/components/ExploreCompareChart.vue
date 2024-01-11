<template>
	<div class="fill-height">
		<v-row class="no-gutters flex-wrap flex-column fill-height">
			<explore-tools-panel 
				v-if="filters && filterSelections && exploreData && layout !== 'dashboard'"
				:showCompareOptions="true"
				:labelsOrLinesOption="compareLabelsOrLines"
				:setLabelsOrLinesOption="setCompareLabelsOrLines"
				dataVisualElementId="compare_chart_container"
				dataVisualName="compare_chart"
				:setCompareSelections="setCompareSelections"
			>
			</explore-tools-panel>
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
import { mapActions, mapState, mapGetters } from 'vuex'
import i18n from '@/i18n'
import * as echarts from 'echarts/core';
import { SVGRenderer } from 'echarts/renderers';
import { AriaComponent, LegendComponent, GridComponent } from 'echarts/components';
import { BarChart, CustomChart } from 'echarts/charts';
import ExploreToolsPanel from '@/app/components/ExploreToolsPanel'
import { format } from '@/services/formatter'

export default {
	name: 'ExploreCompareChart',
	components: {
		ExploreToolsPanel
	},
	props: {
		layout: {
			type: String,
			default: 'tabs'
		}
	},
	data() {
		return {
			chart: null
		}
	},
	computed: {
		...mapState(['locale', 'exploreData', 'compareSelections', 'compareLabelsOrLines', 'exploreTab', 'indicator', 'filterSelections']),
		...mapGetters(['filters']),
		smallScreen() {
			return document.body.clientWidth <= 1440;
		}
	},
	watch: {
		locale() {
			this.drawChart()
		},
		compareLabelsOrLines() {
			this.drawChart()
		},
		exploreTab(newValue) {
			if (newValue === 'compare') {
				window.setTimeout(() => this.chart?.resize(), 100);
			}
		},
		exploreData(newValue) {
			if (newValue && this.chart) {
				this.drawChart();
			}
		},
		showCompareLabels() {
			this.drawChart();
		}
	},
	mounted () {
		setTimeout(() => { 
			echarts.use([SVGRenderer, AriaComponent, LegendComponent, GridComponent, BarChart, CustomChart]);
			this.chart = echarts.init(document.getElementById('compare_chart_container'), null, { renderer: 'svg'});
			window.addEventListener('resize', () => {
				if (this.exploreTab === 'compare') {
					this.chart.resize();
				}
			});
			if (this.exploreData) {
				this.drawChart();
			}
		}, 100);
	},
	methods: {
		...mapActions(['setDockedTooltip', 'setCompareLabelsOrLines', 'setCompareSelections']),
		drawChart() {
			let filteredLocation = this.exploreData.locationData.find(ld => 
					ld.location.id === this.exploreData.filters.locationFilter.options[0].id && 
					ld.location.typeId === this.exploreData.filters.locationTypeFilter.options[0].id);
			if (!this.chart || !filteredLocation) {
				return;
			} else {
				this.chart.dispose();
			}
			this.chart = echarts.init(document.getElementById('compare_chart_container'), null, { renderer: 'svg'});
			this.chart.on('mouseover', (params) => {
				if (params.componentType === 'series') {
					let comparedIndicatorFilters = params.data.indicatorFilters ? JSON.parse(JSON.stringify(params.data.indicatorFilters)) : null;
					this.setDockedTooltip({ 
						value: params.data.value,
						suppressed: params.data.suppressed,
						noData: params.data.noData,
						moeLow: params.data.moeLow,
						moeHigh: params.data.moeHigh,
						location: params.data.location,
						year: this.exploreData.filters.yearFilter.options[0].id,
						indicatorFilters: comparedIndicatorFilters
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
			let xAxisData = [];
			if (this.exploreData.compareData) {
				if (this.compareSelections.type.id === 'i') {
					xAxisData.push(this.indicator['name_' + this.locale]);
				} else if (this.compareSelections.type.id === 'l') {
					xAxisData.push(this.exploreData.filters.locationFilter.options[0]['name_' + this.locale]);
				} else if (this.compareSelections.type.id === 'y') {
					xAxisData.push(this.exploreData.filters.yearFilter.options[0]['name_' + this.locale]);
				} else if (this.exploreData.filters.indicatorFilters.find(f => f.type.id === this.compareSelections.type.id).options[0]) {
					xAxisData.push(this.exploreData.filters.indicatorFilters.find(f => f.type.id === this.compareSelections.type.id).options[0]['name_' + this.locale])
				}
				xAxisData.push(...this.compareSelections.options.filter(o => !!o).map(o => o['name_' + this.locale]))
			} else {
				xAxisData.push('')
			}
			option.xAxis = { 
				type: 'category', 
				data: xAxisData,
				axisTick: { show: false },
				axisLabel: { 
					...textStyle, 
					interval: 0, 
					width: '100', 
					overflow: 'break', 
					lineHeight: 16, 
					rotate: this.smallScreen ? 45 : 0,
					margin: this.smallScreen ? 20 : 10
				},
				name: this.compareSelections 
					? '' 
					: (this.exploreData.category.parentCategoryId ? this.exploreData.category['name_' + this.locale] + ' - ' : '') + this.exploreData.indicator['name_' + this.locale],
				nameLocation: 'center',
				nameTextStyle: textStyle
			};
			option.color = '#3b5a98';
			let seriesData = [];
			seriesData.push({ 
				value: filteredLocation.yearData[this.exploreData.filters.yearFilter.options[0].id]?.value || 0,
				suppressed: filteredLocation.yearData[this.exploreData.filters.yearFilter.options[0].id]?.suppressed,
				noData: !filteredLocation.yearData[this.exploreData.filters.yearFilter.options[0].id] || filteredLocation.yearData[this.exploreData.filters.yearFilter.options[0].id]?.value === null,
				moeLow: filteredLocation.yearData[this.exploreData.filters.yearFilter.options[0].id]?.moeLow, 
				moeHigh: filteredLocation.yearData[this.exploreData.filters.yearFilter.options[0].id]?.moeHigh,
				location: filteredLocation.location['name_' + this.locale] ,
				indicatorFilters: this.exploreData.filters.indicatorFilters
			});
			if (this.exploreData.compareData) {
				seriesData.push(...this.exploreData.compareData.map((cd, index) => {
					let compareIndicatorFilters = JSON.parse(JSON.stringify(this.exploreData.filters.indicatorFilters));
					if (!isNaN(this.compareSelections.type.id)) {
						compareIndicatorFilters.find(f => f.type.id === this.compareSelections.type.id).options[0] = this.compareSelections.options[index];
					}
					let year = this.compareSelections.type.id === 'y' ? this.compareSelections.options[index].id : this.exploreData.filters.yearFilter.options[0].id;
					return  { 
						value: cd.yearData[year]?.value || 0,
						suppressed: cd.yearData[year]?.suppressed,
						noData: !cd.yearData[year] || cd.yearData[year]?.value === null,
						moeLow: cd.yearData[year]?.moeLow,
						moeHigh: cd.yearData[year]?.moeHigh,
						location: cd.location['name_' + this.locale],
						indicatorFilters: compareIndicatorFilters
					};
			 	}))
			}
			option.series = [];
			if (this.compareLabelsOrLines === 'lines') {
				// animation delays are not working for custom series, so add it empty and populate it after the chart is drawn
				option.series.push({
					type: 'custom',
					name: 'error',
					renderItem: function() {
						return {
							type: 'group',
						}
					}
				});
			}
			option.series.push({
				data: seriesData,
				type: 'bar',
				cursor: 'default',
				emphasis: {
					disabled: true
				},
				label: { 
					show: true, 
					position: 'top',
					formatter: (o) => {
						if (o.data.suppressed) {
							return '{a|' + i18n.t('data.suppressed') + '}';
						} else if (o.data.noData) {
							return '{a|' + i18n.t('data.no_data') + '}';
						} else if (this.compareLabelsOrLines === 'labels') {
							let rows = ['{a|' + i18n.t('data.value') +': ' + format(this.exploreData.indicator.typeId, o.data.value) + '}'];
							if (o.data.moeLow || o.data.moeHigh) {
								rows.push('{b|' + (this.smallScreen ? '' : (i18n.t('data.moe_range') + ': ') )
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
			});
			let allValues = seriesData.map(d => d.value);
			if (this.compareLabelsOrLines === 'lines') {
				allValues = allValues.concat(...seriesData.map(d => d.moeHigh || 0));
				allValues = allValues.concat(...seriesData.map(d => d.moeLow || 0));
			}
			let maxValue = Math.max(...allValues);
			let minValue = Math.min(0, ...allValues);
			let rounder = 1;
			for (let i = 1; i < Math.floor(maxValue).toString().length; i++) {
				rounder = rounder * 10;
			}
			let axisMax = Math.ceil(maxValue / rounder) * rounder;
			let axisMin = Math.floor(minValue / rounder) * rounder;
			option.yAxis.max = axisMax;
			option.yAxis.min = axisMin;
			option.yAxis.axisLabel.formatter = (value) => {
				return value === axisMin || value === axisMax || value === 0 ? value : ''	;
			};
			option.aria = { enabled: true };

			this.chart.setOption(option);
			if (this.compareLabelsOrLines === 'lines') {
				window.setTimeout(() => {
					let errorSeriesData = seriesData.map((d, i) => [xAxisData[i], d.moeHigh, d.moeLow]);
					this.chart.setOption({
						series: [
							{
								name: 'error',
								data: errorSeriesData,
								renderItem: function(params, api) {
									let xValue = api.value(0);
									let highPoint = api.coord([xValue, api.value(1)]) || 0;
									let lowPoint = api.coord([xValue, api.value(2)]) || 0;
									let halfWidth = api.size([1, 0])[0] * 0.1;
									let style = {
										stroke: '#b8237e',
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
						]
					});
				}, 1000);
			}
		}
	}
}
</script>

<style lang="scss" scoped>

</style>