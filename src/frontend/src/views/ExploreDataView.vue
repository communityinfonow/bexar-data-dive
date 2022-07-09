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
            :headers="headers"
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
import router from '@/router/index'
import MenuToolbar from '@/components/MenuToolbar'
export default {
  name: 'ExploreDataView',
  components: {
    MenuToolbar,
  },
  data() {
    return {
      search: "",
      headers: [
        {
          text: "Location",
          value: "location"
        }, 
        {
          text: "Year",
          value: "year"
        },
        {
          text: "Race/Ethnicity",
          value: "race" //TODO: hide filter columns if not applicable to indicator
        },
        {
          text: "Age",
          value: "age"
        },
        {
          text: "Sex",
          value: "sex"
        },
        {
          text: "Education Level",
          value: "education"
        },
        {
          text: "Income Level",
          value: "income"
        },
        {
          text: "Value",
          value: "value" //TODO: suppression/no data
        },
        {
          text: "MOE (low)",
          value: "moeLow"
        },
        {
          text: "MOE (high)",
          value: "moeHigh"
        },
        {
          text: "Universe",
          value: "universeValue"
        }
      ]
    }
  },
  computed: {
    ...mapState(['indicatorMenu', 'exploreData', 'locale']),
    showIntro() {
      return !this.exploreData && !router.currentRoute.query.indicator;
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
