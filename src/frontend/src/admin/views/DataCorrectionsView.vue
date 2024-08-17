<template>
	<v-container v-if="ready">
		<h1 class="text-h3 mb-4">Data Corrections</h1>
		<v-row dense>
			<v-col cols="12" v-for="(dc, idx) in dataCorrections" :key="dc.id">
				<v-form ref="update_forms" v-model="updateForms[idx]">
					<v-card class="mb-8">
						<v-card-text>
							<v-btn-toggle v-model="dc.correctionType">
								<v-btn value="indicator">Indicator</v-btn>
								<v-btn value="source">Source</v-btn>
							</v-btn-toggle>
							<v-select
								v-if="dc.correctionType === 'source'"
								v-model="dc.source"
								:items="sources"
								:item-value="item => item.id"
								:item-text="item => item.name_en"
								label="Source"
								required
								:rules="[rules.required]"
							>
							</v-select>
							<v-select
								v-if="dc.correctionType === 'indicator'"
								v-model="dc.indicator"
								:items="sortedIndicators"
								:item-value="item => item.id"
								:item-text="item => item.name_en"
								:item-disabled="item => !item.hasData"
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
							<template v-if="updateDataCorrectionFilters[idx]">
								<v-select
									v-if="updateDataCorrectionFilters[idx].yearFilter"
									v-model="dc.years"
									:items="updateDataCorrectionFilters[idx].yearFilter.options"
									:item-value="item => item.id"
									:item-text="item => item.name_en"
									label="Years"
									required
									:rules="[rules.required]"
									multiple
								>
								</v-select>
								<v-select
									v-if="updateDataCorrectionFilters[idx].locationTypeFilter"
									v-model="dc.locationTypes"
									:items="updateDataCorrectionFilters[idx].locationTypeFilter.options"
									:item-value="item => item.id"
									:item-text="item => item.name_en"
									label="Location Types"
									required
									:rules="[rules.required]"
									multiple
								>
								</v-select>
								<v-select
									v-if="updateDataCorrectionFilters[idx].indicatorFilters"
									v-model="dc.filterTypes"
									:items="[{ id: null, name_en: 'No Filter'}].concat(updateDataCorrectionFilters[idx].indicatorFilters.map(f => f.type))"
									:item-value="item => item.id"
									:item-text="item => item.name_en"
									label="Filter Types"
									required
									:rules="[rules.required]"
									multiple
								>
								</v-select>
								<v-textarea
									v-model="dc.note"
									label="Note"
									auto-grow
								>
								</v-textarea>
								<v-switch label="Display" v-model="dc.display"></v-switch>
							</template>	
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
							<v-btn-toggle v-model="newDataCorrection.correctionType">
								<v-btn value="indicator">Indicator</v-btn>
								<v-btn value="source">Source</v-btn>
							</v-btn-toggle>
							<v-select
								v-if="newDataCorrection.correctionType === 'source'"
								v-model="newDataCorrection.source"
								:items="sources"
								:item-value="item => item.id"
								:item-text="item => item.name_en"
								label="Source"
								required
								:rules="[rules.required]"
								@change="getFilters(newDataCorrection, null)"
							/>
							<v-select
								v-if="newDataCorrection.correctionType === 'indicator'"
								v-model="newDataCorrection.indicator"
								:items="sortedIndicators"
								:item-value="item => item.id"
								:item-text="item => item.name_en"
								:item-disabled="item => !item.hasData"
								label="Indicator"
								required
								:rules="[rules.required]"
								@change="getFilters(newDataCorrection, null)"
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
								:item-text="item => item.name_en"
								:item-value="item => item.id"
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
								:item-text="item => item.name_en"
								:item-value="item => item.id"
								label="Location Types"
								required
								:rules="[rules.required]"
								multiple
							>
							</v-select>
							<v-select
								v-if="filters.indicatorFilters"
								v-model="newDataCorrection.filterTypes"
								:items="[{ id: null, name_en: 'No Filter'}].concat(filters.indicatorFilters.map(f => f.type))"
								:item-text="t => t.name_en"
								:item-value="t => t.id"
								label="Filter Types"
								required
								:rules="[rules.required]"
								multiple
							>
							</v-select>
							<v-textarea
								v-model="newDataCorrection.note"
								label="Note"
								auto-grow
							>
							</v-textarea>
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
		this.getSources();
		this.getDataCorrectionsAndFilters();
	},
	data() {
		return {
			rules: {
				required: value => !!value || 'Required'
			},
			addForm: null,
			updateForms: [],
			newDataCorrection: {},
			updateDataCorrectionDateMenus: [],
			updateDataCorrectionFilters: [],
			newDataCorrectionDateMenu: false,
			messageText: '',
			message: false,
			filters: {}
		}
	},
	computed: {
		...mapState(['indicators', 'sources', 'dataCorrections']),
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
			'getSources',
			'getDataCorrections', 
			'addDataCorrection', 
			'updateDataCorrection'
		]),
		getDataCorrectionsAndFilters() {
			this.getDataCorrections().then(() => {
				this.updateForms = this.dataCorrections.map(() => null);
				this.dataCorrections.forEach((dc, idx) => this.updateDataCorrectionDateMenus[idx] = false);
				this.updateDataCorrectionFilters = this.dataCorrections.map(() => null);
				this.dataCorrections.forEach((dc, idx) => {
					this.getFilters(dc, idx)
				});
			});
		},
		getFilters(correction, idx) {
			return axios.get('/api/filters', { params: {
				indicator: correction.indicator,
				source: correction.source
			}}).then(response => { 
				if (idx === null) {
					this.filters = response.data;
				} else {
					this.$set(this.updateDataCorrectionFilters, idx, response.data);
				}
			})
		},
		addHandler() {
			this.$refs.add_form.validate();
			if (this.addForm) {
				this.newDataCorrection.filterTypes = this.newDataCorrection.filterTypes || [];
				this.addDataCorrection(this.newDataCorrection).then(() => {
					this.newDataCorrection = {};
					this.messageText = 'Data correction added'
					this.message = true;
					this.$refs.add_form.resetValidation();
					this.getDataCorrectionsAndFilters();
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
					this.getDataCorrectionsAndFilters();
				});
			}
		}
	}
}
</script>

<style lang="scss" scoped>

</style>