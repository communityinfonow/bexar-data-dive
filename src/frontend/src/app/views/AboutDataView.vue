<template>
  <v-container v-if="aboutData" fluid class="pa-0">
    <v-row class="no-gutters">
      <v-col cols="7" class="pa-4 col col-auto">
        <h1 class="text-h3 mb-4">{{ $t('about_data_view.name') }}</h1>
        <section v-for="category in sortedAboutData.categories" :key="category.category.id">
          <h2 class="mb-4 text-h4">{{ category.category['name_' + locale]}}</h2>
          <section v-for="item in category.items" :key="item.id">
            <template v-if="item.items">
              <h3 class="mb-2 text-h5">{{ item.category['name_' + locale] }}</h3>
              <section v-for="subItem in item.items" :key="subItem.id" class="mb-8">
                <h4 class="mb-1 text-h6" :id="'indicator_' + subItem.indicator.id">{{ subItem.indicator['name_' + locale] }}</h4>
                <section v-if="subItem.indicator['description_' + locale] !== ''" v-html="subItem.indicator['description_' + locale]"></section>
                <p v-if="subItem.indicator['description_' + locale] === ''">{{ $t('tools.community.coming_soon') }}</p>
                <v-btn v-if="subItem.indicator.hasData" text color="primary" @click.stop="openCitationDialog(subItem)">{{ $t('about_data_view.source_citation') }}</v-btn>
                <v-btn text link color="primary" target="_blank" :href="subItem.source.url">{{ $t('about_data_view.visit_source') }} <sup><v-icon x-small color="primary">mdi-open-in-new</v-icon></sup></v-btn>
              </section>
            </template>
            <template v-else>
              <h3 class="mb-1 text-h6" :id="'indicator_' + item.indicator.id">{{ item.indicator['name_' + locale] }}</h3>
              <section class="mb-8">
                <section v-if="!item.items && item.indicator['description_' + locale] !== ''" v-html="item.indicator['description_' + locale]"></section>
                <p v-if="item.indicator['description_' + locale] === ''">{{ $t('tools.community.coming_soon') }}</p>
                <v-btn v-if="item.indicator.hasData" text color="primary" @click.stop="openCitationDialog(item)">{{ $t('about_data_view.source_citation') }}</v-btn>
                <v-btn text link color="primary" target="_blank" :href="item.source.url">{{ $t('about_data_view.visit_source') }} <sup><v-icon x-small color="primary">mdi-open-in-new</v-icon></sup></v-btn>
              </section>
            </template>
          </section>
        </section>
      </v-col>
      <v-col cols="5">
        <side-menu :title="$t('about_data_view.indicators')" :menu="sortedMenu" :selectItem="scrollToItem"></side-menu>
      </v-col>
    </v-row>
    <v-dialog v-model="citationDialog" width="40%">
      <v-card>
        <v-card-title>{{ $t('about_data_view.source_citation') }}</v-card-title>
        <v-card-text>
          <v-select 
            v-if="filters" 
            multiple 
            v-model="citationYears" 
            :items="yearOptions" 
            :label="filters.yearFilter.type['name_' + locale] + '(s)'" 
            outlined 
            dense
            :menu-props="{
              maxHeight: '180px'
            }"
            ></v-select>
          <p>{{ $t('about_data_view.click_copy') }}</p>
          <v-sheet rounded class="pa-2 d-flex align-center citation-content cursor-pointer" @click="copyCitation">
            <span ref="citationEl" v-html="citation"></span>
            <v-spacer></v-spacer>
            <v-btn class="ml-2" color="primary" icon @click="copyCitation">
              <v-icon>mdi-content-copy</v-icon>
            </v-btn>
          </v-sheet>
          <p>{{ citationMessage }}</p>
        </v-card-text>
        <v-card-actions>
          <v-btn text @click="citationDialog = false">{{ $t('survey.close') }}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import goTo from 'vuetify/lib/services/goto'
import { mapActions, mapState } from 'vuex'
import router from '@/app/router/index'
import SideMenu from '@/app/components/SideMenu'
import i18n from '@/i18n'
import axios from 'axios'

export default {
  name: 'AboutDataView',
  components: {
    SideMenu
  },
  created() {
    if (!this.aboutData) {
      this.getAboutData();
    }
  },
  data() {
    return {
      citationDialog: false,
      citationMessage: '',
      citationYears: [],
      item: null,
      filters: null,
      yearOptions: []
    }
  },
  computed: {
    ...mapState(['indicatorMenu', 'aboutData', 'locale']),
    sortedMenu() { //FIXME: DRY
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
    },
    sortedAboutData() {
        let sortedAboutData = JSON.parse(JSON.stringify(this.aboutData));
        sortedAboutData.categories.forEach(c => {
          if (c.subcategories.length) {
            c.items = c.items.concat(c.subcategories);
            c.items.forEach(item => {
              item.name_en = item.category ? item.category.name_en : item.indicator.name_en;
              item.name_es = item.category ? item.category.name_en : item.indicator.name_es;
            });
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

        return sortedAboutData;
    },
		copyMessage() {
			return i18n.t('about_data_view.click_copy');
		},
    citation() {
      if (!this.item || !this.citationYears.length) {
        return;
      }
      return this.item.source['name_' + this.locale] + '. '
                + ' (' + this.citationYears.slice(0).sort().join(', ') + '). '
                + '<em>' + this.item.indicator['name_' + this.locale] + '</em>. '
                + i18n.t('about_data_view.processed_published') + ' '
                + new Date().getDate() + ' ' + new Date().toLocaleString(this.locale, { month: 'long' }) + ' ' + new Date().getFullYear() + '. '
                + window.location.href + '.';
    }
  },
  watch: {
    aboutData(newValue) {
      if (newValue) {
        if (router.currentRoute.query.indicator) {
          this.$nextTick(() => {
            this.scrollToItem({ id: router.currentRoute.query.indicator })
          });
        };
      }
    }
  },
  mounted () {
    if (this.aboutData && router.currentRoute.query.indicator) {
      this.scrollToItem({ id: router.currentRoute.query.indicator })
    };
  },
  methods: {
    ...mapActions(['getAboutData']),
    scrollToItem(item) {
      goTo("#indicator_" + item.id)
      if (router.currentRoute.query.indicator != item.id) {
        router.replace({
            query: {
              ...router.currentRoute.query,
              indicator: item.id
            },
          })
      }
    },
    openCitationDialog(item) {
      this.item = item;
      axios.get('/api/filters', { params: {
        indicator: item.indicator.id
      }}).then(response => { 
        this.filters = response.data;
        this.yearOptions = response.data.yearFilter.options.map(o => o.id).reverse();
        this.citationYears = [this.yearOptions[this.yearOptions.length - 1]];
        this.citationDialog = true;
      });
    },
    copyCitation() {
			navigator.permissions.query({ name: 'clipboard-write' }).then(result => {
				if (result.state === 'granted' || result.state === 'prompt') {
					window.navigator.clipboard
						.write([new window.ClipboardItem({ "text/html": new Blob([this.$refs.citationEl.innerHTML], { type: "text/html"}) })])
						.then((self = this) => {
							self.citationMessage = i18n.t('tools.common.copy_success')
							window.setTimeout(() => (self.citationMessage = ''), 2000)
						});
				} else {
					this.citationMessage = i18n.t('tools.common.copy_failure');
				}
			});
		}
  },
}
</script>

<style lang="scss" scoped>
.citation-content {
  border: 1px solid $color-primary;
}
.cursor-pointer {
  cursor: pointer;
}
</style>
