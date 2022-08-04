<template>
	<v-row class="no-gutters">
		<v-col v-if="imagePath" cols="2">
			<v-img :src="imagePath" class="mr-4"></v-img>
		</v-col>
		<v-col :cols="imagePath ? 10 : 12">
			<p>{{ text }}</p>
			<v-btn 
				v-if="viewRoute" 
				color="primary" 
				:to="viewRoute" 
				:aria-label="$t('featured_card.view') + ' ' + name"
				class="mt-6"
			>
				{{ $t('featured_card.view') }}
			</v-btn>
		</v-col>
	</v-row>
</template>

<script>

export default {
  name: 'ToolItem',
  props: {
    text: {
      type: String,
    },
	imagePath: {
		type: String
	},
	viewRoute: {
		type: String
	}
  },
}
</script>

<style lang="scss" scoped></style>
