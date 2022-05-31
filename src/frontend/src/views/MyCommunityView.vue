<template>
  <v-container fluid class="pa-0 fill-height">
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
        <l-map
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
        </l-map>
      </v-col>
      <v-col v-if="community" cols="auto" class="pa-4 grow">
        <h1 class="text-h3 mb-2">{{ communityName }}</h1>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import { latLng } from 'leaflet'
import { LMap, LTileLayer } from 'vue2-leaflet'
import MenuToolbar from '@/components/MenuToolbar'
export default {
  name: 'MyCommunityView',
  components: {
    LMap,
    LTileLayer,
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
    ...mapState(['locale', 'locationMenu', 'community']),
    communityName() {
      return this.community['name_' + this.locale]
    }
  },
  mounted () {
    ;
  },
  methods: {
    ...mapActions(['setCommunity']),
    selectItem(item) {
      this.setCommunity(item)
    },
    initializeMap() {},
    resizeHandler() {
      this.$refs.indicatorMap?.mapObject?.invalidateSize()
    },
  },
}
</script>

<style lang="scss" scoped></style>
