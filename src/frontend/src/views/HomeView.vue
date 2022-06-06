<template>
  <v-container fluid>
    <h1 class="text-h3 mb-2">{{ $t('app.name') }}</h1>
    <p class="text-subtitle-1 mb-4">{{ $t('app.tagline') }}</p>
    <p>{{ $t('home_view.welcome_message') }}</p>
    <h2 class="text-h5 mb-2">{{ $t('home_view.available_tools') }}</h2>
    <section class="d-flex flex-row">
      <v-card
        v-for="tool in tools"
        :key="tool.route"
        max-width="30%"
        class="d-flex flex-column flex-grow-1 ma-4"
      >
        <v-img src="@/assets/todo.jpg" class="white--text align-end">
          <v-card-title>{{ tool.name }}</v-card-title>
        </v-img>
        <v-card-text>{{ tool.shortDescription }}</v-card-text>
        <v-spacer></v-spacer>
        <v-card-actions>
          <v-btn plain text link color="primary" :to="{ name: 'about-the-tools' }">{{
            $t('home_view.learn_more')
          }}</v-btn>
          <v-spacer></v-spacer>
          <v-btn color="primary" :to="{ name: tool.route }" :aria-label="$t('home_view.view') + ' ' + tool.name">{{
            $t('home_view.view')
          }}</v-btn>
        </v-card-actions>
      </v-card>
    </section>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'HomeView',
  computed: { ...mapGetters(['tools']) },
  data() {
    return {}
  },
}
</script>

<style lang="scss" scoped></style>
