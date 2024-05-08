<template>
	<v-btn-toggle rounded borderless>
		<download-menu :downloadData="downloadData" :downloadImage="downloadImage"></download-menu>
		<share-menu></share-menu>
		<view-menu v-if="viewMenu" 
			:indicatorId="indicatorId" 
			:locationTypeId="locationTypeId" 
			:locationId="locationId" 
			:year="year"
			:raceId="raceId"
			:ageId="ageId"
			:sexId="sexId"
			:educationId="educationId"
			:incomeId="incomeId"
			:linkToCommunity="linkToCommunity" 
			:linkToExplore="linkToExplore" 
			:linkToTables="linkToTables"
		>
		</view-menu>
		<v-tooltip v-model="open" top z-index="9999">
			<template v-slot:activator="{ on }">
				<v-btn v-on="on" icon data-html2canvas-ignore to="help" :aria-label="$t('help_view.name')">
					<v-icon color="green" width="28px" height="28px">mdi-help-circle</v-icon>
				</v-btn>
			</template>
			<span>{{ $t('help_view.name') }}</span>
		</v-tooltip>
	</v-btn-toggle>
</template>

<script>
import DownloadMenu from '@/app/components/DownloadMenu'
import ShareMenu from '@/app/components/ShareMenu'
import ViewMenu from '@/app/components/ViewMenu.vue'

export default {
	name: 'ButtonMenu',
	components: {
		DownloadMenu,
		ShareMenu,
		ViewMenu
	},
	props: {
		downloadData: {
			type: Function
		},
		downloadImage: {
			type: Function
		},
		viewMenu: {
			type: Boolean,
			default: false
		},
		indicatorId: {
			type: String
		},
		locationId: {
			type: String
		},
		locationTypeId: {
			type: String
		},
		year: {
			type: String
		},
		raceId: {
			type: String
		},
		ageId: {
			type: String
		},
		sexId: {
			type: String
		},
		educationId: {
			type: String
		},
		incomeId: {
			type: String
		},
		linkToCommunity: {
			type: Boolean,
			default: false
		},
		linkToExplore: {
			type: Boolean,
			default: false
		},
		linkToTables: {
			type: Boolean,
			default: false
		}
	},
	data() {
		return {
			open: false
		}
	}
}
</script>

<style lang="scss" scoped>
</style>