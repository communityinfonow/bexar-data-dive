<template>
	<div v-if="item" data-app="true" id="app" class="embed community v-application v-application--is-ltr theme--light">
		<div class="v-application--wrap">
			<main class="v-main">
				<div class="v-main__wrap">
					<div class="container pa-0 container--fluid">
						<community-indicator
							:locale="locale"
							:community="community"
							:item="item"
							:parentName="parentName"
							:maxDemographics="maxDemographics"
							:filterType="{ id: compareById }"
							:labelsOrLines="labelsOrLines"
							embedded
						>
						</community-indicator>
					</div>
				</div>
			</main>
		</div>
	</div>
</template>

<script>
import router from '@/app/router'
import store from '@/app/store/index.js'
import vuetify from '@/plugins/vuetify'
import i18n from '@/i18n'
import CommunityIndicator from '@/app/components/CommunityIndicator'
import { mapActions, mapState } from 'vuex'
import axios from 'axios'
export default {
	name: 'EmbedCommunityIndicator',
	router,
	store,
	vuetify,
	i18n,
	components: { CommunityIndicator },
	props: {
		locale: {
			type: String
		},
		locationId: {
			type: String
		},
		locationTypeId: {
			type: String
		},
		indicatorId: {
			type: String
		},
		compareById: {
			type: String
		},
		labelsOrLines: {
			type: String
		}
	},
	data() {
		return {
			community: null
		}
	},
	computed: {
		...mapState(['locationMenu', 'indicatorMenu']),
		item() {
			let i = this.community?.indicatorData
				.flatMap(c => c.indicators.concat(c.subcategories?.flatMap(sc => sc.indicators) || []))
				.find(i => i.indicator.id === this.indicatorId)
			return i
		},
		parentName() {
			let name = null
			this.community?.indicatorData
				.map(c => c.subcategories)
				.find(sc => {
					if (sc?.flatMap(i => i.id).includes(this.indicatorId)) {
						name = sc.name
					}
				})

			return name;
		},
		maxDemographics() {
			return this.community ? Math.max(...this.community.indicatorData.flatMap(id => id.indicators.concat(id.subcategories.flatMap(sc => sc.indicators))).map(i => i.demographicData.length)) : 0;
		},
	},
	created() {
		axios.defaults.baseURL = process.env.VUE_APP_DOMAIN
	},
	mounted() {
		this.getCommunityData({ 
			community: { id: this.locationId, categoryId: this.locationTypeId }, 
			filterType: this.compareById 
		}).then(data => {
			this.community = data
		});
	},
	methods: {
		...mapActions(['getCommunityData'])
	}
}
</script>

<style lang="scss">
	@import "vuetify/dist/vuetify.min.css";
	@import "@/assets/css/app.scss";
	@import "@/assets/css/embed.scss";
</style>