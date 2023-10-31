<template>
  <v-btn text tile @click="selectItem(item)" style="height: 100%;">
    <v-list-item-title v-if="!singleItem" :class="textClass">
      {{ itemName }}
    </v-list-item-title>
    <span v-else :class="textClass">
      {{ itemName }}
    </span>
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
    singleItem: {
      type: Boolean
    },
    textClass: {
      type: String
    }
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
