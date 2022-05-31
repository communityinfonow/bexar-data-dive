<template>
  <v-toolbar dense class="menu-toolbar">
    <v-toolbar-items class="d-flex flex-grow-1">
      <template v-for="category in menu.categories">
        <MenuCategory v-if="category.items.length > 1" :category="category" :selectItem="selectItem" :key="'category_' + category.id"></MenuCategory>
        <MenuButton text v-else :key="'category_' + category.id" :item="category.items[0]" :selectItem="selectItem"></MenuButton>
      </template>
      <v-spacer class="flex-grow-1"></v-spacer>
      <v-autocomplete
        class="flex-grow-0"
        prepend-icon="mdi-magnify"
        label="Search"
        placeholder="Search"
        v-model="select"
        :items="items"
        :search-input.sync="search"
        hide-no-data
        hide-details
        solo
        flat
      ></v-autocomplete>
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
import { mapState } from 'vuex'
import MenuCategory from '@/components/MenuCategory'
import MenuButton from '@/components/MenuButton'

export default {
  name: 'MenuToolbar',
  components: {
    MenuCategory, MenuButton
  },
  props: {
    menu: {
      type: Object,
    },
    selectItem: {
      type: Function,
    },
  },
  data() {
    return {
      items: [],
      search: null,
      select: null,
    }
  },
  computed: {
    ...mapState(['locale']),
    itemNames() {
      return this.menu.categories.flatMap(category => {
        return category.items.map(item => {
          return item['name_' + this.locale]
        });
      });
    },
  },
  watch: {
    search(val) {
      if (val && val !== this.select) {
        this.items = this.itemNames
      } else {
        this.items = []
      }
    },
  },
  methods: {},
}
</script>

<style lang="scss" scoped>
.menu-toolbar ::v-deep .v-toolbar__content {
  padding: 0;
}
</style>
