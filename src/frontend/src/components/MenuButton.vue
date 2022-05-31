<template>
  <v-btn text @click="selectItem(item)">
    <v-list-item-title>
      {{ itemName }}
    </v-list-item-title>
  </v-btn>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'MenuButton',
  props: {
    item: {
      type: Object,
    },
    selectItem: {
      type: Function,
    },
  },
  computed: {
	...mapState(['locale']),
	itemName() {
		return this.item['name_' + this.locale];
	}
  },
  methods: {},
}
</script>

<style lang="scss" scoped></style>
