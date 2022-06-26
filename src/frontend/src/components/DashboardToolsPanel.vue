<template>
	<v-row class="mt-2">
		<v-col cols="4">
			<v-select
				label="Compare by"
				dense
				flat
				return-object
				:items="compareByItems"
				:item-text="'name_' + locale"
				v-model="compareBy"
			>
			</v-select>
		</v-col>
		<v-col cols="4">
			<v-select
				label="Compare with"
				dense
				flat
				return-object
				:items="compareWithItems"
				:item-text="'name_' + locale"
				v-model="compareWith"
				multiple
				
			>
			</v-select>
		</v-col>
		<v-col cols="2">
			<v-btn
				color="primary"
				tile
			>
				Compare
			</v-btn>
		</v-col>
		<!--<v-col cols="2">
			[todo: labels toggle]
		</v-col>-->
	</v-row>
</template>

<script>

import { mapState } from 'vuex'

export default {
	name: 'DashboardToolsPanel',
	//TODO: set the options for the 'by' select
	// then dynamically set the options for the 'with' select
	// then handle compare click event
	// first validate that a by and with are selected
	// then fetch data - will need API updates for by and multiple withs
	computed: {
		...mapState(['filters', 'locale']),
		compareByItems() {
			let items = [];
			items.push(this.filters?.locationFilter.type);
			this.filters?.indicatorFilters.forEach(filter => {
				items.push(filter.type)
			});
			
			return items;
		},
		compareWithItems() {
			let items = [];
			if (this.compareBy?.name_en === 'Location') {
				items = this.filters?.locationFilter.options;
			} else {
				items = this.filters?.indicatorFilters.find(filter => filter.type.name_en === this.compareBy?.name_en)?.options;
			}
			
			return items;
		}
	},
	data() {
		return {
			compareBy: null,
			compareWith: null
		}
	},
}
</script>

<style lang="scss" scoped>

</style>