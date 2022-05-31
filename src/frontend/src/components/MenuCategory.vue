<template>
	<v-menu offset-y :internal-activator="true" :key="'category_' + category.id">
		<template v-slot:activator="{ on }">
			<v-btn text v-on="on">
				{{ categoryName }}
				<v-icon right>mdi-chevron-down</v-icon>
			</v-btn>
		</template>
		<v-list >
		<template v-for="item in category.items">
			<MenuItem
			:item="item"
			:key="'item_' + item.id"
			:selectItem="selectItem"
			></MenuItem>
		</template>
		</v-list>
	</v-menu>
</template>

<script>
import MenuItem from '@/components/MenuItem'
import { mapState } from 'vuex'

export default {
  name: 'MenuToolbar',
  components: {
    MenuItem,
  },
  props: {
    category: {
      type: Object,
    },
	selectItem: { 
		type: Function,
	}
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
  methods: {},
}
</script>

<style lang="scss" scoped>
.menu-toolbar ::v-deep .v-toolbar__content {
  padding: 0;
}
</style>
