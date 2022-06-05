<template>
	<v-card outlined tile>
		<v-card-title >
			<span class="text-h6 font-weight-light">{{ $t('tools.dashboard.filters') }}</span>
		</v-card-title>
		<v-card-text>
			<template v-for="filter in filters">
				<v-autocomplete
					:key="filter.id"
					:label="filter.type['name_' + locale]"
					:placeholder="filter.type['name_' + locale]"
					v-model="selections[filter.type.id]"
					:items="filter.options"
					:item-text="'name_' + locale"
					item-value="id"
					hide-no-data
					hide-details
					
					flat
				></v-autocomplete>
			</template>
		</v-card-text>
	</v-card>
</template>

<script>
import { mapState } from 'vuex'
export default {
	name: 'FiltersPanel',
	data() {
		return {
			selections: {}
		}
	},
	computed: {
		...mapState(['filters', 'locale'])
	},
	watch: {
		filters(newValue) {
			this.selections = {}
			newValue.forEach((filter) => {
				this.selections[filter.type.id] = filter.options[0].id
			})
		}
	},
}
</script>

<style lang="scss" scoped>

</style>