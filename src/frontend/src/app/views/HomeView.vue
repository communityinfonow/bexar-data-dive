<template>
  <v-container fluid>
    <section class="d-flex">
      <img class="ma-4" src="/img/bdd_logo_color.png" style="height: 100px;" alt="Bexar Data Dive Logo" />
      <h1 class="text-h3 my-4 logo-header">
        {{ $t('app.name_line_1') }}<br>
        {{ $t('app.name_line_2') }}
      </h1>
    </section>
    <!--<p class="text-subtitle-1 mb-4">{{ $t('app.tagline') }}</p>-->
    <p class="welcome-message font-weight-light my-8 mx-4">{{ $t('home_view.welcome_message') }}</p>
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
import { mapGetters } from 'vuex'
import FeaturedCard from '@/app/components/FeaturedCard'
export default {
  name: 'HomeView',
  components: {
    FeaturedCard,
  },
  computed: { ...mapGetters(['tools']) },
  data() {
    return {}
  },
}
</script>

<style lang="scss" scoped>
  .logo-header {
    color: $color-primary;
  }
  .welcome-message {
    width: 820px;
    font-size: 1.2em;
  }
</style>