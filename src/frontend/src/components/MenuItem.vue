<template>
  <v-list-item @click="selectItem(item)" :disabled="!item.hasData">
    <v-list-item-title>
      {{ itemName }}
      <span v-if="!item.hasData">
        ({{ $t('tools.community.coming_soon') }})
      </span>
    </v-list-item-title>
  </v-list-item>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'MenuItem',
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
