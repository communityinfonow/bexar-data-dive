<template>
  <v-container v-if="indicatorMenu" fluid class="fill-height pa-0">
    <v-row class="no-gutters flex-wrap flex-column fill-height">
      <v-col cols="auto">
        <MenuToolbar
          class="flex-column"
          :menu="indicatorMenu"
          :selectItem="selectItem"
          :flattenSingleItems="false"
          :goBack="goBack"
        ></MenuToolbar>
      </v-col>
      <v-col v-if="showIntro" cols="auto" class="pa-4">
          <h1 class="text-h3 mb-2">{{ $t('tools.explore.name') }}</h1>
          <p>{{ $t('tools.explore.long_description') }}</p>
          <p>{{ $t('tools.explore.get_started') }}</p>
          <h2 v-if="featuredIndicators" class="text-h5 mt-8 mb-2">{{ $t('tools.common.featured_indicators') }}</h2>
          <section v-if="featuredIndicators" class="d-flex flex-row">
            <template v-for="indicator in featuredIndicators">
              <featured-card 
                :key="indicator.id" 
                :item="indicator"
                :name="indicator['name_' + locale]" 
                :description="indicator['description_' + locale]" 
                :about_route="'about-data?indicator=' + indicator.id" 
                :view_route="indicator.route"
                :click_route="selectItem"
              >
              </featured-card>
            </template>
          </section>
      </v-col>
      <v-col v-if="indicator && exploreData" cols="auto" class="pt-4 px-4">
          <v-breadcrumbs
            :items="breadcrumbs"
            class="pt-0 pl-0"
          >
          </v-breadcrumbs>
          <h1 class="text-h3 mb-1"><span v-if="exploreData.category.parentCategoryId">{{ exploreData.category['name_' + locale] }} - </span>{{ indicator['name_' + locale] }}</h1>
          <h2 v-if="source" class="text-subtitle-1 mb-2">{{ source['name_' + locale] }}</h2>
      </v-col>
      <v-col v-if="indicator" cols="auto" class="explore-content">
        <v-row class="fill-height no-gutters">
          <v-col cols="9" class="px-4">
            <v-row class="fill-height no-gutters flex-column">
              <v-col cols="auto" class="shrink">
                <v-tabs v-model="tab" grow>
                  <v-tab v-for="tab in tabs" :key="tab" @click="selectTab(tab)">
                    {{ $t('tools.explore.tabs.' + tab) }}
                  </v-tab>
                </v-tabs>
              </v-col>
              <v-col cols="auto" class="grow">
                <v-tabs-items v-model="tab" class="fill-height">
                  <v-tab-item v-for="tab in tabs" :key="tab" transition="none" reverse-transition="none" class="fill-height">
                    <explore-map v-if="tab === 'map'"></explore-map>
                    <explore-trend-chart v-if="tab === 'trend'"></explore-trend-chart>
                    <explore-compare-chart v-if="tab === 'compare'"></explore-compare-chart>
                  </v-tab-item>
                </v-tabs-items>
              </v-col>
            </v-row>
          </v-col>
          <v-col cols="3">
            <v-row class="fill-height no-gutters flex-column">
              <filters-panel></filters-panel>
              <docked-tooltip class="grow" :helpMessage="dockedTooltipHelpMessage" :activeTab="tab"></docked-tooltip>
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
import FeaturedCard from '@/components/FeaturedCard'
import FiltersPanel from '@/components/FiltersPanel'
import DockedTooltip from '@/components/DockedTooltip'
import ExploreMap from '@/components/ExploreMap'
import ExploreTrendChart from '@/components/ExploreTrendChart'
import ExploreCompareChart from '@/components/ExploreCompareChart'
export default {
  name: 'ExploreView',
  components: {
    MenuToolbar, 
    FeaturedCard,
    FiltersPanel,
    DockedTooltip,
    ExploreMap,
    ExploreTrendChart,
    ExploreCompareChart
  },
  data() {
    return {
      tabs: ['map', 'trend', 'compare'],
      tab: null
    }
  },
  computed: {
    ...mapState(['indicatorMenu', 'indicator', 'source', 'locale', 'featuredIndicators', 'exploreData']),
    showIntro() {
      return !this.indicator && !router.currentRoute.query.indicator;
    },
    dockedTooltipHelpMessage() {
      switch (this.tab) {
        case 0:
          return i18n.t('tools.explore.docked_tooltip.help_messages.map');
        case 1:
          return i18n.t('tools.explore.docked_tooltip.help_messages.trend');
        case 2:
          return i18n.t('tools.explore.docked_tooltip.help_messages.compare');
      }
      return null
    },
    breadcrumbs() {
      return [
        {
          text: i18n.t('tools.explore.name'),
          disabled: false,
          href: '/explore'
        },
        {
          text: (this.exploreData.category.parentCategoryId ? this.exploreData.category['name_' + this.locale] + ' - ' : '') + this.indicator['name_' + this.locale],
          disabled: true
        }
      ]
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$store.dispatch('setIndicator', null)
      vm.$store.dispatch('setExploreData', null)
      vm.$store.dispatch('setToolRoute', { key: 'explore', route: to.fullPath });
    });
  },
  beforeRouteUpdate(to, from, next) {
    if (from.query.indicator && !to.query.indicator) {
      this.setIndicator(null)
      this.setExploreData(null)
    }
    this.setToolRoute({ key: 'explore', route: to.fullPath })
    next();
  },
  mounted () {
    if (router.currentRoute.query.indicator && this.indicatorMenu) {
      this.setIndicator(this.indicatorMenu.categories
        .flatMap(category => category.subcategories.flatMap(sc => sc.items).concat(category.items))
        .find(item => item.id == router.currentRoute.query.indicator))
    } else {
      this.setIndicator(null)
    }
    if (router.currentRoute.query.tab) {
      this.selectTab(router.currentRoute.query.tab)
    }
    if (!this.featuredIndicators) {
      this.getFeaturedIndicators()
    }
  },
  updated () {
    if (router.currentRoute.query.indicator && this.indicatorMenu) {
      let matchedIndicator = this.indicatorMenu.categories
        .flatMap(category => category.subcategories.flatMap(sc => sc.items).concat(category.items))
        .find(item => item.id == router.currentRoute.query.indicator)
      if (matchedIndicator?.id !== this.indicator?.id) {
        this.setIndicator(matchedIndicator)
      }
    } else {
      this.setIndicator(null)
    }
  },
  methods: {
    ...mapActions(['setIndicator', 'setExploreData', 'setExploreTab', 'getFeaturedIndicators', 'setToolRoute']),
    goBack() {
      router.push('/explore')
    },
    selectItem(item) {
      if (item.id !== this.indicator?.id) {
        this.setIndicator(item)
        router.replace({
          query: {
            lang: router.currentRoute.query.lang,
            indicator: item.id,
            tab: router.currentRoute.query.tab
          },
        }).then(() => this.selectTab('map'));
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
        this.setExploreTab(selectedTab);
      }
    },
  },
}
</script>

<style lang="scss" scoped>
  .explore-content {
    height: 725px;
  }
</style>
