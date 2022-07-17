<template>
  <v-container v-if="indicatorMenu" fluid class="pa-0">
    <v-row class="no-gutters flex-column">
      <v-col cols="auto" class="pa-4 col col-auto">
        <h1 class="text-h3 mb-2">{{ $t('about_data_view.name') }}</h1>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'AboutDataView',
  data() {
    return {}
  },
  computed: {
  ...mapState(['indicatorMenu', 'locale'])
  }
}
</script>

<style lang="scss" scoped></style>
