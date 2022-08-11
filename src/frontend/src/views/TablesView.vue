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
      <v-col v-if="indicator && tablesData" cols="auto" class="pt-4 px-4">
          <h1 class="text-h3 mb-1 d-flex justify-space-between">
            <span>
              <span v-if="tablesData.category.parentCategoryId">{{ tablesData.category['name_' + locale] }} - </span>
              {{ tablesData.indicator['name_' + locale] }}
            </span>
            <v-btn color="accent" icon @click="downloadTablesData">
              <v-icon>mdi-download</v-icon>
            </v-btn>
          </h1>
          <h2 class="text-subtitle-1 mb-2">{{ tablesData.source['name_' + locale] }}</h2>
          <v-text-field v-model="search" :label="$t('tools.common.search')" hide-details @input="loadTablesData()">
            <template v-slot:append><v-icon color="accent">mdi-magnify</v-icon></template>
          </v-text-field>
          <v-data-table
            :headers="filteredHeaders"
            :items="items"
            :search="search"
            :page.sync="page"
            :items-per-page.sync="itemsPerPage"
            :sort-by.sync="sortBy"
            :sort-desc.sync="sortDesc"
            :footer-props="footerOptions"
            :server-items-length="tablesData.totalRows"
          >
            <template v-slot:header.locationType="{ header }">
              {{ header.text }}
              <v-menu offset-y max-height="400px" allow-overflow eager>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="accent" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item>
                    <v-btn block color="accent" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-list-item>
                  <v-list-item v-for="locationType in locationTypes" :key="locationType.name" @click.stop="">
                    <v-list-item-action>
                      <v-checkbox
                        v-model="locationType.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ locationType['name_' + locale] }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </template>
            <template v-slot:header.location="{ header }">
              {{ header.text }}
              <v-menu offset-y max-height="400px" allow-overflow eager>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="accent" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item>
                    <v-btn block color="accent" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-list-item>
                  <v-list-item v-for="location in locations" :key="location.name" @click.stop="">
                    <v-list-item-action>
                      <v-checkbox
                        v-model="location.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ location['name_' + locale] }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </template>
            <template v-slot:header.year="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="accent" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item>
                    <v-btn block color="accent" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-list-item>
                  <v-list-item v-for="year in years" :key="year.name" @click.stop="">
                    <v-list-item-action>
                      <v-checkbox
                        v-model="year.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ year['name_' + locale] }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </template>
            <template v-if="filteredHeaders.find(f => f.value === 'race')" v-slot:header.race="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="accent" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item>
                    <v-btn block color="accent" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-list-item>
                  <v-list-item v-for="race in races" :key="race.name" @click.stop="">
                    <v-list-item-action>
                      <v-checkbox
                        v-model="race.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ race['name_' + locale] }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </template>
            <template v-if="filteredHeaders.find(f => f.value === 'age')" v-slot:header.age="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="accent" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item>
                    <v-btn block color="accent" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-list-item>
                  <v-list-item v-for="age in ages" :key="age.name" @click.stop="">
                    <v-list-item-action>
                      <v-checkbox
                        v-model="age.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ age['name_' + locale] }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </template>
            <template v-if="filteredHeaders.find(f => f.value === 'sex')" v-slot:header.sex="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="accent" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item>
                    <v-btn block color="accent" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-list-item>
                  <v-list-item v-for="sex in sexes" :key="sex.name" @click.stop="">
                    <v-list-item-action>
                      <v-checkbox
                        v-model="sex.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ sex['name_' + locale] }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </template>
            <template v-if="filteredHeaders.find(f => f.value === 'education')" v-slot:header.education="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="accent" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item>
                    <v-btn block color="accent" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-list-item>
                  <v-list-item v-for="education in educations" :key="education.name" @click.stop="">
                    <v-list-item-action>
                      <v-checkbox
                        v-model="education.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ education['name_' + locale] }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </template>
            <template v-if="filteredHeaders.find(f => f.value === 'income')" v-slot:header.income="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="accent" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item>
                    <v-btn block color="accent" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-list-item>
                  <v-list-item v-for="income in incomes" :key="income.name" @click.stop="">
                    <v-list-item-action>
                      <v-checkbox
                        v-model="income.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ income['name_' + locale] }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </template>
          </v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import i18n from '@/i18n'
