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
          <v-form ref="selectionForm" lazy-validation v-model="selectionForm" class="d-flex flex-column justify-start">
            <v-btn
              color="accent"
              @click="viewCustomLocations"
            >
              <v-icon left>mdi-folder-open</v-icon>
              {{ $t('tools.custom_locations.open')}}
            </v-btn>
            <span class="text-button text-center font-italic mt-2">- {{ $t('tools.custom_locations.or') }} -</span>
            <h2 class="text-button text-center">{{ $t('tools.custom_locations.create') }}</h2>
            <h2 class="text-subtitle-1">{{ $t('tools.custom_locations.step_1') }}</h2>
            <v-text-field
              v-if="customLocationGeojson"
              :label="$t('tools.custom_locations.location_name')"
              v-model="customLocation.name"
              :hint="customLocations.find(l => customLocation.name && l.name === customLocation.name) ? $t('tools.custom_locations.overwrite') : null"
              persistent-hint
              :rules="[rules.name_required, rules.custom_name]"
            ></v-text-field>
            <h2 class="text-subtitle-1">{{ $t('tools.custom_locations.step_2') }}</h2>
            <v-select
              v-if="layers.length"
              :items="layers"
              :label="$t('tools.common.download.headers.location_type')"
              :item-text="item => item['name_' + locale]"
              :item-value="item => item.id"
              :return-object="true"
              v-model="selectedLayer"
              @change="selectLocationType"
            ></v-select>
            <v-row class="selection-lists mb-2" style="overflow-y: auto;">
              <v-col :md="6" :sm="12" v-if="$vuetify.breakpoint.smAndDown">
                <span class="text-subtitle-2">{{ $t('tools.custom_locations.selected_locations') }}</span>
                <p v-if="!customLocationGeojson.features.length" class="mt-2 mb-0 font-italic">{{ $t('tools.custom_locations.no_locations_selected') }}</p>
                <v-list v-if="selectionGeojson" style="overflow-y: auto;">
                  <v-list-item-group multiple v-model="customLocationGeojson.features">
                    <v-list-item v-for="location in customLocationGeojson.features" :key="location.properties.id" :value="location">
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
              </v-col>
              <v-col :md="6" :sm="12">
                <span class="text-subtitle-2">{{ $t('tools.custom_locations.available_locations') }}</span>
                <p class="mt-2 mb-0 font-italic" :class="!selectionForm && customLocationGeojson.features.length < 2 ? 'error--text' : ''">{{ $t('tools.custom_locations.two_locations') }}</p>
                <v-list v-if="selectionGeojson" style="overflow-y: auto;">
                  <v-list-item-group multiple v-model="customLocationGeojson.features">
                    <template v-for="location in selectionGeojson.features">
                      <v-list-item v-show="!customLocationGeojson.features.some(f => f.properties.id === location.properties.id)" :key="location.properties.id" :value="location">
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
                    </template>
                  </v-list-item-group>
                </v-list>
              </v-col>
              <v-col :md="6" :sm="12" v-if="$vuetify.breakpoint.mdAndUp">
                <span class="text-subtitle-2">{{ $t('tools.custom_locations.selected_locations') }}</span>
                <p v-if="!customLocationGeojson.features.length" class="mt-2 mb-0 font-italic">{{ $t('tools.custom_locations.no_locations_selected') }}</p>
                <v-list v-if="selectionGeojson" style="overflow-y: auto;">
                  <v-list-item-group multiple v-model="customLocationGeojson.features">
                    <v-list-item v-for="location in customLocationGeojson.features" :key="location.properties.id" :value="location">
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
              </v-col>
            </v-row>
            <v-spacer></v-spacer>
            <div class="d-flex">
              <v-btn
                color="primary"
                class="mt-4 mr-2 flex-grow-1"
                @click="saveCustomLocation"
              >
                {{ $t('tools.custom_locations.save') }}
              </v-btn>
              <v-btn
                color="primary"
                class="mt-4 ml-2 flex-grow-1"
                @click="exportCustomLocation"
              >
                {{ $t('tools.custom_locations.export') }}
              </v-btn>
            </div>
          </v-form>
        </v-col>
      </v-col>
    </v-row>
    <v-dialog v-model="openDialog" class="open-dialog" width="40%">
      <v-card>
        <v-card-title class="headline">{{ $t('tools.custom_locations.open') }}</v-card-title>
        <v-card-text>
          <v-card-subtitle>{{ $t('tools.custom_locations.open_description') }}</v-card-subtitle>
          <v-alert v-if="!customLocations.length" type="info">{{ $t('tools.custom_locations.no_locations_saved') }}</v-alert>
          <v-list>
            <v-list-item-group v-model="customLocationSelected" color="primary">
              <v-list-item v-for="location in customLocations" :key="location.id" :value="location">
                <v-list-item-content>
                  <v-list-item-title>{{ location.name }}</v-list-item-title>
                </v-list-item-content>
                <v-list-item-action class="flex-row">
                  <v-btn text inline color="primary" @click="viewCommunity(location)">
                    {{ $t('tools.custom_locations.view_community') }}
                  </v-btn>
                  <v-btn text inline @click="removeCustomLocation(location.id)">
                    {{ $t('tools.custom_locations.delete') }}
                  </v-btn>
                </v-list-item-action>
              </v-list-item>
            </v-list-item-group>
          </v-list>
          <v-file-input accept=".txt" :label="$t('tools.custom_locations.load')" prepend-icon="mdi-upload" outlined v-model="customLocationFile" :rules="[rules.customLocationFileValid]" @change="customLocationFileValid = true"></v-file-input>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text :disabled="!customLocationSelected && !customLocationFile" @click="openCustomLocation">{{ $t('tools.custom_locations.open') }}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar v-model="message" light multiLine timeout="-1" class="mb-8">
			{{ messageText }}
       <template v-slot:action="{ attrs }">
        <v-btn
          text
          color="primary"
          v-bind="attrs"
          @click="viewCommunity(customLocation)"
        >
          {{ $t('tools.custom_locations.view_community') }}
        </v-btn>
        <v-btn
          text
          v-bind="attrs"
          @click="message = false"
        >
          {{ $t('tools.custom_locations.dismiss') }}
        </v-btn>
      </template>
		</v-snackbar>
  </v-container>
