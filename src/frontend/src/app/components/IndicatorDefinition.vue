<template>
	<div :id="'indicator_definition_' + indicator.id" style="display: inline">
	<v-tooltip 
		v-model="open" 
		:open-on-hover="false" 
		:open-on-focus="false" 
		:open-on-click="false" 
		right 
		min-width="220px"
		max-width="20%"
		z-index="9999" 
		content-class="definition-tooltip-container" 
		:attach="element">
	<template v-slot:activator="{ attrs}">
		<span  v-bind="attrs" class="ml-2">
			<v-btn icon data-html2canvas-ignore @click.stop="toggle">
				<v-img :src="domain + '/img/icon_ux_info__active.svg'" width="40px" height="40px" style="transform: scale(0.8)"></v-img>
			</v-btn>
		</span>
	</template>
	<div class="definition-tooltip" v-click-outside="close">
		<div class="definition-tooltip-wedge"></div>
		<span v-html="indicator['description_' + locale] && indicator['description_' + locale].substring(3, indicator['description_' + locale].indexOf('</p>')) || $t('tools.community.coming_soon')"></span>
		<v-btn color="red" text block class="mt-2" :target="embedded ? '_blank' : ''" :href="domain + '/about-data?indicator=' + this.indicator.id">
			{{ $t('featured_card.learn_more') }}
		</v-btn>
	</div>
	</v-tooltip>
	</div>
</template>

<script>

export default {
	name: 'IndicatorDefinition',
	props: {
		embedded: {
			type: Boolean
		},
		indicator: {
			type: Object
		},
		locale: {
			type: String
		}
	},
	computed: {
		domain() {
			return process.env.VUE_APP_DOMAIN
		}
	},
	data() {
		return {
			open: false,
			opening: false,
			element: null
		}
	},
	mounted() {
		this.element = this.embedded ? this.$el.closest('.v-application') : ''
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