<template>
  <v-container v-if="locationMenu" fluid class="pa-0 fill-height">
    <v-row class="no-gutters flex-column fill-height">
      <v-col cols="auto" class="shrink">
        <MenuToolbar
          class="flex-column"
          :menu="locationMenu"
          :selectItem="selectItem"
        ></MenuToolbar>
      </v-col>
      <v-col v-if="!community" cols="auto" class="pa-4 shrink">
        <h1 class="text-h3 mb-2">{{ $t('tools.my_community.name') }}</h1>
        <p>{{ $t('tools.my_community.long_description') }}</p>
        <p>{{ $t('tools.my_community.get_started') }}</p>
      </v-col>
      <v-col v-if="!community" cols="auto" class="pa-4 grow">
        <!--<l-map
          ref="communityMap"
          :zoom="zoom"
          :center="center"
          :options="{ zoomDelta: 0.5, zoomSnap: 0.5, preferCanvas: true }"
          :style="{ height: '100%' }"
          v-resize:debounce.100="resizeHandler"
          @ready="initializeMap"
        >
          <l-tile-layer
            url="https://stamen-tiles.a.ssl.fastly.net/toner-background/{z}/{x}/{y}.png"
            :options="{ crossOrigin: 'anonymous' }"
            attribution="Map tiles by <a href='http://stamen.com'>Stamen Design</a>, 
            under <a href='http://creativecommons.org/licenses/by/3.0'>CC BY 3.0</a>. 
            Data by <a href='http://openstreetmap.org'>OpenStreetMap</a>, 
            under <a href='http://www.openstreetmap.org/copyright'>ODbL</a>."
          />
          <l-tile-layer
            url="https://stamen-tiles.a.ssl.fastly.net/toner-hybrid/{z}/{x}/{y}.png"
            :options="{ crossOrigin: 'anonymous' }"
            attribution="Map tiles by <a href='http://stamen.com'>Stamen Design</a>, 
            under <a href='http://creativecommons.org/licenses/by/3.0'>CC BY 3.0</a>. 
            Data by <a href='http://openstreetmap.org'>OpenStreetMap</a>, 
            under <a href='http://www.openstreetmap.org/copyright'>ODbL</a>."
          />
        </l-map>-->
      </v-col>
      <v-col v-if="community" cols="auto" class="pa-4 grow">
        <h1 class="text-h3 mb-2">{{ community['name_' + locale] }}</h1>
        <section v-for="category in indicatorMenu.categories" :key="'category_' + category.id">
          <h2>{{ category['name_' + locale]}}</h2>
          <section v-for="indicator in category.items" :key="'indicator_' + indicator.id" class="ml-8">
            <h3>{{ indicator['name_' + locale]}}</h3>
          </section>
        </section>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import router from '@/router/index'
import { latLng } from 'leaflet'
//import { LMap, LTileLayer } from 'vue2-leaflet'
import MenuToolbar from '@/components/MenuToolbar'
export default {
  name: 'MyCommunityView',
  components: {
    //LMap,
    //LTileLayer,
    MenuToolbar,
  },
  data() {
    return {
      zoom: 9,
      center: latLng(29.43445, -98.473562383),
      communityId: null,
    }
  },
  computed: {
    ...mapState(['locale', 'locationMenu', 'community', 'indicatorMenu']),
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$store.dispatch('setCommunity', null)
    });
  },
  beforeRouteUpdate(to, from, next) {
    if (from.query.location && !to.query.location) {
      this.setCommunity(null)
    }
    next();
  },
  mounted () {
    if (router.currentRoute.query.location && this.locationMenu) {
      this.setCommunity(this.locationMenu.categories
        .flatMap(category => category.items)
        .find(item => item.id == router.currentRoute.query.location && item.categoryId == router.currentRoute.query.locationType))
    } else {
      this.setCommunity(null)
    }
  },
  updated () {
    if (router.currentRoute.query.location && this.locationMenu) {
      this.setCommunity(this.locationMenu.categories
        .flatMap(category => category.items)
        .find(item => item.id == router.currentRoute.query.location && item.categoryId == router.currentRoute.query.locationType))
    } else {
      this.setCommunity(null)
    }
  },
  methods: {
    ...mapActions(['setCommunity']),
    selectItem(item) {
      if (item.id !== this.community?.id || item.categoryId !== this.community?.locationTypeId) {
        this.setCommunity(item)
        router.replace({
          query: {
            ...router.currentRoute.query,
            location: item.id,
            locationType: item.categoryId
          },
        })
      }
    },
    initializeMap() {},
    resizeHandler() {
      this.$refs.indicatorMap?.mapObject?.invalidateSize()
    },
  },
}
</script>

<style lang="scss" scoped></style>
