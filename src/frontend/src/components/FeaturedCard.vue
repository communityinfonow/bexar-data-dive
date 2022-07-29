<template>
	<v-card
        max-width="30%"
        class="d-flex flex-column flex-grow-1 ma-4"
      >
        <v-img src="@/assets/todo.jpg" class="white--text align-end">
          <v-card-title>{{ name }}</v-card-title>
        </v-img>
        <v-card-text>{{ description }}</v-card-text>
        <v-spacer></v-spacer>
        <v-card-actions>
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

</style>