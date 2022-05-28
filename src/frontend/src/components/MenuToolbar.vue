<template>
  <v-toolbar dense class="menu-toolbar">
    <v-toolbar-items class="d-flex flex-grow-1">
      <template v-for="item in menu.items">
        <v-btn v-if="!item.items" text :key="item.id" @click="selectItem(item)">
          {{ item.name }}
        </v-btn>
        <v-menu v-else offset-y :internal-activator="true" :key="item.id">
          <template v-slot:activator="{ on }">
            <v-btn text v-on="on">
              {{ item.name }}
              <v-icon right>mdi-chevron-down</v-icon>
            </v-btn>
          </template>
          <v-list>
            <template v-for="item in item.items">
              <MenuGroup
                v-if="item.items"
                :item="item"
                :key="'cat_' + item.id"
              ></MenuGroup>
              <MenuItem
                v-else
                :item="item"
                :key="'item_' + item.id"
                :selectItem="selectItem"
              ></MenuItem>
            </template>
          </v-list>
        </v-menu>
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
import MenuGroup from '@/components/MenuGroup'
import MenuItem from '@/components/MenuItem'
export default {
  name: 'MenuToolbar',
  components: {
    MenuGroup,
    MenuItem,
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
    itemNames() {
      return this.menu.items.flatMap(function mapItem(item) {
        if (item.items) {
          return item.items.flatMap(mapItem)
        } else {
          return item.name
        }
      })
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
