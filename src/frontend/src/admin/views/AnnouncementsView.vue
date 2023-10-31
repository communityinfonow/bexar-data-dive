<template>
	<v-container>
		<h1 class="text-h3 mb-4">Announcements</h1>
		<v-row dense>
			<v-col cols="12" v-for="(ann, idx) in announcements" :key="ann.id">
				<v-form ref="update_forms" v-model="updateForms[idx]">
					<v-card class="mb-8">
						<v-card-text>
							<v-text-field v-model="ann.title_en" label="Title (English)" :rules="[rules.required]"></v-text-field>
							<v-text-field v-model="ann.title_es" label="Title (Spanish)" :rules="[rules.required]"></v-text-field>
							<v-menu
								ref="update_announcement_date_menus"
								v-model="updateAnnouncementDateMenus[idx]"
								:close-on-content-click="false"
								:return-value.sync="ann.date"
								transition="scale-transition"
								offset-y
								min-width="auto"
							>
								<template v-slot:activator="{ on, attrs }">
									<v-text-field
										v-model="ann.date"
										label="Date"
										prepent-icon="mdi-calendar"
										readonly
										v-bind="attrs"
										v-on="on"
										:rules="[rules.required]"
									>
									</v-text-field>
								</template>
								<v-date-picker
									v-model="ann.date"
									no-title
									scrollable
								>
									<v-spacer></v-spacer>
									<v-btn
										text
										rounded
										color="red"
										@click="menu = false"
									>
										Cancel
									</v-btn>
									<v-btn
										text
										rounded
										color="red"
										@click="$refs.update_announcement_date_menus[idx].save(ann.date)"
									>
										OK
									</v-btn>
								</v-date-picker>
							</v-menu>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="ann.message_en" label="Message (English)" :rules="[rules.required]"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="ann.message_en"></div>
								</v-col>
							</v-row>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="ann.message_es" label="Message (Spanish)" :rules="[rules.required]"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="ann.message_es"></div>
								</v-col>
							</v-row>
							<v-switch label="Display" v-model="ann.display"></v-switch>
						</v-card-text>
						<v-card-actions>
							<v-btn rounded color="red" @click="updateHandler(ann, idx)">
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
						<v-card-title>Add Announcement</v-card-title>
						<v-card-text>
							<v-text-field v-model="newAnnouncement.title_en" label="Title (English)" :rules="[rules.required]"></v-text-field>
							<v-text-field v-model="newAnnouncement.title_es" label="Title (Spanish)" :rules="[rules.required]"></v-text-field>
							<v-menu
								ref="newAnnouncmentDateMenu"
								v-model="newAnnouncementDateMenu"
								:close-on-content-click="false"
								:return-value.sync="newAnnouncement.date"
								transition="scale-transition"
								offset-y
								min-width="auto"
							>
								<template v-slot:activator="{ on, attrs }">
									<v-text-field
										v-model="newAnnouncement.date"
										label="Date"
										prepent-icon="mdi-calendar"
										readonly
										v-bind="attrs"
										v-on="on"
										:rules="[rules.required]"
									>
									</v-text-field>
								</template>
								<v-date-picker
									v-model="newAnnouncement.date"
									no-title
									scrollable
								>
									<v-spacer></v-spacer>
									<v-btn
										rounded
										text
										color="red"
										@click="menu = false"
									>
										Cancel
									</v-btn>
									<v-btn
										rounded
										text
										color="red"
										@click="$refs.newAnnouncmentDateMenu.save(newAnnouncement.date)"
									>
										OK
									</v-btn>
								</v-date-picker>
							</v-menu>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="newAnnouncement.message_en" label="Message (English)" :rules="[rules.required]"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="newAnnouncement.message_en"></div>
								</v-col>
							</v-row>
							<v-row>
								<v-col cols="6">
									<v-textarea class="flex-grow-1" v-model="newAnnouncement.message_es" label="Message (Spanish)" :rules="[rules.required]"></v-textarea>
								</v-col>
								<v-col cols="6">
									<p class="subtitle-1 font-italic">Preview</p>
									<div class="flex-grow-1" v-html="newAnnouncement.message_es"></div>
								</v-col>
							</v-row>
							<v-switch label="Display" v-model="newAnnouncement.display"></v-switch>
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
	name: 'AnnouncementsView',
	mounted () {
		this.getAnnouncements();
	},
	data() {
		return {
			rules: {
				required: value => !!value || 'Required'
			},
			addForm: null,
			updateForms: [],
			newAnnouncement: {},
			updateAnnouncementDateMenus: {},
			newAnnouncementDateMenu: false,
			messageText: '',
			message: false,
		}
	},
	computed: {
		...mapState(['announcements'])
	},
	methods: {
		...mapActions([
			'getAnnouncements', 
			'addAnnouncement', 
			'updateAnnouncement'
		]),
		addHandler() {
			this.$refs.add_form.validate();
			if (this.addForm) {
				this.addAnnouncement(this.newAnnouncement).then(() => {
					this.newAnnouncement = {};
					this.messageText = 'Announcement added'
					this.message = true;
					this.$refs.add_form.resetValidation();
				});
			}
		},
		updateHandler(ann, idx) {
			this.$refs.update_forms[idx].validate();
			if (this.updateForms[idx]) {
				this.updateAnnouncement(ann).then(() => {
					this.messageText = 'Announcement updated'
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