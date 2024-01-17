<template>
	<div class="fill-height">
		<v-row class="no-gutters flex-wrap flex-column fill-height">
			<explore-tools-panel 
				v-if="filters && filterSelections && exploreData && layout !== 'gallery'"
				:showCompareOptions="true"
				:labelsOrLinesOption="trendLabelsOrLines"
				:setLabelsOrLinesOption="setTrendLabelsOrLines"
				dataVisualElementId="trend_chart_container"
				dataVisualName="trend_chart"
				:includeLocationFilterInCompareBy="false"
				:includeYearFilterInCompareBy="false"
				:setCompareSelections="setTrendCompareSelections"

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
import { AriaComponent, LegendComponent, GridComponent, TooltipComponent } from 'echarts/components';
import { LineChart, BarChart, CustomChart } from 'echarts/charts';
import ExploreToolsPanel from '@/app/components/ExploreToolsPanel'
import { format } from '@/services/formatter'

export default {
	name: 'ExploreTrendChart',
	components: {
		ExploreToolsPanel
	},
	props: {
		tab: {
			type: String,
		},
		layout: {
			type: String,
			default: 'tabs'
		}
	},
	data() {
		return {
			chart: null,
			seriesColors: ['#3B5A98', '#F05A28', '#F6921E', '#B8237E', '#3AA38F', '#84ACCE', '#E77478', '#663F46', '#C5DCA0'],
		}
	},
	computed: {
		...mapState(['locale', 'exploreData', 'trendCompareSelections', 'trendLabelsOrLines', 'exploreTab', 'indicator', 'filterSelections']),
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
			echarts.use([SVGRenderer, AriaComponent, LegendComponent, GridComponent, LineChart, BarChart, CustomChart, TooltipComponent]);
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
		...mapActions(['setDockedTooltip', 'setTrendLabelsOrLines', 'setTrendCompareSelections']),
		drawChart() {
			if (this.chart) {
				this.chart.dispose();
			}
			this.chart = echarts.init(document.getElementById('trend_chart_container'), null, { renderer: 'svg'});
			this.chart.on('mouseover', (params) => {
				if (params.componentType === 'series') {
					let comparedIndicatorFilters = params.data.indicatorFilters ? JSON.parse(JSON.stringify(params.data.indicatorFilters)) : null;
					this.setDockedTooltip({
						value: params.data.value,
						noData: params.data.noData,
						suppressed: params.data.suppressed,
						moeLow: params.data.moeLow,
						moeHigh: params.data.moeHigh,
						location: this.exploreData.filters.locationFilter.options[0]['name_' + this.locale],
						year: params.name,
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
				fontSize: '16px'
			};
			let option = {};
			option.grid = { left: 40, right: 20, containLabel: true };
			option.yAxis = { 
				type: 'value', 
				splitLine: { show: false },
				splitNumber: 1,
				axisLabel: Object.assign({}, textStyle)
			};
			let trendYears = Array.from(new Set([...this.exploreData.locationData.flatMap(ld => Object.keys(ld.yearData))]));
			trendYears.sort();
			option.xAxis = { 
				type: 'category', 
				data: trendYears,
				axisTick: { show: false },
				axisLabel: Object.assign({}, textStyle)
			};
			let seriesNames = [];
			if (this.exploreData.trendCompareData) {
				seriesNames.push(this.exploreData.filters.indicatorFilters.find(f => f.type.id === this.trendCompareSelections.type.id).options[0]['name_' + this.locale])
				seriesNames.push(...this.trendCompareSelections.options.filter(o => !!o).map(o => o['name_' + this.locale]))
			} else {
				seriesNames.push('')
			}
			//option.color = '#3b5a98';
			let seriesData = [Object.assign({}, this.exploreData.locationData.find(ld => 
						ld.location.id === this.exploreData.filters.locationFilter.options[0].id && 
						ld.location.typeId === this.exploreData.filters.locationTypeFilter.options[0].id)?.yearData)
			];
			seriesData[0].indicatorFiltrers = this.exploreData.filters.indicatorFilters
			if (this.exploreData.trendCompareData) {
				seriesData.push(...this.exploreData.trendCompareData.map((cd, index) => {
					let compareIndicatorFilters = JSON.parse(JSON.stringify(this.exploreData.filters.indicatorFilters));
					if (!isNaN(this.trendCompareSelections.type.id)) {
						compareIndicatorFilters.find(f => f.type.id === this.trendCompareSelections.type.id).options[0] = this.trendCompareSelections.options[index];
					}
					return {
						...cd.yearData,
						indicatorFilters: compareIndicatorFilters	
					}
				}))
			}
			option.series = []
			let allValues = []
			seriesData.forEach((indicatorData, index) => {
				allValues = allValues.concat(Object.values(indicatorData).map(yd => yd.value || 0));
				if (this.trendLabelsOrLines === 'lines') {
					allValues = allValues.concat(...Object.values(indicatorData).map(yd => yd.moeHigh || 0));
					allValues = allValues.concat(...Object.values(indicatorData).map(yd => yd.moeLow || 0));
				}
				option.series.push({
					// first series for valid values
					name: seriesNames[index],
					data: trendYears
						.map(ty => {
							let yd = indicatorData[ty]; 
							return { 
								value: yd?.value, 
								noData: yd?.value === null,
								suppressed: yd?.suppressed,
								moeLow: yd?.moeLow, 
								moeHigh: yd?.moeHigh,
								indicatorFilters: indicatorData.indicatorFilters
							}; 
						}),
					type: 'line',
					cursor: 'default',
					color: this.seriesColors[index],
					emphasis: {
						disabled: true
					},
					symbol: 'circle',
					symbolSize: 12,
					label: index > 0 ? { show: false } : {
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
				})
				option.series.push({
					// second series for nulls/no data
					data: trendYears
						.map(ty => {
							let yd = indicatorData[ty];
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
				})
				if (this.trendLabelsOrLines === 'lines') {
					option.series.push({
						name: seriesNames[index] + '_moe_low',
						data: trendYears
							.map(ty => {
								let yd = indicatorData[ty]; 
								return { 
									value: yd?.moeLow, 
									noData: yd?.value === null,
									suppressed: yd?.suppressed,
									moeLow: yd?.moeLow, 
									moeHigh: yd?.moeHigh
								};
							}),
						type: 'line',
						stack: seriesNames[index] + '_moe',
						lineStyle: { opacity: 0 },
						itemStyle: { opacity: 0 },
						areaStyle: { opacity: 0 }

					});
					option.series.push({
						name: seriesNames[index] + '_moe_high',
						data: trendYears
							.map(ty => {
								let yd = indicatorData[ty]; 
								return { 
									value: yd?.moeHigh - yd?.moeLow, 
									noData: yd?.value === null,
									suppressed: yd?.suppressed,
									moeLow: yd?.moeLow, 
									moeHigh: yd?.moeHigh
								};
							}),
						type: 'line',
						stack: seriesNames[index] + '_moe',
						lineStyle: { opacity: 0 },
						itemStyle: { opacity: 0 },
						areaStyle: { opacity: 0.25 },
						color: this.seriesColors[index]
					});
				}
			})

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
			option.aria = { enabled: true };
			option.yAxis.axisLabel.formatter = (value) => {
				return value === axisMin || value === axisMax || value === 0 ? Number(value).toLocaleString() : '';
			};

			if (this.trendCompareSelections) {
				option.legend = {
					top: 0,
					left: 'center',
					data: seriesNames,
					selectedMode: false
				};
				option.tooltip = {
					trigger: 'axis',
					axisPointer: {
						type: 'shadow'
					},
					textStyle: Object.assign({}, textStyle),
					formatter: (params) => {
						let rows = [params[0].name]
						params.forEach(p => {
							if (p.seriesType === 'line' && p.seriesName.indexOf('_moe_') === -1) {
								let row = ''
								row += '<span>' + p.marker + p.seriesName + ': ' + format(this.exploreData.indicator.typeId, p.value) + '</span>'
								if (p.data.moeLow || p.data.moeHigh) {
									row += '<span class="ml-3 text-subtitle-2 font-weight-light">' + i18n.t('data.moe_range') + ': ' + format(this.exploreData.indicator.typeId, p.data.moeLow)
									row += ' - ' + format(this.exploreData.indicator.typeId, p.data.moeHigh) + '</span>'
								}
								rows.push(row)
							}
						});
						return rows.join('<br/>');
					}
				};
			}

			this.chart.setOption(option);
		}
	}
}
</script>

<style lang="scss" scoped>

</style>