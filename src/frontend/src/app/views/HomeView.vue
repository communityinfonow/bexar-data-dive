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
        <v-card
          v-if="bexarDataFacts && locationMenu"
          flat
          width="100%"
          class="mt-5 mx-4 featured-card"
        >
          <v-card-title class="flex-nowrap featured-card-title did-you-know black--text py-12" style="padding-left: 16.667%">
            <img src="/img/logo__active.svg" class="mr-4" height="48" width="48">
            <div>{{ $t('home_view.did_you_know') }}</div>
          </v-card-title>
          <v-card-text class="featured-card-text black--text">
            <v-row>
              <v-col cols="12" style="padding-left: 16.667%">
                <div class="text-h4 font-weight-bold">
                  <span v-if="bexarDataFactsCategoryName">{{ bexarDataFactsCategoryName }} -</span>
                  {{ bexarDataFacts[bexarDataFactsIndex].indicator['name_' + locale] }}
                  <div class="mt-2">
                    <v-chip color="secondary" label v-if="bexarDataFacts[bexarDataFactsIndex].indicator['name_' + locale]" large>
                      <span v-if="bexarDataFacts[bexarDataFactsIndex].demographicData.find(d => d.demographicFilter.id === null).suppressed" class="text-h4 mb-0">{{ $t('data.suppressed') }}</span>
                      <span v-else-if="bexarDataFacts[bexarDataFactsIndex].demographicData.find(d => d.demographicFilter.id === null).value === null" class="text-h4 mb-0">{{ $t('data.no_data') }}</span>
                      <template v-else>
                        <div class="text-h4 font-weight-bold mb-0">{{ formatValue(bexarDataFacts[bexarDataFactsIndex].indicatorType.id, bexarDataFacts[bexarDataFactsIndex].demographicData.find(d => d.demographicFilter.id === null).value) }}</div>
                        <div v-if="bexarDataFacts[bexarDataFactsIndex].demographicData.find(d => d.demographicFilter.id === null).moeLow && bexarDataFacts[bexarDataFactsIndex].demographicData.find(d => d.demographicFilter.id === null).moeHigh" class="ml-2">{{ $t('data.moe_range') }} {{ formatValue(bexarDataFacts[bexarDataFactsIndex].indicatorType.id, bexarDataFacts[bexarDataFactsIndex].demographicData.find(d => d.demographicFilter.id === null).moeLow) }} - {{ formatValue(bexarDataFacts[bexarDataFactsIndex].indicatorType.id, bexarDataFacts[bexarDataFactsIndex].demographicData.find(d => d.demographicFilter.id === null).moeHigh) }}</div> 
                      </template>
                    </v-chip>
                  </div>
                </div>
                <p v-if="bexarDataFacts[0].filterType" class="text-subtitle-1 mt-8">{{ $t('tools.community.compare_by')}}:</p>
                <div v-for="fact in bexarDataFacts" :key="fact.filterType ? fact.filterType.id : 0">
                  <v-btn color="red" dark rounded :class="'font-weight-bold ' + (fact.filterType ? 'my-2' : 'mt-8 mb-2')"
                    :to="{
                      name: 'explore',
                      query: fact.filterType ? {
                        language: locale,
                        indicator: fact.indicator.id,
                        tab: 'compare',
                        compareBy: fact.filterType.id,
                        compareWith: fact.demographicData.filter(d => d.demographicFilter.id !== null).map(d => d.demographicFilter.id),
                        locationTypeId: 1,
                        location: 48029,
                        year: fact.year
                      } : {
                        language: locale,
                        indicator: fact.indicator.id,
                        tab: 'compare',
                        locationTypeId: 1,
                        location: 48029,
                        year: fact.year
                      }
                    }"
                  >
                    <span v-if="fact.filterType">{{ fact.filterType.name_en }}</span>
                    <span v-else>{{ $t('featured_card.view') }}</span>
                  </v-btn>
                </div>
              </v-col>
            </v-row>
          </v-card-text>
          <v-card-actions class="featured-card-actions pa-8">
            <div class="text-caption">
              <div>{{ $t('tools.common.download.headers.location') }}: {{ locationMenu.categories.find(c => c.id == '1').items.find(i => i.id == '48029')['name_'  + locale] }}</div>
              <div>{{ $t('tools.common.download.headers.source') }}: {{ bexarDataFacts[bexarDataFactsIndex].source['name_' + locale] }}, {{ bexarDataFacts[bexarDataFactsIndex].year }}</div>
            </div>
            <v-spacer></v-spacer>
            <v-btn 
              outlined
              color="black"
              class="font-weight-bold" 
              style="border-width: 2px;"
              rounded
              :to="bexarDataFactsLink"
              :aria-label="$t('about_data_view.name')"
            >
              {{ $t('about_data_view.name') }}
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
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
          title_class="announcements"
          background_color="light"
          :primary_button_text="$t('featured_card.learn_more')"
          style="margin-bottom: 0 !important;"
          :inlineActions="true"
        >
        </featured-card>
        <div v-for="announcement in nextTwoAnnouncements" :key="announcement.id" class="d-flex pl-8 pr-4 mb-4">
          <div>
            <p class="text-h6 font-weight-bold mb-0">{{ announcement['title_' + locale] }}</p>
            <p class="text-subtitle-2">{{ new Date(announcement.date + 'T00:00:00').toLocaleDateString() }}</p>
          </div>
          <v-spacer></v-spacer>
          <v-btn 
            color="red"
            class="white--text font-weight-bold px-2"
            style="letter-spacing: 1.25px" 
            rounded
            :to="'/announcements#' + announcement.id"
            :aria-label="$t('featured_card.view')"
          >
            {{ $t('featured_card.learn_more') }}
          </v-btn>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import FeaturedCard from '@/app/components/FeaturedCard'
import { format } from '@/services/formatter'

export default {
  name: 'HomeView',
  components: {
    FeaturedCard
  },
  created() {
    if (!this.announcements) {
      this.getAnnouncements();
    }
  },
  computed: { 
    ...mapState(['locale', 'announcements', 'bexarDataFacts', 'locationMenu', 'indicatorMenu']), 
    ...mapGetters(['tools', 'about_views']),
    bexarDataFactsCategoryName() {
      let category = this.indicatorMenu?.categories.map(c => c.subcategories).flat().find(sc => sc.id == this.bexarDataFacts[this.bexarDataFactsIndex].indicator.categoryId);
      return category ? category['name_' + this.locale] : null;
    },
    bexarDataFactsLink() {
      return this.bexarDataFacts && this.bexarDataFacts.length ? '/about-data?indicator=' + this.bexarDataFacts[this.bexarDataFactsIndex].indicator.id : null
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
      bexarDataFactsIndex: 0
    }
  },
  methods: {
    ...mapActions(['getAnnouncements']),
    formatValue(type, value) {
			return format(type, value)
		}
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
