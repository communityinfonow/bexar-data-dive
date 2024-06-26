<template>
  <v-toolbar dense class="menu-toolbar d-print-none px-8">
    <v-toolbar-items style="width: 100%;">
      <v-row style="max-width: 100%;">
        <v-col cols="10">
          <v-slide-group show-arrows style="height: 100%;">
            <template v-for="category in sortedMenu.categories">
              <v-slide-item :key="'category_' + category.id" v-if="category.items.length">
                <MenuButton text v-if="category.items.length === 1 && flattenSingleItems" :item="category.items[0]" :selectItem="selectItem" :singleItem="true" textClass="font-weight-bold blue--text"></MenuButton>
                <MenuCategory v-else :category="category" :selectItem="selectItem" textClass="font-weight-bold blue--text"></MenuCategory>
              </v-slide-item>
            </template>
          </v-slide-group>
        </v-col>
        <v-col cols="2" class="mt-1">
          <v-autocomplete
            :label="$t('tools.common.search') + ' ' + searchType"
            :placeholder="$t('tools.common.search') + ' ' + searchType"
            v-model="selectedItem"
            :items="searchItems"
            :item-text="(item) => { return item['name_' + locale] + (item.hasData ? '' : ' (' + $t('tools.community.coming_soon') + ')') }"
            :item-disabled="(item) => { return !item.hasData ? 'disabled' : '' }"
            @change="selectMatch"
            return-object
            hide-no-data
            hide-details
            solo
            flat
            rounded
            background-color="grey"
            dense
          >
            <template v-slot:prepend-inner><v-img src="/img/icon_ux_search.svg" height="1em" width="1em" class="mr-2"></v-img></template>
          </v-autocomplete>
        </v-col>
      </v-row>
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
import { mapState } from 'vuex'
import MenuCategory from '@/app/components/MenuCategory'
import MenuButton from '@/app/components/MenuButton'

export default {
  name: 'MenuToolbar',
  components: {
    MenuCategory,
    MenuButton
  },
  props: {
    menu: {
      type: Object,
    },
    selectItem: {
      type: Function,
    },
    flattenSingleItems: {
      type: Boolean
    },
    searchType: {
      type: String
    }
  },
  data() {
    return {
      items: [],
      selectedItem: null,
    }
  },
  computed: {
    ...mapState(['locale']),
    sortedMenu() {
      let sortedMenu = JSON.parse(JSON.stringify(this.menu));
      sortedMenu.categories.forEach(c => {
        if (c.subcategories) {
          c.items = c.items.concat(c.subcategories);
          c.items.sort((a, b) => {
            if (a['name_' + this.locale] < b['name_' + this.locale]) {
              return -1;
            } else if (a['name_' + this.locale] > b['name_' + this.locale]) {
              return 1;
            } else {
              return 0;
            }
          })
        }
      });
      return sortedMenu;
    },
    searchItems() {
      return this.menu.categories.flatMap(category => {
          return category.items;
        }).concat(this.menu.categories.flatMap(category => {
          return category.subcategories?.flatMap(subcategory => {
            return subcategory.items;
          });
        }));
    },
  },
  methods: {
    selectMatch() {
        this.selectItem(this.selectedItem)
        this.$nextTick(() => {
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

.menu-toolbar:hover ::v-deep .v-slide-group__prev i,
.menu-toolbar:hover ::v-deep .v-slide-group__next i {
  color: var(--v-primary-base);
}
</style>
