<template>
  <v-container v-if="announcements" fluid class="pa-0">
    <v-row class="no-gutters">
      <v-col cols="7" class="pa-4 col col-auto">
        <h1 class="text-h3 mb-4">{{ $t('announcements_view.name') }}</h1>
        <section v-for="item in announcements" :key="item.id" class="mb-12">
          <h2 class="mb-2 text-h5" :id="'question_' + item.id">{{ item['title_' + locale]}}</h2>
          <h3 class="mb-4 text-subtitle-1">{{ new Date(item.date + 'T00:00:00').toLocaleDateString() }}</h3>
          <section class="mb-8">
            <section v-html="item['message_' + locale]"></section>
          </section>
        </section>
      </v-col>
      <v-col cols="5">
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
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
