<template>
  <v-list-item @click="selectItem(item)">
    <v-list-item-title>
      {{ item.name }}
    </v-list-item-title>
  </v-list-item>
</template>

<script>
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
  methods: {},
}
</script>

<style lang="scss" scoped></style>
