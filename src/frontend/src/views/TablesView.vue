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
            <span>
              <span v-if="tablesData.category.parentCategoryId">{{ tablesData.category['name_' + locale] }} - </span>
              {{ tablesData.indicator['name_' + locale] }}
            </span>
              <vue-excel-xlsx
                type="button"
                class="v-btn v-btn--icon v-btn--round theme--light v-size--default accent--text"
                :aria-label="$t('tools.common.download')"
                :data="tablesData.items"
                :columns="xlsxColumns"
                :file-name="tablesData.indicator['name_' + locale]"
                :file-type="'xlsx'"
                :sheet-name="tablesData.indicator['name_' + locale]"
              >
              <span class="v-btn__content">
                <v-icon>mdi-download</v-icon>
              </span>
            </vue-excel-xlsx>
          </h1>
          <h2 class="text-subtitle-1 mb-2">{{ tablesData.source['name_' + locale] }}</h2>
          <v-text-field v-model="search" :label="$t('tools.common.search')" hide-details>
            <template v-slot:append><v-icon color="accent">mdi-magnify</v-icon></template>
          </v-text-field>
          <v-data-table
            :headers="filteredHeaders"
            :items="filteredItems"
            :search="search"
            :options="tableOptions"
            :footer-props="footerOptions"
            multi-sort
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
                  <v-list-item v-for="locationType in locationTypes" :key="locationType.name" @click.stop>
                    <v-list-item-action>
                      <v-checkbox
                        v-model="locationType.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ locationType.name }}</v-list-item-title>
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
                  <v-list-item v-for="location in locations" :key="location.name" @click.stop>
                    <v-list-item-action>
                      <v-checkbox
                        v-model="location.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ location.name }}</v-list-item-title>
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
                  <v-list-item v-for="year in years" :key="year.name" @click.stop>
                    <v-list-item-action>
                      <v-checkbox
                        v-model="year.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ year.name }}</v-list-item-title>
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
                  <v-list-item v-for="race in races" :key="race.name" @click.stop>
                    <v-list-item-action>
                      <v-checkbox
                        v-model="race.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ race.name }}</v-list-item-title>
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
                  <v-list-item v-for="age in ages" :key="age.name" @click.stop>
                    <v-list-item-action>
                      <v-checkbox
                        v-model="age.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ age.name }}</v-list-item-title>
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
                  <v-list-item v-for="sex in sexes" :key="sex.name" @click.stop>
                    <v-list-item-action>
                      <v-checkbox
                        v-model="sex.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ sex.name }}</v-list-item-title>
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
                  <v-list-item v-for="education in educations" :key="education.name" @click.stop>
                    <v-list-item-action>
                      <v-checkbox
                        v-model="education.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ education.name }}</v-list-item-title>
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
                  <v-list-item v-for="income in incomes" :key="income.name" @click.stop>
                    <v-list-item-action>
                      <v-checkbox
                        v-model="income.selected"
                        color="primary"
                        hide-details
                      ></v-checkbox>
                    </v-list-item-action>
                    <v-list-item-title>{{ income.name }}</v-list-item-title>
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
//FIXME: server-side paging for performance
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
          text: i18n.t('tools.tables.headers.location_type'),
          value: "locationType",
          filter: (value, search, item) => {
            return this.filteredLocationTypes.find(i => i.name === item.name);
          }
        },
        {
          text: i18n.t('tools.tables.headers.location'),
          value: "location",
          filter: (value, search, item) => {
            return this.filteredLocations.find(i => i.name === item.name);
          }
        }, 
        {
          text: i18n.t('tools.tables.headers.year'),
          value: "year",
          filter: (value, search, item) => {
            return this.filteredYears.find(i => i.name === item.name);
          }
        },
        {
          text: i18n.t('tools.tables.headers.race'),
          value: "race",
          filter: (value, search, item) => {
            return this.filteredRaces.find(i => i.name === item.name);
          }
        },
        {
          text: i18n.t('tools.tables.headers.age'),
          value: "age",
          filter: (value, search, item) => {
            return this.filteredAges.find(i => i.name === item.name);
          }
        },
        {
          text: i18n.t('tools.tables.headers.sex'),
          value: "sex",
          filter: (value, search, item) => {
            return this.filteredSexes.find(i => i.name === item.name);
          }
        },
        {
          text: i18n.t('tools.tables.headers.education'),
          value: "education",
          filter: (value, search, item) => {
            return this.filteredEducations.find(i => i.name === item.name);
          }
        },
        {
          text: i18n.t('tools.tables.headers.income'),
          value: "income",
          filter: (value, search, item) => {
            return this.filteredIncomes.find(i => i.name === item.name);
          }
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
      if (!this.races.length) {
        filtered = filtered.filter(h => h.value !== 'race');
      }
      if (!this.ages.length) {
        filtered = filtered.filter(h => h.value !== 'age');
      }
      if (!this.sexes.length) {
        filtered = filtered.filter(h => h.value !== 'sex');
      }
      if (!this.educations.length) {
        filtered = filtered.filter(h => h.value !== 'education');
      }
      if (!this.incomes.length) {
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
      return this.races.filter(i => i.selected);
    },
    filteredAges() {
      return this.ages.filter(i => i.selected);
    },
    filteredSexes() {
      return this.sexes.filter(i => i.selected);
    },
    filteredEducations() {
      return this.educations.filter(i => i.selected);
    },
    filteredIncomes() {
      return this.incomes.filter(i => i.selected);
    },
    items() {
      return this.tablesData?.items.map(i => {
        return {
          ...i,
          locationType: i['locationType_' + this.locale],
          location: i['location_' + this.locale],
          race: i['race_' + this.locale],
          sex: i['sex_' + this.locale],
          age: i['age_' + this.locale],
          education: i['education_' + this.locale],
          income: i['income_' + this.locale]
        };
      });
    },
    filteredItems() {
      return this.items.filter(item => {
        return this.filteredLocationTypes.find(i => i.name === item.locationType)
          && this.filteredLocations.find(i => i.name === item.location)
          && this.filteredYears.find(i => i.name === item.year)
          && (!this.races.length || this.filteredRaces.find(i => i.name === item.race))
          && (!this.ages.length || this.filteredAges.find(i => i.name === item.age))
          && (!this.sexes.length || this.filteredSexes.find(i => i.name === item.sex))
          && (!this.educations.length || this.filteredEducation.find(i => i.name === item.education))
          && (!this.incomes.length || this.filteredIncomes.find(i => i.name === item.income))
      });
    }
  },
  watch: {
		locale(newValue, oldValue) {
      if (oldValue) {
			  this.getTablesData(router.currentRoute.query.indicator);
      }
		},
    items(newValue) {
      if (newValue) {
        this.locationTypes = new Array(...new Set(newValue.map(i => i.locationType)))
          .map(i => { return { name: i, selected: true }})
          .filter(i => !!i.name);
        this.locations = new Array(...new Set(newValue.map(i => i.location)))
          .map(i => { return { name: i, selected: true }})
          .filter(i => !!i.name);
        this.years = new Array(...new Set(newValue.map(i => i.year)))
          .map(i => { return { name: i, selected: true}})
          .filter(i => !!i.name);
        this.races = new Array(...new Set(newValue.map(i => i.race)))
          .map(i => { return { name: i, selected: true}})
          .filter(i => !!i.name);
        this.ages = new Array(...new Set(newValue.map(i => i.age)))
          .map(i => { return { name: i, selected: true}})
          .filter(i => !!i.name);
        this.sexes = new Array(...new Set(newValue.map(i => i.sex)))
          .map(i => { return { name: i, selected: true}})
          .filter(i => !!i.name);
        this.educations = new Array(...new Set(newValue.map(i => i.education)))
          .map(i => { return { name: i, selected: true}})
          .filter(i => !!i.name);
        this.incomes = new Array(...new Set(newValue.map(i => i.income)))
          .map(i => { return { name: i, selected: true}})
          .filter(i => !!i.name);
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
