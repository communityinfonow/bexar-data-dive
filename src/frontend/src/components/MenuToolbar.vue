<template>
  <v-toolbar dense class="menu-toolbar">
    <v-toolbar-items class="d-flex flex-grow-1">
      <template v-for="category in menu.categories">
        <MenuCategory :category="category" :selectItem="selectItem" :key="'category_' + category.id"></MenuCategory>
      </template>
      <v-spacer class="flex-grow-1"></v-spacer>
      <v-autocomplete
        class="flex-grow-0"
        prepend-icon="mdi-magnify"
        label="Search"
        placeholder="Search"
        v-model="selectedItem"
        :items="searchItems"
        :item-text="'name_' + locale"
        :search-input.sync="search"
        @change="selectMatch"
        return-object
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

export default {
  name: 'MenuToolbar',
  components: {
    MenuCategory
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
      search: '',
      selectedItem: null,
    }
  },
  computed: {
    ...mapState(['locale']),
    searchItems() {
      return this.menu.categories.flatMap(category => {
        return category.items;
      });
    },
  },
  methods: {
    selectMatch() {
        this.selectItem(this.selectedItem)
        this.$nextTick(() => {
          this.search = ''
          this.selectedItem = null
        });
    }
  },
}
</script>

<style lang="scss" scoped>
.menu-toolbar ::v-deep .v-toolbar__content {
  padding: 0;
}
</style>
