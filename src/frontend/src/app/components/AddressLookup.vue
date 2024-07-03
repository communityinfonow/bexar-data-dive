<template>
	<div>
		<v-form>
				<v-autocomplete
					v-model="address"
					return-object
					@change="lookupAddress"
					:search-input.sync="search"	
					:items="addressMatches"
					:label="$t('tools.community.find_communities')"
					:placeholder="$t('tools.community.enter_address')"
					prepend-inner-icon="mdi-magnify"
					solo
					hide-no-data
					text
					rounded
					background-color="red"
					dark
					auto-select-first
					@keypress.enter.prevent
				>
					<template v-slot:append>
						<v-tooltip bottom z-index="9999" content-class="mt-2">
							<template v-slot:activator="{ on }">
								<v-icon v-on="on" style="cursor: pointer;">mdi-information</v-icon>
							</template>
							<span>{{ $t('tools.community.find_tooltip') }}</span>
						</v-tooltip>
					</template>
				</v-autocomplete>
		</v-form>
		<v-dialog v-model="dialog" max-width="60%" @click:outside="close" style="z-index: 10002">
			<v-card>
				<v-card-title class="text-subtitle-1">
					{{ $t('tools.community.find_communities') }}
					<v-spacer></v-spacer>
					<v-btn icon @click="close" data-html2canvas-ignore>
						<v-icon>mdi-close</v-icon>
					</v-btn>
				</v-card-title>
				<v-card-text v-if="address">
					<div class="d-flex text-dive-h6" style="color: rgba(0, 0, 0, 0.87)">
						<div>
							{{ address.text }}
						</div>
						<v-spacer></v-spacer>
						<v-tooltip v-model="open" bottom z-index="99999">
							<template v-slot:activator="{ on, attrs }">
								<v-btn v-bind="attrs" v-on="on" icon data-html2canvas-ignore :aria-label="$t('tools.common.download.name')" @click="download">
									<v-img src="/img/icon_ux_menu_download__active.svg" contain width="24px" height="24px"></v-img>
								</v-btn>
							</template>
							<span>{{ $t('tools.common.download.name') }}</span>
						</v-tooltip>
					</div>
					<v-row v-if="locations" class="mt-8">
						<v-col sm="12" lg="6">
							<l-map
								v-if="componentInitialized"
								ref="addressMap"
								:zoom="zoom"
								:center="center"
								:options="{ preferCanvas: true }"
								:style="{ height: '180px' }"
								v-resize:debounce.100="resizeHandler"
								@ready="initializeAddressMap"
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
								<l-marker
									v-if="addressGeojson"
									:lat-lng="[addressGeojson.coordinates[1], addressGeojson.coordinates[0]]"
								></l-marker>
								<l-geo-json
									v-if="communityGeojson"
									:geojson="communityGeojson"
									
								></l-geo-json>
							</l-map>
						</v-col>
						<v-col sm="12" lg="6">
							<v-list class="ml-4">
								<v-list-item v-for="location in locations" :key="location.typeId + '_' + location.id" @click.stop="selectCommunity(location)" @mouseenter="communityGeojson = location.geojson" @mouseleave="communityGeojson = null">
									<v-list-item-content>
										<v-list-item-title>{{ location['name_' + locale].substring(0, location['name_' + locale].lastIndexOf(" ")) }}:</v-list-item-title>
									</v-list-item-content>
									<v-list-item-content class="align-end font-weight-bold" style="max-width: 80px;">
										<v-list-item-title class="pl-4">{{ location['name_' + locale].substring(location['name_' + locale].lastIndexOf(" ")) }}</v-list-item-title>
									</v-list-item-content>
									<v-list-item-action data-html2canvas-ignore>
										<v-btn text small dark plain rounded color="red" @click.stop="selectCommunity(location)">
											<span v-if="$vuetify.breakpoint.lgAndUp">{{ $t('tools.community.view_community') }}</span>
											<span v-if="$vuetify.breakpoint.mdAndDown">{{ $t('tools.community.view') }}</span>
										</v-btn>
									</v-list-item-action>
								</v-list-item>
							</v-list>
						</v-col>
					</v-row>
				</v-card-text>
				<v-card-actions>
				</v-card-actions>
			</v-card>
		</v-dialog>
	</div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import axios from 'axios'
import L from 'leaflet'
import { latLng } from 'leaflet'
import { LMap, LTileLayer, LGeoJson, LMarker } from 'vue2-leaflet'
import debounce from 'debounce'
import html2canvas from 'html2canvas'

