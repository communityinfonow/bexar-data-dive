<template>
  <v-menu offset-x open-on-hover>
    <template v-slot:activator="{ on }">
      <v-list-item v-on="on">
        <v-list-item-title>
          {{ item.name }}
        </v-list-item-title>
        <v-list-item-icon v-if="item.items">
          <v-icon>mdi-chevron-right</v-icon>
        </v-list-item-icon>
      </v-list-item>
    </template>
    <v-list v-if="item.items">
      <template v-for="item in item.items">
        <MenuGroup
          v-if="item.items"
          :item="item"
          :key="'cat_' + item.id"
        ></MenuGroup>
        <MenuItem v-else :item="item" :key="'item_' + item.id"></MenuItem>
      </template>
    </v-list>
  </v-menu>
</template>

<script>
import MenuItem from '@/components/MenuItem'

export default {
  name: 'MenuGroup',
  components: {
    MenuItem,
  },
  props: {
    item: {
      type: Object,
    },
  },
}
</script>

<style lang="scss" scoped></style>
