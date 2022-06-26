<template>
  <v-container v-if="indicatorMenu" fluid class="fill-height pa-0">
    <v-row class="no-gutters flex-wrap flex-column fill-height">
      <v-col cols="auto">
        <MenuToolbar
          class="flex-column"
          :menu="indicatorMenu"
          :selectItem="selectItem"
          :flattenSingleItems="false"
        ></MenuToolbar>
      </v-col>
      <v-col v-if="showIntro" cols="auto" class="pa-4">
          <h1 class="text-h3 mb-2">{{ $t('tools.dashboard.name') }}</h1>
          <p>{{ $t('tools.dashboard.long_description') }}</p>
          <p>{{ $t('tools.dashboard.get_started') }}</p>
      </v-col>
      <v-col v-if="indicator" cols="auto" class="pt-4 px-4">
          <h1 class="text-h3 mb-1">{{ indicator['name_' + locale] }}</h1>
          <h2 v-if="source" class="text-subtitle-1 mb-2">{{ source['name_' + locale] }}</h2>
      </v-col>
      <v-col v-if="indicator" cols="auto" class="grow">
        <v-row class="fill-height no-gutters">
          <v-col cols="9" class="px-4">
            <v-row class="fill-height no-gutters flex-column">
              <v-col cols="auto" class="shrink">
                <v-tabs v-model="tab" grow>
                  <v-tab v-for="tab in tabs" :key="tab" @click="selectTab(tab)">
                    {{ $t('tools.dashboard.tabs.' + tab) }}
                  </v-tab>
                </v-tabs>
              </v-col>
              <v-col cols="auto" class="grow">
                <v-tabs-items v-model="tab" class="fill-height">
                  <v-tab-item v-for="tab in tabs" :key="tab" transition="none" reverse-transition="none" class="fill-height">
                    <dashboard-map v-if="tab === 'map'"></dashboard-map>
                    <dashboard-trend-chart v-if="tab === 'trend'"></dashboard-trend-chart>
                    <dashboard-compare-chart v-if="tab === 'compare'"></dashboard-compare-chart>
                  </v-tab-item>
                </v-tabs-items>
              </v-col>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-row class="fill-height no-gutters flex-column">
              <filters-panel></filters-panel>
              <docked-tooltip class="grow" :helpMessage="dockedTooltipHelpMessage"></docked-tooltip>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import router from '@/router/index'
import i18n from '@/i18n'
import MenuToolbar from '@/components/MenuToolbar'
import FiltersPanel from '@/components/FiltersPanel'
import DockedTooltip from '@/components/DockedTooltip'
import DashboardMap from '@/components/DashboardMap'
import DashboardTrendChart from '@/components/DashboardTrendChart'
import DashboardCompareChart from '@/components/DashboardCompareChart'
export default {
  name: 'DashboardView',
  components: {
    MenuToolbar, 
    FiltersPanel,
    DockedTooltip,
    DashboardMap,
    DashboardTrendChart,
    DashboardCompareChart
  },
  data() {
    return {
      tabs: ['map', 'trend', 'compare'],
      tab: null
    }
  },
  computed: {
    ...mapState(['indicatorMenu', 'indicator', 'source', 'locale']),
    showIntro() {
      return !this.indicator && !router.currentRoute.query.indicator;
    },
    dockedTooltipHelpMessage() {
      switch (this.tab) {
        case 0:
          return i18n.t('tools.dashboard.docked_tooltip.help_messages.map');
        case 1:
          return i18n.t('tools.dashboard.docked_tooltip.help_messages.trend');
        case 2:
          return i18n.t('tools.dashboard.docked_tooltip.help_messages.compare');
      }
      return null
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$store.dispatch('setIndicator', null)
    });
  },
  beforeRouteUpdate(to, from, next) {
    if (from.query.indicator && !to.query.indicator) {
      this.setIndicator(null)
    }
    next();
  },
  mounted () {
    if (router.currentRoute.query.indicator && this.indicatorMenu) {
      this.setIndicator(this.indicatorMenu.categories
        .flatMap(category => category.items)
        .find(item => item.id == router.currentRoute.query.indicator))
    } else {
      this.setIndicator(null)
    }
    if (router.currentRoute.query.tab) {
      this.selectTab(router.currentRoute.query.tab)
    }
  },
  updated () {
    if (router.currentRoute.query.indicator && this.indicatorMenu) {
      let matchedIndicator = this.indicatorMenu.categories
        .flatMap(category => category.items)
        .find(item => item.id == router.currentRoute.query.indicator)
      if (matchedIndicator?.id !== this.indicator?.id) {
        this.setIndicator(matchedIndicator)
      }
    } else {
      this.setIndicator(null)
    }
  },
  methods: {
    ...mapActions(['setIndicator', 'setDashboardTab']),
    selectItem(item) {
      if (item.id !== this.indicator?.id) {
        this.setIndicator(item)
        router.replace({
          query: {
            ...router.currentRoute.query,
            indicator: item.id,
          },
        });
        this.selectTab('map');
      }
    },
    selectTab(selectedTab) {
      if (selectedTab !== router.currentRoute.query.tab) {
        router.replace({
          query: {
            ...router.currentRoute.query,
            tab: selectedTab
          }
        })
      }
      let tabIndex = this.tabs.indexOf(selectedTab)
      if (tabIndex !== this.tab) {
        this.tab = tabIndex;
        this.setDashboardTab(selectedTab);
      }
    },
  },
}
</script>

<style lang="scss" scoped></style>
