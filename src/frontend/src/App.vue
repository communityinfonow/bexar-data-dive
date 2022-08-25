<template>
  <v-app>
    <AppHeader></AppHeader>
    <NavigationDrawer></NavigationDrawer>
    <v-main>
      <router-view />
    </v-main>
    <v-progress-linear
      v-if="loading"
      indeterminate
      fixed
      bottom
      height="12px"
      color="accent"
      style="z-index: 10001"
    ></v-progress-linear>
    <AppFooter></AppFooter>
  </v-app>
</template>

<script>
import { mapState } from 'vuex'
import AppHeader from '@/components/AppHeader'
import AppFooter from '@/components/AppFooter'
import NavigationDrawer from '@/components/NavigationDrawer'

export default {
  name: 'App',
  components: { AppHeader, AppFooter, NavigationDrawer },
  computed: {
    ...mapState(['loading'])
  },
}
</script>

<style lang="scss" scoped>
@media print {
  @page {
    orientation: landscape;
    
  }
}
::v-deep .v-menu__content {
  z-index: 1000 !important;
}
::v-deep .v-btn--plain:not(.v-btn--active):not(.v-btn--loading):not(:focus):not(:hover) .v-btn__content {
  opacity: 1 !important;
}
::v-deep .theme--light.v-breadcrumbs .v-breadcrumbs__item--disabled {
  color: rgba(0, 0, 0, 0.6)
}
</style>