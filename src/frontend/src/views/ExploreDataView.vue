<template>
  <v-container v-if="indicatorMenu" fluid class="pa-0 fill-height">
    <v-row class="no-gutters flex-column fill-height">
      <v-col cols="auto" class="shrink">
        <MenuToolbar
          class="flex-column"
          :menu="indicatorMenu"
          :selectItem="selectItem"
          :flattenSingleItems="false"
        ></MenuToolbar>
      </v-col>
      <v-col v-if="showIntro" cols="auto" class="pa-4 grow">
        <h1 class="text-h3 mb-2">{{ $t('tools.explore_data.name') }}</h1>
        <p>{{ $t('tools.explore_data.long_description') }}</p>
        <p>{{ $t('tools.explore_data.get_started') }}</p>
      </v-col>
      <v-col v-if="exploreData" cols="auto" class="pt-4 px-4">
          <h1 class="text-h3 mb-1">{{ exploreData.indicator['name_' + locale] }}</h1>
          <h2 class="text-subtitle-1 mb-2">{{ exploreData.source['name_' + locale] }}</h2>
          <v-text-field v-model="search" append-icon="mdi-magnify" label="Search" hide-details></v-text-field>
          <v-data-table
            :headers="filteredHeaders"
            :items="exploreData.items"
            :search="search"
            multi-sort
          >
          </v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
//TODO: need to persist indicator in route
import { mapActions, mapState } from 'vuex'
import i18n from '@/i18n'
import router from '@/router/index'
import MenuToolbar from '@/components/MenuToolbar'
export default {
  name: 'ExploreDataView',
  components: {
    MenuToolbar,
  },
  data() {
    return {
      search: ""
    }
  },
  computed: {
    ...mapState(['indicatorMenu', 'exploreData', 'locale']),
    showIntro() {
      return !this.exploreData && !router.currentRoute.query.indicator;
    },
    headers() {
      return [
        {
          text: i18n.t('tools.explore_data.headers.location'),
          value: "location"
        }, 
        {
          text: i18n.t('tools.explore_data.headers.year'),
          value: "year"
        },
        {
          text: i18n.t('tools.explore_data.headers.race'),
          value: "race"
        },
        {
          text: i18n.t('tools.explore_data.headers.age'),
          value: "age"
        },
        {
          text: i18n.t('tools.explore_data.headers.sex'),
          value: "sex"
        },
        {
          text: i18n.t('tools.explore_data.headers.education'),
          value: "education"
        },
        {
          text: i18n.t('tools.explore_data.headers.income'),
          value: "income"
        },
        {
          text: i18n.t('tools.explore_data.headers.value'),
          value: "value" //TODO: suppression/no data
        },
        {
          text: i18n.t('tools.explore_data.headers.moe_low'),
          value: "moeLow"
        },
        {
          text: i18n.t('tools.explore_data.headers.moe_high'),
          value: "moeHigh"
        },
        {
          text: i18n.t('tools.explore_data.headers.universe'),
          value: "universeValue"
        }
      ];
    },
    filteredHeaders() {
      let filtered = JSON.parse(JSON.stringify(this.headers));
      if (!this.exploreData.items.find(i => i.race !== null)) {
        filtered = filtered.filter(h => h.value !== 'race');
      }
      if (!this.exploreData.items.find(i => i.age !== null)) {
        filtered = filtered.filter(h => h.value !== 'age');
      }
      if (!this.exploreData.items.find(i => i.sex !== null)) {
        filtered = filtered.filter(h => h.value !== 'sex');
      }
      if (!this.exploreData.items.find(i => i.education !== null)) {
        filtered = filtered.filter(h => h.value !== 'education');
      }
      if (!this.exploreData.items.find(i => i.income !== null)) {
        filtered = filtered.filter(h => h.value !== 'income');
      }
      return filtered;
    }
  },
  methods: {
    ...mapActions(['getExploreData']),
    selectItem(item) {
      this.getExploreData(item.id)
    },
  },
}
</script>

<style lang="scss" scoped></style>
