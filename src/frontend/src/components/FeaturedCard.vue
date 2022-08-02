<template>
	<v-card
        max-width="30%"
        class="d-flex flex-column flex-grow-1 ma-4"
      >
        <v-img :src="imagePath" class="align-end featured-image-gradient">
			<v-card-title class="featured-card-title">{{ name }}</v-card-title>
        </v-img>
		  <v-card-text class="featured-card-text">{{ description }}</v-card-text>
			<v-spacer class="featured-card-actions"></v-spacer>
			<v-card-actions class="featured-card-actions">
				<v-btn plain text link color="primary" :to="about_route">
					{{ $t('featured_card.learn_more') }}
				</v-btn>
				<v-spacer></v-spacer>
				<v-btn v-if="view_route" color="primary" :to="view_route" :aria-label="$t('featured_card.view') + ' ' + name">
					{{ $t('featured_card.view') }}
				</v-btn>
				<v-btn v-if="click_route" color="primary" @click="click_route(item)" :aria-label="$t('featured_card.view') + ' ' + name">
					{{ $t('featured_card.view') }}
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
			imagePath: {
				type: String
			},
			name: {
				type: String 
			},
			description: {
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
			}
		},
	}
</script>

<style lang="scss" scoped>
	.featured-card-title {
		padding-top: 2em;
		color: var(--v-primary-base);
		background: linear-gradient(transparent, rgba($color-accent, 0.8))
	}
	.featured-card-text {
		min-height: 100px;
		background: linear-gradient(rgba($color-accent, 0.8), rgba($color-accent, 1.0))
	}
	.featured-card-actions {
		background: $color-accent;
	}
</style>