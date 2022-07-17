<template>
	<v-row class="no-gutters">
		<v-col v-if="img" cols="2">
			<v-img src="@/assets/todo.jpg" class="mr-4"></v-img>
		</v-col>
		<v-col :cols="img ? 10 : 12">
			{{ text }}
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
	img: {
		type: String
	}
  },
}
</script>

<style lang="scss" scoped></style>
