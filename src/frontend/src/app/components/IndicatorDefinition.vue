<template>
	<v-tooltip :open-on-hover="false" bottom max-width="20%" color="accent" z-index="9999">
	<template v-slot:activator="{ on, attrs}">
		<sup v-on="on" v-bind="attrs">
			<v-btn icon color="accent">
				<v-icon>mdi-book-information-variant</v-icon>
			</v-btn>
		</sup>
	</template>
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