import Vue from 'vue'
import Vuex from 'vuex'
import router from '@/router/index'
import i18n from '@/i18n'
import axios from 'axios'
import { format } from '@/formatter/formatter'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loading: false,
    locale: '',
    communityRoute: 'community',
    exploreRoute: 'explore',
    tablesRoute: 'tables',
    locationMenu: null,
    indicatorMenu: null,
    featuredIndicators: null,
    community: null,
    indicator: null,
    source: null,
    filters: null,
    dockedTooltip: null,
    showMapLabels: false,
    showTrendLabels: false,
    showCompareLabels: false,
    exploreData: null,
    exploreTab: null,
    filterSelections: null,
    compareSelections: null,
    tablesData: null,
    aboutData: null
  },
  getters: {
    tools: (state) => {
      return [
        {
          key: 'community',
          name: i18n.t('tools.community.name'),
          imagePath: "/img/tools_community.png",
          route: state.communityRoute,
          icon: 'mdi-map',
          shortDescription: i18n.t('tools.community.short_description'),
          fullDescription: i18n.t('tools.community.long_description'),
        },
        {
          key: 'explore',
          name: i18n.t('tools.explore.name'),
          imagePath: "/img/tools_explore.png",
          route: state.exploreRoute,
          icon: 'mdi-view-dashboard',
          shortDescription: i18n.t('tools.explore.short_description'),
          fullDescription: i18n.t('tools.explore.long_description'),
          subTools: [
            {
              name: i18n.t('tools.explore.tabs.map'),
              fullDescription: "The map pulls data from multiple local, state and national data sources at the county, ZIP code, SSA (Statistical Small Area), and census tract level to display an indicators geographic distribution."
            },
            {
              name: i18n.t('tools.explore.tabs.trend'),
              fullDescription: "The trend chart pulls data from multiple local, state and national data sources at the county, ZIP code, SSA (Statistical Small Area), and census tract level to display an indicator, or multiple indicators, across time."
            },
            {
              name: i18n.t('tools.explore.tabs.compare'),
              fullDescription: "The comparison chart pulls data from multiple local, state and national data sources at the county, ZIP code, SSA (Statistical Small Area), and census tract level to display an indicator, or multiple indicators, across categories."
            }
          ]
        },
        {
          key: 'tables',
          name: i18n.t('tools.tables.name'),
          imagePath: "/img/tools_tables.png",
          route: state.tablesRoute,
          icon: 'mdi-grid',
          shortDescription: i18n.t('tools.tables.short_description'),
          fullDescription: i18n.t('tools.tables.long_description'),
        },
      ]
    },
    about_views: () => {
      return [
        {
          name: i18n.t('about_tools_view.name'),
          route: 'about-tools',
          icon: 'mdi-information'
        },
        {
          name: i18n.t('about_data_view.name'),
          route: 'about-data',
          icon: 'mdi-database'
        }
      ]
    }
  },
  mutations: {
    SET_LOADING(state, loading) {
      state.loading = loading
    },
    SET_LOCALE(state, locale) {
      state.locale = locale
    },
    SET_INDICATOR_MENU(state, indicatorMenu) {
      state.indicatorMenu = indicatorMenu
    },
    SET_LOCATION_MENU(state, locationMenu) {
      state.locationMenu = locationMenu
    },
    SET_FEATURED_INDICATORS(state, indicators) {
      state.featuredIndicators = indicators
    },
    SET_COMMUNITY(state, community) {
      state.community = community
    },
    SET_INDICATOR(state, indicator) {
      state.indicator = indicator
    },
    SET_SOURCE(state, source) {
      state.source = source
    },
    SET_FILTERS(state, filters) {
      state.filters = filters
    },
    SET_EXPLORE_DATA(state, exploreData) {
      state.exploreData = exploreData
    },
    SET_DOCKED_TOOLTIP(state, value) {
      state.dockedTooltip = value
    },
    SET_EXPLORE_TAB(state, exploreTab) {
      state.exploreTab = exploreTab
    },
    SET_FILTER_SELECTIONS(state, selections) { 
      state.filterSelections = selections
    },
    SET_COMPARE_SELECTIONS(state, selections) {
      state.compareSelections = selections
    },
    SET_TABLES_DATA(state, tablesData) {
      state.tablesData = tablesData
    },
    SET_ABOUT_DATA(state, data) {
      state.aboutData = data;
    },
    SET_TOOL_ROUTE(state, params) {
      state[params.key + 'Route'] = params.route
    },
    SET_SHOW_MAP_LABELS(state, showLabels) {
      state.showMapLabels = showLabels
    },
    SET_SHOW_TREND_LABELS(state, showLabels) {
      state.showTrendLabels = showLabels
    },
    SET_SHOW_COMPARE_LABELS(state, showLabels) {
      state.showCompareLabels = showLabels
    }
  },
  actions: {
    setLoading(context, loading) {
      context.commit('SET_LOADING', loading)
    },
    setLocale(context, locale) {
      if (locale !== this.state.locale) {
        context.commit('SET_LOCALE', locale)
        localStorage.setItem('cinow-locale', locale)
        i18n.locale = locale
        document.title = i18n.t('app.name')
        if (router.currentRoute.query.lang !== locale) {
          router.replace({
            query: {
              ...router.currentRoute.query,
              lang: locale,
            },
          })
        }
        context.dispatch('getIndicatorMenu')
        context.dispatch('getLocationMenu')
      }
    },
    getIndicatorMenu(context) {
      axios.get('/api/indicator-menu').then(response => {
        context.commit('SET_INDICATOR_MENU', response.data)
      })
    },
    getLocationMenu(context) {
      axios.get('/api/location-menu').then(response => {
        context.commit('SET_LOCATION_MENU', response.data)
      })
    },
    getFeaturedIndicators(context) {
      axios.get('/api/featured-indicators').then(response => {
        context.commit('SET_FEATURED_INDICATORS', response.data)
      })
    },
    setCommunity(context, community) {
      if (community !== null) {
        context.dispatch('getCommunityData', community)
      } else {
        context.commit('SET_COMMUNITY', community)
      }
    },
    getCommunityData(context, community) {
      axios.get('/api/community-data', { params: { 
          location: community.id, 
          locationType: community.categoryId 
        }
      }).then(response => {
        context.commit('SET_COMMUNITY', response.data)
      })
    },
    setIndicator(context, indicator) {
      context.commit('SET_INDICATOR', indicator)
      if (indicator == null) {
        context.commit('SET_SOURCE', null)
        context.commit('SET_FILTER_SELECTIONS', null)
        context.commit('SET_COMPARE_SELECTIONS', null)
        return Promise.resolve();
      } else {
        context.commit('SET_COMPARE_SELECTIONS', null)
        return context.dispatch('getSource', indicator).then(() => {
          return context.dispatch('getFilters', indicator)
        });
      }
    },
    getSource(context, indicator) {
      return axios.get('/api/indicator-source', { params: {
        indicator: indicator.id
      }}).then(response => {
        context.commit('SET_SOURCE', response.data)
      })
    },
    getFilters(context, indicator) {
      return axios.get('/api/filters', { params: {
        indicator: indicator.id
      }}).then(response => { 
        context.commit('SET_FILTERS', response.data)
      })
    },
    getExploreData(context) {
      if (!this.state.filterSelections) {
        return;
      }
      axios.post('/api/explore-data', {
        indicator: context.state.indicator.id, 
        filters: this.state.filterSelections,
        comparisons: this.state.compareSelections
      }).then(response => {
        context.commit('SET_EXPLORE_DATA', response.data)
      })
    },
    setExploreData(context, data) {
      context.commit('SET_EXPLORE_DATA', data)
    },
    setDockedTooltip(context, data) {
      context.commit('SET_DOCKED_TOOLTIP', data)
    },
    setExploreTab(context, tab) {
      context.commit('SET_EXPLORE_TAB', tab)
    },
    setFilterSelections(context, selections) {
      context.commit('SET_FILTER_SELECTIONS', selections);
      let filterQuery = {
        ...router.currentRoute.query,
        locationType: selections.locationType,
        location: selections.location,
        year: selections.year

      };
      Object.entries(selections.indicatorFilters).forEach(e => {
        filterQuery['filter_' + e[0]] = e[1].id;
      });
      if (JSON.stringify(filterQuery) !== JSON.stringify(router.currentRoute.query)) {
        router.replace({
          query: filterQuery
        });
      }
      context.dispatch('getExploreData');
    },
    setCompareSelections(context, selections) {
      context.commit('SET_COMPARE_SELECTIONS', selections);
      let compareWiths = selections.options.map(o => (o.typeId ? o.typeId + "_" : "") + o.id)
      if (compareWiths.length === 1) {
        compareWiths = compareWiths[0]
      }
      let compareQuery = {
        ...router.currentRoute.query,
        compareBy: selections.type.id,
        compareWith: compareWiths

      };
      if (JSON.stringify(compareQuery) !== JSON.stringify(router.currentRoute.query)) {
        router.replace({
          query: compareQuery
        });
      }
      context.dispatch('getExploreData');
    },
    getTablesData(context, request) {
        axios.post('/api/tables-data', { 
          ...request 
        }).then(response => {
          response.data.items.forEach(i => {
            if (i.suppressed) {
              i.valueLabel = i18n.t('data.suppressed');
            } else if (i.value === null) { 
              i.valueLabel = i18n.t('data.no_data');
            } else  {
              i.valueLabel = format(response.data.indicator.typeId, i.value);
            }
            i.moeLowLabel = format(response.data.indicator.typeId, i.moeLow);
            i.moeHighLabel = format(response.data.indicator.typeId, i.moeHigh);
            i.universeValueLabel = format('1', i.universeValue);
          })
          context.commit('SET_TABLES_DATA', response.data);
        });
    },
    setTablesData(context, data) {
      context.commit('SET_TABLES_DATA', data);
    },
    getAboutData(context) {
      axios.get('/api/about-data').then(response => {
        context.commit('SET_ABOUT_DATA', response.data);
      });
    },
    setToolRoute(context, params) {
      context.commit('SET_TOOL_ROUTE', params);
    },
    setShowMapLabels(context, showLabels) {
      context.commit('SET_SHOW_MAP_LABELS', showLabels)
    },
    setShowTrendLabels(context, showLabels) {
      context.commit('SET_SHOW_TREND_LABELS', showLabels)
    },
    setShowCompareLabels(context, showLabels) {
      context.commit('SET_SHOW_COMPARE_LABELS', showLabels)
    }
  },
  modules: {},
})
