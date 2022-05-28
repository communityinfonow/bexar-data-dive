import Vue from 'vue'
import Vuex from 'vuex'
import router from '@/router/index'
import i18n from '@/i18n'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    locale: '',
    locationMenu: {},
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
        context.dispatch('setIndicatorMenu')
        context.dispatch('setLocationMenu')
        if (
          router.currentRoute.name === 'my-community' &&
          this.state.community
        ) {
          context.dispatch('setCommunity')
        }
      }
    },
    setIndicatorMenu(context) {
      //TODO: call API
      if (this.state.locale === 'en') {
        context.commit('SET_INDICATOR_MENU', {
          items: [
            {
              id: 1,
              name: 'Category 1',
              items: [
                { id: 1, name: 'ind 1' },
                { id: 1, name: 'ind 2' },
              ],
            },
          ],
        })
      } else if (this.state.locale === 'es') {
        context.commit('SET_INDICATOR_MENU', {
          items: [
            {
              id: 1,
              name: 'Category 1 (es)',
              items: [
                { id: 1, name: 'ind 1 (es)' },
                { id: 2, name: 'ind 2 (es)' },
              ],
            },
          ],
        })
      }
    },
    setLocationMenu(context) {
      //TODO: call API
      if (this.state.locale === 'en') {
        context.commit('SET_LOCATION_MENU', {
          items: [
            {
              id: 1,
              name: 'Bexar County',
            },
            {
              id: 2,
              name: 'Super Neighborhoods',
              items: [
                { id: 1, name: 'SNH 1' },
                { id: 2, name: 'SNH 2' },
              ],
            },
          ],
        })
      } else if (this.state.locale === 'es') {
        context.commit('SET_LOCATION_MENU', {
          items: [
            {
              id: 1,
              name: 'Bexar County (es)',
            },
            {
              id: 2,
              name: 'Super Neighborhoods (es)',
              items: [
                { id: 1, name: 'SNH 1 (es)' },
                { id: 2, name: 'SNH 2 (es)' },
              ],
            },
          ],
        })
      }
    },
    setCommunity(context, communityId) {
      //TODO: call API
      if (this.state.locale === 'en') {
        context.commit('SET_COMMUNITY', {
          id: communityId,
          name: 'Bexar County',
        })
      } else if (this.state.locale == 'es') {
        context.commit('SET_COMMUNITY', {
          id: communityId,
          name: 'Bexar County (es)',
        })
      }
    },
  },
  modules: {},
})
