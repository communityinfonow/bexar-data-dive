<template>
	<v-row class="mt-2">
		<v-col cols="4">
			<v-select
				label="Compare by"
				dense
				flat
			>
			</v-select>
		</v-col>
		<v-col cols="4">
			<v-select
				label="Compare with"
				dense
				flat
				multiple
			>
			</v-select>
		</v-col>
		<v-col cols="2">
			<v-btn
				color="primary"
				tile
			>
				Compare
			</v-btn>
		</v-col>
		<!--<v-col cols="2">
			[todo: labels toggle]
		</v-col>-->
	</v-row>
</template>

<script>
	export default {
		name: 'DashboardToolsPanel'
		//TODO: set the options for the 'by' select
		// then dynamically set the options for the 'with' select
		// then handle compare click event
		// first validate that a by and with are selected
		// then fetch data - will need API updates for by and multiple withs
	}
</script>

<style lang="scss" scoped>

</style>