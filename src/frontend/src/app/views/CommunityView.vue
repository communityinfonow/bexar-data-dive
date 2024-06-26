<template>
  <v-container v-if="locationMenu" fluid class="pa-0 fill-height">
    <v-row class="no-gutters flex-column fill-height">
      <v-col cols="auto" class="grow">
        <section :class="'page-header d-flex flex-column light--text px-12 pb-4 ' +  + (!showIntro ? 'main-content' : '')">
          <v-breadcrumbs :items="breadcrumbs" class="mb-8" dark>
            <template v-slot:divider>
              <v-icon>mdi-chevron-right</v-icon>
            </template>
          </v-breadcrumbs>
          <h1 v-if="showIntro" class="text-dive-h3">{{ $t('tools.community.name') }}</h1>
          <div v-if="showIntro" class="font-weight-medium mt-2" style="font-size: 1.25rem;">
            {{ $t('tools.community.headline') }}
            {{ $t('tools.community.long_description') }}
          </div>
          <div v-if="community">
            <v-row>
              <v-col cols="2">
                <l-map
                  v-if="componentInitialized"
                  ref="communityMap"
                  :zoom="zoom"
                  :center="center"
                  :options="{ zoomControl: false, preferCanvas: true }"
                  :style="{ height: '290px' }"
                  v-resize:debounce.100="resizeHandler"
                  @ready="initializeCommunityMap"
                >
                  <l-tile-layer
                    url="https://tiles.stadiamaps.com/tiles/stamen_toner_background/{z}/{x}/{y}{r}.png"
                    :options="{ crossOrigin: 'anonymous' }"
                    :attribution="$t('tools.common.map_attribution')"
                  />
                  <l-tile-layer
                    url="https://tiles.stadiamaps.com/tiles/stamen_toner_lines/{z}/{x}/{y}{r}.png"
                    :options="{ crossOrigin: 'anonymous' }"
                    :attribution="$t('tools.common.map_attribution')"
                  ></l-tile-layer>
                  <l-tile-layer
                    url="https://tiles.stadiamaps.com/tiles/stamen_toner_labels/{z}/{x}/{y}{r}.png"
                    :options="{ crossOrigin: 'anonymous' }"
                    :attribution="$t('tools.common.map_attribution')"
                  ></l-tile-layer>
                  <l-geo-json
                    v-if="communityGeojson"
                    :geojson="communityGeojson"
                    :options="options"
                  ></l-geo-json>
                </l-map>
              </v-col>
              <v-col cols="10">
                <div class="d-flex flex-column fill-height">
                  <div class="d-flex flex-row justify-space-between">
                    <h1 class="text-dive-h3" id="community_name">
                      {{ community.location['name_' + locale] }}
                      <span v-if="community.location.typeId === '7'">
                        ({{ locationMenu.categories.find(c => c.id === customLocations.find(cl => cl.id === community.location.id).typeId)['name_' + this.locale]}})
                      </span>
                    </h1>
                    <div>
                      <button-menu :downloadData="downloadCommunityData"></button-menu>
                    </div>
                  </div>
                  <v-spacer></v-spacer>
                  <div>
                    <v-select 
                      style="width: 240px;"
                      filled
                      rounded
                      dense
                      background-color="#fff"
                      :label="$t('tools.community.skip')"
                      :items="categories"
                      :item-text="(item) => { return item['name_' + locale] + (item.hasData ? '' : ' (' + $t('tools.community.coming_soon') + ')') }"
                      :item-disabled="(item) => !item.hasData"
                      item-value="id"
                      v-model="selectedCategory"
                      @change="skipToCategory"
                    >
                    </v-select>
                    <v-select
                      style="width: 240px;"
                      filled
                      rounded
                      dense
                      background-color="#fff"
                      :label="$t('tools.community.compare_by')"
                      :items="filterTypes"
                      :item-text="'name_' + locale"
                      item-value="id"
                      v-model="selectedFilterType"
                      @change="applyFilter"
                    >
                    </v-select>
                    <div class="v-input v-input--is-label-active v-input--is-dirty theme--light v-text-field v-text-field--is-booted v-select pt-2">
                      <label aria-label id="labelsOrLinesLabel" class="v-label v-label--active theme--light white--text" style="left: 0px; right: auto; position: absolute;">{{ $t('tools.common.chart_options') }}</label>
                      <v-btn-toggle v-model="labelsOrLines" id="labelsOrLines" aria-labelledby="labelsOrLinesLabel" rounded>
                        <v-btn 
                          v-for="item in [['labels', $t('tools.common.chart_options_labels')], ['lines', $t('tools.common.chart_options_lines')]]" 
                          :key="item[0]" 
                          :value="item[0]" 
                          color="red"
                          small
                          dark
                        >
                          {{ item[1] }}
                        </v-btn>
                      </v-btn-toggle>
                    </div>
                  </div>
                </div>
              </v-col>
            </v-row>
          </div>
        </section>
      </v-col>
      <v-col cols="auto" class="shrink sticky-menu" :style="{ 'margin-top': showIntro ? '0' : '-26px' }">
        <MenuToolbar
          class="flex-column"
          :menu="locationMenu"
          :selectItem="selectItem"
          :flattenSingleItems="true"
          :searchType="$t('tools.common.locations')"
        >
        </MenuToolbar>
      </v-col>
      <v-col v-if="showIntro" cols="auto" class="pa-4 grow">
        <l-map
          v-if="componentInitialized"
          ref="selectionMap"
          :zoom="zoom"
          :center="center"
          :options="{ zoomDelta: 0.5, zoomSnap: 0.5, preferCanvas: true }"
          :style="{ height: '100%', 'min-height': '600px' }"
          v-resize:debounce.100="resizeHandler"
          @ready="initializeSelectionMap"
        >
          <l-tile-layer
            url="https://tiles.stadiamaps.com/tiles/stamen_toner_background/{z}/{x}/{y}{r}.png"
            :options="{ crossOrigin: 'anonymous' }"
            :attribution="$t('tools.common.map_attribution')"
          />
          <l-tile-layer
						url="https://tiles.stadiamaps.com/tiles/stamen_toner_lines/{z}/{x}/{y}{r}.png"
						:options="{ crossOrigin: 'anonymous' }"
						:attribution="$t('tools.common.map_attribution')"
					></l-tile-layer>
					<l-tile-layer
						url="https://tiles.stadiamaps.com/tiles/stamen_toner_labels/{z}/{x}/{y}{r}.png"
						:options="{ crossOrigin: 'anonymous' }"
						:attribution="$t('tools.common.map_attribution')"
					></l-tile-layer>
          <l-geo-json
            v-if="selectionGeojson"
            :geojson="selectionGeojson"
            :options="options"
          ></l-geo-json>
          <l-control
            position="topright"
            class="address-lookup"
          >
            <address-lookup :selectLocation="selectItem"></address-lookup>
          </l-control>
          <l-control
            position="bottomright"
            class="layer-control"
            v-if="layers.length"
          >
            <v-card tile outlined :style="{ boxShadow: 'none !important' }">
              <v-card-title class="pb-0 text--primary">
                <v-icon color="green">mdi-layers</v-icon>
                {{ $t('tools.community.community_types') }}
              </v-card-title>
              <v-card-text>
                <v-radio-group
                  v-model="selectedLayer"
                  @change="drawSelectionMap"
                >
                  <v-radio 
                    color="green"
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
        <h2 v-if="noCommunityData" class="text-dive-h5">{{ $t('tools.community.data_coming_soon')}}</h2>
        <section v-for="data in sortedData" :key="'category_' + data.category.id">
          <div v-if="data.category.hasData">
            <h2 :id="'category_' + data.category.id" class="d-flex text-dive-h4 mt-8 mb-4 blue--text" style="text-transform: uppercase;">
              <span class="mr-4">{{ data.category['name_' + locale]}}</span>
              <v-divider class="mt-6"></v-divider>
            </h2>
            <template v-for="item in data.indicators">
              <template v-if="item.indicators">
                <div :key="'category_' + item.category.id">
                  <template v-for="subItem in item.indicators">
                    <community-indicator :item="subItem" :parentName="item.category['name_' + locale]" :key="'sub_indicator_' + subItem.indicator.id" :maxDemographics="maxDemographics" :filterType="filterTypes.find(ft => ft.id === selectedFilterType)" :labelsOrLines="labelsOrLines"></community-indicator>
                  </template>
                </div>
              </template>
              <template v-else>
                <community-indicator :item="item" :key="'indicator_' + item.indicator.id" :maxDemographics="maxDemographics" :filterType="filterTypes.find(ft => ft.id === selectedFilterType)" :labelsOrLines="labelsOrLines"></community-indicator>
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
import { mapActions, mapState, mapGetters } from 'vuex'
import router from '@/app/router/index'
import axios from 'axios'
import L from 'leaflet'
import { latLng } from 'leaflet'
import { LMap, LTileLayer, LControl, LGeoJson } from 'vue2-leaflet'
import { feature, featureCollection, multiPolygon } from '@turf/helpers'
import MenuToolbar from '@/app/components/MenuToolbar'
import CommunityIndicator from '@/app/components/CommunityIndicator'
import ButtonMenu from '@/app/components/ButtonMenu'
import AddressLookup from '@/app/components/AddressLookup.vue'

