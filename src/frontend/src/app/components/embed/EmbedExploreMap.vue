<template>
	<div v-if="exploreData && filters" data-app="true" id="app" class="embed v-application v-application--is-ltr theme--light">
		<div class="v-application--wrap">
			<main class="v-main">
				<div class="v-main__wrap">
					<div class="page-header px-8 pt-4 pb-8 white--text">
						<h1 class="text-dive-h3">
							{{ (exploreData.category.parentCategoryId ? exploreData.category['name_' + locale] + ' - ' : '') }}
							{{ exploreData.indicator['name_' + locale] }}
							<indicator-definition v-if="exploreData.indicator['name_' + locale]" :embedded="true" :locale="locale" :indicator="exploreData.indicator"></indicator-definition>
						</h1>
						<h2 class="text-subtitle-1 mb-2">
							{{ 
								exploreData.filters.locationFilter.options[0]['name_' + locale] 
								+ ', ' 
								+ exploreData.source['name_' + locale] 
								+ ' (' + exploreData.filters.yearFilter.options[0].id + ')' 
							}}
						</h2>
						<div>
							
						</div>
						<div>
							{{ filterList }}
						</div>
					</div>
					<div :style="{ 'height': mapHeight }">
						<explore-map embedded :locale="locale" :exploreData="exploreData"  :filterSelections="filterSelections" :showMapLabels="showMapLabels" :highlightFilteredLocation="highlightFilteredLocation"></explore-map>
					</div>
					<div class="pa-6">
						<div class="d-flex justify-space-between">
							<a target="_blank" :href="diveUrl">
								<img :src="domain + '/img/bexar-data-dive-logo.svg'" style="height: 28px" alt="Bexar Data Dive Logo" />
							</a>
							<a target="_blank" href="https://cinow.info">
								<img :src="domain + '/img/cinow_logo.png'" class="ml-4" style="height: 28px;" alt="CINow Logo" />
							</a>
						</div>
						<div class="text-right mt-2">
							{{ $t('app.copyright', locale) }} &copy; {{ new Date().getFullYear() }}&nbsp;<a href="https://cinow.info" class="link--text">{{ $t('app.cinow', locale) }}</a>. {{ $t('app.all_rights_reserved', locale) }}
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>
</template>

<script>
import router from '@/app/router'
import store from '@/app/store/index.js'
import vuetify from '@/plugins/vuetify'
import i18n from '@/i18n'
import axios from 'axios'
import ExploreMap from '@/app/components/ExploreMap'
import IndicatorDefinition from '@/app/components/IndicatorDefinition'
import { mapActions, mapState } from 'vuex'
export default {
	name: 'EmbedExploreMap',
	router,
	store,
	vuetify,
	i18n,
	components: { ExploreMap, IndicatorDefinition },
	props: {
		locale: {
			type: String
		},
		indicatorId: {
			type: String
		},
		locationId: {
			type: String
		},
		locationTypeId: {
			type: String
		},
		year: {
			type: String
		},
		indicatorFilters: {
			type: String
		},
		mapHeight: {
			type: String,
			default: '600px'
		},
		highlightFilteredLocation: {
			type: Boolean,
			default: true
		},
		showMapLabels: {
			type: Boolean,
			default: false
		}
	},
	data() {
		return {
			indicator: null,
			filters: null,
			filterSelections: null,
			exploreData: null
		}
	},
	computed: {
		...mapState(['locationMenu', 'indicatorMenu']),
		domain() {
			return process.env.VUE_APP_DOMAIN
		},
		filterList() {
			let filterDescriptions = []
			this.filters.indicatorFilters.forEach(f => {
				let description = f.type['name_' + this.locale] + ': ';
				if (this.exploreData.filters.indicatorFilters.find(i => i.type.id === f.type.id).options[0] !== null) {
					description += f.options.find(o => o.id === this.exploreData.filters.indicatorFilters.find(i => i.type.id === f.type.id).options[0].id)['name_' + this.locale];
				} else {
					description += f.options[0]['name_' + this.locale];
				}
				filterDescriptions.push(description);
			});
			
			return filterDescriptions.join(', ');
		},
		diveUrl() {
			let url = 'https://dive.cinow.info/explore'
				+ '?lang=' + this.locale
				+ '&tab=map'
				+ '&indicator=' + this.indicatorId
				+ '&locationType=' + this.locationTypeId
				+ '&location=' + this.locationId
				+ '&year=' + this.year
			
			this.indicatorFilters
				.split(',')
				.map(f => f.split('_'))
				.forEach(([key, value]) => url += '&filter_' + key + (value && value !== 'null' ? ('=' + value) : ''))

			return url
		}
	},
	watch: {
		indicatorMenu() {
			if (!this.indicator) {
				this.$nextTick(() => this.init());
			}
		}
	},
	created() {
		axios.defaults.baseURL = process.env.VUE_APP_DOMAIN
	},
	mounted() {
		if (!this.locationMenu) {
			this.getLocationMenu()
		}
		if (!this.indicatorMenu) {
			this.getIndicatorMenu()
		} else {
			this.init();
		}
	},
	methods: {
		...mapActions(['getLocationMenu', 'getIndicatorMenu']),
		init() {
			this.indicator = this.indicatorMenu.categories.flatMap(c => c.items.concat(c.subcategories.flatMap(s => s.items))).find(i => i.id === this.indicatorId)
			let indicatorFiltersParam = {}
			if (this.indicatorFilters) {
				indicatorFiltersParam = { 
					...this.indicatorFilters
						.split(',')
						.map(f => f.split('_'))
						.reduce((acc, [key, value]) => ({ ...acc, [key]: { id: value === 'null' ? null : value } }), {}) 
				}
			}
			this.filterSelections = {
				year: this.year,
				location: this.locationId,
				locationType: this.locationTypeId,
				indicatorFilters: indicatorFiltersParam
			};
			axios.get('/api/filters', { params: {
				indicator: this.indicator.id
			}}).then(response => { 
				this.filters = response.data;
			})
			axios.post('/api/explore-data', {
				indicator: this.indicator.id, 
				filters: this.filterSelections
			}).then(response => {
				this.exploreData = response.data;
			});
		}
	},
}
</script>

<style lang="scss" scoped>
	@import "vuetify/dist/vuetify.min.css";
	@import "leaflet/dist/leaflet.css";
	@import "@/assets/css/app.scss";
	@import "@/assets/css/embed.scss";
</style>