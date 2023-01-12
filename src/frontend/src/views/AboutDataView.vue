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
                <p><a target="_blank" :href="subItem.source.url">{{ $t('about_data_view.visit_source') }} <sup><v-icon x-small color="primary">mdi-open-in-new</v-icon></sup></a></p>
              </section>
            </template>
            <template v-else>
              <h3 class="mb-1 text-h6" :id="'indicator_' + item.indicator.id">{{ item.indicator['name_' + locale] }}</h3>
              <section class="mb-8">
                <section v-if="!item.items && item.indicator['description_' + locale] !== ''" v-html="item.indicator['description_' + locale]"></section>
                <p v-if="item.indicator['description_' + locale] === ''">{{ $t('tools.community.coming_soon') }}</p>
                <p><a target="_blank" :href="item.source.url">{{ $t('about_data_view.visit_source') }} <sup><v-icon x-small color="primary">mdi-open-in-new</v-icon></sup></a></p>
              </section>
            </template>
          </section>
        </section>
      </v-col>
      <v-col cols="5">
        <side-menu :title="$t('about_data_view.indicators')" :menu="sortedMenu" :selectItem="scrollToItem"></side-menu>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import goTo from 'vuetify/lib/services/goto'
import { mapActions, mapState } from 'vuex'
import router from '@/router/index'
import SideMenu from '@/components/SideMenu'

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
    return {}
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
    }
  },
}
</script>

<style lang="scss" scoped></style>
