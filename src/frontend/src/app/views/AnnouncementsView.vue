<template>
  <v-container v-if="announcements" fluid class="pa-0">
    <section class="page-header d-flex flex-column light--text px-12 pb-4">
      <v-breadcrumbs :items="breadcrumbs" class="mb-8" dark>
        <template v-slot:divider>
          <v-icon>mdi-chevron-right</v-icon>
        </template>
      </v-breadcrumbs>
      <h1 class="text-dive-h3">{{ $t('announcements_view.name') }}</h1>
      <div v-if="$t('announcements_view.welcome')" class="font-weight-medium mt-2" style="font-size: 1.25rem;" v-html="$t('announcements_view.welcome')"></div>
    </section>
    <v-row class="no-gutters">
      <v-col cols="7" class="pa-4 col col-auto">
        <section v-for="item in announcements" :key="item.id" class="mb-8 mt-4">
          <h2 class="mb-2 text-dive-h4" :id="'question_' + item.id">{{ item['title_' + locale]}}</h2>
          <h3 class="mb-4 text-subtitle-1">{{ new Date(item.date + 'T00:00:00').toLocaleDateString() }}</h3>
          <section class="mb-8">
            <section v-html="item['message_' + locale]" style="font-size: 1.25rem"></section>
          </section>
          <v-divider class="my-4"></v-divider>
        </section>
      </v-col>
      <v-col cols="5">
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import i18n from '@/i18n'
import { mapActions, mapState } from 'vuex'

export default {
  name: 'FaqsView',
  components: {
  },
  created() {
    if (!this.announcements) {
      this.getAnnouncements();
    }
  },
  data() {
    return {}
  },
  computed: {
    ...mapState(['announcements', 'locale']),
    breadcrumbs() {
      let crumbs = [
        {
          text: i18n.t('home_view.name'),
          disabled: false,
          href: '/home'
        },
        {
          text: i18n.t('announcements_view.name'),
          disabled: true
        }
      ];

      return crumbs;
    },
  },
  watch: {
  },
  mounted () {
  },
  methods: {
    ...mapActions(['getAnnouncements']),
  },
}
</script>

<style lang="scss" scoped></style>
