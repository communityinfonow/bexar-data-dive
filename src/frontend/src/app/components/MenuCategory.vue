<template>
	<v-menu offset-y :internal-activator="true" :key="'category_' + category.id" max-height="60%" allow-overflow eager v-model="menuOpen">
		<template v-slot:activator="{ on }">
			<v-btn text tile v-on="on" style="height: 100%;" :class="textClass">
				{{ categoryName }}
				<v-icon right>mdi-chevron-down</v-icon>
			</v-btn>
		</template>
		<v-list>
      <template v-for="item in category.items">
        <v-menu v-if="item.items" offset-x open-on-hover :key="'subcategory_' + item.id">
          <template v-slot:activator="{ on }">
            <v-list-item v-on="on">
              <v-list-item-title>{{ item['name_' + locale]}}</v-list-item-title>
              <v-list-item-icon>
                <v-icon right>mdi-chevron-right</v-icon>
              </v-list-item-icon>
            </v-list-item>
          </template>
          <v-list>
            <template v-for="subItem in item.items">
            <MenuItem
              :item="subItem"
              :key="'item_' + subItem.id"
              :selectItem="selectSubcategoryItem"
              :singleItem="false"
            ></MenuItem>
            </template>
          </v-list>
        </v-menu>
        <MenuItem
          v-else
          :item="item"
          :key="'item_' + item.id"
          :selectItem="selectItem"
          :singleItem="false"
        ></MenuItem>
      </template>
    </v-list>
	</v-menu>
</template>

<script>
import MenuItem from '@/app/components/MenuItem'
import { mapState } from 'vuex'

export default {
  name: 'MenuCategory',
  components: {
    MenuItem,
  },
  props: {
    category: {
      type: Object,
    },
    selectItem: { 
      type: Function,
    },
    textClass: {
      type: String
    }
  },
  data() {
    return {
      items: [],
      search: null,
      select: null,
      menuOpen: false
    }
  },
  computed: {
	...mapState(['locale']),
	categoryName() {
		return this.category['name_' + this.locale];
	}
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
  methods: {
    selectSubcategoryItem(item) {
      this.selectItem(item);
      this.menuOpen = false;
    }
  },
}
</script>

<style lang="scss" scoped>
.menu-toolbar ::v-deep .v-toolbar__content {
  padding: 0;
}
@supports (-moz-appearance:none) {
  .v-menu__content {
    overflow-y: scroll;
  }
}
</style>
