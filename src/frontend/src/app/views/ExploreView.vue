<template>
  <v-container v-if="indicatorMenu" fluid class="fill-height pa-0">
    <v-row class="no-gutters flex-wrap flex-column fill-height">
      <v-col cols="auto" class="grow">
        <section :class="'page-header d-flex flex-column light--text px-12 pb-4 ' + (!showIntro ? 'main-content' : '')">
          <v-breadcrumbs :items="breadcrumbs" class="mb-8" dark>
            <template v-slot:divider>
              <v-icon>mdi-chevron-right</v-icon>
            </template>
          </v-breadcrumbs>
          <h1 v-if="showIntro" class="text-dive-h3">{{ $t('tools.explore.name') }}</h1>
          <div v-if="indicator && exploreData" cols="auto" id="explore_indicator">
            <h1 class="text-dive-h3">
              <span v-if="exploreData.category.parentCategoryId">
                {{ exploreData.category['name_' + locale] }} - 
              </span>
              {{ indicator['name_' + locale] }}
              <indicator-definition :indicator="indicator"></indicator-definition>
            </h1>
            <h2 v-if="source" class="text-subtitle-1 mb-2">{{ source['name_' + locale] }}</h2>
          </div>
          <div v-if="showIntro" class="font-weight-medium mt-2" style="font-size: 1.25rem;">
            {{ $t('tools.explore.headline') }}
            {{ $t('tools.explore.long_description') }}
          </div>
        </section>
      </v-col>
      <v-col cols="auto" class="shrink sticky-menu">
        <MenuToolbar
          class="flex-column"
          :menu="indicatorMenu"
          :selectItem="selectItem"
          :flattenSingleItems="false"
          :searchType="$t('tools.common.indicators')"
        ></MenuToolbar>
      </v-col>
      <v-col v-if="showIntro" cols="auto" class="pa-4">
          <section class="mb-8">
            <h2 class="text-dive-h4 mt-16 mb-2 font-weight-light text-center" style="text-transform: uppercase;">{{ $t('tools.common.featured_indicators') }}</h2>
            <p style="margin: 0 30%; font-size: 1.25em;">{{ $t('tools.tables.get_started') }}</p>
          </section>
          <section v-if="featuredIndicators" class="d-flex" :class="{ 'flex-row': $vuetify.breakpoint.mdAndUp, 'flex-column': $vuetify.breakpoint.smAndDown }">
            <template v-for="indicator in featuredIndicators">
              <featured-card 
                :key="indicator.id" 
                :item="indicator"
                :name="indicator['name_' + locale]" 
                :postText="$t('tools.explore.docked_tooltip.source') + ': ' + indicator.source['name_' + locale]"
                :about_route="'about-data?indicator=' + indicator.id" 
                :view_route="indicator.route"
                :click_route="selectItem"
                :primary_button_text="$t('featured_card.view')"
                :secondary_button_text="$t('featured_card.learn_more')"
                :iconPath="getCategoryIconPath(indicator.categoryId)"
                dense
              >
              </featured-card>
            </template>
          </section>
      </v-col>
      <v-col v-if="indicator" cols="auto" class="explore-content">
        <v-row class="fill-height no-gutters mt-4">
          <v-col cols="3">
            <v-row class="fill-height no-gutters flex-column px-4">
              <filters-panel></filters-panel>
              <docked-tooltip class="grow" :helpMessage="dockedTooltipHelpMessage" :activeTab="tab"></docked-tooltip>
            </v-row>
          </v-col>
          <v-col cols="9" class="px-4">
            <v-row class="fill-height no-gutters flex-column">
              <v-col cols="auto" class="d-flex shrink">
                <v-tabs v-model="tab" grow color="red">
                  <v-tab v-for="tab in tabs" :key="tab" @click="selectTab(tab)">
                    <span v-if="layout === 'gallery' && locale === 'es'">&nbsp;Opciones {{ tab === 'map' ? 'de' : 'del' }}&nbsp;</span>
                    {{ $t('tools.explore.tabs.' + tab + '.name') }}
                    <span v-if="layout === 'gallery' && locale === 'en'">&nbsp;Options</span>
                  </v-tab>
                </v-tabs>
                <div class="d-flex align-center">
                  <label aria-label id="layoutLabel" class="v-label v-label--active theme--light mx-4">{{ $t('tools.explore.layouts.layout') }}</label>
                  <v-btn-toggle v-model="layout" class="my-2" color="red" mandatory aria-labelledby="layoutLabel" rounded>
                    <v-btn :value="'tabs'" color="red" dark small><v-icon color="white" class="mr-2">mdi-tab</v-icon>{{ $t('tools.explore.layouts.tabs') }}</v-btn>
                    <v-btn :value="'gallery'" color="red" dark small><v-icon color="white" class="mr-2">mdi-view-dashboard</v-icon>{{ $t('tools.explore.layouts.gallery') }}</v-btn>
                  </v-btn-toggle>
                </div>
              </v-col>
              <v-col  v-if="layout === 'tabs'" cols="auto" class="grow">
                <v-tabs-items v-model="tab" class="fill-height">
                  <v-tab-item v-for="tab in tabs" :key="tab" transition="none" reverse-transition="none" class="fill-height">
                    <explore-map v-if="tab === 'map'"></explore-map>
                    <explore-trend-chart v-if="tab === 'trend'"></explore-trend-chart>
                    <explore-compare-chart v-if="tab === 'compare'"></explore-compare-chart>
                  </v-tab-item>
                </v-tabs-items>
              </v-col>
              <v-col v-if="layout === 'gallery'" cols="auto" class="gallery-content">
                <v-row class="no-gutters">
                  <v-col cols="12">
                    <explore-tools-panel
                      key="gallery-map-tools-panel" 
                      v-if="filters && filterSelections && exploreData && tabs[tab] === 'map'"
                      :showHighlightFilteredLocation="true"
                      :highlightFilteredLocation="highlightFilteredLocation"
                      :setHighlightFilteredLocation="setHighlightFilteredLocation"
                      :showLabels="showMapLabels"
                      :showMapControls="indicator.showReport"
                      :setLabelsOrLinesOption="setShowMapLabels"
                      dataVisualElementId="explore_map"
                      dataVisualName="map"
                      :layout="layout"
                    >
                    </explore-tools-panel>
                    <explore-tools-panel 
                      key="gallery-trend-tools-panel"
                      v-if="filters && filterSelections && exploreData && tabs[tab] === 'trend'"
                      :showCompareOptions="true"
                      :labelsOrLinesOption="trendLabelsOrLines"
                      :setLabelsOrLinesOption="setTrendLabelsOrLines"
                      dataVisualElementId="trend_chart_container"
                      dataVisualName="trend_chart"
                      :includeLocationFilterInCompareBy="false"
                      :includeYearFilterInCompareBy="false"
                      :setCompareSelections="setTrendCompareSelections"
                      :layout="layout"
                    >
                    </explore-tools-panel>
                    <explore-tools-panel 
                      key="gallery-compare-tools-panel"
                      v-if="filters && filterSelections && exploreData && tabs[tab] === 'compare'"
                      :showCompareOptions="true"
                      :labelsOrLinesOption="compareLabelsOrLines"
                      :setLabelsOrLinesOption="setCompareLabelsOrLines"
                      dataVisualElementId="compare_chart_container"
                      dataVisualName="compare_chart"
                      :setCompareSelections="setCompareSelections"
                      :layout="layout"
                    >
                    </explore-tools-panel>
                  </v-col>
                </v-row>
                <v-row id="gallery-data-visuals" class="fill-height no-gutters">
                  <v-col cols="5" class="fill-height">
                    <v-sheet outlined class="pa-4 fill-height">
                      <explore-map :layout="layout"></explore-map>
                    </v-sheet>
                  </v-col>
                  <v-col cols="7">
                    <v-sheet outlined class="pa-4" style="width: 100%; height: 50%;">
                      <explore-trend-chart :layout="layout"></explore-trend-chart>
                    </v-sheet>
                    <v-sheet outlined class="pa-4" style="width: 100%; height: 50%;">
                      <explore-compare-chart :layout="layout"></explore-compare-chart>
                    </v-sheet>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex'
