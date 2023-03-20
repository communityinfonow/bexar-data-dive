<template>
	<v-container>
		<h1 class="text-h3 mb-4">Indicators</h1>
		<v-row dense>
			<v-col cols="12" v-for="(ind, idx) in indicators" :key="ind.id">
				<v-form ref="update_forms" v-model="updateForms[idx]" lazy-validation>
					<v-card class="mb-8">
						<v-card-title>Indicator {{ ind.id }}</v-card-title>
						<v-card-text>
							<v-text-field v-model="ind.name_en" label="Name (English)" :rules="[rules.required]"></v-text-field>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="ind.description_en" label="Description (English)"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="ind.description_en"></div>
								</v-col>
							</v-row>
							<v-text-field v-model="ind.name_es" label="Name (Spanish)" :rules="[rules.required]"></v-text-field>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="ind.description_es" label="Description (Spanish)"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="ind.description_es"></div>
								</v-col>
							</v-row>
							<v-select 
								label="Category" 
								:items="categories"
								item-text="name_en"
								item-value="id"
								v-model="ind.categoryId"
								:rules="[rules.required]"
							>
							</v-select>
							<v-select 
								label="Type"
								:items="types"
								item-text="name"
								item-value="id"
								v-model="ind.typeId"
								:rules="[rules.required]"
							>
							</v-select>
							<v-select 
								label="Source"
								:items="sourceOptions"
								item-text="name_en"
								item-value="id"
								v-model="ind.sourceId"
								:rules="[rules.required]"
							>
							</v-select>
							<v-switch label="Featured" v-model="ind.featured"></v-switch>
							<v-switch label="Display" v-model="ind.display"></v-switch>
						</v-card-text>
						<v-card-actions>
							<v-btn color="primary" @click="updateHandler(ind, idx)">
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
						<v-card-title>Add Indicator</v-card-title>
						<v-card-text>
							<v-text-field type="number" v-model="newIndicator.id" label="ID" :rules="[rules.required, rules.uniqueAdd]"></v-text-field>
							<v-text-field v-model="newIndicator.name_en" label="Name (English)" :rules="[rules.required]"></v-text-field>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="newIndicator.description_en" label="Description (English)"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="newIndicator.description_en"></div>
								</v-col>
							</v-row>
							<v-text-field v-model="newIndicator.name_es" label="Name (Spanish)" :rules="[rules.required]"></v-text-field>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="newIndicator.description_es" label="Description (Spanish)"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="newIndicator.description_es"></div>
								</v-col>
							</v-row>
							<v-select 
								label="Category" 
								:items="categories"
								item-text="name_en"
								item-value="id"
								v-model="newIndicator.categoryId"
								:rules="[rules.required]"
							>
							</v-select>
							<v-select 
								label="Type"
								:items="types"
								item-text="name"
								item-value="id"
								v-model="newIndicator.typeId"
								:rules="[rules.required]"
							>
							</v-select>
							<v-select 
								label="Source"
								:items="sourceOptions"
								item-text="name_en"
								item-value="id"
								v-model="newIndicator.sourceId"
								:rules="[rules.required]"
							>
							</v-select>
							<v-switch label="Featured" v-model="newIndicator.featured"></v-switch>
							<v-switch label="Display" v-model="newIndicator.display"></v-switch>
						</v-card-text>
						<v-card-actions>
							<v-btn type="submit" color="primary" @click.prevent="addHandler">
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
	name: 'IndicatorsView',
	mounted () {
		this.getCategories();
		this.getTypes();
		this.getSources();
		this.getIndicators();
	},
	data() {
		return {
			rules: {
				required: value => !!value || 'Required',
				uniqueAdd: value => !this.indicators?.find(i => i.id == value) || 'ID must be unique',
			},
			addForm: null,
			updateForms: [],
			newIndicator: {},
			messageText: '',
			message: false
		}
	},
	computed: {
		...mapState(['categories', 'types', 'sources', 'indicators']),
		sourceOptions() {
			return this.sources?.filter(s => !!s.display)
		}
	},
	methods: {
		...mapActions([
			'getCategories', 
			'getTypes', 
			'getSources', 
			'getIndicators', 
			'addIndicator', 
			'updateIndicator'
		]),
		addHandler() {
			this.$refs.add_form.validate();
			if (this.addForm) {
				this.addIndicator(this.newIndicator).then(() => {
					this.newIndicator = {};
					this.messageText = 'Indicator added'
					this.message = true;
					this.$refs.add_form.resetValidation();
				});
			}
		},
		updateHandler(indicator, index) {
			this.$refs.update_forms[index].validate();
			if (this.updateForms[index]) {
				this.updateIndicator(indicator).then(() => {
					this.messageText = 'Indicator updated'
					this.message = true;
					this.$refs.update_forms[index].resetValidation();
				});
			}
		}
	}
}
</script>

<style lang="scss" scoped>

</style>