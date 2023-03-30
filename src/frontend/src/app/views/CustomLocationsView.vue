<template>
  <v-container v-if="locationMenu" fluid class="pa-0 fill-height">
    <v-row class="no-gutters flex-column fill-height">
      <v-col cols="auto">
        <v-breadcrumbs :items="breadcrumbs">
          <template v-slot:divider>
            <v-icon>mdi-chevron-right</v-icon>
          </template>
        </v-breadcrumbs>
      </v-col>
      <v-col cols="auto" class="pa-4 shrink">
        <div class="d-flex justify-space-between">
          <h1 class="text-h3 mb-2">{{ $t('tools.custom_locations.name') }}</h1>
          <v-btn
            icon
            color="accent"
            class="mt-4"
            @click="viewCustomLocations"
            :title="$t('tools.custom_locations.open')"
          >
            <v-icon>mdi-folder-open</v-icon>
          </v-btn>
        </div>
        <p>{{ $t('tools.custom_locations.long_description') }}</p>
        <p>{{ $t('tools.custom_locations.get_started') }}</p>
      </v-col>
      <v-col cols="auto" class="pa-4 grow d-flex">
        <v-col cols="8">
          <l-map
            v-if="componentInitialized"
            ref="selectionMap"
            :zoom="zoom"
            :center="center"
            :options="{ zoomDelta: 0.5, zoomSnap: 0.5, preferCanvas: true }"
            :style="{ height: '100%', 'min-height': '300px' }"
            v-resize:debounce.100="resizeHandler"
            @ready="initializeSelectionMap"
          >
            <l-tile-layer
              url="https://stamen-tiles.a.ssl.fastly.net/toner-background/{z}/{x}/{y}.png"
              :options="{ crossOrigin: 'anonymous' }"
              :attribution="$t('tools.common.map_attribution')"
            />
            <l-tile-layer
              url="https://stamen-tiles.a.ssl.fastly.net/toner-hybrid/{z}/{x}/{y}.png"
              :options="{ crossOrigin: 'anonymous' }"
              :attribution="$t('tools.common.map_attribution')"
            />
            <l-geo-json
              v-if="selectionGeojson"
              :geojson="selectionGeojson"
              :options="selectionOptions"
            ></l-geo-json>
            <l-geo-json
              v-if="customLocationGeojson"
              :geojson="customLocationGeojson"
              :options="customOptions"
            ></l-geo-json>
          </l-map>
        </v-col>
        <v-col cols="4">
          <section ref="selectionPanel" class="d-flex flex-column justify-start">
            <v-select
              v-if="layers.length"
              :items="layers"
              :label="$t('tools.community.community_types')"
              :item-text="item => item['name_' + locale]"
              :item-value="item => item.id"
              :menu-props="{ maxHeight: '400' }"
              :return-object="true"
              v-model="selectedLayer"
              @change="selectLocationType"
            ></v-select>
            <v-list v-if="selectionGeojson" style="overflow-y: auto;">
              <v-list-item-group multiple v-model="customLocationGeojson.features">
                <v-list-item v-for="location in selectionGeojson.features" :key="location.properties.id" :value="location">
                  <template v-slot:default="{active}">
                    <v-list-item-action>
                      <v-checkbox
                        color="accent"
                        :input-value="active"
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-content>
                      <v-list-item-title>{{ location.properties.name }}</v-list-item-title>
                    </v-list-item-content>
                  </template>
                </v-list-item>
              </v-list-item-group>
            </v-list>
            <v-spacer></v-spacer>
            <v-text-field
              v-if="customLocationGeojson"
              :label="$t('tools.custom_locations.location_name')"
              v-model="customLocation.name"
            ></v-text-field>
            <div class="d-flex flex-column">
              <v-btn
                :disabled="!customLocation.name || customLocationGeojson.features.length < 2"
                color="primary"
                class="mt-4"
                @click="saveCustomLocation"
              >
                {{ $t('tools.custom_locations.save') }}
              </v-btn>
              <v-btn
                text
                :disabled="!customLocation.name || customLocationGeojson.features.length < 2"
                color="accent"
                class="mt-4"
                @click="exportCustomLocation"
              >
                {{ $t('tools.custom_locations.export') }}
              </v-btn>
            </div>
          </section>
        </v-col>
      </v-col>
    </v-row>
    <v-dialog v-model="openDialog" class="open-dialog" width="40%">
      <v-card>
        <v-card-title class="headline">{{ $t('tools.custom_locations.open') }}</v-card-title>
        <v-card-text>
          <v-card-subtitle>{{ $t('tools.custom_locations.open_description') }}</v-card-subtitle>
          <v-alert v-if="!customLocations.length" type="info">{{ $t('tools.custom_locations.no_locations') }}</v-alert>
          <v-list>
            <v-list-item-group v-model="customLocationSelected" color="primary">
              <v-list-item v-for="location in customLocations" :key="location.key" :value="location">
                <v-list-item-content>
                    <v-list-item-title>{{ location.name }}</v-list-item-title>
                  </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
          <span>TODO: maybe go for a file upload instead of copy/paste code?</span>
          <v-text-field v-model="customLocationCode" :label="$t('tools.custom_locations.code')" prepend-inner-icon="mdi-code-braces" outlined></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text :disabled="!customLocationSelected && !customLocationCode" @click="openCustomLocation">{{ $t('tools.custom_locations.open') }}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import i18n from '@/i18n'
