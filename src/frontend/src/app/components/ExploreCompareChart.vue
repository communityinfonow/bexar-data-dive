<template>
	<div class="fill-height">
		<v-row class="no-gutters flex-wrap flex-column fill-height">
			<explore-tools-panel 
				v-if="filters && exploreData"
				:showCompareOptions="true"
				:showLabels="showCompareLabels"
				:setShowLabels="setShowCompareLabels"
				dataVisualElementId="compare_chart_container"
				dataVisualName="compare_chart"
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
import { mapActions, mapState } from 'vuex'
import i18n from '@/i18n'
import * as echarts from 'echarts/core';
import { SVGRenderer } from 'echarts/renderers';
import { AriaComponent, LegendComponent, GridComponent } from 'echarts/components';
import { BarChart } from 'echarts/charts';
import ExploreToolsPanel from '@/app/components/ExploreToolsPanel'
import { format } from '@/formatter/formatter'

export default {
	name: 'ExploreCompareChart',
	components: {
		ExploreToolsPanel
	},
	data() {
		return {
			chart: null
		}
	},
	computed: {
		...mapState(['locale', 'filters', 'exploreData', 'compareSelections', 'showCompareLabels', 'exploreTab']),
		smallScreen() {
			return document.body.clientWidth <= 1440;
		}
	},
	watch: {
		locale() {
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
			echarts.use([SVGRenderer, AriaComponent, LegendComponent, GridComponent, BarChart]);
			this.chart = echarts.init(document.getElementById('compare_chart_container'), null, { renderer: 'svg'});
			this.chart.on('mouseover', (params) => {
				if (params.componentType === 'series') {
					let comparedIndicatorFilters = JSON.parse(JSON.stringify(params.data.indicatorFilters));
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
		...mapActions(['setDockedTooltip', 'setShowCompareLabels']),
		drawChart() {
			if (!this.chart) {
				return;
			}
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
			let xAxisData = [];
			if (this.exploreData.compareData) {
				if (this.compareSelections.type.id === 'l') {
					xAxisData.push(this.exploreData.filters.locationFilter.options[0]['name_' + this.locale]);
				} else if (this.compareSelections.type.id === 'y') {
					xAxisData.push(this.exploreData.filters.yearFilter.options[0]['name_' + this.locale]);
				} else {
					xAxisData.push(this.exploreData.filters.indicatorFilters.find(f => f.type.id === this.compareSelections.type.id).options[0]['name_' + this.locale])
				}
				xAxisData.push(...this.compareSelections.options.map(o => o['name_' + this.locale]))
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
					width: '80', 
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
			let filteredLocation = this.exploreData.locationData.find(ld => 
						ld.location.id === this.exploreData.filters.locationFilter.options[0].id && 
						ld.location.typeId === this.exploreData.filters.locationTypeFilter.options[0].id);
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
			option.series = {
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
						} else if (this.showCompareLabels) {
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
			};
			option.aria = { enabled: true };

			this.chart.setOption(option);
			
		}
	}
}
</script>

<style lang="scss" scoped>

</style>