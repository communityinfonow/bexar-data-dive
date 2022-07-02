import Vue from 'vue'
import Vuex from 'vuex'
import router from '@/router/index'
import i18n from '@/i18n'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    locale: '',
    locationMenu: null,
    indicatorMenu: null,
    community: null,
    indicator: null,
    source: null,
    filters: null,
    dockedTooltipValue: null,
    dashboardData: null,
    dashboardTab: null,
    filterSelections: null,
    compareSelections: null
  },
  getters: {
    tools: () => {
      return [
        {
          name: i18n.t('tools.my_community.name'),
          route: 'my-community',
          icon: 'mdi-map',
          shortDescription: i18n.t('tools.my_community.short_description'),
          fulldescription: i18n.t('tools.my_community.long_description'),
        },
        {
          name: i18n.t('tools.dashboard.name'),
          route: 'dashboard',
          icon: 'mdi-view-dashboard',
          shortDescription: i18n.t('tools.dashboard.short_description'),
          fulldescription: i18n.t('tools.dashboard.long_description'),
        },
        {
          name: i18n.t('tools.explore_data.name'),
          route: 'explore-data',
          icon: 'mdi-grid',
          shortDescription: i18n.t('tools.explore_data.short_description'),
          fulldescription: i18n.t('tools.explore_data.long_description'),
        },
      ]
    },
  },
  mutations: {
    SET_LOCALE(state, locale) {
      state.locale = locale
    },
    SET_INDICATOR_MENU(state, indicatorMenu) {
      state.indicatorMenu = indicatorMenu
    },
    SET_LOCATION_MENU(state, locationMenu) {
      state.locationMenu = locationMenu
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
    SET_DASHBOARD_DATA(state, dashboardData) {
      state.dashboardData = dashboardData
    },
    SET_DOCKED_TOOLTIP_VALUE(state, value) {
      state.dockedTooltipValue = value
    },
    SET_DASHBOARD_TAB(state, dashboardTab) {
      state.dashboardTab = dashboardTab
    },
    SET_FILTER_SELECTIONS(state, selections) { 
      state.filterSelections = selections
    },
    SET_COMPARE_SELECTIONS(state, selections) {
      state.compareSelections = selections
    }
  },
  actions: {
    setLocale(context, locale) {
      if (locale !== this.state.locale) {
        context.commit('SET_LOCALE', locale)
        localStorage.setItem('cinow-locale', locale)
        i18n.locale = locale
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
        context.commit('SET_FILTERS', null)
      } else {
        context.dispatch('getSource', indicator)
        context.dispatch('getFilters', indicator)
      }
    },
    getSource(context, indicator) {
      axios.get('/api/indicator-source', { params: {
        indicator: indicator.id
      }}).then(response => {
        context.commit('SET_SOURCE', response.data)
      })
    },
    getFilters(context, indicator) {
      axios.get('/api/filters', { params: {
        indicator: indicator.id
      }}).then(response => { 
        context.commit('SET_FILTERS', response.data)
      })
    },
    getDashboardData(context) {
      axios.post('/api/dashboard-data', {
        indicator: context.state.indicator.id, 
        filters: this.state.filterSelections,
        comparisons: this.state.compareSelections
      }).then(response => {
        context.commit('SET_DASHBOARD_DATA', response.data)
      })
    },
    setDockedTooltipValue(context, data) {
      context.commit('SET_DOCKED_TOOLTIP_VALUE', data)
    },
    setDashboardTab(context, tab) {
      context.commit('SET_DASHBOARD_TAB', tab)
    },
    setFilterSelections(context, selections) {
      context.commit('SET_FILTER_SELECTIONS', selections)
      context.dispatch('getDashboardData')
    },
    setCompareSelections(context, selections) {
      context.commit('SET_COMPARE_SELECTIONS', selections)
      context.dispatch('getDashboardData')
    }
  },
  modules: {},
})