export default {
	name: 'AddressLookup',
	components: {
		LMap,
		LTileLayer,
		LGeoJson,
		LMarker
	},
	props: {
		selectLocation: {
			type: Function,
		},
	},
	data() {
		return {
			geocodioApiKey: process.env.VUE_APP_GEOCODIO_API_KEY,
			componentInitialized: false,
			addressMapInitialized: false,
			dialog: false,
			search: '',
			addressMatches: [],
			address: null,
			locations: null,
			zoom: 10,
			center: latLng(29.43445, -98.473562383),
			addressGeojson: null,
			communityGeojson: null,
			markerIcon: L.divIcon({ html: '<i class="mdi mdi-map-marker"></i>', iconSize: [30, 30], iconAnchor: [15, 30] }),
			open: false
		}
	},
	computed: {
		...mapState(['loading', 'locale']),
  	},
	watch: {
		dialog(newValue) {
			if (newValue) {
				this.$nextTick(() => {
					setTimeout(() => {
						this.$refs.addressMap?.mapObject?.invalidateSize();
					}, 100);
				});
			}
		},
		search(newValue, oldValue) {
			if (newValue !== oldValue) {
				this.findAddressMatches();
			}
		}
	},
	created() {
		this.findAddressMatches = debounce(this.findAddressMatches, 500);
	},
	mounted () {
		setTimeout(() => { 
			this.componentInitialized = true;
		}, 100);
	},
	methods: {
		...mapActions(['setLoading']),
		findAddressMatches() {
			if (!this.search) {
				this.addressMatches = [];
				return;
			}
			axios.get('/api/address-matches', {
				params: {
				address: this.search
				}
			}).then(response => {
				this.addressMatches = response.data
					.filter(r => r.address.house_number && r.address.road && r.address.city && r.address.state && r.address.postcode && r.address.county === 'Bexar County')
					.map(r => {
						return {
							text: r.address.house_number + ' ' + r.address.road + ' ' + r.address.city + ', ' + r.address.state + ' ' + r.address.postcode,
							lat: r.lat,
							lng:  r.lon
						}
					});
			}).catch(error => {
				console.error(error);
			});
		},
		lookupAddress() {
			this.addressGeojson = {
				type: 'Point',
				coordinates: [this.address.lng, this.address.lat]
			};
			this.center = latLng(this.address.lat, this.address.lng);
			axios.get('/api/communities-by-point', {
				params: {
					lat: this.address.lat,
					lng: this.address.lng
				}
			}).then(response => {
				this.locations = response.data;
				this.locations.forEach (l => {
					l.geojson = JSON.parse(l.geojson);
					l.categoryId = l.typeId
				});
				this.dialog = true;
			}).catch(error => {
				console.error(error);
			
			});
		},
		close() {
			this.dialog = false
		},
		resizeHandler() {
			this.$refs.addressMap?.mapObject?.invalidateSize();
		},
		initializeAddressMap() {
			this.addressMapInitialized = true;
		},
		selectCommunity(community) {
			this.selectLocation(community);
			this.close();
		},
		download() {
			this.setLoading(true);
			html2canvas(document.querySelector('header'), { scale: 2 }).then((headerCanvas) => {
				html2canvas(document.querySelector('.v-dialog'), {
					allowTaint: true,
					useCORS: true,
					scrollX: 0,
					scrollY: 0,
					windowWidth: document.documentElement.offsetWidth,
					windowHeight: document.documentElement.offsetHeight,
					scale: 2
				}).then(dialogCanvas => {
					let imageCanvas = document.createElement('canvas');
					imageCanvas.width = headerCanvas.width * 0.3; // dialog is 60% width, so 30% of scaled header width
					imageCanvas.height = (headerCanvas.height + dialogCanvas.height) / 2;
					imageCanvas.getContext('2d').scale(0.5, 0.5);
					imageCanvas.getContext('2d').fillStyle = 'white';
					imageCanvas.getContext('2d').fillRect(0, 0, imageCanvas.width * 2, imageCanvas.height * 2);
					imageCanvas.getContext('2d').drawImage(headerCanvas, 0, 0);
					imageCanvas.getContext('2d').drawImage(dialogCanvas, 0, headerCanvas.height);
					
					let imageLink = document.createElement('a');
					imageLink.download = 'my_communities.png';
					imageLink.href = imageCanvas.toDataURL('image/png').replace('image/png', 'image/octet-stream');
					imageLink.click();
					
					this.setLoading(false);
				});
			});
		}
	}
}
</script>

<style lang="scss" scoped>

</style>