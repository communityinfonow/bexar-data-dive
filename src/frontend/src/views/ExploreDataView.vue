<template>
  <v-container fluid class="pa-0 fill-height">
    <v-row class="no-gutters flex-column fill-height">
      <v-col cols="auto" class="shrink">
        <MenuToolbar
          class="flex-column"
          :menu="indicatorMenu"
          :selectItem="selectItem"
          :flattenSingleItems="false"
        ></MenuToolbar>
      </v-col>
      <v-col cols="auto" class="pa-4 shrink">
        <h1 class="text-h3 mb-2">{{ $t('tools.explore_data.name') }}</h1>
        <p>{{ $t('tools.explore_data.long_description') }}</p>
        <p>{{ $t('tools.explore_data.get_started') }}</p>
        <h2 class="text-h5 my-8">
          {{ $t('tools.common.featured_indicators') }}
        </h2>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
import MenuToolbar from '@/components/MenuToolbar'
export default {
  name: 'ExploreDataView',
  components: {
    MenuToolbar,
  },
  data() {
    return {}
  },
  computed: {
    ...mapState(['indicatorMenu']),
  },
  methods: {
    selectItem(item) {
      console.log(item)
    },
  },
}
</script>

<style lang="scss" scoped></style>
