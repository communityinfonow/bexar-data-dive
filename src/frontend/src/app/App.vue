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
      color="accent"
      style="z-index: 10001"
    ></v-progress-linear>
    <AppFooter></AppFooter>
  </v-app>
</template>

<script>
import { mapState } from 'vuex'
import AppHeader from '@/app/components/AppHeader'
import PrintHeader from '@/app/components/PrintHeader'
import AppFooter from '@/app/components/AppFooter'
import NavigationDrawer from '@/app/components/NavigationDrawer'
import VisitorSurvey from '@/app/components/VisitorSurvey'

export default {
  name: 'App',
  components: { AppHeader, PrintHeader, AppFooter, NavigationDrawer, VisitorSurvey },
  computed: {
    ...mapState(['loading'])
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
  z-index: 1000 !important;
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

::v-deep .page-header {
  background: url('~@/assets/img/background_header.svg');
  background-size: cover;
}

::v-deep .container {
  padding: 0;
}
</style>