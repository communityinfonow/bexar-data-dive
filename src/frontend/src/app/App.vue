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
  z-index: 10000 !important;
}
::v-deep .v-btn--plain:not(.v-btn--active):not(.v-btn--loading):not(:focus):not(:hover) .v-btn__content {
  opacity: 1 !important;
}
::v-deep .theme--light.v-breadcrumbs .v-breadcrumbs__item--disabled {
  color: rgba(0, 0, 0, 0.6)
}

::v-deep .v-breadcrumbs {
  padding-left: 0px !important;
  padding-bottom: 0;
  text-transform: uppercase;
}

::v-deep .v-breadcrumbs__item {
  color: $color-light !important;
  font-weight: bold;
}

::v-deep .v-breadcrumbs__item--disabled {
  font-weight: normal;
}

::v-deep .v-breadcrumbs__divider {
  padding: 0px !important;
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

::v-deep .page-header.main-content .v-breadcrumbs {
  padding-top: 20px;
}

main {
  padding-bottom: 200px !important;
}

::v-deep .sticky-menu {
  position: sticky;
  top: 80px;
  z-index: 1001;
}

::v-deep .v-btn.v-item--active.v-btn--active.v-btn--is-elevated.v-btn--has-bg.theme--dark.v-size--small.red .v-btn__content {
  color: $color-grey !important;
}
::v-deep .v-btn.v-btn--disabled.v-btn--has-bg.theme--dark.v-size--small {
  background-color: $color-primary !important;
}
::v-deep .v-btn.v-btn--disabled.v-btn--has-bg.theme--dark.v-size--small .v-btn__content{
  color: $color-grey !important;
}

::v-deep .definition-tooltip-container {
  background: transparent;
}

::v-deep .definition-tooltip {
  color: $color-primary;
  background-color: $color-light;
  border: 1px solid $color-primary;
  border-radius: 1em;
  padding: 1em;
}

::v-deep .definition-tooltip-wedge {
  content: "";
  width: 0;
  height: 0;
  border-top: 20px solid transparent;
  border-bottom: 20px solid transparent;
  border-right: 20px solid $color-primary;
  position: absolute;
  top: calc(50% - 20px);
  left: -3px;
}
::v-deep .definition-tooltip-wedge::after {
  content: "";
  width: 0;
  height: 0;
  border-top: 20px solid transparent;
  border-bottom: 20px solid transparent;
  border-right: 20px solid $color-light;
  position: absolute;
  top: calc(50% - 20px);
  left: 1px;
}

::v-deep .text-dive-h1 {
  font-size: 3.5rem;
  font-weight: bold;
}
::v-deep .text-dive-h2 {
  font-size: 3.0rem;
  font-weight: bold;
}
::v-deep .text-dive-h3 {
  font-size: 2.5rem;
  font-weight: bold;
}
::v-deep .text-dive-h4 {
  font-size: 2.0rem;
  font-weight: bold;
}
::v-deep .text-dive-h5 {
  font-size: 1.5rem;
  font-weight: bold;
}
::v-deep .text-dive-h6 {
  font-size: 1.0rem;
  font-weight: bold;
}
</style>