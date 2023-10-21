<template>
  <v-list dense>
    <v-list-item class="px-0"><v-btn small text color="red" @click="applyFilter">Apply Filter</v-btn></v-list-item>
    <v-list-item class="px-0" v-if="selectedLocationType.id !== '7'"><v-btn small text color="red" @click="viewReport">View Report</v-btn></v-list-item>
  </v-list>
</template>

<script>
export default {
  name: 'ExploreMapPopup',
  props: {
    feature: {
      type: Object,
      required: true
    },
    applyFilter: {
      type: Function,
      required: true
    },
    viewReport: {
      type: Function,
      required: true
    },
    selectedLocationType: {
      type: Object,
      required: true
    },
  },
  data: () => ({
  }),
  methods: {
  }
}
</script>

<style>

</style>