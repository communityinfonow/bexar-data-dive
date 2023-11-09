<template>
	<v-tooltip v-model="open" :open-on-hover="false" :open-on-focus="false" :open-on-click="false" right max-width="20%" z-index="9999" content-class="definition-tooltip-container">
	<template v-slot:activator="{ attrs}">
		<span  v-bind="attrs" class="ml-2">
			<v-btn icon data-html2canvas-ignore @click.stop="toggle">
				<v-img src="/img/icon_ux_info__active.svg" width="40px" height="40px" style="transform: scale(0.8)"></v-img>
			</v-btn>
		</span>
	</template>
	<div class="definition-tooltip" v-click-outside="close">
		<div class="definition-tooltip-wedge"></div>
		<span v-html="indicator['description_' + locale] && indicator['description_' + locale].substring(3, indicator['description_' + locale].indexOf('</p>')) || $t('tools.community.coming_soon')"></span>
		<v-btn color="red" text block class="mt-2" :to="'about-data?indicator=' + this.indicator.id">
			{{ $t('featured_card.learn_more') }}
		</v-btn>
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
			open: false,
			opening: false
		}
	},
	methods: {
		toggle() {
			// using v-click-outside to close the tooltip conflicts with the click handler on the button,
			// so set a timeout to prevent auto-closing on open
			this.opening = true
			setTimeout(() => {
				this.opening = false
			}, 250)
			this.open = !this.open
		},
		close() {
			if (!this.opening && this.open) {
				this.open = false
			}
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