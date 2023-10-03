<template>
  <v-container fluid>
    <section class="page-header d-flex flex-column text-center light--text pa-12">
      <div class="font-weight-medium" style="font-size: 1.125em;">{{ $t('home_view.powered_by') }}</div>
      <div class="font-weight-bold" style="font-size: 3em;">{{ $t('home_view.connecting_line_1') }}</div>
      <div class="font-weight-bold" style="font-size: 3em;">{{ $t('home_view.connecting_line_2') }}</div>
      <div class="font-weight-medium mt-2" style="font-size: 1.125em;">{{ $t('home_view.welcome_line_1') }}</div>
      <div class="font-weight-medium" style="font-size: 1.125em;">{{ $t('home_view.welcome_line_2') }}</div>
    </section>
    <h2 class="font-weight-bold mt-12 mb-12 text-center" style="font-size: 2em; text-transform: uppercase;">{{ $t('home_view.available_tools') }}</h2>
    <section class="d-flex" :class="{ 'flex-row': $vuetify.breakpoint.mdAndUp, 'flex-column': $vuetify.breakpoint.smAndDown }">
      <template v-for="tool in tools">
        <featured-card 
          v-if="tool.showOnHome"
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
    <v-row>
      <v-col cols="6">
        TODO: bexar data facts
      </v-col>
      <v-col cols="6">
        <v-card v-if="currentAnnouncement" flat>
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
