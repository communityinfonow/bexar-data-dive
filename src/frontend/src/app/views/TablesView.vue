<template>
  <v-container v-if="indicatorMenu" fluid class="pa-0 fill-height">
    <v-row class="no-gutters flex-column fill-height">
      <v-col cols="auto" class="grow">
        <section :class="'page-header d-flex flex-column light--text pa-12 pb-0 ' + (!showIntro ? 'main-content' : '')">
          <h1 v-if="showIntro" class="text-dive-h3">{{ $t('tools.tables.name') }}</h1>
          <h1 v-if="tablesData && indicator" class="text-dive-h3">
            <span>
              <span v-if="tablesData.category.parentCategoryId">{{ tablesData.category['name_' + locale] }} - </span>
              {{ tablesData.indicator['name_' + locale] }}
              <indicator-definition :indicator="indicator"></indicator-definition>
              <h2 class="text-subtitle-1 mb-2">{{ tablesData.source['name_' + locale] }}</h2>
            </span>
          </h1>
          <div v-if="showIntro" class="font-weight-medium mt-2" style="font-size: 1.25rem;">
            {{ $t('tools.tables.headline') }}
            {{ $t('tools.tables.long_description') }}
          </div>
          <v-breadcrumbs :items="breadcrumbs" class="mb-2" dark>
            <template v-slot:divider>
              <v-icon>mdi-chevron-right</v-icon>
            </template>
          </v-breadcrumbs>
        </section>
      </v-col>
      <v-col cols="auto" class="shrink sticky-menu">
        <MenuToolbar
          class="flex-column"
          :menu="indicatorMenu"
          :selectItem="selectItem"
          :flattenSingleItems="false"
          :searchType="$t('tools.common.indicators')"
        ></MenuToolbar>
      </v-col>
      <v-col v-if="showIntro && featuredIndicators" cols="auto">
        <section class="mb-8">
          <h2 class="text-dive-h4 text-uppercase mt-16 mb-2 text-center font-weight-light">{{ $t('tools.common.featured_indicators') }}</h2>
          <p style="margin: 0 30%; font-size: 1.25em;">{{ $t('tools.tables.get_started') }}</p>
        </section>
        <section class="d-flex" :class="{ 'flex-row': $vuetify.breakpoint.mdAndUp, 'flex-column': $vuetify.breakpoint.smAndDown }">
          <template v-for="indicator in featuredIndicators">
            <featured-card 
              :key="indicator.id" 
              :item="indicator"
              :name="indicator['name_' + locale]" 
              :postText="$t('tools.explore.docked_tooltip.source') + ': ' + indicator.source['name_' + locale]"
              :about_route="'about-data?indicator=' + indicator.id" 
              :view_route="indicator.route"
              :click_route="selectItem"
              :primary_button_text="$t('featured_card.view')"
              :secondary_button_text="$t('featured_card.learn_more')"
              :iconPath="getCategoryIconPath(indicator.categoryId)"
              dense
            >
            </featured-card>
          </template>
        </section>
      </v-col>
      <v-col v-if="indicator && tablesData" cols="auto" class="pt-4 px-4">
          <v-alert dense type="error" v-if="locationLimitExceeded">{{ $t('tools.tables.location_limit_exceeded') }}</v-alert>
          <div class="d-flex">
            <v-text-field v-model="search" :disabled="locationLimitExceeded" :label="$t('tools.common.search')" hide-details @input="loadTablesData()">
              <template v-slot:append><v-icon color="green">mdi-magnify</v-icon></template>
            </v-text-field>
            <div class="mt-2">
              <button-menu :downloadData="downloadTablesData" class="ml-2"></button-menu>
            </div>
          </div>
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
            <!-- FIXME: DRY -->
            <template v-slot:header.locationType="{ header }">
              {{ header.text }}
              <v-menu offset-y eager>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="green" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <v-list class="filter-list">
                    <v-list-item>
                      <v-list-item-action>
                        <v-checkbox v-model="selectAllLocationTypes" @click.stop="" @change="selectAllLocationTypesChange"></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>
                        <em v-if="selectAllLocationTypes">{{ $t('tools.tables.clear_selections') }}</em>
                        <em v-else>{{ $t('tools.tables.select_all') }}</em>
                      </v-list-item-title>
                    </v-list-item>
                    <v-list-item v-for="locationType in locationTypes" :key="locationType.name" @click.stop="" :title="locationType.id === '7' && !indicator.aggregable ? $t('data.not_aggregable') : ''">
                      <v-list-item-action>
                        <v-checkbox
                          v-model="locationType.selected"
                          color="red"
                          hide-details
                          :disabled="locationType.id === '7' && !indicator.aggregable"
                        ></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>{{ locationType['name_' + locale] }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                  <v-card-actions>
                    <v-btn block rounded dark color="green" :disabled="locationLimitExceeded" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-card-actions>
                </v-card>
              </v-menu>
            </template>
            <template v-slot:header.location="{ header }">
              {{ header.text }}
              <v-menu offset-y eager>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="green" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <v-list class="filter-list">
                    <v-list-item>
                      <v-list-item-action>
                        <v-checkbox v-model="selectAllLocations" @click.stop="" @change="selectAllLocationsChange"></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>
                        <em v-if="selectAllLocations">{{ $t('tools.tables.clear_selections') }}</em>
                        <em v-else>{{ $t('tools.tables.select_all') }}</em>
                      </v-list-item-title>
                    </v-list-item>
                    <v-list-item v-for="location in locations" :key="location.name" @click.stop="" :title="location.typeId === '7' && !indicator.aggregable ? $t('data.not_aggregable') : ''">
                      <v-list-item-action>
                        <v-checkbox
                          v-model="location.selected"
                          color="red"
                          hide-details
                          :disabled="location.typeId === '7' && !indicator.aggregable"
                        ></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>{{ location['name_' + locale] }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                  <v-card-actions>
                    <v-btn block rounded dark color="green" :disabled="locationLimitExceeded" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-card-actions>
                </v-card>
              </v-menu>
            </template>
            <template v-slot:header.year="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="green" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <v-list class="filter-list">
                    <v-list-item>
                      <v-list-item-action>
                        <v-checkbox v-model="selectAllYears" @click.stop="" @change="selectAllYearsChange"></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>
                        <em v-if="selectAllYears">{{ $t('tools.tables.clear_selections') }}</em>
                        <em v-else>{{ $t('tools.tables.select_all') }}</em>
                      </v-list-item-title>
                    </v-list-item>
                    <v-list-item v-for="year in years" :key="year.name" @click.stop="">
                      <v-list-item-action>
                        <v-checkbox
                          v-model="year.selected"
                          color="red"
                          hide-details
                        ></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>{{ year['name_' + locale] }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                  <v-card-actions>
                    <v-btn block rounded dark color="green" :disabled="locationLimitExceeded" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-card-actions>
                </v-card>
              </v-menu>
            </template>
            <template v-if="filteredHeaders.find(f => f.value === 'race')" v-slot:header.race="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="green" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <v-list class="filter-list">
                    <v-list-item>
                      <v-list-item-action>
                        <v-checkbox v-model="selectAllRaces" @click.stop="" @change="selectAllRacesChange"></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>
                        <em v-if="selectAllRaces">{{ $t('tools.tables.clear_selections') }}</em>
                        <em v-else>{{ $t('tools.tables.select_all') }}</em>
                      </v-list-item-title>
                    </v-list-item>
                    <v-list-item v-for="race in races" :key="race.name" @click.stop="">
                      <v-list-item-action>
                        <v-checkbox
                          v-model="race.selected"
                          color="red"
                          hide-details
                        ></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>{{ race['name_' + locale] }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                  <v-card-actions>
                    <v-btn block rounded dark color="green" :disabled="locationLimitExceeded" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-card-actions>
                </v-card>
              </v-menu>
            </template>
            <template v-if="filteredHeaders.find(f => f.value === 'age')" v-slot:header.age="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="green" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <v-list class="filter-list">
                    <v-list-item>
                      <v-list-item-action>
                        <v-checkbox v-model="selectAllAges" @click.stop="" @change="selectAllAgesChange"></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>
                        <em v-if="selectAllAges">{{ $t('tools.tables.clear_selections') }}</em>
                        <em v-else>{{ $t('tools.tables.select_all') }}</em>
                      </v-list-item-title>
                    </v-list-item>
                    <v-list-item v-for="age in ages" :key="age.name" @click.stop="">
                      <v-list-item-action>
                        <v-checkbox
                          v-model="age.selected"
                          color="red"
                          hide-details
                        ></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>{{ age['name_' + locale] }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                  <v-card-actions>
                    <v-btn block rounded dark color="green" :disabled="locationLimitExceeded" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-card-actions>
                </v-card>
              </v-menu>
            </template>
            <template v-if="filteredHeaders.find(f => f.value === 'sex')" v-slot:header.sex="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="green" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <v-list class="filter-list">
                    <v-list-item>
                      <v-list-item-action>
                        <v-checkbox v-model="selectAllSexes" @click.stop="" @change="selectAllSexesChange"></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>
                        <em v-if="selectAllSexes">{{ $t('tools.tables.clear_selections') }}</em>
                        <em v-else>{{ $t('tools.tables.select_all') }}</em>
                      </v-list-item-title>
                    </v-list-item>
                    <v-list-item v-for="sex in sexes" :key="sex.name" @click.stop="">
                      <v-list-item-action>
                        <v-checkbox
                          v-model="sex.selected"
                          color="red"
                          hide-details
                        ></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>{{ sex['name_' + locale] }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                  <v-card-actions>
                    <v-btn block rounded dark color="green" :disabled="locationLimitExceeded" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-card-actions>
                </v-card>
              </v-menu>
            </template>
            <template v-if="filteredHeaders.find(f => f.value === 'education')" v-slot:header.education="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="green" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <v-list class="filter-list">
                    <v-list-item>
                      <v-list-item-action>
                        <v-checkbox v-model="selectAllEducations" @click.stop="" @change="selectAllEducationsChange"></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>
                        <em v-if="selectAllEducations">{{ $t('tools.tables.clear_selections') }}</em>
                        <em v-else>{{ $t('tools.tables.select_all') }}</em>
                      </v-list-item-title>
                    </v-list-item>
                    <v-list-item v-for="education in educations" :key="education.name" @click.stop="">
                      <v-list-item-action>
                        <v-checkbox
                          v-model="education.selected"
                          color="red"
                          hide-details
                        ></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>{{ education['name_' + locale] }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                  <v-card-actions>
                    <v-btn block rounded dark color="green" :disabled="locationLimitExceeded" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-card-actions>
                </v-card>
              </v-menu>
            </template>
            <template v-if="filteredHeaders.find(f => f.value === 'income')" v-slot:header.income="{ header }">
              {{ header.text }}
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="green" v-bind="attrs" v-on="on" :aria-label="$t('tools.tables.filter')">
                    <v-icon>mdi-filter-variant</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <v-list class="filter-list">
                    <v-list-item>
                      <v-list-item-action>
                        <v-checkbox v-model="selectAllIncomes" @click.stop="" @change="selectAllIncomesChange"></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>
                        <em v-if="selectAllIncomes">{{ $t('tools.tables.clear_selections') }}</em>
                        <em v-else>{{ $t('tools.tables.select_all') }}</em>
                      </v-list-item-title>
                    </v-list-item>
                    <v-list-item v-for="income in incomes" :key="income.name" @click.stop="">
                      <v-list-item-action>
                        <v-checkbox
                          v-model="income.selected"
                          color="red"
                          hide-details
                        ></v-checkbox>
                      </v-list-item-action>
                      <v-list-item-title>{{ income['name_' + locale] }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                  <v-card-actions>
                    <v-btn block rounded dark color="green" :disabled="locationLimitExceeded" @click="loadTablesData()">{{ $t('tools.explore.apply_filters') }}</v-btn>
                  </v-card-actions>
                </v-card>
              </v-menu>
            </template>
          </v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import axios from 'axios'
import i18n from '@/i18n'
import router from '@/app/router/index'
import MenuToolbar from '@/app/components/MenuToolbar'
import FeaturedCard from '@/app/components/FeaturedCard'
import ButtonMenu from '@/app/components/ButtonMenu'
import IndicatorDefinition from '@/app/components/IndicatorDefinition.vue'
import { getCategoryIconPath } from '@/services/icons'
import debounce from 'debounce'

export default {
  name: 'TablesView',
  components: {
    MenuToolbar,
    FeaturedCard,
    ButtonMenu,
    IndicatorDefinition
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
      incomes: [],
      selectAllLocationTypes: true,
      selectAllLocations: true,
      selectAllYears: true,
      selectAllRaces: true,
      selectAllAges: true,
      selectAllSexes: true,
      selectAllEducations: true,
      selectAllIncomes: true
    }
  },
  computed: {
    ...mapState(['indicatorMenu', 'indicator', 'tablesData', 'locale', 'featuredIndicators']),
    ...mapGetters(['filters']),
    showIntro() {
      return !this.indicator && !router.currentRoute.query.indicator;
    },
    breadcrumbs() {
      let crumbs = [
        {
          text: i18n.t('home_view.name'),
          disabled: false,
          href: '/home'
        },
        {
          text: i18n.t('tools.tables.name'),
          disabled: !this.tablesData,
          href: '/tables'
        }
      ];

      if (this.tablesData && this.indicator) {
        crumbs.push({
          text: (this.tablesData.category.parentCategoryId ? this.tablesData.category['name_' + this.locale] + ' - ' : '') + this.indicator['name_' + this.locale],
          disabled: true
        });
      }

      return crumbs;
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
            || !this.indicator.aggregable && this.filteredLocationTypes.length === this.locationTypes.length - 1 && !this.filteredLocationTypes.find(lt => lt.id === '7')
          ? undefined 
          : this.filteredLocationTypes.map(i => i.id),
        locations: this.locations.length === this.filteredLocations.length
            || !this.indicator.aggregable && this.filteredLocations.length === this.locations.filter(l => l.typeId !== '7').length && !this.filteredLocations.find(l => l.typeId === '7')
          ? undefined
          : this.filteredLocations.map(i => i.typeId + '_' + i.id),
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
    locationLimitExceeded() {
      let availableLocationCount = !this.indicator.aggregable
        ? this.locations.filter(l => l.typeId !== '7').length
        : this.locations.length;
      return this.selections.locations?.length !== availableLocationCount
         && this.selections.locations?.length > 50
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
          .map(o => Object.assign({ 
              selected: !router.currentRoute.query.locationTypes || [].concat(router.currentRoute.query.locationTypes).find(q => q === o.id) 
            }, o)
          );
        if (!this.indicator.aggregable && this.locationTypes.find(lt => lt.id === '7')) {
          this.locationTypes.find(lt => lt.id === '7').selected = false;
        }
        this.locations = newValue.locationFilter.options
          .map(o => Object.assign({ 
              selected: !router.currentRoute.query.locations || [].concat(router.currentRoute.query.locations).find(q => q === o.typeId + '_' + o.id) 
            }, o)
          );
        if (!this.indicator.aggregable) {
          this.locations.filter(l => l.typeId === '7').forEach(l => l.selected = false);
        }
        this.years = newValue.yearFilter.options
          .map(o => Object.assign({ 
              selected: !router.currentRoute.query.years || [].concat(router.currentRoute.query.years).find(q => q == o.id)  
            }, o)
          );
        this.races = newValue.indicatorFilters?.find(f => f.type.id === '1')?.options
          .map(o => Object.assign({ 
              selected: !router.currentRoute.query.races || [].concat(router.currentRoute.query.races).find(q => (q === '000' && !o.id) || q == o.id)   
            }, o)
          );
        this.ages = newValue.indicatorFilters?.find(f => f.type.id === '2')?.options
          .map(o => Object.assign({ 
              selected: !router.currentRoute.query.ages || [].concat(router.currentRoute.query.ages).find(q => (q === '000' && !o.id) || q == o.id)   
            }, o)
          );
        this.sexes = newValue.indicatorFilters?.find(f => f.type.id === '3')?.options
          .map(o => Object.assign({ 
              selected: !router.currentRoute.query.sexes || [].concat(router.currentRoute.query.sexes).find(q => (q === '000' && !o.id) || q == o.id)   
            }, o)
          );
        this.educations = newValue.indicatorFilters?.find(f => f.type.id === '4')?.options
          .map(o => Object.assign({ 
              selected: !router.currentRoute.query.educations || [].concat(router.currentRoute.query.educations).find(q => (q === '000' && !o.id) || q == o.id)    
            }, o)
          );
        this.incomes = newValue.indicatorFilters?.find(f => f.type.id === '5')?.options
          .map(o => Object.assign({ 
              selected: !router.currentRoute.query.incomes || [].concat(router.currentRoute.query.incomes).find(q => (q === '000' && !o.id) || q == o.id)    
            }, o)
          );
      }
    }
	},
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$store.dispatch('setIndicator', null);
      vm.$store.dispatch('setTablesData', null);
      vm.$store.dispatch('setToolRoute', { key: 'tables', route: to.fullPath });
    });
  },
  beforeRouteUpdate(to, from, next) {
    if (from.query.indicator && !to.query.indicator) {
      this.setIndicator(null)
      this.setTablesData(null)
    }
    this.setToolRoute({ key: 'tables', route: to.fullPath })
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
    ...mapActions(['setIndicator', 'setTablesData', 'getTablesData', 'getFeaturedIndicators', 'setToolRoute' ]),
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
      if (!this.selectAllLocations && this.selections.locations?.length > 50) {
        return;
      }
      this.getTablesData(this.selections);
    },
    downloadTablesData() {
      let downloadSelections = Object.assign({}, this.selections);
      downloadSelections.perPage = -1;
      axios.post('/api/tables-download', { 
        ...downloadSelections 
      }).then(response => {
        let fileLink = document.createElement('a');
        fileLink.download = 'table_download.csv';
        fileLink.href = window.URL.createObjectURL(new Blob([response.data], { type: 'octet/stream'}))
        fileLink.click();
      });
    },
    selectAllLocationTypesChange() {
      this.locationTypes.forEach(i => i.selected = this.selectAllLocationTypes);
      if (!this.indicator.aggregable && this.locationTypes.find(lt => lt.id === '7')) {
        this.locationTypes.find(lt => lt.id === '7').selected = false;
      }
    },
    selectAllLocationsChange() {
      this.locations.forEach(i => i.selected = this.selectAllLocations);
      if (!this.indicator.aggregable) {
        this.locations.filter(l => l.typeId === '7').forEach(l => l.selected = false);
      }
    },
    selectAllYearsChange() {
      this.years.forEach(i => i.selected = this.selectAllYears);
    },
    selectAllRacesChange() {
      this.races.forEach(i => i.selected = this.selectAllRaces);
    },
    selectAllAgesChange() {
      this.ages.forEach(i => i.selected = this.selectAllAges);
    },
    selectAllSexesChange() {
      this.sexes.forEach(i => i.selected = this.selectAllSexes);
    },
    selectAllEducationsChange() {
      this.educations.forEach(i => i.selected = this.selectAllEducations);
    },
    selectAllIncomesChange() {
      this.incomes.forEach(i => i.selected = this.selectAllIncomes);
    },
    getCategoryIconPath(category) {
      return getCategoryIconPath(category);
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep .v-data-table-header .v-icon,
::v-deep .v-data-table-header .v-icon:before,
::v-deep .v-data-table-header .v-icon:after,
::v-deep .v-data-table-header .v-data-table-header__sort-badge {
  color: var(--v-green-base) !important;
}
.filter-list {
  min-width: 160px;
  max-height: 400px;
  overflow-y: auto;
}
</style>
