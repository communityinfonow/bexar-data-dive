<template>
	<v-container>
		<h1 class="text-h3 mb-4">Audit Log</h1>
		<v-simple-table>
			<template v-slot:default>
				<thead>
					<tr>
						<th>Date</th>
						<th>Table</th>
						<th>Type</th>
						<th>Old Data</th>
						<th>New Data</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="entry in auditLog" :key="entry.date">
						<td>{{ new Date(entry.date).toLocaleString() }}</td>
						<td>{{ entry.table }}</td>
						<td>{{ entry.type }}</td>
						<td><pre>{{ JSON.parse(entry.oldData) }}</pre></td>
						<td><pre>{{ JSON.parse(entry.newData) }}</pre></td>
					</tr>
				</tbody>
			</template>
		</v-simple-table>
	</v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
	name: 'AuditLogView',
	mounted () {
		this.getAuditLog();
	},
	data() {
		return {
		}
	},
	computed: {
		...mapState(['auditLog'])
	},
	methods: {
		...mapActions(['getAuditLog']),
	},
}
</script>

<style lang="scss" scoped>

</style>