<template>
	<v-container>
		<h1 class="text-h3 mb-4">Sources</h1>
		<v-row dense>
			<v-col cols="12" v-for="(src, idx) in sources" :key="src.id">
				<v-form ref="update_forms" v-model="updateForms[idx]">
					<v-card class="mb-8">
						<v-card-title>Source {{ src.id }}</v-card-title>
						<v-card-text>
							<v-text-field v-model="src.name_en" label="Name (English)" :rules="[rules.required]"></v-text-field>
							<v-text-field v-model="src.name_es" label="Name (Spanish)" :rules="[rules.required]"></v-text-field>
							<v-text-field v-model="src.url" label="Website" :rules="[rules.required]"></v-text-field>
							<v-text-field v-model="src.trendInterval" label="Trend Interval" :rules="[rules.required]" type="number" ></v-text-field>
							<v-switch label="Display" v-model="src.display"></v-switch>
						</v-card-text>
						<v-card-actions>
							<v-btn color="primary" @click="updateHandler(src, idx)">
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
						<v-card-title>Add Source</v-card-title>
						<v-card-text>
							<v-text-field type="number" v-model="newSource.id" label="ID" :rules="[rules.required, rules.uniqueAdd]"></v-text-field>
							<v-text-field v-model="newSource.name_en" label="Name (English)" :rules="[rules.required]"></v-text-field>
							<v-text-field v-model="newSource.name_es" label="Name (Spanish)" :rules="[rules.required]"></v-text-field>
							<v-text-field v-model="newSource.url" label="Website" :rules="[rules.required]"></v-text-field>
							<v-text-field v-model="newSource.trendInterval" label="Trend Interval" :rules="[rules.required]" type="number" ></v-text-field>
							<v-switch label="Display" v-model="newSource.display"></v-switch>
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
	name: 'SourcesView',
	mounted () {
		this.getSources();
	},
	data() {
		return {
			rules: {
				required: value => !!value || 'Required',
				uniqueAdd: value => !this.sources?.find(s => s.id == value) || 'ID must be unique',
			},
			addForm: null,
			updateForms: [],
			newSource: {},
			messageText: '',
			message: false,
		}
	},
	computed: {
		...mapState(['sources'])
	},
	methods: {
		...mapActions([
			'getSources', 
			'addSource', 
			'updateSource'
		]),
		addHandler() {
			this.$refs.add_form.validate();
			if (this.addForm) {
				this.addSource(this.newSource).then(() => {
					this.newSource = {};
					this.messageText = 'Source added'
					this.message = true;
					this.$refs.add_form.resetValidation();
				});
			}
		},
		updateHandler(src, idx) {
			this.$refs.update_forms[idx].validate();
			if (this.updateForms[idx]) {
				this.updateSource(src).then(() => {
					this.messageText = 'Source updated'
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