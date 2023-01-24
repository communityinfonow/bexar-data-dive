import Vue from 'vue'
import Vuex from 'vuex'
//import router from '@/admin/router/index'
//import i18n from '@/i18n'
//import axios from 'axios'
//import { format } from '@/formatter/formatter'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loading: false,
    loggedIn: false,
    username: null
  },
  getters: {
    tools: () => {
      return [
        {
          key: 'announcements',
          name: 'Announcements',
          route: '/admin/announcements'
        },
        {
          key: 'indicators',
          name: 'Indicators',
          route: '/admin/indicators'
        },
        {
          key: 'filters',
          name: 'Filters',
          route: '/admin/filters'
        },
        {
          key: 'sources',
          name: 'Sources',
          route: '/admin/sources'
        },
        {
          key: 'about-tools',
          name: 'About the Tools',
          route: '/admin/about-tools'
        },
        {
          key: 'about-data',
          name: 'About the Data',
          route: '/admin/about-data'
        },
        {
          key: 'faqs',
          name: 'Frequently Asked Questions',
          route: '/admin/faqs'
        }
      ]
    },
  },
  mutations: {
    SET_LOADING(state, loading) {
      state.loading = loading
    },
    SET_LOGGED_IN(state, loggedIn) {
      state.loggedIn = loggedIn
    },
    SET_USERNAME(state, username) {
      state.username = username
    }
  },
  actions: {
    setLoading(context, loading) {
      context.commit('SET_LOADING', loading)
    },
    setLoggedIn(context, loggedIn) {
      context.commit('SET_LOGGED_IN', loggedIn)
    },
    setUsername(context, username) {
      context.commit('SET_USERNAME', username)
    }
  },
  modules: {},
})
