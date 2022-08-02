<template>
  <v-container fluid>
    <section class="d-flex">
      [BDD logo]
      <h1 class="text-h3 mb-2 ml-2 logo-header">
        {{ $t('app.name_line_1') }}<br>
        {{ $t('app.name_line_2') }}
      </h1>
    </section>
    <p class="text-subtitle-1 mb-4">{{ $t('app.tagline') }}</p>
    <p>{{ $t('home_view.welcome_message') }}</p>
    <h2 class="text-h5 mt-8 mb-2">{{ $t('home_view.available_tools') }}</h2>
    <section class="d-flex flex-row">
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
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import FeaturedCard from '@/components/FeaturedCard'
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
</style>
