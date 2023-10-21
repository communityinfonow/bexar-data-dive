<template>
	<v-tooltip :open-on-hover="false" right max-width="20%" z-index="9999">
	<template v-slot:activator="{ on, attrs}">
		<span v-on="on" v-bind="attrs" class="ml-2">
			<v-btn icon data-html2canvas-ignore>
				<v-img src="/img/icon_ux_info__active.svg" width="40px" height="40px"></v-img>
			</v-btn>
		</span>
	</template>
	<!-- TODO: indicator definition tooltip styling -->
	<span v-html="indicator['description_' + locale] && indicator['description_' + locale].substring(3, indicator['description_' + locale].indexOf('</p>')) || $t('tools.community.coming_soon')"></span>
	</v-tooltip>
</template>

<script>
import { mapState } from 'vuex'

export default {
	name: 'IndicatorDefinition',
	props: {
		indicator: {
			type: Object
		}
	},
	computed: {
		...mapState(['locale']),
	},
}
</script>

<style lang="scss" scoped>
/* override vuetify 75% font sizing - it causes a strange layout bug in the community view */
sup {
	font-size: inherit;
}
.v-tooltip__content {
	pointer-events: auto;
	word-break: break-word;
}
</style>