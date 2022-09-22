<template>
  <v-container v-if="locationMenu" fluid class="pa-0 fill-height">
    <v-row class="no-gutters flex-column fill-height">
      <v-col cols="auto">
        <MenuToolbar
          class="flex-column"
          :menu="locationMenu"
          :selectItem="selectItem"
          :flattenSingleItems="true"
        ></MenuToolbar>
      </v-col>
      <v-col cols="auto">
        <v-breadcrumbs :items="breadcrumbs">
          <template v-slot:divider>
            <v-icon>mdi-chevron-right</v-icon>
          </template>
        </v-breadcrumbs>
      </v-col>
      <v-col v-if="showIntro" cols="auto" class="pa-4 shrink">
        <h1 class="text-h3 mb-2">{{ $t('tools.community.name') }}</h1>
        <p>{{ $t('tools.community.long_description') }}</p>
        <p>{{ $t('tools.community.get_started') }}</p>
      </v-col>
      <v-col v-if="showIntro" cols="auto" class="pa-4 grow">
        <l-map
          v-if="componentInitialized"
          ref="selectionMap"
          :zoom="zoom"
          :center="center"
          :options="{ zoomDelta: 0.5, zoomSnap: 0.5, preferCanvas: true }"
          :style="{ height: '100%' }"
          v-resize:debounce.100="resizeHandler"
          @ready="initializeSelectionMap"
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
          <l-geo-json
            v-if="selectionGeojson"
            :geojson="selectionGeojson"
            :options="options"
          ></l-geo-json>
          <l-control
            position="bottomright"
            class="layer-control"
            v-if="layers.length"
          >
            <v-card tile outlined :style="{ boxShadow: 'none !important' }">
              <v-card-title class="pb-0 text--primary">
                <v-icon color="accent">mdi-layers</v-icon>
                {{ $t('tools.community.community_types') }}
              </v-card-title>
              <v-card-text>
                <v-radio-group
                  v-model="selectedLayer"
                  @change="drawSelectionMap"
                >
                  <v-radio 
                    color="accent"
                    v-for="layer in layers" 
                    :key="layer.id" 
                    :value="layer" 
                    :label="layer['name_' + locale]">
                  </v-radio>
                </v-radio-group>
              </v-card-text>
            </v-card>
          </l-control>
        </l-map>
      </v-col>
      <v-col v-if="community" cols="auto" class="pa-4 grow">
        <v-row class="mb-8">
          <v-col cols="2">
            <l-map
              v-if="componentInitialized"
              ref="communityMap"
              :zoom="zoom"
              :center="center"
              :options="{ zoomControl: false, preferCanvas: true }"
              :style="{ height: '200px' }"
              v-resize:debounce.100="resizeHandler"
              @ready="initializeCommunityMap"
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
              <l-geo-json
                v-if="communityGeojson"
                :geojson="communityGeojson"
                :options="options"
              ></l-geo-json>
            </l-map>
          </v-col>
          <v-col cols="10">
            <div class="d-flex justify-space-between">
              <h1 class="text-h3 mt-2 mb-4" id="community_name">{{ community.location['name_' + locale] }}</h1>
              <download-menu :downloadData="downloadCommunityData"></download-menu>
            </div>
            <v-select 
              style="width: 200px;"
              color="accent"
              :label="$t('tools.community.skip')"
              :items="categories"
              :item-text="(item) => { return item['name_' + locale] + (item.hasData ? '' : ' (' + $t('tools.community.coming_soon') + ')') }"
              :item-disabled="(item) => !item.hasData"
              item-value="id"
              v-model="selectedCategory"
              @change="skipToCategory"
            >
            </v-select>
          </v-col>
        </v-row>
        <h2 v-if="noCommunityData" class="text-h4">{{ $t('tools.community.data_coming_soon')}}</h2>
        <section v-for="data in sortedData" :key="'category_' + data.category.id">
          <div v-if="data.category.hasData">
            <h2 :id="'category_' + data.category.id" class="text-h4 mb-4">{{ data.category['name_' + locale]}}</h2>
            <template v-for="item in data.indicators">
              <template v-if="item.indicators">
                <div :key="'category_' + item.category.id">
                  <template v-for="subItem in item.indicators">
                    <community-indicator :item="subItem" :parentName="item.category['name_' + locale]" :key="'sub_indicator_' + subItem.indicator.id" :maxDemographics="maxDemographics"></community-indicator>
                  </template>
                </div>
              </template>
              <template v-else>
                <community-indicator :item="item" :key="'indicator_' + item.indicator.id" :maxDemographics="maxDemographics"></community-indicator>
              </template>
            </template>
          </div>
        </section>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import i18n from '@/i18n'
