<template>
	<div>
		<v-form>
				<!-- TODO: i18n -->
				<v-autocomplete
					v-model="address"
					return-object
					@change="lookupAddress"
					:search-input.sync="search"	
					:items="addressMatches"
					label="Find your community"
					placeholder="Address"
					prepend-inner-icon="mdi-magnify"
					solo
					:hide-no-data="hideNoData"
					text
					rounded
					background-color="grey"
				>
				</v-autocomplete>
		</v-form>
		<v-dialog v-model="dialog" max-width="800px" @click:outside="close" style="z-index: 10002">
			<v-card>
				<v-card-title class="text-subtitle-1">
					Address Lookup
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
						<v-btn icon data-html2canvas-ignore :aria-label="$t('tools.common.download.name')" @click="download">
							<v-img src="/img/icon_ux_menu_download__active.svg" contain width="24px" height="24px"></v-img>
						</v-btn>
					</div>
					<v-row v-if="locations" class="mt-8">
						<v-col cols="6">
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
						<v-col cols="6">
							<v-list class="ml-4">
								<v-list-item v-for="location in locations" :key="location.id" @click.stop="selectCommunity(location)" @mouseenter="communityGeojson = location.geojson" @mouseleave="communityGeojson = null">
									<v-list-item-content>
										<v-list-item-title>{{ location['name_' + locale] }}</v-list-item-title>
									</v-list-item-content>
									<v-list-item-action data-html2canvas-ignore>
										<v-btn text dark plain rounded color="red" @click.stop="selectCommunity(location)">View Community</v-btn>
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
import { mapState } from 'vuex'
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
			hideNoData: true,
			addressMatches: [],
			address: null,
			locations: null,
			zoom: 10,
			center: latLng(29.43445, -98.473562383),
			addressGeojson: null,
			communityGeojson: null,
			markerIcon: L.divIcon({ html: '<i class="mdi mdi-map-marker"></i>', iconSize: [30, 30], iconAnchor: [15, 30] })
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
				this.hideNoData = true;
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
				this.hideNoData = false;
			}).catch(error => {
				this.hideNoData = false;
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
			html2canvas(document.querySelector('.v-dialog'), {
				allowTaint: true,
				useCORS: true,
				scrollX: 0,
				scrollY: 0,
				windowWidth: document.documentElement.offsetWidth,
				windowHeight: document.documentElement.offsetHeight,
				scale: 2
			}).then(canvas => {
				canvas.toBlob(blob => {
					let downloadLink = document.createElement('a');
					downloadLink.download = 'address_communities.png';
					downloadLink.href = URL.createObjectURL(blob);
					downloadLink.click();
				});
			});
		}
	}
}
</script>

<style lang="scss" scoped>

</style>