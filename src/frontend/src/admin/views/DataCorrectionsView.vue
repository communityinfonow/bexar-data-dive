<template>
	<v-container v-if="ready">
		<h1 class="text-h3 mb-4">Data Corrections</h1>
		<v-row dense>
			<v-col cols="12" v-for="(dc, idx) in dataCorrections" :key="dc.id">
				<v-form ref="update_forms" v-model="updateForms[idx]">
					<v-card class="mb-8">
						<v-card-text>
							<v-select
								v-model="dc.indicator"
								:items="sortedIndicators"
								label="Indicator"
								required
								:rules="[rules.required]"
							>
							</v-select>
							<v-menu
								ref="update_data_correction_date_menus"
								v-model="updateDataCorrectionDateMenus[idx]"
								:close-on-content-click="false"
								:return-value.sync="dc.dateCorrected"
								transition="scale-transition"
								offset-y
								min-width="auto"
							>
								<template v-slot:activator="{ on, attrs }">
									<v-text-field
										v-model="dc.dateCorrected"
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
									v-model="dc.dateCorrected"
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
										@click="$refs.update_data_correction_date_menus[idx].save(dc.dateCorrected)"
									>
										OK
									</v-btn>
								</v-date-picker>
							</v-menu>
							<p>TODO: year multi-select...will need all filters for indicator(s) that have data corrections already...</p>
							<p>TODO: location multi-select</p>
							<p>TODO: filter type multi-select</p>
							<p>TODO: display toggle</p>
						</v-card-text>
						<v-card-actions>
							<v-btn rounded color="red" @click="updateHandler(dc, idx)">
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
						<v-card-title>Add Data Correction</v-card-title>
						<v-card-text>
							<v-select
								v-model="newDataCorrection.indicator"
								:items="sortedIndicators"
								:item-text="item => item.name_en"
								return-object
								label="Indicator"
								required
								:rules="[rules.required]"
								@change="getFilters(newDataCorrection.indicator)"
							>
							</v-select>
							<v-menu
								ref="new_data_correction_date_menu"
								v-model="newDataCorrectionDateMenu"
								:close-on-content-click="false"
								:return-value.sync="newDataCorrection.dateCorrected"
								transition="scale-transition"
								offset-y
								min-width="auto"
							>
								<template v-slot:activator="{ on, attrs }">
									<v-text-field
										v-model="newDataCorrection.dateCorrected"
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
									v-model="newDataCorrection.dateCorrected"
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
										@click="$refs.new_data_correction_date_menu.save(newDataCorrection.dateCorrected)"
									>
										OK
									</v-btn>
								</v-date-picker>
							</v-menu>
							<v-select
								v-if="filters.yearFilter"
								v-model="newDataCorrection.years"
								:items="filters.yearFilter.options"
								item-text="name_en"
								item-value="id"
								label="Years"
								required
								:rules="[rules.required]"
								multiple
							>
							</v-select>
							<v-select
								v-if="filters.locationTypeFilter"
								v-model="newDataCorrection.locationTypes"
								:items="filters.locationTypeFilter.options"
								item-text="name_en"
								item-value="id"
								label="Location Types"
								required
								:rules="[rules.required]"
								multiple
								return-object
							>
							</v-select>
							<v-select
								v-if="filters.indicatorFilters"
								v-model="newDataCorrection.filterTypes"
								:items="filters.indicatorFilters.map(f => f.type)"
								:item-text="t => t.name_en"
								label="Filter Types"
								required
								:rules="[rules.required]"
								multiple
								return-object
							>
							</v-select>
							<v-switch label="Display" v-model="newDataCorrection.display"></v-switch>
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
import axios from 'axios'
export default {
	name: 'DataCorrectionsView',
	mounted () {
		this.getIndicators();
		//this.getDataCorrections();
	},
	data() {
		return {
			rules: {
				required: value => !!value || 'Required'
			},
			addForm: null,
			updateForms: [],
			newDataCorrection: {},
			updateDataCorrectionDateMenus: {},
			newDataCorrectionDateMenu: false,
			messageText: '',
			message: false,
			filters: {}
		}
	},
	computed: {
		...mapState(['indicators', 'dataCorrections']),
		ready() {
			return this.indicators
		},
		sortedIndicators() {
			return JSON.parse(JSON.stringify(this.indicators)).sort((a, b) => a.name_en.localeCompare(b.name_en))
		}
	},
	methods: {
		...mapActions([
			'getIndicators',
			'getDataCorrections', 
			'addDataCorrection', 
			'updateDataCorrection'
		]),
		getFilters(indicator) {
			console.log(indicator)
			return axios.get('/api/filters', { params: {
				indicator: indicator.id
			}}).then(response => { 
				this.filters = response.data;
			})
		},
		addHandler() {
			this.$refs.add_form.validate();
			if (this.addForm) {
				this.addDataCorrection(this.newDataCorrection).then(() => {
					this.newDataCorrection = {};
					this.messageText = 'Data correction added'
					this.message = true;
					this.$refs.add_form.resetValidation();
				});
			}
		},
		updateHandler(dc, idx) {
			this.$refs.update_forms[idx].validate();
			if (this.updateForms[idx]) {
				this.updateDataCorrection(dc).then(() => {
					this.messageText = 'Data correction updated'
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