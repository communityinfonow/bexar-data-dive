<template>
  <v-container v-if="indicatorMenu" fluid class="pa-0 fill-height align-start">
    <v-container fluid class="pa-0">
      <v-row class="no-gutters">
        <v-col cols="12">
          <MenuToolbar
            class="flex-column"
            :menu="indicatorMenu"
            :selectItem="selectItem"
          ></MenuToolbar>
        </v-col>
        </v-row>
      <v-row v-if="showIntro" class="no-gutters">
        <v-col cols="12" class="pa-4">
            <h1 class="text-h3 mb-2">{{ $t('tools.dashboard.name') }}</h1>
            <p>{{ $t('tools.dashboard.long_description') }}</p>
            <p>{{ $t('tools.dashboard.get_started') }}</p>
        </v-col>
      </v-row>
      <v-row v-if="indicator" class="no-gutters">
        <v-col cols="12" class="pt-4 px-4">
            <h1 class="text-h3 mb-2">{{ indicator['name_' + locale] }}</h1>
        </v-col>
      </v-row>
      <v-row v-if="indicator" class="no-gutters">
        <v-col cols="9" class="px-4">
          <v-tabs v-model="tab" grow>
            <v-tab v-for="tab in tabs" :key="tab" @click="selectTab(tab)">
              {{ $t('tools.dashboard.tabs.' + tab) }}
            </v-tab>
          </v-tabs>
          <v-tabs-items v-model="tab" class="fill-height">
            <v-tab-item v-for="tab in tabs" :key="tab" :transition="false" :reverse-transition="false" class="fill-height">
              <dashboard-map v-if="tab === 'map'"></dashboard-map>
            </v-tab-item>
          </v-tabs-items>
        </v-col>
        <v-col cols="3">
          <filters-panel></filters-panel>
        </v-col>
      </v-row>
      <v-row>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import router from '@/router/index'
import MenuToolbar from '@/components/MenuToolbar'
import FiltersPanel from '@/components/FiltersPanel'
import DashboardMap from '../components/DashboardMap.vue'
export default {
  name: 'MyCommunityView',
  components: {
    MenuToolbar, 
    FiltersPanel,
    DashboardMap
  },
  data() {
    return {
      tabs: ['map', 'trend', 'compare'],
      tab: null
    }
  },
  computed: {
    ...mapState(['indicatorMenu', 'indicator', 'locale']),
    showIntro() {
      return !this.indicator && !router.currentRoute.query.indicator;
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
    ...mapActions(['setIndicator']),
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
      }
    },
  },
}
</script>

<style lang="scss" scoped></style>
