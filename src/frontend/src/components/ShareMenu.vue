<template>
	<v-menu offset-y min-width="300px">
		<template v-slot:activator="{ on }">
			<v-btn color="accent" icon data-html2canvas-ignore v-on="on">
				<v-icon>mdi-share</v-icon>
			</v-btn>
		</template>
		<v-card class="pa-4">
			<v-text-field
				autofocus
				readonly
				:label="copyMessage"
				class="cursor-pointer"
				append-icon="mdi-content-copy"
				@focus="$event.target.select()"
				@click.stop="copyShareUrl"
				@click:append.stop="copyShareUrl"
				:value="shareUrl"
				:messages="shareMessages"
			>
			</v-text-field>
		</v-card>
	</v-menu>
</template>

<script>
import i18n from '@/i18n'
//import router from '@/router/index'

export default {
	name: 'ShareMenu',
	data() {
		return {
			shareUrl: window.location.href,
			shareMessages: []
		}
	},
	computed: {
		copyMessage() {
			return i18n.t('tools.common.click_copy');
		}
	},
	watch: {
		$route(to) {
			this.shareUrl = window.location.origin + to.fullPath;
		}
	},
	methods: {
		copyShareUrl() {
			navigator.permissions.query({ name: 'clipboard-write' }).then(result => {
				if (result.state === 'granted' || result.state === 'prompt') {
					window.navigator.clipboard
						.writeText(this.shareUrl)
						.then((self = this) => {
							self.shareMessages = [i18n.t('tools.common.copy_success')]
							window.setTimeout(() => (self.shareMessages = []), 2000)
						});
				} else {
					this.shareMessages = [
						i18n.t('tools.common.copy_failure')
					];
				}
			});
		}
	},
}
</script>

<style lang="scss" scoped>
	.cursor-pointer {
		cursor: pointer;
	}
</style>