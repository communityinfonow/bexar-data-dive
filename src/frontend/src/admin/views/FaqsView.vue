<template>
	<v-container>
		<h1 class="text-h3 mb-4">Frequently Asked Questions</h1>
		<v-row dense>
			<v-col cols="12" v-for="(q, i) in faqs" :key="q.id">
				<v-form ref="update_forms" v-model="updateForms[i]">
					<v-card class="mb-8">
						<v-card-title>Question {{ q.id }}</v-card-title>
						<v-card-text>
							<v-text-field type="number" v-model="q.sort_order" label="Sort order" :rules="[rules.required, rules.uniqueUpdate]"></v-text-field>
							<v-text-field v-model="q.question_en" label="Question (English)" :rules="[rules.required]"></v-text-field>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="q.answer_en" label="Answer (English)" :rules="[rules.required]"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="q.answer_en"></div>
								</v-col>
							</v-row>
							<v-text-field v-model="q.question_es" label="Question (Spanish)" :rules="[rules.required]"></v-text-field>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="q.answer_es" label="Answer (Spanish)" :rules="[rules.required]"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="q.answer_es"></div>
								</v-col>
							</v-row>
							<v-switch label="Display" v-model="q.display"></v-switch>
						</v-card-text>
						<v-card-actions>
							<v-btn rounded color="red" @click="updateHandler(q, i)">
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
						<v-card-title>New Question</v-card-title>
						<v-card-text>
							<v-text-field type="number" v-model="newFaq.sort_order" label="Sort order" :rules="[rules.required, rules.uniqueAdd]"></v-text-field>
							<v-text-field v-model="newFaq.question_en" label="Question (English)" :rules="[rules.required]"></v-text-field>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="newFaq.answer_en" label="Answer (English)" :rules="[rules.required]"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="newFaq.answer_en"></div>
								</v-col>
							</v-row>
							<v-text-field v-model="newFaq.question_es" label="Question (Spanish)" :rules="[rules.required]"></v-text-field>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="newFaq.answer_es" label="Answer (Spanish)" :rules="[rules.required]"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="newFaq.answer_es"></div>
								</v-col>
							</v-row>
							<v-switch label="Display" v-model="newFaq.display"></v-switch>
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
	name: 'FaqsView',
	mounted () {
		this.getFaqs();
	},
	data() {
		return {
			rules: {
				required: value => !!value || 'Required',
				uniqueAdd: value => !this.faqs?.find(q => q.sort_order == value) || 'Sort order must be unique',
				uniqueUpdate: value => this.faqs?.filter(q => q.sort_order == value).length <= 1 || 'Sort order must be unique'
			},
			addForm: null,
			updateForms: [],
			newFaq: {},
			messageText: '',
			message: false,
		}
	},
	computed: {
		...mapState(['faqs'])
	},
	methods: {
		...mapActions(['getFaqs', 'addFaq', 'updateFaq']),
		addHandler() {
			this.$refs.add_form.validate();
			if (this.addForm) {
				this.addFaq(this.newFaq).then(() => {
					this.newFaq = {};
					this.messageText = 'FAQ added'
					this.message = true;
					this.$refs.add_form.resetValidation();
				});
			}
		},
		updateHandler(q, i) {
			this.$refs.update_forms[i].validate();
			if (this.updateForms[i]) {
				this.updateFaq(q).then(() => {
					this.messageText = 'FAQ updated'
					this.message = true;
					this.$refs.update_forms[i].resetValidation();
				});
			}
		}
	}
}
</script>

<style lang="scss" scoped>

</style>