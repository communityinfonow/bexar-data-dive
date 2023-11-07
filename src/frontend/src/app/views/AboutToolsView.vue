<template>
  <v-container fluid class="pa-0">
    <v-row class="no-gutters flex-column">
      <v-col cols="auto" class="pa-12 col col-auto">
        <section v-for="(tool, index) in tools" :key="'tool_' + index" class="mb-16">
          <v-row class="no-gutters">
            <v-col cols="4">
              <v-img :src="tool.imagePath" class="mr-12"></v-img>
            </v-col>
            <v-col cols="8">
              <h2 class="d-flex blue--text text-dive-h5" style="text-transform: uppercase;">
                <img :src="tool.iconPath" height="48px" width="48px">
                <span class="mx-4 my-2">{{ tool.name }}</span>
              </h2>
              <p class="blue--text font-weight-bold mt-2 mb-4">{{ tool.headline }}</p>
              <p>{{ tool.fullDescription }}</p>
              <section v-for="(subTool, index) in tool.subTools" :key="'subtool_' + index" class="">
                <h3 class="text-dive-h6 blue--text text-uppercase font-weight-bold mt-2">{{ subTool.name }}</h3>
                <p>{{ subTool.fullDescription }}</p>
              </section>
              <section class="text-right">
                <v-btn 
                  dark
                  rounded
                  color="red" 
                  :to="tool.route" 
                  :aria-label="$t('featured_card.view') + ' ' + tool.name"
                  class="mt-6"
                >
                  {{ $t('featured_card.view') }}
                </v-btn>
              </section>
            </v-col>
          </v-row>
          <v-divider class="my-6"></v-divider>
        </section>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import i18n from '@/i18n'
import { mapGetters } from 'vuex'

export default {
  name: 'AboutToolsView',
  computed: {
  ...mapGetters(['tools']),
  breadcrumbs() {
      let crumbs = [
        {
          text: i18n.t('home_view.name'),
          disabled: false,
          href: '/home'
        },
        {
          text: i18n.t('about_tools_view.name'),
          disabled: true
        }
      ];

      return crumbs;
    }
  }
}
</script>

<style lang="scss" scoped></style>
