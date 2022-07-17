<template>
  <v-container v-if="indicatorMenu" fluid class="pa-0">
    <v-row class="no-gutters flex-column">
      <v-col cols="auto" class="pa-4 col col-auto">
        <h1 class="text-h3 mb-4">{{ $t('about_data_view.name') }}</h1>
        <section v-for="category in sortedMenu.categories" :key="category.id">
          <h2 class="mb-3">{{ category['name_' + locale]}}</h2>
          <section v-for="item in category.items" :key="item.id">
            <h3 v-if="item.items" class="mb-2">{{ item['name_' + locale] }}</h3>
            <h4 v-else class="mb-1">{{ item['name_' + locale] }}</h4>
            <p v-if="!item.items">{{ item['description_' + locale]}}</p> 
          </section>
        </section>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'AboutDataView',
  data() {
    return {}
  },
  computed: {
    ...mapState(['indicatorMenu', 'locale']),
    sortedMenu() {
        let sortedMenu = JSON.parse(JSON.stringify(this.indicatorMenu));
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
    }
  }
}
</script>

<style lang="scss" scoped></style>