export default {
  name: 'CommunityView',
  components: {
    LMap,
    LTileLayer,
    LControl,
    LGeoJson,
    MenuToolbar,
    CommunityIndicator,
    ButtonMenu,
    AddressLookup
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
      selectedCategory: null,
      selectedFilterType: null,
      labelsOrLines: 'labels'
    }
  },
  computed: {
    ...mapState(['locale', 'community', 'customLocations', 'filterTypes' ]),
    ...mapGetters(['locationMenu']),
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
    },
    filterTypes(newValue) {
      if (!this.selectedFilterType && newValue.length) {
        this.selectedFilterType = newValue[0].id;
      }
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
    if (router.currentRoute.query.location && this.locationMenu && router.currentRoute.query.filterType) {
      this.selectedFilterType = router.currentRoute.query.filterType;
      this.setCommunity({ community: this.locationMenu.categories
        .flatMap(category => category.items)
        .find(item => item.id == router.currentRoute.query.location && item.categoryId == router.currentRoute.query.locationType), filterType: this.selectedFilterType })
    } else {
      this.setCommunity(null);
      this.selectedFilterType = this.filterTypes ? this.filterTypes[0]?.id : null;
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
    if (router.currentRoute.query.location && this.locationMenu && router.currentRoute.query.filterType) {
      this.selectedFilterType = router.currentRoute.query.filterType;
      let matchedCommunity = this.locationMenu.categories
        .flatMap(category => category.items)
        .find(item => item.id == router.currentRoute.query.location && item.categoryId == router.currentRoute.query.locationType)
      if ((matchedCommunity?.id !== this.community?.location.id || matchedCommunity?.categoryId !== this.community?.location.typeId)  && this.selectedFilterType) {
        this.getCommunityData({ community: matchedCommunity, filterType: this.selectedFilterType })
      }
    } else {
      this.setCommunity(null)
    }
  },
  methods: {
    ...mapActions(['setCommunity', 'getCommunityData', 'setToolRoute']),
    selectItem(item) {
      if (item.id !== this.community?.id || item.categoryId !== this.community?.locationTypeId) {
        this.getCommunityData({ community: item, filterType: this.selectedFilterType })
        router.replace({
          query: {
            ...router.currentRoute.query,
            location: item.id,
            locationType: item.categoryId,
            filterType: this.selectedFilterType
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
        if (this.selectedLayer.id === "7") {
          this.selectionGeojson = featureCollection(
            this.customLocations.map(cl => multiPolygon(cl.geojson.features
                .map(clf => clf.geometry)
                .reduce((acc, cur) => acc.concat(cur.coordinates), []),
              { id: cl.id, typeId: "7", name: cl.name + ' (' + this.locationMenu.categories.find(c => c.id === cl.typeId)['name_' + this.locale] + ')'}))
            );
          this.refreshOptions = Math.random(); // force a refresh
          return;
        }
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
          if (response.data.typeId !== "7") {
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
          } else {
            this.communityGeojson = multiPolygon(JSON.parse(response.data.geojson).features
              .map(clf => clf.geometry)
              .reduce((acc, cur) => acc.concat(cur.coordinates), []),
              { id: response.data.id, typeId: "7", name: response.data['name_' + this.locale] });
          }
          
          this.refreshOptions = Math.random(); // force a refresh
          this.$refs.communityMap?.mapObject.fitBounds(L.geoJSON(this.communityGeojson).getBounds());
        });
      };
    },
    onEachFeature(feature, layer) {
			layer.bindTooltip(feature.properties.name);
      layer.options.color = '#3b5a98';
      layer.on('mouseover', () => {
        layer.setStyle({
          color: '#f6921e',
          weight: 4
        });
      });
      layer.on('mouseout', () => {
        layer.setStyle({
          color: '#3b5a98',
          weight: 3
        });
      });
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
    applyFilter() {
      router.replace({
        query: {
          ...router.currentRoute.query,
          filterType: this.selectedFilterType
        },
      });
      this.getCommunityData({ community: this.community.location, filterType: this.selectedFilterType });
    },
    downloadCommunityData() {
      let csv = [
        i18n.t('tools.common.download.headers.category'),
				i18n.t('tools.common.download.headers.indicator'), 
				i18n.t('tools.common.download.headers.source'), 
        i18n.t('tools.common.download.headers.location_id'),
				i18n.t('tools.common.download.headers.location'), 
				i18n.t('tools.common.download.headers.year'),
				i18n.t('tools.common.download.headers.race'),
				i18n.t('tools.common.download.headers.value'),
				i18n.t('tools.common.download.headers.range')
			].join(',');
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
            + '"' + this.community.location.id + '",'
            + '"' + this.community.location['name_' + this.locale] + '",'
            + ind.year + ','
            + '"' + (data.demographicFilter['name_' + this.locale] || i18n.t('data.all')) + '",'
            + (data.suppressed ? i18n.t('data.suppressed') : data.value === null ? i18n.t('data.no_data') : data.value) + ','
            + (data.moeLow && data.moeHigh ? (data.moeLow + ' - ' + data.moeHigh) : '');
        });
      }).filter(record => record !== '');
    }
  },
}
</script>

<style lang="scss" scoped></style>
