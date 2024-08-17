<template>
  <v-app>
    <AppHeader></AppHeader>
    <PrintHeader></PrintHeader>
    <NavigationDrawer></NavigationDrawer>
    <v-main>
      <router-view />
    </v-main>
    <VisitorSurvey class="mr-4"></VisitorSurvey>
    <v-progress-linear
      v-if="loading"
      indeterminate
      fixed
      bottom
      height="12px"
      color="green"
      style="z-index: 10001"
    ></v-progress-linear>
    <AppFooter></AppFooter>
  </v-app>
</template>

<script>
import store from '@/app/store/index.js'
import { mapState } from 'vuex'
import vuetify from '@/plugins/vuetify'
import AppHeader from '@/app/components/AppHeader'
import PrintHeader from '@/app/components/PrintHeader'
import AppFooter from '@/app/components/AppFooter'
import NavigationDrawer from '@/app/components/NavigationDrawer'
import VisitorSurvey from '@/app/components/VisitorSurvey'

export default {
  name: 'App',
  store,
  vuetify,
  components: { AppHeader, PrintHeader, AppFooter, NavigationDrawer, VisitorSurvey },
  computed: {
    ...mapState(['loading'])
  },
}
</script>

<style lang="scss" scoped>
  @import "@/assets/css/app.scss";
</style>