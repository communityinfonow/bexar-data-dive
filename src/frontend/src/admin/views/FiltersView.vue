<template>
	<v-container>
		<h1 class="text-h3 mb-4">Filters</h1>
		<v-row dense>
			<v-col cols="12" v-for="(fo, idx) in filterOptions" :key="fo.id">
				<v-form ref="update_forms" v-model="updateForms[idx]">
					<v-card class="mb-8">
						<v-card-title>Filter {{ fo.id }}</v-card-title>
						<v-card-text>
							<v-text-field v-model="fo.name_en" label="Name (English)" :rules="[rules.required]"></v-text-field>
							<v-text-field v-model="fo.name_es" label="Name (Spanish)" :rules="[rules.required]"></v-text-field>
							<v-select 
								label="Filter type" 
								:items="filterTypes"
								item-text="name_en"
								item-value="id"
								v-model="fo.typeId"
								:rules="[rules.required]"
							>
							</v-select>
							<v-text-field v-model="fo.sortOrder" label="Sort order" :rules="[rules.required, rules.uniqueSortUpdate(fo)]" type="number" ></v-text-field>
							<v-switch label="Display" v-model="fo.display"></v-switch>
						</v-card-text>
						<v-card-actions>
							<v-btn rounded color="red" @click="updateHandler(fo, idx)">
								Save
							</v-btn>
						</v-card-actions>
					</v-card>
				</v-form>
			</v-col>
		</v-row>
		<v-row dense>
			<v-col cols="12">
				<v-form ref="add_form" v-model="addForm">
					<v-card class="mb-8">
						<v-card-title>Add Filter</v-card-title>
						<v-card-text>
							<v-text-field type="number" v-model="newFilterOption.id" label="ID" :rules="[rules.required, rules.uniqueIdAdd]"></v-text-field>
							<v-text-field v-model="newFilterOption.name_en" label="Name (English)" :rules="[rules.required]"></v-text-field>
							<v-text-field v-model="newFilterOption.name_es" label="Name (Spanish)" :rules="[rules.required]"></v-text-field>
							<v-select 
								label="Filter type" 
								:items="filterTypes"
								item-text="name_en"
								item-value="id"
								v-model="newFilterOption.typeId"
								:rules="[rules.required]"
							>
							</v-select>
							<v-text-field v-model="newFilterOption.sortOrder" label="Sort order" :rules="[rules.required, rules.uniqueSortAdd]" type="number" ></v-text-field>
							<v-switch label="Display" v-model="newFilterOption.display"></v-switch>
						</v-card-text>
						<v-card-actions>
							<v-btn rounded type="submit" color="red" @click.prevent="addHandler">
								Add
							</v-btn>
						</v-card-actions>
					</v-card>
				</v-form>
			</v-col>
		</v-row>
		<v-snackbar v-model="message" timeout="2000" color="success" class="mb-8">
			{{ messageText }}
		</v-snackbar>
	</v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'
export default {
	name: 'FiltersView',
	mounted () {
		this.getFilterTypes();
		this.getFilterOptions();
	},
	data() {
		return {
			rules: {
				required: value => !!value || 'Required',
				uniqueIdAdd: value => !this.filterOptions?.find(fo => fo.id == value) || 'ID must be unique',
				uniqueSortAdd: value => !this.filterOptions?.find(fo => fo.sortOrder == value && fo.typeId == this.newFilterOption.typeId) || 'Sort order must be unique',
				uniqueSortUpdate: (o) => {
					return value => this.filterOptions?.filter(fo => fo.sortOrder == value && fo.typeId == o.typeId).length <= 1 || 'Sort order must be unique'
				}
			},
			addForm: null,
			updateForms: [],
			newFilterOption: {},
			messageText: '',
			message: false
		}
	},
	computed: {
		...mapState(['filterTypes', 'filterOptions'])
	},
	methods: {
		...mapActions([
			'getFilterTypes',
			'getFilterOptions', 
			'addFilterOption', 
			'updateFilterOption'
		]),
		addHandler() {
			this.$refs.add_form.validate();
			if (this.addForm) {
				this.addFilterOption(this.newFilterOption).then(() => {
					this.newFilterOption = {};
					this.messageText = 'Filter added'
					this.message = true;
					this.$refs.add_form.resetValidation();
				});
			}
		},
		updateHandler(fo, idx) {
			this.$refs.update_forms[idx].validate();
			if (this.updateForms[idx]) {
				this.updateFilterOption(fo).then(() => {
					this.messageText = 'Filter updated'
					this.message = true;
					this.$refs.update_forms[idx].resetValidation();
				});
			}
		}
	}
}
</script>

<style lang="scss" scoped>

</style>