<template>
	<v-menu offset-y>
		<template v-slot:activator="{ on }">
			<v-btn color="accent" icon data-html2canvas-ignore v-on="on">
				<v-icon>mdi-download</v-icon>
			</v-btn>
		</template>
		<v-list>
			<v-list-item v-if="downloadData" @click="downloadData">
				<v-list-item-title>Data</v-list-item-title>
			</v-list-item>
			<v-list-item v-if="downloadImage" @click="downloadImage">
				<v-list-item-title >Image</v-list-item-title>
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
	}
</script>

<style lang="scss" scoped>

</style>