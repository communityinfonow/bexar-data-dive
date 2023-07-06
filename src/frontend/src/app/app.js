import Vue from 'vue'
import App from './App.vue'
import router from '@/app/router'
import store from '@/app/store'
import vuetify from '@/plugins/vuetify'
import i18n from '@/i18n'
import axios from 'axios'
import 'leaflet/dist/leaflet.css'

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  i18n,
  render: (h) => h(App),
  created: function () {
    let queryLocale = router.currentRoute.query.lang
    let storedLocale
    try {
      storedLocale = localStorage.getItem('cinow-locale')
    } catch {
      storedLocale = null
    }
    if (queryLocale) {
      this.$store.dispatch('setLocale', queryLocale)
    } else if (storedLocale) {
      this.$store.dispatch('setLocale', storedLocale)
    } else {
      this.$store.dispatch('setLocale', 'en')
    }

    let storedSurveySubmitted
    try {
      storedSurveySubmitted = sessionStorage.getItem('cinow-survey-submitted') === 'true'
    } catch {
      storedSurveySubmitted = false
    }
    this.$store.dispatch('setSurveySubmitted', storedSurveySubmitted)

    let storedCustomLocations
    try {
      storedCustomLocations = JSON.parse(localStorage.getItem('cinow-custom-locations')) || []
    } catch {
      storedCustomLocations = []
    }
    this.$store.dispatch('setCustomLocations', storedCustomLocations)

    let requestCount = 0
    axios.interceptors.request.use(function(config) {
      if (requestCount === 0) {
        store.dispatch('setLoading', true);
      }
      requestCount++;

      return config;
    });
    axios.interceptors.response.use(response => {
      requestCount--;
      if (requestCount === 0) {
        store.dispatch('setLoading', false);
      }

      return response;
    }, error => {
      requestCount--;
      if (requestCount === 0) {
        store.dispatch('setLoading', false);
      }

      return Promise.reject(error);
    });
  },
  mounted: function () {
    
  },
}).$mount('#app')
