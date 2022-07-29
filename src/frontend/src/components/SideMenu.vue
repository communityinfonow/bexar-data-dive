<template>
  <v-card style="position: fixed; top: 0; bottom: 0; right: 0; margin: 64px 0 34px 0; overflow-y: scroll;">
	<v-card-title>{{ title }}</v-card-title>
	<v-card-actions>
		<v-autocomplete
			prepend-icon="mdi-magnify"
			:label="$t('tools.common.search')"
			:placeholder="$t('tools.common.search')"
			v-model="selectedItem"
			:items="searchItems"
			:item-text="'name_' + locale"
			:search-input.sync="search"
			@change="selectFromSearch"
			return-object
			hide-no-data
			hide-details
			solo
			flat
		></v-autocomplete>
	</v-card-actions>
	<v-list>
		<template v-for="category in menu.categories">
			<v-list-group :value="true" no-action :key="category.id">
				<template v-slot:activator>
					<v-list-item-title>{{ category['name_' + locale] }}</v-list-item-title>
				</template>
				<template v-for="item in category.items">
					<v-list-group :value="true" v-if="item.items" :key="item.id" no-action sub-group>
						<template v-slot:activator>
							<v-list-item-content>
								<v-list-item-title>{{ item['name_' + locale] }}</v-list-item-title>
							</v-list-item-content>
						</template>
						<v-list-item v-for="subItem in item.items" :key="subItem.id" @click="selectFromList(subItem)">
							<v-list-item-title>
								{{ subItem['name_' + locale]}}
							</v-list-item-title>
						</v-list-item>
					</v-list-group>
					<template v-else>
						<v-list-item :key="item.id" @click="selectFromList(item)">
							<v-list-item-title>
								{{ item['name_' + locale]}}
							</v-list-item-title>
						</v-list-item>
					</template>
				</template>
			</v-list-group>
		</template>
	</v-list>
  </v-card>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'SideMenu',
  props: {
    menu: {
      type: Object,
    },
    selectItem: {
      type: Function,
    },
	title: {
		type: String
	}
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
			return category.subcategories.flatMap(subcategory => {
				return subcategory.items;
			});
	  	}));
    },
  },
  methods: {
	selectFromList(item) {
		this.selectItem(item)
	},
    selectFromSearch() {
        this.selectItem(this.selectedItem)
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
