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
        <h1 class="text-h3 mb-2">{{ $t('tools.tables.name') }}</h1>
        <p>{{ $t('tools.tables.long_description') }}</p>
        <p>{{ $t('tools.tables.get_started') }}</p>
      </v-col>
      <v-col v-if="tablesData" cols="auto" class="pt-4 px-4">
          <h1 class="text-h3 mb-1">{{ tablesData.indicator['name_' + locale] }}</h1>
          <h2 class="text-subtitle-1 mb-2">{{ tablesData.source['name_' + locale] }}</h2>
          <vue-excel-xlsx
            :data="tablesData.items"
            :columns="xlsxColumns"
            :file-name="tablesData.indicator['name_' + locale]"
            :file-type="'xlsx'"
            :sheet-name="tablesData.indicator['name_' + locale]"
          >
            Download
          </vue-excel-xlsx>
          <v-text-field v-model="search" append-icon="mdi-magnify" label="Search" hide-details></v-text-field>
          <v-data-table
            :headers="filteredHeaders"
            :items="tablesData.items"
            :search="search"
            :options="tableOptions"
            :footer-props="footerOptions"
            multi-sort
          >
          </v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
//TODO: may need to fetch items per page if performance starts to degrade when legit data is loaded
import { mapActions, mapState } from 'vuex'
import i18n from '@/i18n'
import router from '@/router/index'
import MenuToolbar from '@/components/MenuToolbar'
export default {
  name: 'TablesView',
  components: {
    MenuToolbar,
  },
  data() {
    return {
      tableOptions: {
        itemsPerPage: 50
      },
      footerOptions: {
        itemsPerPageOptions: [10, 50, 100, -1]
      },
      search: ""
    }
  },
  computed: {
    ...mapState(['indicatorMenu', 'tablesData', 'locale']),
    showIntro() {
      return !this.tablesData && !router.currentRoute.query.indicator;
    },
    xlsxColumns() {
      return this.headers.map(h => {
        return {
          label: h.text,
          field: h.value
        };
      });
    },
    /*xlsxData() {
      return this.tablesData.items.map(i => {
        return {

        };
      });
    },*/
    headers() {
      return [
        {
          text: i18n.t('tools.tables.headers.location'),
          value: "location"
        }, 
        {
          text: i18n.t('tools.tables.headers.year'),
          value: "year"
        },
        {
          text: i18n.t('tools.tables.headers.race'),
          value: "race"
        },
        {
          text: i18n.t('tools.tables.headers.age'),
          value: "age"
        },
        {
          text: i18n.t('tools.tables.headers.sex'),
          value: "sex"
        },
        {
          text: i18n.t('tools.tables.headers.education'),
          value: "education"
        },
        {
          text: i18n.t('tools.tables.headers.income'),
          value: "income"
        },
        {
          text: i18n.t('tools.tables.headers.value'),
          value: "valueLabel"
        },
        {
          text: i18n.t('tools.tables.headers.moe_low'),
          value: "moeLow"
        },
        {
          text: i18n.t('tools.tables.headers.moe_high'),
          value: "moeHigh"
        },
        {
          text: i18n.t('tools.tables.headers.universe'),
          value: "universeValue"
        }
      ];
    },
    filteredHeaders() {
      let filtered = JSON.parse(JSON.stringify(this.headers));
      if (!this.tablesData.items.find(i => i.race !== null)) {
        filtered = filtered.filter(h => h.value !== 'race');
      }
      if (!this.tablesData.items.find(i => i.age !== null)) {
        filtered = filtered.filter(h => h.value !== 'age');
      }
      if (!this.tablesData.items.find(i => i.sex !== null)) {
        filtered = filtered.filter(h => h.value !== 'sex');
      }
      if (!this.tablesData.items.find(i => i.education !== null)) {
        filtered = filtered.filter(h => h.value !== 'education');
      }
      if (!this.tablesData.items.find(i => i.income !== null)) {
        filtered = filtered.filter(h => h.value !== 'income');
      }
      return filtered;
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$store.dispatch('setTablesData', null)
    });
  },
  beforeRouteUpdate(to, from, next) {
    if (from.query.indicator && !to.query.indicator) {
      this.setTablesData(null)
    }
    next();
  },
  mounted () {
    if (router.currentRoute.query.indicator && this.indicatorMenu) {
      this.setTablesData(this.indicatorMenu.categories
        .flatMap(category => category.subcategories.flatMap(sc => sc.items).concat(category.items))
        .find(item => item.id == router.currentRoute.query.indicator))
    } else {
      this.setTablesData(null)
    }
  },
  updated () {
    if (router.currentRoute.query.indicator && this.indicatorMenu) {
      let matchedIndicator = this.indicatorMenu.categories
        .flatMap(category => category.subcategories.flatMap(sc => sc.items).concat(category.items))
        .find(item => item.id == router.currentRoute.query.indicator)
      if (matchedIndicator?.id !== this.tablesData?.indicator.id) {
        this.getTablesData(matchedIndicator.id)
      }
    } else {
      this.setTablesData(null)
    }
  },
  methods: {
    ...mapActions(['setTablesData', 'getTablesData']),
    selectItem(item) {
      this.getTablesData(item.id);
      router.replace({
          query: {
            ...router.currentRoute.query,
            indicator: item.id
          },
        });
    },
  },
}
</script>

<style lang="scss" scoped></style>
