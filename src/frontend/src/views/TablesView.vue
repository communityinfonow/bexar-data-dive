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
        <h2 v-if="featuredIndicators" class="text-h5 mt-8 mb-2">{{ $t('tools.common.featured_indicators') }}</h2>
          <section v-if="featuredIndicators" class="d-flex flex-row">
            <template v-for="indicator in featuredIndicators">
              <featured-card 
                :key="indicator.id" 
                :item="indicator"
                :name="indicator['name_' + locale]" 
                :description="indicator['description_' + locale]" 
                :about_route="'about-data?indicator=' + indicator.id" 
                :view_route="indicator.route"
                :click_route="selectItem"
              >
              </featured-card>
            </template>
          </section>
      </v-col>
      <v-col v-if="tablesData" cols="auto" class="pt-4 px-4">
          <h1 class="text-h3 mb-1 d-flex justify-space-between">
            <span>{{ tablesData.indicator['name_' + locale] }}</span>
            <div>
              <vue-excel-xlsx
                :data="tablesData.items"
                :columns="xlsxColumns"
                :file-name="tablesData.indicator['name_' + locale]"
                :file-type="'xlsx'"
                :sheet-name="tablesData.indicator['name_' + locale]"
              >
                <v-btn icon color="primary" aria-label="Download"><v-icon>mdi-download</v-icon></v-btn>
              </vue-excel-xlsx>
            </div>
          </h1>
          <h2 class="text-subtitle-1 mb-2">{{ tablesData.source['name_' + locale] }}</h2>
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
//TODO: may need to fetch items per page if performance starts to degrade when legit data is loaded. will impact download too.
import { mapActions, mapState } from 'vuex'
import i18n from '@/i18n'
import router from '@/router/index'
import MenuToolbar from '@/components/MenuToolbar'
import FeaturedCard from '@/components/FeaturedCard'
export default {
  name: 'TablesView',
  components: {
    MenuToolbar,
    FeaturedCard
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
    ...mapState(['indicatorMenu', 'tablesData', 'locale', 'featuredIndicators']),
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
  watch: {
		locale(newValue, oldValue) {
      if (oldValue) {
			  this.getTablesData(router.currentRoute.query.indicator);
      }
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
    if (!this.featuredIndicators) {
      this.getFeaturedIndicators()
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
    ...mapActions(['setTablesData', 'getTablesData', 'getFeaturedIndicators']),
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
