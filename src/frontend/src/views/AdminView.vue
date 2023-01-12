<template>
	<div v-if="loggedIn">
		welcome to the admin view, {{ email }}
	</div>
</template>

<script>
import axios from 'axios'

export default {
	name: 'AdminView',
	data() {
		return {
			loggedIn: false,
			email: null
		}
	},
	mounted () {
		axios.get('/api/admin/username')
			.then((response) => {
				this.loggedIn = true
				this.email = response.data
			})
			.catch(error => {
				console.log(error);
				if (error.response.status === 401) {
					console.log('401 error encountered');
					window.location = window.location.protocol +'//' + window.location.hostname + ':' + process.env.VUE_APP_API_PORT + '/oauth2/authorization/google';
				}
			});
	},
}
</script>

<style lang="scss" scoped>

</style>