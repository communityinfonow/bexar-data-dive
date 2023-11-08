<template>
	<v-tooltip :value="open" :open-on-hover="false" right max-width="20%" z-index="9999" content-class="definition-tooltip-container">
	<template v-slot:activator="{ on, attrs}">
		<span v-on="on" v-bind="attrs" class="ml-2">
			<v-btn icon data-html2canvas-ignore>
				<v-img src="/img/icon_ux_info__active.svg" width="40px" height="40px" style="transform: scale(0.8)"></v-img>
			</v-btn>
		</span>
	</template>
	<div class="definition-tooltip" v-click-outside="toggle">
		<div class="definition-tooltip-wedge"></div>
		<span v-html="indicator['description_' + locale] && indicator['description_' + locale].substring(3, indicator['description_' + locale].indexOf('</p>')) || $t('tools.community.coming_soon')"></span>
	</div>
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
	data() {
		return {
			open: false
		}
	},
	methods: {
		toggle() {
			this.open = !this.open
		}
	}
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