</template>

<script>
import i18n from '@/i18n'
import { mapActions, mapState, mapGetters } from 'vuex'
import axios from 'axios'
import { latLng } from 'leaflet'
import { LMap, LTileLayer, LGeoJson } from 'vue2-leaflet'
import { feature, featureCollection } from '@turf/helpers'

export default {
  //FIXME: find all places where "7" or "Custom..." is hard-coded and do it better.
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
      selectionForm: null,
      selectionGeojson: null,
      customLocationFile: null,
      customLocationSelected: null,
      customLocation: { id: null, name: null, typeId: null, ids: []},
      customLocationGeojson: featureCollection([]),
      refreshOptions: false,
      selectedCategory: null,
      openDialog: false,
      message: false,
      messageText: null,
      customLocationFileValid: true
    }
  },
  computed: {
    ...mapState(['locale', 'customLocations' ]),
    ...mapGetters(['locationMenu']),
    breadcrumbs() {
      let crumbs = [
        {
          text: i18n.t('home_view.name'),
          disabled: false,
          href: '/home'
        },
        {
          text: i18n.t('tools.custom_locations.name'),
          disabled: !this.customLocation.id,
          href: '/custom-locations'
        }
      ];

      if (this.customLocation.id) {
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
    },
    rules() {
      return {
        name_required: v => !!v || i18n.t('tools.custom_locations.name_required'),
        custom_name: this.locationMenu.categories
          .filter(c => c.id !== "7")
          .map(c => c.items)
          .reduce((acc, curr) => acc.concat(curr), [])
          .map(l => l.name_en.toLowerCase())
          .concat(this.locationMenu.categories
            .filter(c => c.id !== "7")
            .map(c => c.items)
            .reduce((acc, curr) => acc.concat(curr), [])
            .map(l => l.name_es.toLowerCase()))
          .indexOf(this.customLocation.name?.toLowerCase()) === -1 || i18n.t('tools.custom_locations.custom_name'),
        two_locations: this.customLocationGeojson.features.length > 1 || i18n.t('tools.custom_locations.two_locations'),
        customLocationFileValid: this.customLocationFileValid || i18n.t('tools.custom_locations.invalid_file')
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
      this.customLocation.typeId = this.selectedLayer.id;
			if (this.selectionMapInitialized) {
				this.drawSelectionMap();
			}
		}, 250);
  },
  updated () {
    
  },
  methods: {
    ...mapActions(['setCommunity', 'setToolRoute', 'addCustomLocation', 'deleteCustomLocation']),
    selectLocation(feature) {
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
      this.$refs.selectionForm.$el.style.height = this.$refs.selectionMap.$el.clientHeight + 'px';
		},
    selectLocationType() {
      this.customLocation.typeId = this.selectedLayer.id;
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
      if (this.customLocationSelected) {
        this.customLocation = Object.assign({}, this.customLocationSelected);
        this.loadCustomLocation();
      } else {
        let reader = new FileReader();
        reader.addEventListener('load', () => {
          this.customLocationFileValid = true;
          axios.get('/api/custom-locations', { params: { 
              id: reader.result.replace("\n", "").replace("\r", "")
            }
          }).then(response => {
            this.customLocation = response.data;
            this.loadCustomLocation();
          }).catch(error => {
            this.customLocationFileValid = false;
            console.log(error);
          })
        });
        reader.readAsText(this.customLocationFile);
      }
    },
    loadCustomLocation() {
      this.customLocationSelected = null;
      this.customLocationFile = null;
      this.selectedLayer = this.layers.find(l => l.id === this.customLocation.typeId);
      this.drawSelectionMap().then(() => {
        this.customLocationGeojson = featureCollection(this.selectionGeojson.features.filter(f => this.customLocation.ids.includes(f.id)));
        this.openDialog = false;
        this.$router.replace({
          query: {
            ...this.$router.currentRoute.query,
            typeId: 7,
            id: this.customLocation.id

          }
        });
      });
    },
    saveCustomLocation() {
      if (this.$refs.selectionForm.validate()) {
        this.customLocation.id = this.customLocation.id || crypto.randomUUID();
        this.customLocation.ids = this.customLocationGeojson.features.map(f => f.id)
        this.customLocation.geojson = this.customLocationGeojson;
        let matchingCustomLocation = this.customLocations.find(l => this.customLocation.name && l.name === this.customLocation.name);
        if (matchingCustomLocation) {
          this.customLocation.id = matchingCustomLocation.id;
        }
        this.addCustomLocation(this.customLocation).then(() => {
          this.messageText = i18n.t('tools.custom_locations.saved');
          this.message = true;
        });
      }
    },
    exportCustomLocation() {
      if (this.$refs.selectionForm.validate()) {
        this.customLocation.id = this.customLocation.id || crypto.randomUUID();
        this.customLocation.ids = this.customLocationGeojson.features.map(f => f.id)
        this.customLocation.geojson = this.customLocationGeojson;
        let matchingCustomLocation = this.customLocations.find(l => this.customLocation.name && l.name === this.customLocation.name);
        if (matchingCustomLocation) {
          this.customLocation.id = matchingCustomLocation.id;
        }
        this.addCustomLocation(this.customLocation).then(() => {
          let downloadLink = document.createElement('a');
          downloadLink.download = 'Dive Custom Location - ' + this.customLocation.name + '.txt';
          downloadLink.href = 'data:text/plain;charset=utf-8,' + this.customLocation.id;
          downloadLink.click();
        });
      }
    },
    removeCustomLocation(id) {
      this.deleteCustomLocation(id);
    },
    viewCommunity(customLocation) {
      this.$router.push({
        name: 'community',
        query: {
          lang: this.locale,
          locationType: 7,
          location: customLocation.id
        }
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.open-dialog {
  z-index: 9999;
}
.selection-lists .v-list-item__title {
  text-overflow: initial;
  white-space: initial;
}
</style>
