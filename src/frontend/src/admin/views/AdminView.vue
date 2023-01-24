<template>
	<div>
		welcome to the admin view, {{ username }}
	</div>
</template>

<script>
import { mapState } from 'vuex'

export default {
	name: 'AdminView',
	data() {
		return {
		}
	},
	computed: {
		...mapState(['username'])
	},
	mounted () {
		
	},
}
</script>

<style lang="scss" scoped>

</style>