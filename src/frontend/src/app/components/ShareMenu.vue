<template>
	<v-menu offset-y min-width="440px">
		<template v-slot:activator="{ on: menu, attrs }">
			<v-tooltip v-model="open" top z-index="9999">
				<template v-slot:activator="{ on: tooltip }">
					<v-btn icon data-html2canvas-ignore v-bind="attrs" v-on="{ ...tooltip, ...menu }" :aria-label="$t('tools.common.share.name')">
						<v-img src="/img/icon_ux_menu_share__active.svg" width="24px" height="24px"></v-img>
					</v-btn>
				</template>
				<span>{{ $t('tools.common.share.name') }}</span>
			</v-tooltip>
		</template>
		<v-card class="pa-4">
			<p class="text-h6">{{ $t('tools.common.share.link') }}</p>
			<v-text-field
				prepend-icon="mdi-link"
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
			<div v-if="tagName && tagName !== ''">
				<p class="text-h6">{{ $t('tools.common.share.embed') }}</p>
				<p class="text-body-2">{{ $t('tools.common.share.embed_guide_1') }}
					<a target="_blank" href="https://cinow.info/wp-content/uploads/2024/09/Dive-Guide-How-to-embed-Dive-maps-and-charts-in-your-website-ENG-and-SPA.pdf">{{ $t('tools.common.share.embed_guide_2') }}</a>
					{{ $t('tools.common.share.embed_guide_3') }}
				</p>
				<v-text-field
					prepend-icon="mdi-code-tags"
					readonly
					:label="copyEmbedScriptMessage"
					class="cursor-pointer"
					append-icon="mdi-content-copy"
					@focus="$event.target.select()"
					@click.stop="copyEmbedScriptCode"
					@click:append.stop="copyEmbedScriptCode"
					:value="embedScriptCode"
					:messages="embedScriptMessages"
					:hint="$t('tools.common.embed_script_hint')"
					persistent-hint
				>
				</v-text-field>
				<v-textarea
					prepend-icon="mdi-code-tags"
					readonly
					:label="copyEmbedComponentMessage"
					class="cursor-pointer mt-4"
					append-icon="mdi-content-copy"
					@focus="$event.target.select()"
					@click.stop="copyEmbedComponentCode"
					@click:append.stop="copyEmbedComponentCode"
					:value="embedComponentCode"
					:messages="embedComponentMessages"
					:hint="$t('tools.common.embed_component_hint')"
					persistent-hint
				>
				</v-textarea>
			</div>
		</v-card>
	</v-menu>
</template>

<script>
import i18n from '@/i18n'
import { mapState } from 'vuex'

export default {
	name: 'ShareMenu',
	props: {
		tagName: {
			type: String
		},
		tagAttributes: {
			type: Object,
			default: () => new Object()
		},
		layout: {
			type: String
		}
	},
	data() {
		return {
			shareUrl: window.location.href,
			shareMessages: [],
			embedScriptMessages: [],
			embedComponentMessages: [],
			open: false
		}
	},
	computed: {
		...mapState(['locale', 'community']),
		copyMessage() {
			return i18n.t('tools.common.click_copy');
		},
		copyEmbedScriptMessage() {
			return i18n.t('tools.common.click_copy_embed_script');
		
		},
		copyEmbedComponentMessage() {
			return i18n.t('tools.common.click_copy_embed_component');
		},
		embedScriptCode() {
			return '<script src="' + process.env.VUE_APP_DOMAIN + '/' + 'dive.min.js"></' + 'script>';
		},
		embedComponentCode() {
			let embedLines = [];
			embedLines.push('<dive-embed-' + this.tagName);
			Object.entries(this.tagAttributes)
				.filter(([key, value]) => key && !!value)
				.map(([key, value]) => value && (' ' + key + '="' + value + '"'))
				.forEach((line) => embedLines.push('\t' + line));
			embedLines.push('></dive-embed-' + this.tagName + '>');
			return embedLines.join('\n');
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
		},
		copyEmbedScriptCode() {
			try {
				window.navigator.clipboard
					.writeText(this.embedScriptCode)
					.then((self = this) => {
						self.embedScriptMessages = [i18n.t('tools.common.copy_success')]
						window.setTimeout(() => (self.embedScriptMessages = []), 2000)
					})
					.catch(() => {
						this.embedScriptMessages = [
							i18n.t('tools.common.copy_failure')
						];
					});
			} catch (e) {
				this.embedScriptMessages = [
					i18n.t('tools.common.copy_failure')
				];
			}
		},
		copyEmbedComponentCode() {
			try {
				window.navigator.clipboard
					.writeText(this.embedComponentCode)
					.then((self = this) => {
						self.embedComponentMessages = [i18n.t('tools.common.copy_success')]
						window.setTimeout(() => (self.embedComponentMessages = []), 2000)
					})
					.catch(() => {
						this.embedComponentMessages = [
							i18n.t('tools.common.copy_failure')
						];
					});
			} catch (e) {
				this.embedComponentMessages = [
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