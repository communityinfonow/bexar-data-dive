import Vue from 'vue'
import App from './App.vue'
import router from '@/app/router'
import store from '@/app/store'
import vuetify from '@/plugins/vuetify'
import i18n from '@/i18n'
import axios from 'axios'
import axiosRetry from 'axios-retry';
import 'leaflet/dist/leaflet.css'
import L from 'leaflet'

Vue.config.productionTip = false
delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
   iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
   iconUrl: require('leaflet/dist/images/marker-icon.png'),
   shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

new Vue({
  router,
  store,
  vuetify,
  i18n,
  render: (h) => h(App),
  created: function () {
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
    axiosRetry(axios, { retries: 1, retryCondition: (error) => {
      return error.response.status === 503 && error.response.data === 'please try again'
     }
    });

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
    this.$store.dispatch('getFeaturedIndicators')
    this.$store.dispatch('getBexarDataFacts')
  },
  mounted: function () {
    
  },
}).$mount('#app')
