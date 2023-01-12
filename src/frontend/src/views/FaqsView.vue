<template>
  <v-container v-if="faqs" fluid class="pa-0">
    <v-row class="no-gutters">
      <v-col cols="7" class="pa-4 col col-auto">
        <h1 class="text-h3 mb-4">{{ $t('faqs_view.name') }}</h1>
        <section v-for="item in faqs" :key="item.id">
          <h2 class="mb-4 text-h5" :id="'question_' + item.id">{{ item['question_' + locale]}}</h2>
          <section class="mb-8">
            <section v-html="item['answer_' + locale]"></section>
          </section>
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
import router from '@/router/index'
import SideMenu from '@/components/SideMenu'

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