import goTo from 'vuetify/lib/services/goto'
import { mapActions, mapState } from 'vuex'
import router from '@/router/index'
import axios from 'axios'
import L from 'leaflet'
import { latLng } from 'leaflet'
import { LMap, LTileLayer, LControl, LGeoJson } from 'vue2-leaflet'
import { feature, featureCollection } from '@turf/helpers'
import MenuToolbar from '@/components/MenuToolbar'
import CommunityIndicator from '@/components/CommunityIndicator'
import DownloadMenu from '@/components/DownloadMenu'

export default {
  name: 'CommunityView',
  components: {
    LMap,
    LTileLayer,
    LControl,
    LGeoJson,
    MenuToolbar,
    CommunityIndicator,
    DownloadMenu
  },
  data() {
    return {
      componentInitialized: false,
			selectionMapInitialized: false,
      communityMapInitialized: false,
      zoom: 10,
			center: latLng(29.43445, -98.473562383),
      selectedLayer: null,
      selectionGeojson: null,
      communityGeojson: null,
      refreshOptions: false,
      selectedCategory: null
    }
  },
  computed: {
    ...mapState(['locale', 'locationMenu', 'community' ]),
    showIntro() {
      return !this.community && !router.currentRoute.query.location;
    },
    breadcrumbs() {
      let crumbs = [
        {
          text: i18n.t('home_view.name'),
          disabled: false,
          href: '/home'
        },
        {
          text: i18n.t('tools.community.name'),
          disabled: !this.community,
          href: '/community'
        }
      ];

      if (this.community) {
        crumbs.push({
          text: this.community.location['name_' + this.locale],
          disabled: true
        });
      }

      return crumbs;
    },
    noCommunityData() {
      return this.community && !this.categories.some(c => c.hasData);
    },
    sortedData() {
      let sortedData = JSON.parse(JSON.stringify(this.community?.indicatorData));
      sortedData.forEach(c => {
        c.category.hasData = !!c.indicators.find(i => !!i.year) || c.subcategories.flatMap(sc => sc.indicators).find(i => !!i.year);
        if (c.subcategories.length) {
          c.indicators = c.indicators.concat(c.subcategories);
          c.indicators.sort((a, b) => {
            let aName = a.indicator ? a.indicator['name_' + this.locale] : a.category['name_' + this.locale];
            let bName = b.indicator ? b.indicator['name_' + this.locale] : b.category['name_' + this.locale];
            if (aName < bName) {
              return -1;
            } else if (aName > bName) {
              return 1;
            } else {
              return 0;
            }
          });
        }
      });

      return sortedData;
    },
    maxDemographics() {
      return this.community ? Math.max(...this.community.indicatorData.flatMap(id => id.indicators.concat(id.subcategories.flatMap(sc => sc.indicators))).map(i => i.demographicData.length)) : 0;
    },
    categories() {
      return this.sortedData.map(i => i.category);
    },
    layers() {
      return this.locationMenu?.categories?.map(locationType => {
        return {
          id: locationType.id,
          name_en: locationType.name_en,
          name_es: locationType.name_es
        };
      });
    },
    options() {
			this.refreshOptions;
			return {
				onEachFeature: this.onEachFeature
			}
		}
  },
  watch: {
    locale() {
			this.drawSelectionMap()
		},
    community() {
      this.drawCommunityMap();
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$store.dispatch('setCommunity', null)
      vm.$store.dispatch('setToolRoute', { key: 'community', route: to.fullPath })
    });
  },
  beforeRouteUpdate(to, from, next) {
    if (from.query.location && !to.query.location) {
      this.setCommunity(null)
    }
    this.setToolRoute({ key: 'community', route: to.fullPath })
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
    setTimeout(() => { 
			this.componentInitialized = true;
      this.selectedLayer = this.layers?.find(l => l.id === '4')
			if (this.selectionMapInitialized) {
				this.drawSelectionMap();
			}
		}, 100);
  },
  updated () {
    if (router.currentRoute.query.location && this.locationMenu) {
      let matchedCommunity = this.locationMenu.categories
        .flatMap(category => category.items)
        .find(item => item.id == router.currentRoute.query.location && item.categoryId == router.currentRoute.query.locationType)
      if (matchedCommunity?.id !== this.community?.location.id || matchedCommunity?.categoryId !== this.community?.location.typeId) {
        this.getCommunityData(matchedCommunity)
      }
    } else {
      this.setCommunity(null)
    }
  },
  methods: {
    ...mapActions(['setCommunity', 'getCommunityData', 'setToolRoute']),
    selectItem(item) {
      if (item.id !== this.community?.id || item.categoryId !== this.community?.locationTypeId) {
        this.getCommunityData(item)
        router.replace({
          query: {
            ...router.currentRoute.query,
            location: item.id,
            locationType: item.categoryId
          },
        });
      }
    },
    initializeSelectionMap() {
			this.selectionMapInitialized = true;
      this.drawSelectionMap();
		},
    initializeCommunityMap() {
			this.communityMapInitialized = true;
      this.drawCommunityMap();
		},
		resizeHandler() {
			this.$refs.selectionMap?.mapObject?.invalidateSize();
      this.$refs.communityMap?.mapObject?.invalidateSize();
		},
    drawSelectionMap() {
      if (this.selectedLayer) {
        axios.get('/api/community-locations', { params: { 
            locationType: this.selectedLayer.id
          }
        }).then(response => {
          this.selectionGeojson = featureCollection(response.data.map(location => 
            feature(JSON.parse(location.geojson), 
              {
                name: location['name_' + this.locale],
                id: location.id,
                typeId: location.typeId
              }, 
              { 
                id: location.id
              }
            )
          ));
          this.refreshOptions = Math.random(); // force a refresh
        });
      };
    },
    drawCommunityMap() {
      if (this.community) {
        axios.get('/api/community-location', { params: { 
            location: this.community.location.id,
            locationType: this.community.location.typeId
          }
        }).then(response => {
          this.communityGeojson = feature(JSON.parse(response.data.geojson), 
            {
              name: response.data['name_' + this.locale],
              id: response.data.id,
              typeId: response.data.typeId
            }, 
            { 
              id: response.data.id
            }
          );
          this.refreshOptions = Math.random(); // force a refresh
          this.$refs.communityMap?.mapObject.fitBounds(L.geoJSON(this.communityGeojson).getBounds());
        });
      };
    },
    onEachFeature(feature, layer) {
			layer.bindTooltip(feature.properties.name);
      layer.options.color = '#3b5a98';
      layer.on('click', () => {
        this.selectItem({
          id: feature.properties.id,
          categoryId: feature.properties.typeId
        })
      });
		},
    skipToCategory(category) {
      goTo("#category_" + category)
    },
    downloadCommunityData() {
      //TODO: espanol headers
      let csv = 'Category,Indicator,Source,Location,Year,Race/Ethnicity,Value,Range';
      this.sortedData.forEach(item => {
        item.subcategories.forEach(subcat => {
          csv += this.generateIndicatorCsvRecords(item.category, subcat.category, subcat.indicators)
        });
        csv += this.generateIndicatorCsvRecords(item.category, null, item.indicators.filter(ind => !ind.indicators));
      });
      
      let downloadLink = document.createElement('a');
      downloadLink.download = 'community_data.csv';
      downloadLink.href = 'data:text/csv;charset=utf-8,' + encodeURIComponent(csv);
      downloadLink.click();
    },
    generateIndicatorCsvRecords(cat, subcat, inds) {
      return inds.map(ind => {
        return ind.demographicData.map(data => {
          return '\n"' + cat['name_' + this.locale] + '",' 
            + '"' + ((subcat ? subcat['name_' + this.locale] + ' - ' : '') + ind.indicator['name_' + this.locale])+ '",'
            + '"' + ind.source['name_' + this.locale] + '",'
            + '"' + this.community.location['name_' + this.locale] + '",'
            + ind.year + ','
            + '"' + (data.raceFilter['name_' + this.locale] || i18n.t('data.all')) + '",'
            + (data.suppressed ? i18n.t('data.suppressed') : data.value === null ? i18n.t('data.no_data') : data.value) + ','
            + (data.moeLow && data.moeHigh ? (data.moeLow + ' - ' + data.moeHigh) : '');
        });
      }).filter(record => record !== '');
    }
  },
}
</script>

<style lang="scss" scoped></style>
