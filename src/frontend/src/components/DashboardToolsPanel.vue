<template>
	<v-form v-if="filters" ref="compareForm" v-model="valid">
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
					@change="clearCompareWith"
					:rules="[v => !!v || 'Please make a selection']"
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
					:rules="[v => !!v || 'Please make a selection']"
					multiple
				>
				</v-select>
			</v-col>
			<v-col cols="2">
				<v-btn
					color="primary"
					tile
					@click="applyComparison"
				>
					Compare
				</v-btn>
			</v-col>
			<!--<v-col cols="2">
				[todo: labels toggle]
			</v-col>-->
		</v-row>
	</v-form>
</template>

<script>

import { mapActions, mapState } from 'vuex'

export default {
	name: 'DashboardToolsPanel',
	//TODO: 
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
			compareWith: null,
			valid: true
		}
	},
	methods: {
		...mapActions(['setCompareSelections']),
		clearCompareWith() {
			this.compareWith = null;
		},
		validateComparison() {
			this.$refs.compareForm.validate();
		},
		getComparison() {	
			return {
				type: this.compareBy,
				filterOptions: this.compareWith
			};
		},
		applyComparison() { 
			this.validateComparison();
			if (this.valid) {
				this.setCompareSelections(this.getComparison());
			}
		}
	},
}
</script>

<style lang="scss" scoped>

</style>