<template>
	<v-btn-toggle rounded borderless>
		<download-menu :downloadData="downloadData" :downloadImage="downloadImage"></download-menu>
		<share-menu></share-menu>
		<view-menu v-if="viewMenu" :indicatorId="indicatorId" :linkToExplore="linkToExplore" :linkToTables="linkToTables"></view-menu>
		<v-btn icon data-html2canvas-ignore to="help" :aria-label="$t('help_view.name')">
			<v-icon color="green" width="28px" height="28px">mdi-help-circle</v-icon>
		</v-btn>
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
			type: Number
		},
		linkToExplore: {
			type: Boolean,
			default: false
		},
		linkToTables: {
			type: Boolean,
			default: false
		}
	}
}
</script>

<style lang="scss" scoped>
</style>