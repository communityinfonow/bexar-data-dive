<template>
  <v-container v-if="faqs" fluid class="pa-0">
    <v-row class="no-gutters">
      <v-col cols="7" class="pa-4 col col-auto">
        <section v-for="item in faqs" :key="item.id" :class="item.id === 1 ? 'mt-4' : ''" :id="'question_' + item.id">
          <v-divider v-if="item.id !== 1" class="my-12"></v-divider>
          <h2 class="mb-4 text-dive-h5">{{ item['question_' + locale]}}</h2>
          <section class="mb-8">
            <section v-html="item['answer_' + locale]"></section>
          </section>
        </section>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import i18n from '@/i18n'
import goTo from 'vuetify/lib/services/goto'
import { mapActions, mapState } from 'vuex'
import router from '@/app/router/index'

export default {
  name: 'FaqsView',
  components: {
    
  },
  created() {
    if (!this.faqs) {
      this.getFaqs();
    }
  },
  props: {
    selectedFaq: {
      type: Object
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
    }
  },
  watch: {
    selectedFaq(newValue) {
      if (newValue) {
        if (router.currentRoute.query.item) {
          this.$nextTick(() => {
            this.scrollToItem({ id: router.currentRoute.query.item })
          });
        };
      }
    }
  },
  mounted () {
    if (this.selectedFaq && router.currentRoute.query.item) {
      this.scrollToItem({ id: router.currentRoute.query.item })
    };
  },
  methods: {
    ...mapActions(['getFaqs']),
    scrollToItem(item) {
      if (item.id === '1') {
        goTo('#page-header')
      } else {
        goTo("#question_" + item.id)
      }
      if (router.currentRoute.query.item != item.id) {
        router.replace({
          query: {
            ...router.currentRoute.query,
            item: item.id
          },
        })
      }
    }
  },
}
</script>

<style lang="scss" scoped></style>
