<template>
  <v-list dense>
    <v-list-item class="px-0"><v-btn small text color="primary" @click="applyFilter">Apply Filter</v-btn></v-list-item>
    <v-list-item class="px-0"><v-btn small text color="primary" @click="viewReport">View Report</v-btn></v-list-item>
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
	}
  },
  data: () => ({
  }),
  methods: {
  }
}
</script>

<style>

</style>