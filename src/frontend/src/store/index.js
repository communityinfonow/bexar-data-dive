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
    indicatorMenu: {},
    community: null,
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
      context.commit('SET_COMMUNITY', community)
      if (community !== null) {
        context.dispatch('getCommunity', community)
      }
    },
    getCommunity(context, community) {
      axios.get('/api/my-community', { params: { 
          location: community.id, 
          locationType: community.categoryId 
        }
      }).then(response => {
        context.commit('SET_COMMUNITY', response.data)
      })
    }
  },
  modules: {},
})