import { mapActions, mapState } from 'vuex'
import axios from 'axios'
import { latLng } from 'leaflet'
import { LMap, LTileLayer, LGeoJson } from 'vue2-leaflet'
import { feature, featureCollection } from '@turf/helpers'

export default {
  name: 'CustomLocationsView',
  components: {
    LMap,
    LTileLayer,
    LGeoJson
  },
  data() {
    return {
      componentInitialized: false,
			selectionMapInitialized: false,
      zoom: 10,
			center: latLng(29.43445, -98.473562383),
      selectedLayer: null,
      selectionGeojson: null,
      customLocationCode: null,
      customLocationSelected: null,
      customLocation: { key: null, name: null, locationTypeId: null, locationIds: []},
      customLocationGeojson: featureCollection([]),
      refreshOptions: false,
      selectedCategory: null,
      openDialog: false,
    }
  },
  computed: {
    ...mapState(['locale', 'locationMenu', 'customLocations' ]),
    breadcrumbs() {
      let crumbs = [
        {
          text: i18n.t('home_view.name'),
          disabled: false,
          href: '/home'
        },
        {
          text: i18n.t('tools.custom_locations.name'),
          disabled: !this.customLocation.key,
          href: '/custom-locations'
        }
      ];

      if (this.customLocation.key) {
        crumbs.push({
          text: this.customLocation.name,
          disabled: true
        });
      }

      return crumbs;
    },
    layers() {
      return this.locationMenu?.categories
        .filter(locationType => {
          return locationType.id !== "1" && locationType.id !== "6" && locationType.id !== "7";
        }).map(locationType => {
        return {
          id: locationType.id,
          name_en: locationType.name_en,
          name_es: locationType.name_es
        };
      });
    },
    selectionOptions() {
			this.refreshOptions;
			return {
				onEachFeature: this.onEachSelectionFeature
			}
		},
    customOptions() {
      this.refreshOptions;
      return {
        onEachFeature: this.onEachCustomFeature
      }
    }
  },
  watch: {
    locale() {
			this.drawSelectionMap()
		}
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$store.dispatch('setToolRoute', { key: 'customLocation', route: to.fullPath })
    });
  },
  beforeRouteUpdate(to, from, next) {
    this.setToolRoute({ key: 'customLocation', route: to.fullPath })
    next();
  },
  mounted () {
    setTimeout(() => { 
			this.componentInitialized = true;
      this.selectedLayer = this.layers?.find(l => l.id === '4')
      this.customLocation.locationTypeId = this.selectedLayer.id;
			if (this.selectionMapInitialized) {
				this.drawSelectionMap();
			}
		}, 250);
  },
  updated () {
    
  },
  methods: {
    ...mapActions(['setCommunity', 'setToolRoute', 'addCustomLocation']),
    selectLocation(feature) {
      console.log(feature)
      if (this.customLocationGeojson.features.find(f => f.id === feature.id)) {
        this.customLocationGeojson.features = this.customLocationGeojson.features.filter(f => f.id !== feature.id);
      } else {
        this.customLocationGeojson.features.push(feature);
      }
    },
    initializeSelectionMap() {
			this.selectionMapInitialized = true;
      this.drawSelectionMap();
		},
    resizeHandler() {
			this.$refs.selectionMap?.mapObject?.invalidateSize();
      this.$refs.selectionPanel.style.height = this.$refs.selectionMap.$el.clientHeight + 'px';
		},
    selectLocationType() {
      this.customLocation.locationTypeId = this.selectedLayer.id;
      this.customLocationGeojson = featureCollection([]);
      this.drawSelectionMap();
    },
    drawSelectionMap() {
      if (this.selectedLayer) {
        return axios.get('/api/community-locations', { params: { 
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
    onEachSelectionFeature(feature, layer) {
			layer.bindTooltip(feature.properties.name);
      layer.options.color = '#3b5a98';
      layer.on('click', () => {
        this.selectLocation(feature)
      });
		},
    onEachCustomFeature(feature, layer) {
      layer.bindTooltip(feature.properties.name);
      layer.options.color = '#f6921e';
      layer.on('click', () => {
        this.selectLocation(feature)
      });
    },
    viewCustomLocations() {
      this.openDialog = true;
    },
    openCustomLocation() {
      this.customLocation = this.customLocationSelected ? Object.assign({}, this.customLocationSelected) : JSON.parse(this.customLocationCode);
      //TODO: validate customLocationCode to ensure it is a valid custom location json object
      this.customLocationSelected = null;
      this.customLocationCode = null;
      this.selectedLayer = this.layers.find(l => l.id === this.customLocation.locationTypeId);
      this.drawSelectionMap().then(() => {
        this.customLocationGeojson = featureCollection(this.selectionGeojson.features.filter(f => this.customLocation.locationIds.includes(f.id)));
        this.openDialog = false;
      });
    },
    saveCustomLocation() {
      this.customLocation.key = crypto.randomUUID();
      this.customLocation.locationIds = this.customLocationGeojson.features.map(f => f.id)
      this.customLocation.geojson = this.customLocationGeojson;
      this.addCustomLocation(this.customLocation);
    },
    exportCustomLocation() {
      let downloadLink = document.createElement('a');
      downloadLink.download = 'bexar_data_dive__' + this.customLocation.name + '.json';
      downloadLink.href = 'data:text/csv;charset=utf-8,' + encodeURIComponent(JSON.stringify(this.customLocation));
      downloadLink.click();
    }
  },
}
</script>

<style lang="scss" scoped>
.open-dialog {
  z-index: 9999;
}
</style>
