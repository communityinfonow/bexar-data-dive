<template>
  <v-container v-if="faqs" fluid class="pa-0">
    <section class="page-header d-flex flex-column light--text pa-12 pb-0">
      <h1 class="font-weight-bold" style="font-size: 2.5rem;">{{ $t('faqs_view.name') }}</h1>
      <div class="font-weight-medium mt-2" style="font-size: 1.25rem;" v-html="$t('faqs_view.welcome')"></div>
      <v-breadcrumbs :items="breadcrumbs" class="mb-2" dark>
        <template v-slot:divider>
          <v-icon>mdi-chevron-right</v-icon>
        </template>
      </v-breadcrumbs>
    </section>
    <v-row class="no-gutters">
      <v-col cols="7" class="pa-4 col col-auto">
        <section v-for="item in faqs" :key="item.id">
          <h2 class="mb-4 text-h5" :id="'question_' + item.id">{{ item['question_' + locale]}}</h2>
          <section class="mb-8">
            <section v-html="item['answer_' + locale]"></section>
          </section>
          <v-divider class="my-4"></v-divider>
        </section>
      </v-col>
      <v-col cols="5">
        <side-menu :title="$t('faqs_view.questions')" :menu="sideMenu" :selectItem="scrollToItem"></side-menu>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import i18n from '@/i18n'
import goTo from 'vuetify/lib/services/goto'
import { mapActions, mapState } from 'vuex'
import router from '@/app/router/index'
import SideMenu from '@/app/components/SideMenu'

export default {
  name: 'FaqsView',
  components: {
    SideMenu
  },
  created() {
    if (!this.faqs) {
      this.getFaqs();
    }
  },
  data() {
    return {}
  },
  computed: {
    ...mapState(['faqs', 'locale']),
    breadcrumbs() {
      let crumbs = [
        {
          text: i18n.t('home_view.name'),
          disabled: false,
          href: '/home'
        },
        {
          text: i18n.t('faqs_view.name'),
          disabled: true
        }
      ];

      return crumbs;
    },
    sideMenu() {
      return {
        categories: [
          {
            name_en: i18n.t('faqs_view.questions'),
            name_es: i18n.t('faqs_view.questions'),
            subcategories: [],
            items: this.faqs.map(faq => {
              return {
                ...faq,
                name_en: faq.question_en,
                name_es: faq.question_es
              }
            })
          }
        ]
      }
    }
  },
  watch: {
    faqs(newValue) {
      if (newValue) {
        if (router.currentRoute.query.question) {
          this.$nextTick(() => {
            this.scrollToItem({ id: router.currentRoute.query.question })
          });
        };
      }
    }
  },
  mounted () {
    if (this.faqs && router.currentRoute.query.question) {
      this.scrollToItem({ id: router.currentRoute.query.question })
    };
  },
  methods: {
    ...mapActions(['getFaqs']),
    scrollToItem(item) {
      goTo("#question_" + item.id)
      if (router.currentRoute.query.question != item.id) {
        router.replace({
            query: {
              ...router.currentRoute.query,
              question: item.id
            },
          })
      }
    }
  },
}
</script>

<style lang="scss" scoped></style>
