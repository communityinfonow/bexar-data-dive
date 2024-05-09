<template>
	<v-card
		flat
        :width="fill_width ? '100%' : maxWidth"
        class="d-flex flex-column flex-grow-1 ma-4 featured-card"
		@click="click_route ? click_route(item) : null"
		:to="view_route ? view_route : null"
		:href="href ? href : null"
		:target="href ? '_blank' : null"
      >
		<v-img v-if="imagePath" :src="imagePath" class="align-end featured-image-gradient"></v-img>
		<v-card-title :class="'flex-nowrap featured-card-title ' + text_color + '--text ' + background_color + (dense ? ' pb-0' : '') + ' ' + title_class" v-if="name">
			<img v-if="iconPath" :src="iconPath" class="mr-4" height="48" width="48">
			<div>{{ name }}</div>
		</v-card-title>
		<v-card-text :class="'d-flex featured-card-text ' + text_color + '--text ' + background_color + (dense ? ' pt-0' : '')" :style="{ minHeight: description ? '132px' : '' }">
			<div>
				<p class="text-h6 font-weight-bold mb-0">{{ header }}</p>
				<p class="description" v-html="description"></p>
				<p>{{ postText }}</p>
			</div>
			<div v-if="inlineActions" class="pb-8 mr-4 align-self-end">
				<v-btn 
					v-if="view_route" 
					color="red"
					class="white--text font-weight-bold px-2 ml-2" 
					rounded
					:to="view_route"
					:aria-label="$t('featured_card.view') + ' ' + name"
				>
					{{ primary_button_text }}
				</v-btn>
				<v-btn 
					v-if="href" 
					color="red"
					class="white--text font-weight-bold px-2 ml-2" 
					rounded
					:href="href"
					:aria-label="$t('featured_card.view') + ' ' + name"
				>
					{{ primary_button_text }}
				</v-btn>
				<v-btn 
					v-if="click_route" 
					color="red" 
					class="white--text font-weight-bold px-2 ml-2"
					@click="click_route(item)" 
					:aria-label="$t('featured_card.view') + ' ' + name"
					rounded
				>
					{{ primary_button_text }}
				</v-btn>
			</div>
		</v-card-text>
		<v-spacer class="featured-card-actions"></v-spacer>
		<v-card-actions v-if=!inlineActions :class="'featured-card-actions pa-8 ' + background_color">
			<v-btn v-if="about_route" text color="red" link :to="about_route" rounded style="text-transform: none;">
				{{ secondary_button_text }}
			</v-btn>
			<v-spacer></v-spacer>
			<v-btn 
				v-if="view_route" 
				color="red"
				class="white--text font-weight-bold" 
				rounded
				:to="view_route"
				:aria-label="$t('featured_card.view') + ' ' + name"
			>
				{{ primary_button_text }}
			</v-btn>
			<v-btn 
				v-if="href" 
				color="red"
				class="white--text font-weight-bold" 
				rounded
				:href="href"
				:aria-label="$t('featured_card.view') + ' ' + name"
			>
				{{ primary_button_text }}
			</v-btn>
			<v-btn 
				v-if="click_route" 
				color="red" 
				class="white--text font-weight-bold"
				@click="click_route(item)" 
				:aria-label="$t('featured_card.view') + ' ' + name"
				rounded
			>
				{{ primary_button_text }}
			</v-btn>
		</v-card-actions>
      </v-card>
</template>

<script>
	export default {
		name: 'FeaturedCard',
		props: {
			item: {
				type: Object
			},
			iconPath: {
				type: String
			},
			imagePath: {
				type: String
			},
			name: {
				type: String 
			},
			header: {
				type: String
			},
			description: {
				type: String 
			},
			postText: {
				type: String
			},
			about_route: {
				type: String 
			},
			view_route: {
				type: String 
			},
			click_route: {
				type: Function
			},
			fill_width: {
				type: Boolean,
				default: false
			},
			text_color: {
				type: String,
				default: 'blue'
			},
			background_color: {
				type: String
			},
			primary_button_text: {
				type: String,
			},
			secondary_button_text: {
				type: String
			},
			dense: {
				type: Boolean,
				default: false
			},
			href: {
				type: String
			},
			title_class: {
				type: String
			},
			inlineActions: {
				type: Boolean,
				default: false
			}
		},
		computed: {
			maxWidth() {
				if (this.$vuetify.breakpoint.mdAndDown) {
					return '';
				}

				return '30%';
			}
		},
	}
</script>

<style lang="scss">
	.featured-image-gradient {
		background: rgba($color-grey, 1);
	}
	.featured-card-title {
		font-size: 1.5rem;
		background: rgba($color-grey, 1);
		font-weight: bold;
		border-top: 4px solid white;
	}
	.featured-card-title.did-you-know,
	.featured-card-title.announcements {
		font-size: 2.5rem;
	}
	.featured-card-text {
		background: rgba($color-grey, 1);
	}
	.featured-card-text .description {
		font-size: 1.25rem;
		line-height: 1.75rem;
		overflow: hidden;
		display: -webkit-box;
		-webkit-line-clamp: 3;
		-webkit-box-orient: vertical;
	}
	.featured-card-actions {
		background: rgba($color-grey, 1);
	}
	.featured-card-title.light
	.featured-card-text.light
	.featured-card-actions.light {
		background: rgba($color-light, 1);
	}
</style>