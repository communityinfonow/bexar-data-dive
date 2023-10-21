<template>
  <v-app v-if="loggedIn">
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
      color="green"
      style="z-index: 10001"
    ></v-progress-linear>
    <AppFooter></AppFooter>
  </v-app>
</template>

<script>
import { mapState } from 'vuex'
import AppHeader from '@/admin/components/AdminHeader'
import AppFooter from '@/admin/components/AppFooter'
import NavigationDrawer from '@/admin/components/NavigationDrawer'

export default {
  name: 'App',
  components: { AppHeader, AppFooter, NavigationDrawer },
  computed: {
    ...mapState(['loading', 'loggedIn'])
  },
}
</script>

<style lang="scss" scoped>
@media screen {
  .print-header {
    display: none !important;
  }
}

@media print {
  @page {
    orientation: landscape; 
  }

  ::v-deep .v-breadcrumbs {
    display: none;
  }

  .print-header {
    display: block;
  }
}
::v-deep .v-menu__content {
  z-index: 10000 !important;
}
::v-deep .v-btn--plain:not(.v-btn--active):not(.v-btn--loading):not(:focus):not(:hover) .v-btn__content {
  opacity: 1 !important;
}
::v-deep .theme--light.v-breadcrumbs .v-breadcrumbs__item--disabled {
  color: rgba(0, 0, 0, 0.6)
}

::v-deep .v-breadcrumbs {
  padding-top: 16px;
  padding-bottom: 0;
}

::v-deep .v-card__title {
  word-break: break-word;
}
</style>