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
					@change="selectCompareBy"
					:rules="[v => !!v || 'Please make a selection']"
				>
				</v-select>
			</v-col>
			<v-col cols="4">
				<v-autocomplete
					label="Compare with"
					dense
					flat
					return-object
					:items="compareWithItems"
					:item-text="'name_' + locale"
					v-model="compareWith"
					:rules="[v => !!v || 'Please make a selection']"
					multiple
					:search-input.sync="compareWithQuery"
					@change="compareWithQuery = ''"
				>
				</v-autocomplete>
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
import router from '@/router/index'

export default {
	name: 'DashboardToolsPanel',
	computed: {
		...mapState(['filters', 'locale'])
	},
	data() {
		return {
			compareByItems: [],
			compareBy: null,
			compareWithQuery: '',
			compareWithItems: [],
			compareWith: [],
			valid: true
		}
	},
	mounted () {
		this.compareByItems.push(this.filters?.locationFilter.type);
		this.filters?.indicatorFilters.forEach(filter => {
			this.compareByItems.push(filter.type)
		});
		if (router.currentRoute.query.compareBy) {
			if (router.currentRoute.query.compareBy === 'l') {
				this.compareBy = this.compareByItems[0];
				this.selectCompareBy();
				router.currentRoute.query.compareWith.forEach(p => {
					this.compareWith.push(this.compareWithItems.find(i => i.typeId == p.split("_")[0] && i.id == p.split("_")[1]));
				});
			} else {
				this.compareBy = this.compareByItems.find(i => i.id);
				this.selectCompareBy();
				router.currentRoute.query.compareWith.forEach(p => {
					this.compareWith.push(this.compareWithItems.find(i => i.id == p));
				});
			}
			this.applyComparison();
		}
	},
	methods: {
		...mapActions(['setCompareSelections']),
		selectCompareBy() {
			this.compareWith = [];
			this.compareWithItems = [];
			if (this.compareBy?.name_en === 'Location') {
				this.compareWithItems = this.filters?.locationFilter.options;
			} else {
				this.compareWithItems = this.filters?.indicatorFilters.find(filter => filter.type.name_en === this.compareBy?.name_en)?.options;
			}
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