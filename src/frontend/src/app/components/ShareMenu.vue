<template>
	<v-menu offset-y min-width="300px">
		<template v-slot:activator="{ on }">
			<v-btn icon data-html2canvas-ignore v-on="on" :aria-label="$t('tools.common.share.name')">
				<v-img src="/img/icon_ux_menu_share__active.svg" width="24px" height="24px"></v-img>
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
			try {
				window.navigator.clipboard
					.writeText(this.shareUrl)
					.then((self = this) => {
						self.shareMessages = [i18n.t('tools.common.copy_success')]
						window.setTimeout(() => (self.shareMessages = []), 2000)
					})
					.catch(() => {
						this.shareMessages = [
							i18n.t('tools.common.copy_failure')
						];
					});
			} catch (e) {
				this.shareMessages = [
					i18n.t('tools.common.copy_failure')
				];
			}
		}
	},
}
</script>

<style lang="scss" scoped>
	.cursor-pointer {
		cursor: pointer;
	}
</style>