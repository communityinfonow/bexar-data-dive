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
    <h2 class="text-dive-h4 text-uppercase mt-16 mb-2 font-weight-light text-center">{{ $t('home_view.available_tools') }}</h2>
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
      <v-col cols="12" lg="6">
        <!-- TODO: translations -->
        <v-card
          v-if="featuredIndicator && locationMenu"
          flat
          width="100%"
          class="d-flex flex-column flex-grow-1 ma-4 featured-card fill-height"
          :to="'about-data?indicator=' + featuredIndicator.id"
        >
          <v-card-title class="flex-nowrap featured-card-title black--text py-12" style="padding-left: 16.667%">
            <img src="/img/logo__active.svg" class="mr-4" height="48" width="48">
            <div>Bexar Data Facts</div>
          </v-card-title>
          <v-card-text class="featured-card-text black--text fill-height">
            <v-row>
                <v-col cols="4" class="text-right">
                  <div class="text-h3 font-weight-bold">
                  {{ featuredIndicatorValue }}
                  </div>
                  <div v-if="featuredIndicatorRange">
                    {{ $t('tools.common.download.headers.range')}}: {{ featuredIndicatorRange }}
                  </div>
                </v-col>
                <v-col cols="8">
                  <div class="text-h6 font-weight-bold">{{ featuredIndicator['name_' + locale] }}</div>
                  <div>{{ $t('tools.common.download.headers.location') }}: {{ locationMenu.categories.find(c => c.id == '1').items.find(i => i.id == '48029')['name_'  + locale] }}</div>
                  <div>{{ $t('tools.common.download.headers.source') }}: {{ featuredIndicator.source['name_' + locale] }}, {{ featuredIndicator.year }}</div>
                </v-col>
            </v-row>
          </v-card-text>
          <v-card-actions class="featured-card-actions pa-8">
            <v-spacer></v-spacer>
            <v-btn 
              outlined
              color="black"
              class="font-weight-bold" 
              style="border-width: 2px;"
              rounded
              :to="'about-data?indicator=' + featuredIndicator.id"
              :aria-label="$t('about_data_view.name')"
            >
              {{ $t('about_data_view.name') }}
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
      <!-- TODO: just show one announcements when bexar data facts goes live? -->
      <v-col cols="12" lg="6" class="pt-12">
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
import { format } from '@/services/formatter'
import i18n from '@/i18n'

export default {
  name: 'HomeView',
  components: {
    FeaturedCard
  },
  created() {
    if (!this.announcements) {
      this.getAnnouncements();
    }
    if (!this.featuredIndicators) {
      this.getFeaturedIndicators();
    }
  },
  computed: { 
    ...mapState(['locale', 'announcements', 'featuredIndicators', 'locationMenu']), 
    ...mapGetters(['tools', 'about_views']),
    featuredIndicator() {
      return this.featuredIndicators ? this.featuredIndicators[this.featuredIndicatorIndex] : null
    },
    featuredIndicatorValue() {
      if (!this.featuredIndicator) {
        return null
      }

      if (this.featuredIndicator.suppressed) {
        return i18n.t('data.suppressed');
      } else if (this.featuredIndicator.value === null) {
        return i18n.t('data.no_data');
      }
      return format(this.featuredIndicator.typeId, this.featuredIndicator.value)
    },
    featuredIndicatorRange() {
      if (!this.featuredIndicator || !this.featuredIndicator.moeLow) {
        return null
      }

      return format(this.featuredIndicator.typeId, this.featuredIndicator.moeLow) + ' - ' + format(this.featuredIndicator.typeId, this.featuredIndicator.moeHigh)
    },
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
    return {
      featuredIndicatorIndex: 0
    }
  },
  methods: {
    ...mapActions(['getAnnouncements', 'getFeaturedIndicators'])
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
