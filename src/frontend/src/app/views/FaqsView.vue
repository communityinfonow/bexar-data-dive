<template>
  <v-container v-if="faqs" fluid class="pa-0">
    <div  v-for="item in faqs" :key="item.id" :class="'mx-8 mb-8 ' + (item.id === 1 ? 'mt-4' : '')" :id="'question_' + item.id">
      <v-divider v-if="item.id !== 1" class="my-12"></v-divider>
      <v-row class="no-gutters">
        <v-col cols="12" lg="4" class="pa-4 col col-auto">
          <h2 class="mb-4 text-dive-h5 blue--text">{{ item['question_' + locale]}}</h2>
        </v-col>
        <v-col cols="12" lg="8" class="pa-4 col col-auto" style="font-size: 1.25rem">
          <section v-html="item['answer_' + locale]"></section>
        </v-col>
      </v-row>
    </div>
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