import router from '@/app/router/index'
import i18n from '@/i18n'
import MenuToolbar from '@/app/components/MenuToolbar'
import FeaturedCard from '@/app/components/FeaturedCard'
import FiltersPanel from '@/app/components/FiltersPanel'
import DockedTooltip from '@/app/components/DockedTooltip'
import ExploreMap from '@/app/components/ExploreMap'
import ExploreTrendChart from '@/app/components/ExploreTrendChart'
import ExploreCompareChart from '@/app/components/ExploreCompareChart'
import IndicatorDefinition from '@/app/components/IndicatorDefinition.vue'
import ExploreToolsPanel from '@/app/components/ExploreToolsPanel.vue'
import { getCategoryIconPath } from '@/services/icons'
export default {
  name: 'ExploreView',
  components: {
    MenuToolbar, 
    FeaturedCard,
    FiltersPanel,
    DockedTooltip,
    ExploreMap,
    ExploreTrendChart,
    ExploreCompareChart,
    IndicatorDefinition,
    ExploreToolsPanel
  },
  data() {
    return {
      tabs: ['map', 'trend', 'compare'],
      tab: null,
      layout: 'tabs'
    }
  },
  computed: {
    ...mapState(['indicatorMenu', 'indicator', 'source', 'locale', 'featuredIndicators', 'exploreData', 'filterSelections',
      'highlightFilteredLocation', 'showMapLabels', 'trendLabelsOrLines', 'compareLabelsOrLines']),
    ...mapGetters(['filters']),
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
      let crumbs = [
        {
          text: i18n.t('home_view.name'),
          disabled: false,
          href: '/home'
        },
        {
          text: i18n.t('tools.explore.name'),
          disabled: !this.exploreData,
          href: '/explore'
        }
      ];

      if (this.exploreData && this.indicator) {
        crumbs.push({
          text: (this.exploreData.category.parentCategoryId ? this.exploreData.category['name_' + this.locale] + ' - ' : '') + this.indicator['name_' + this.locale],
          disabled: true
        });
      }

      return crumbs;
    }
  },
  watch: {
    layout(newValue) {
      if (router.currentRoute.query.layout !== newValue) {
        router.replace({
          query: {
            ...router.currentRoute.query,
            layout: newValue
          }
        })
      }
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
        .find(item => item.id == router.currentRoute.query.indicator)).then(() => {
          if (router.currentRoute.query.tab) {
            this.selectTab(router.currentRoute.query.tab)
          }
        })
    } else {
      this.setIndicator(null)
    }
    if (!this.featuredIndicators) {
      this.getFeaturedIndicators()
    }
    this.layout = router.currentRoute.query.layout || 'tabs'
  },
  updated () {
    if (router.currentRoute.query.indicator && this.indicatorMenu) {
      let matchedIndicator = this.indicatorMenu.categories
        .flatMap(category => category.subcategories.flatMap(sc => sc.items).concat(category.items))
        .find(item => item.id == router.currentRoute.query.indicator)
      if (matchedIndicator?.id !== this.indicator?.id) {
        this.setIndicator(matchedIndicator).then(() => {
          if (router.currentRoute.query.tab) {
            this.selectTab(router.currentRoute.query.tab)
          }
        })
      }
    } else {
      this.setIndicator(null)
    }
  },
  methods: {
    ...mapActions(['setIndicator', 'setExploreData', 'setExploreTab', 'getFeaturedIndicators', 'setToolRoute',
      'setHighlightFilteredLocation', 'setShowMapLabels', 'setTrendLabelsOrLines', 'setTrendCompareSelections', 'setCompareLabelsOrLines', 'setCompareSelections']),
    selectItem(item) {
      if (item.id !== this.indicator?.id) {
        this.setIndicator(item);
        this.setExploreData(null);
        router.replace({
          query: {
            lang: router.currentRoute.query.lang,
            indicator: item.id,
            tab: router.currentRoute.query.tab
          },
        }).then(() => this.selectTab(router.currentRoute.query.tab));
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
    getCategoryIconPath(category) {
      return getCategoryIconPath(category);
    }
  },
}
</script>

<style lang="scss" scoped>
  .explore-content {
    height: 820px;
  }
  .gallery-content {
    height: 690px;
  }
</style>
