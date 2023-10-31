<template>
  <v-container fluid>
    <section class="page-header d-flex flex-column text-center light--text pa-12">
      <div class="font-weight-medium" style="font-size: 1.25rem;">{{ $t('home_view.powered_by') }}</div>
      <h1 class="text-dive-h1">
        {{ $t('home_view.connecting_line_1') }}
        <br>
        {{ $t('home_view.connecting_line_2') }}
      </h1>
      <div class="font-weight-medium mt-2" style="font-size: 1.25rem;">{{ $t('home_view.welcome_line_1') }}</div>
      <div class="font-weight-medium" style="font-size: 1.25rem;">{{ $t('home_view.welcome_line_2') }}</div>
    </section>
    <h2 class="text-dive-h4 text-uppercase mt-16 mb-2 font-weight-black text-center">{{ $t('home_view.available_tools') }}</h2>
    <section class="d-flex" :class="{ 'flex-row': $vuetify.breakpoint.mdAndUp, 'flex-column': $vuetify.breakpoint.smAndDown }">
      <template v-for="tool in tools">
        <featured-card 
          v-if="tool.showOnHome"
          :key="tool.route" 
          :item="tool"
          :iconPath="tool.iconPath"
          :imagePath="tool.imagePath"
          :name="tool.name" 
          :description="tool.shortDescription" 
          about_route="about-tools" 
          :view_route="tool.route"
          :primary_button_text="$t('featured_card.view')"
          :secondary_button_text="$t('featured_card.learn_more')"
        >
        </featured-card>
      </template>
    </section>
    <v-row>
      <!--<v-col cols="6">
        TODO: bexar data facts
      </v-col>-->
      <v-col cols="12">

        <featured-card 
          v-if="currentAnnouncement"
          fill_width
          iconPath="/img/icon_ux_announcement.svg"
          :name="$t('announcements_view.name')" 
          :header="currentAnnouncement['title_' + locale]"
          :description="currentAnnouncement['message_' + locale].indexOf('\n') !== -1 ? currentAnnouncement['message_' + locale].split('\n')[0] : currentAnnouncement['message_' + locale]"
          :postText="new Date(currentAnnouncement.date + 'T00:00:00').toLocaleDateString()"
          :view_route="'/announcements#' + currentAnnouncement.id"
          text_color="black"
          background_color="light"
          :primary_button_text="$t('featured_card.learn_more')"
          style="margin-bottom: 0 !important;"
        >
        </featured-card>
        <template v-for="announcement in nextTwoAnnouncements">
          <div :key="announcement.id">
            <featured-card 
              fill_width
              :header="announcement['title_' + locale]"
              :postText="new Date(announcement.date + 'T00:00:00').toLocaleDateString()"
              :view_route="'/announcements#' + announcement.id"
              text_color="black"
              background_color="light"
              :primary_button_text="$t('featured_card.learn_more')"
              style="margin-top: 0 !important;"
            >
            </featured-card>
          </div>
        </template>
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
    nextTwoAnnouncements() {
      return this.announcements && this.announcements.length > 1 ? this.announcements.slice(1, 3) : null
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
