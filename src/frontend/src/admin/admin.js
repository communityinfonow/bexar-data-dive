import Vue from 'vue'
import Admin from './Admin.vue'
import router from '@/admin/router'
import store from '@/admin/store'
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
  render: (h) => h(Admin),
  created: function () {
    axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest'
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
      if (error.response.status === 401) {
        window.open(window.location.protocol +'//' + window.location.hostname + ':' + process.env.VUE_APP_API_PORT + '/oauth2/authorization/google', '_blank');
      }
      if (error.response.status === 403) {
        window.location = '/unauthorized';
      }
      return Promise.reject(error);
    });
  },
  beforeMount: function () {
    this.loginCheck();
  },
  methods: {
    loginCheck() {
      axios.get('/api/admin/username')
        .then((response) => {
          this.$store.dispatch('setLoggedIn', true);
          this.$store.dispatch('setUsername', response.data);
        });
    }
  },
}).$mount('#app')