import router from '@/router/index'
import MenuToolbar from '@/components/MenuToolbar'
import FeaturedCard from '@/components/FeaturedCard'
import debounce from 'debounce'

export default {
  name: 'TablesView',
  components: {
    MenuToolbar,
    FeaturedCard
  },
  data() {
    return {
      page: 1,
      itemsPerPage: 50,
      sortBy: '',
      sortDesc: false,
      footerOptions: {
        itemsPerPageOptions: [10, 50, 100]
      },
      search: "",
      locationTypes: [],
      locations: [],
      years: [],
      races: [],
      ages: [],
      sexes: [],
      educations: [],
      incomes: []
    }
  },
  computed: {
    ...mapState(['indicatorMenu', 'indicator', 'filters', 'tablesData', 'locale', 'featuredIndicators']),
    showIntro() {
      return !this.indicator && !router.currentRoute.query.indicator;
    },
    selections() {
      return {
        indicator: this.indicator?.id,
        search: this.search,
        page: this.page,
        perPage: this.itemsPerPage,
        sortBy: this.sortBy,
        sortDesc: this.sortDesc,
        lang: this.locale,
        locationTypes: this.locationTypes.length === this.filteredLocationTypes.length 
          ? undefined 
          : this.filteredLocationTypes.map(i => i.id),
        locations: this.locations.length === this.filteredLocations.length
          ? undefined
          : this.filteredLocations.map(i => i.id),
        years: this.years.length === this.filteredYears.length
          ? undefined
          : this.filteredYears.map(i => i.id),  
        races: this.races?.length === this.filteredRaces?.length
          ? undefined
          : this.filteredRaces?.map(i => i.id),
        ages: this.ages?.length === this.filteredAges?.length
          ? undefined
          : this.filteredAges?.map(i => i.id),
        sexes: this.sexes?.length === this.filteredSexes?.length
          ? undefined
          : this.filteredSexes?.map(i => i.id),
        educations: this.educations?.length === this.filteredEducations?.length
          ? undefined
          : this.filteredEducations?.map(i => i.id),
        incomes: this.incomes?.length === this.filteredIncomes?.length
          ? undefined
          : this.filteredIncomes?.map(i => i.id)
      };
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
          text: i18n.t('tools.tables.headers.location_type'),
          value: "locationType"
        },
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
          value: "valueLabel",
          filterable: false
        },
        {
          text: i18n.t('tools.tables.headers.moe_low'),
          value: "moeLowLabel",
          filterable: false
        },
        {
          text: i18n.t('tools.tables.headers.moe_high'),
          value: "moeHighLabel",
          filterable: false
        },
        {
          text: i18n.t('tools.tables.headers.universe'),
          value: "universeValueLabel",
          filterable: false
        }
      ];
    },
    filteredHeaders() {
      let filtered = JSON.parse(JSON.stringify(this.headers));
      if (!this.filters?.indicatorFilters?.find(f => f.type.id === '1')) {
        filtered = filtered.filter(h => h.value !== 'race');
      }
      if (!this.filters?.indicatorFilters?.find(f => f.type.id === '2')) {
        filtered = filtered.filter(h => h.value !== 'age');
      }
      if (!this.filters?.indicatorFilters?.find(f => f.type.id === '3')) {
        filtered = filtered.filter(h => h.value !== 'sex');
      }
      if (!this.filters?.indicatorFilters?.find(f => f.type.id === '4')) {
        filtered = filtered.filter(h => h.value !== 'education');
      }
      if (!this.filters?.indicatorFilters?.find(f => f.type.id === '5')) {
        filtered = filtered.filter(h => h.value !== 'income');
      }
      return filtered;
    },
    filteredLocationTypes() {
      return this.locationTypes.filter(i => i.selected);
    },
    filteredLocations() {
      return this.locations.filter(i => i.selected);
    },
    filteredYears() {
      return this.years.filter(i => i.selected);
    },
    filteredRaces() {
      return this.races?.filter(i => i.selected);
    },
    filteredAges() {
      return this.ages?.filter(i => i.selected);
    },
    filteredSexes() {
      return this.sexes?.filter(i => i.selected);
    },
    filteredEducations() {
      return this.educations?.filter(i => i.selected);
    },
    filteredIncomes() {
      return this.incomes?.filter(i => i.selected);
    },
    items() {
      return this.tablesData?.items.map(i => {
        return {
          ...i,
          locationType: i['locationType_' + this.locale],
          location: i['location_' + this.locale],
          raceId: i.raceId,
          race: i['race_' + this.locale],
          sex: i['sex_' + this.locale],
          age: i['age_' + this.locale],
          education: i['education_' + this.locale],
          income: i['income_' + this.locale]
        };
      });
    }
  },
  watch: {
		locale(newValue, oldValue) {
      if (oldValue) {
        this.loadTablesData();
      }
		},
    page() {
      this.loadTablesData();
    },
    itemsPerPage() {
      this.loadTablesData();
    },
    sortBy() {
      this.loadTablesData();
    },
    sortDesc() {
      this.loadTablesData();
    },
    filters(newValue) {
      if (newValue) {
        this.locationTypes = newValue.locationTypeFilter.options
          .map(o => Object.assign({ selected: true }, o));
        this.locations = newValue.locationFilter.options
          .map(o => Object.assign({ selected: true }, o));
        this.years = newValue.yearFilter.options
          .map(o => Object.assign({ selected: true }, o));
        this.races = newValue.indicatorFilters?.find(f => f.type.id === '1')?.options
          .map(o => Object.assign({ selected: true }, o));
        this.ages = newValue.indicatorFilters?.find(f => f.type.id === '2')?.options
          .map(o => Object.assign({ selected: true }, o));
        this.sexes = newValue.indicatorFilters?.find(f => f.type.id === '3')?.options
          .map(o => Object.assign({ selected: true }, o));
        this.educations = newValue.indicatorFilters?.find(f => f.type.id === '4')?.options
          .map(o => Object.assign({ selected: true }, o));
        this.incomes = newValue.indicatorFilters?.find(f => f.type.id === '5')?.options
          .map(o => Object.assign({ selected: true }, o));
      }
    }
	},
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$store.dispatch('setIndicator', null)
    });
  },
  beforeRouteUpdate(to, from, next) {
    if (from.query.indicator && !to.query.indicator) {
      this.setIndicator(null)
    }
    next();
  },
  created () {
    this.loadTablesData = debounce(this.loadTablesData, 250);
  },
  mounted () {
    if (router.currentRoute.query.indicator && this.indicatorMenu) {
      this.setIndicator(this.indicatorMenu.categories
        .flatMap(category => category.subcategories.flatMap(sc => sc.items).concat(category.items))
        .find(item => item.id == router.currentRoute.query.indicator)).then(() => {
          this.loadTablesData();
        })
    } else {
      this.setIndicator(null)
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
      if (matchedIndicator?.id !== this.indicator?.id) {
        this.setIndicator(matchedIndicator).then(() => {
          this.loadTablesData();
        })
      }
    } else {
      this.setIndicator(null)
    }
  },
  methods: {
    ...mapActions(['setIndicator', 'getTablesData', 'getFeaturedIndicators']),
    selectItem(item) {
      if (item.id !== this.indicator?.id) {
        this.setIndicator(item).then(() => {
          this.loadTablesData();
        })
        router.replace({
          query: {
            lang: router.currentRoute.query.lang,
            indicator: item.id,
            tab: router.currentRoute.query.tab
          },
        });
      }
    },
    loadTablesData() {
      this.getTablesData(this.selections);
    },
    downloadTablesData() {
      window.open('/api/tables-download?indicator=' + this.indicator.id, '_blank');
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep .v-data-table-header .v-icon,
::v-deep .v-data-table-header .v-icon:before,
::v-deep .v-data-table-header .v-icon:after,
::v-deep .v-data-table-header .v-data-table-header__sort-badge {
  color: var(--v-accent-base) !important;
}
</style>
