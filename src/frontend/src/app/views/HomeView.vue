<template>
  <v-container fluid>
    <v-row>
      <v-col cols="7">
      <section class="d-flex">
        <img class="ma-4" src="/img/bdd_logo_color.png" style="height: 100px;" alt="Bexar Data Dive Logo" />
        <h1 class="text-h3 my-4 logo-header">
          {{ $t('app.name_line_1') }}<br>
          {{ $t('app.name_line_2') }}
        </h1>
      </section>
      <p class="welcome-message font-weight-light my-8 mx-4">{{ $t('home_view.welcome_message') }}</p>  
      </v-col>
      <v-col cols="5">
        <v-card v-if="currentAnnouncement" flat class="mr-12 float-right">
          <v-card-text>
            <div class="text-subtitle-1"><v-icon :style="{ transform: announcementView.icon_transform || '' }">{{ announcementView.icon }}</v-icon> {{ $t('announcements_view.name') }}</div>
            <p class="text-h5 text--primary mb-0">{{ currentAnnouncement['title_' + locale] }}</p>
            <p>{{ new Date(currentAnnouncement.date + 'T00:00:00').toLocaleDateString() }}</p>
            <div class="announcement-message text--primary" v-html="currentAnnouncement['message_' + locale]"></div>
          </v-card-text>
          <v-card-actions>
            <v-btn text color="primary" to="/announcements">{{ $t('featured_card.learn_more') }}</v-btn>
        </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <h2 class="text-h5 mt-8 mb-2 mx-4">{{ $t('home_view.available_tools') }}</h2>
    <section class="d-flex" :class="{ 'flex-row': $vuetify.breakpoint.mdAndUp, 'flex-column': $vuetify.breakpoint.smAndDown }">
      <template v-for="tool in tools">
        <featured-card 
          :key="tool.route" 
          :item="tool"
          :imagePath="tool.imagePath"
          :name="tool.name" 
          :description="tool.shortDescription" 
          about_route="about-tools" 
          :view_route="tool.route"
        >
        </featured-card>
      </template>
    </section>
    <p class="my-4 mx-4 font-weight-light font-italic" v-html="$t('home_view.acknowledgement')"></p>
  </v-container>
</template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import FeaturedCard from '@/app/components/FeaturedCard'
export default {
  name: 'HomeView',
  components: {
    FeaturedCard,
  },
  created() {
    if (!this.announcements) {
      this.getAnnouncements();
    }
  },
  computed: { 
    ...mapState(['locale', 'announcements']), 
    ...mapGetters(['tools', 'about_views']),
    currentAnnouncement() {
      return this.announcements ? this.announcements[0] : null
    },
    announcementView() {
      return this.about_views?.find(ab => ab.route === 'announcements')
    }
  },
  data() {
    return {}
  },
  methods: {
    ...mapActions(['getAnnouncements'])
  },
}
</script>

<style lang="scss" scoped>
  .logo-header {
    color: $color-primary;
  }
  .welcome-message {
    max-width: 820px;
    font-size: 1.2em;
  }
  .announcement-message {
    overflow: hidden;
		display: -webkit-box;
		-webkit-line-clamp: 3;
		-webkit-box-orient: vertical;
  }
</style>
