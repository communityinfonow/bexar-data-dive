<template>
	<v-menu offset-y min-width="200px">
		<template v-slot:activator="{ on }">
			<v-btn icon data-html2canvas-ignore v-on="on">
				<v-img src="/img/icon_ux_menu_info__active.svg" width="24px" height="24px"></v-img>
			</v-btn>
		</template>
		<v-list>
			<v-list-item v-if="indicator" :to="aboutRoute">
				{{ $t('tools.common.about_data')}}
			</v-list-item>
			<v-list-item v-if="tool" :to="'about-tools'">
				{{ $t('tools.common.about_tool')}}
			</v-list-item>
		</v-list>
	</v-menu>
</template>

<script>
export default {
	name: 'AboutMenu',
	props: {
		tool: {
			type: Boolean,
			default: false
		},
		indicator: {
			type: Boolean,
			default: false
		},
		indicatorId: {
			type: String,
			default: ''
		},
	},
	computed: {
		aboutRoute() {
			return 'about-data?indicator=' + this.indicatorId
		}
	}
}
</script>

<style lang="scss" scoped>
</style>