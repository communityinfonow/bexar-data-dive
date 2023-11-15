<template>
	<v-btn-toggle rounded borderless>
		<download-menu :downloadData="downloadData" :downloadImage="downloadImage"></download-menu>
		<share-menu></share-menu>
		<v-btn icon data-html2canvas-ignore to="help" :aria-label="$t('help_view.name')">
			<v-icon color="green" width="28px" height="28px">mdi-help-circle</v-icon>
		</v-btn>
	</v-btn-toggle>
</template>

<script>
import DownloadMenu from '@/app/components/DownloadMenu'
import ShareMenu from '@/app/components/ShareMenu'

export default {
	name: 'ButtonMenu',
	components: {
		DownloadMenu,
		ShareMenu
	},
	props: {
		downloadData: {
			type: Function
		},
		downloadImage: {
			type: Function
		}
	}
}
</script>

<style lang="scss" scoped>
</style>