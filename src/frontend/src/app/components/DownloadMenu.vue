<template>
	<v-menu offset-y>
		<template v-slot:activator="{ on: menu, attrs }">
			<v-tooltip v-model="open" top z-index="9999">
				<template v-slot:activator="{ on: tooltip }">
					<v-btn icon data-html2canvas-ignore v-bind="attrs" v-on="{ ...tooltip, ...menu }" :aria-label="$t('tools.common.download.name')">
						<v-img src="/img/icon_ux_menu_download__active.svg" width="24px" height="24px"></v-img>
					</v-btn>
				</template>
				<span>{{ $t('tools.common.download.name') }}</span>
			</v-tooltip>
		</template>
		<v-list>
			<v-list-item v-if="downloadData" @click="downloadData">
				<v-list-item-title>{{ $t('tools.common.download.items.data') }}</v-list-item-title>
			</v-list-item>
			<v-list-item v-if="downloadImage" @click="downloadImage">
				<v-list-item-title >{{ $t('tools.common.download.items.image') }}</v-list-item-title>
			</v-list-item>
		</v-list>
	</v-menu>
</template>

<script>
export default {
	name: 'DownloadMenu',
	props: {
		downloadData: {
			type: Function
		},
		downloadImage: {
			type: Function
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