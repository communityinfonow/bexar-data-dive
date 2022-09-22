<template>
	<v-card
        max-width="30%"
        class="d-flex flex-column flex-grow-1 ma-4"
      >
        <v-img :src="imagePath" class="align-end featured-image-gradient">
			<v-card-title class="featured-card-title white--text">{{ name }}</v-card-title>
        </v-img>
			<v-card-text class="featured-card-text white--text">
				<p class="description" v-html="description"></p>
		  	</v-card-text>
			<v-spacer class="featured-card-actions"></v-spacer>
			<v-card-actions class="featured-card-actions">
				<v-btn text dark link :to="about_route">
					{{ $t('featured_card.learn_more') }}
				</v-btn>
				<v-spacer></v-spacer>
				<v-btn 
					v-if="view_route" 
					color="primary"
					class="white--text font-weight-bold" 
					:to="view_route" 
					:aria-label="$t('featured_card.view') + ' ' + name"
				>
					{{ $t('featured_card.view') }}
				</v-btn>
				<v-btn 
					v-if="click_route" 
					color="accent" 
					class="white--text font-weight-bold"
					@click="click_route(item)" 
					:aria-label="$t('featured_card.view') + ' ' + name"
				>
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
		font-size: 1.5rem;
		background: rgba($color-secondary, 1);
		font-weight: bold;
	}
	.featured-card-text {
		min-height: 100px;
		background: rgba($color-secondary, 1);
	}
	.featured-card-text .description {
		overflow: hidden;
		display: -webkit-box;
		-webkit-line-clamp: 3;
		-webkit-box-orient: vertical;
	}
	.featured-card-actions {
		background: rgba($color-secondary, 1);
	}
</style>