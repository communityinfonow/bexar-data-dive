import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loading: false,
    loggedIn: false,
    username: null,
    faqs: null,
    indicators: null,
    categories: null,
    types: null,
    sources: null,
    announcements: null,
    dataCorrections: null,
    filterTypes: null,
    filterOptions: null,
    auditLog: null,
    missedTranslations: null
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
          key: 'data-corrections',
          name: 'Data Corrections',
          route: '/admin/data-corrections'
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
          key: 'faqs',
          name: 'Frequently Asked Questions',
          route: '/admin/faqs'
        },
        {
          key: 'audit-log',
          name: 'Audit Log',
          route: '/admin/audit-log'
        },
        {
          key: 'missed-tranlsations',
          name: 'Missed Translations',
          route: '/admin/missed-translations'
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
    },
    SET_FAQS(state, faqs) {
      state.faqs = faqs
    },
    SET_INDICATORS(state, indicators) {
      state.indicators = indicators
    },
    SET_CATEGORIES(state, categories) {
      state.categories = categories;
    },
    SET_TYPES(state, types) {
      state.types = types;
    },
    SET_SOURCES(state, sources) {
      state.sources = sources
    },
    SET_FILTER_TYPES(state, types) {
      state.filterTypes = types
    },
    SET_FILTER_OPTIONS(state, options) {
      state.filterOptions = options
    },
    SET_ANNOUNCEMENTS(state, announcements) {
      state.announcements = announcements
    },
    SET_DATA_CORRECTIONS(state, dataCorrections) {
      state.dataCorrections = dataCorrections
    },
    SET_AUDIT_LOG(state, auditLog) {
      state.auditLog = auditLog
    },
    SET_MISSED_TRANSLATIONS(state, missedTranslations) {
      state.missedTranslations = missedTranslations
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
    },
    getFaqs(context) {
      return axios.get('/api/faqs').then(response => {
        context.commit('SET_FAQS', response.data)
      });
    },
    addFaq(context, faq) {
      return axios.post('/api/admin/faqs', faq).then(() => {
        context.dispatch('getFaqs')
      });
    },
    updateFaq(context, faq) {
      return axios.put('/api/admin/faqs', faq).then(() => {
        context.dispatch('getFaqs');
      });
    },
    getIndicators(context) {
      return axios.get('/api/admin/indicators').then(response => {
        context.commit('SET_INDICATORS', response.data)
      });
    },
    addIndicator(context, indicator) {
      return axios.post('/api/admin/indicators', indicator).then(() => {
        context.dispatch('getIndicators')
      });
    },
    updateIndicator(context, indicator) {
      return axios.put('/api/admin/indicators', indicator).then(() => {
        context.dispatch('getIndicators');
      });
    },
    getCategories(context) {
      return axios.get('/api/admin/indicator-categories').then((response) => {
        context.commit('SET_CATEGORIES', response.data);
      });
    },
    getTypes(context) {
      return axios.get('/api/admin/indicator-types').then((response) => {
        context.commit('SET_TYPES', response.data);
      });
    },
    getSources(context) {
      return axios.get('/api/admin/indicator-sources').then((response) => {
        context.commit('SET_SOURCES', response.data);
      });
    },
    addSource(context, src) {
      return axios.post('/api/admin/indicator-sources', src).then(() => {
        context.dispatch('getSources')
      });
    },
    updateSource(context, src) {
      return axios.put('/api/admin/indicator-sources', src).then(() => {
        context.dispatch('getSources');
      });
    },
    getFilterTypes(context) {
      return axios.get('/api/admin/filter-types').then((response) => {
        context.commit('SET_FILTER_TYPES', response.data);
      });
    },
    getFilterOptions(context) {
      return axios.get('/api/admin/filter-options').then((response) => {
        context.commit('SET_FILTER_OPTIONS', response.data);
      });
    },
    addFilterOption(context, option) {
      return axios.post('/api/admin/filter-options', option).then(() => {
        context.dispatch('getFilterOptions')
      });
    },
    updateFilterOption(context, option) {
      return axios.put('/api/admin/filter-options', option).then(() => {
        context.dispatch('getFilterOptions')
      });
    },
    getAnnouncements(context) {
      return axios.get('/api/admin/announcements').then((response) => {
        context.commit('SET_ANNOUNCEMENTS', response.data);
      });
    },
    addAnnouncement(context, ann) {
      return axios.post('/api/admin/announcements', ann).then(() => {
        context.dispatch('getAnnouncements')
      });
    },
    updateAnnouncement(context, ann) {
      return axios.put('/api/admin/announcements', ann).then(() => {
        context.dispatch('getAnnouncements');
      });
    },
    getDataCorrections(context) {
      return axios.get('/api/admin/data-corrections').then((response) => {
        let corrections = response.data.map(c => {
          return {
            ...c,
            locationTypes: c.locationTypes.map(lt => lt.id),
            filterTypes: c.filterTypes.map(ft => ft.id),
            indicator: c.indicator.id
          }
        })
        context.commit('SET_DATA_CORRECTIONS', corrections);
      });
    },
    addDataCorrection(context, correction) {
      let correctionObject = {
        dateCorrected: correction.dateCorrected,
        indicator: { id: correction.indicator },
        years: correction.years,
        locationTypes: correction.locationTypes?.map(lt => ({ id: lt })) || null,
        filterTypes: correction.filterTypes?.map(ft => ({ id: ft })) || null,
        note: correction.note,
        display: correction.display
      };
      return axios.post('/api/admin/data-corrections', correctionObject).then(() => {
        context.dispatch('getDataCorrections')
      });
    },
    updateDataCorrection(context, correction) {
      let correctionObject = {
        id: correction.id,
        dateCorrected: correction.dateCorrected,
        indicator: { id: correction.indicator },
        years: correction.years,
        locationTypes: correction.locationTypes?.map(lt => ({ id: lt })) || null,
        filterTypes: correction.filterTypes?.map(ft => ({ id: ft })) || null,
        note: correction.note,
        display: correction.display
      };
      return axios.put('/api/admin/data-corrections', correctionObject).then(() => {
        context.dispatch('getDataCorrections');
      });
    },
    getAuditLog(context) {
      return axios.get('/api/admin/audit-log').then((response) => {
        context.commit('SET_AUDIT_LOG', response.data);
      });
    },
    getMissedTranslations(context) {
      return axios.get('/api/admin/missed-translations').then((response) => {
        context.commit('SET_MISSED_TRANSLATIONS', response.data);
      });
    }
  },
  modules: {},
})
