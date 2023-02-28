<template>
	<v-container>
		<h1 class="text-h3 mb-4">Missed Translations</h1>
		<v-alert type="info" v-if="missedTranslations && !missedTranslations.length">No missed tranlsations found</v-alert>
		<v-simple-table>
			<template v-slot:default>
				<thead>
					<tr>
						<th>Table</th>
						<th>Data</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(entry, index) in missedTranslations" :key="index">
						<td>{{ entry.table }}</td>
						<td><pre>{{ JSON.parse(entry.data) }}</pre></td>
					</tr>
				</tbody>
			</template>
		</v-simple-table>
	</v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
	name: 'MissedTranslationsView',
	mounted () {
		this.getMissedTranslations();
	},
	data() {
		return {
		}
	},
	computed: {
		...mapState(['missedTranslations'])
	},
	methods: {
		...mapActions(['getMissedTranslations']),
	},
}
</script>

<style lang="scss" scoped>

</style>