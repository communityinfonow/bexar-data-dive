<template>
  <v-container fluid class="pa-0">
    <v-row class="no-gutters flex-column">
      <v-col cols="auto" class="pa-4 col col-auto">
        <h1 class="text-h3 mb-2">{{ $t('about_tools_view.name') }}</h1>
        <section v-for="(tool, index) in tools" :key="'tool_' + index" class="mb-16">
          <h2 class="text-h4 my-4">{{ tool.name }}</h2>
          <v-row class="no-gutters">
            <v-col cols="2">
              <v-img :src="tool.imagePath" class="mr-4"></v-img>
            </v-col>
            <v-col cols="10">
              <p>{{ tool.fullDescription }}</p>
              <section v-for="(subTool, index) in tool.subTools" :key="'subtool_' + index" class="">
                <h3 class="text-h5 my-2">{{ subTool.name }}</h3>
                <p>{{ subTool.fullDescription }}</p>
              </section>
              <v-btn 
                color="primary" 
                :to="tool.route" 
                :aria-label="$t('featured_card.view') + ' ' + tool.name"
                class="mt-6"
              >
                {{ $t('featured_card.view') }}
              </v-btn>
            </v-col>
          </v-row>
        </section>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'AboutToolsView',
  computed: {
  ...mapGetters(['tools'])
  }
}
</script>

<style lang="scss" scoped></style>
