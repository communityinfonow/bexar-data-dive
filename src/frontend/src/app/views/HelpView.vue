<template>
  <v-container fluid class="pa-0">
    <section id="page-header" class="page-header d-flex flex-column light--text pa-12 pb-0">
      <h1 class="text-dive-h3">{{ $t('help_view.name') }}</h1>
      <div class="font-weight-medium mt-2" style="font-size: 1.25rem;" v-html="$t('help_view.headline')"></div>
      <v-breadcrumbs :items="breadcrumbs" class="mb-2" dark>
        <template v-slot:divider>
          <v-icon>mdi-chevron-right</v-icon>
        </template>
      </v-breadcrumbs>
    </section>
    <section class="sticky-menu">
      <MenuToolbar
        :menu="helpMenu"
        :selectItem="selectMenuItem"
        :flattenSingleItems="true"
        :searchType="$t('help_view.name')"
      ></MenuToolbar>
    </section>
    <v-row class="no-gutters">
      <template v-if="!selectedMenuItem">
        <featured-card 
          v-for="item in helpMenu.categories" 
          :key="item.id"
          :item="item"
          :name="item['name_' + locale]"
          :description="item['description_' + locale]"
          :primary_button_text="$t('featured_card.view')"
          :click_route="selectTool"
        >
        </featured-card>
      </template>
      <faqs-view v-if="selectedMenuItem && selectedMenuItem.categoryId === 'faqs'" :selectedFaq="selectedMenuItem"></faqs-view>
      <about-tools-view v-if="selectedMenuItem && selectedMenuItem.categoryId === 'about-tools'"></about-tools-view>
    </v-row>
  </v-container>
</template>

<script>
import i18n from '@/i18n'
import { mapState, mapGetters, mapActions } from 'vuex'
import FeaturedCard from '@/app/components/FeaturedCard'
import FaqsView from '@/app/views/FaqsView'
import AboutToolsView from '@/app/views/AboutToolsView'
import MenuToolbar from '@/app/components/MenuToolbar'
import router from '@/app/router/index'
import goTo from 'vuetify/lib/services/goto'

export default {
  name: 'HelpView',
  components: {
    FeaturedCard,
    FaqsView,
    AboutToolsView,
    MenuToolbar
  },
  created() {
  },
  data() {
    return {
      menuOpen: false,
      selectedMenuItem: null,
      selectedSearchItem: null
    }
  },
  computed: {
    ...mapState(['locale', 'faqs']),
    ...mapGetters(['helpMenu']),
    breadcrumbs() {
      let crumbs = [
        {
          text: i18n.t('home_view.name'),
          disabled: false,
          href: '/home'
        },
        {
          text: i18n.t('help_view.name'),
          disabled: !this.selectedMenuItem,
          href: this.selectedMenuItem ? '/help' : null
        }
      ];

      if (this.selectedMenuItem) {
        let text;
        if (this.selectedMenuItem.categoryId !== this.selectedMenuItem.id) {
          text = this.helpMenu.categories.find(c => c.id === this.selectedMenuItem.categoryId)['name_' + this.locale] + ': '
        } else {
          text = this.selectedMenuItem['name_' + this.locale]
        }
        crumbs.push({
          text: text,
          disabled: true
        })
      }

      return crumbs;
    },
    searchItems() {
      return []
    }
  },
  watch: {
    
  },
  mounted () {
    this.getFaqs().then(() => {
      if (router.currentRoute.query.category && this.helpMenu) {
        if (!router.currentRoute.query.item) {
          this.selectMenuItem(this.helpMenu.categories.find(c => c.id === router.currentRoute.query.category).items[0])
        } else {
          this.selectMenuItem(this.helpMenu.categories
            .find(c => c.id == router.currentRoute.query.category).items.
            find(i => i.id == router.currentRoute.query.item))
        }
        
      }
    })
  },
  methods: {
    ...mapActions(['getFaqs']),
    selectTool(item) {
      this.selectedMenuItem = item.items[0];
      this.updateRoute(item.items[0])
    },
    selectSearchMatch() {
      //TODO: needs implemented
    },
    selectMenuItem(item) {
      if (item.categoryId == item.id) {
        goTo('#page-header')
      }
      this.selectedMenuItem = item;
      this.updateRoute(item)
    },
    updateRoute(item) {
      let query = {
        ...router.currentRoute.query,
          category: item.categoryId || item.id,
      }
      if (item.categoryId !== item.id) {
        query.item = item.id
      } else {
        delete query.item
      }
      if (query.category != router.currentRoute.query.category || query.item != router.currentRoute.query.item) {
        router.replace({
          query: query
        })
      }
    }
  },
}
</script>

<style lang="scss" scoped></style>
