<template>
  <v-app-bar app color="white" light flat height="80px" class="d-print-none">
    <v-toolbar-title class="px-4 py-2">
      <router-link :to="{ name: 'home' }"><img src="/img/bexar-data-dive-logo.svg" style="height: 56px;" alt="Bexar Data Dive Logo" /></router-link>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn-toggle :value="locale" mandatory group data-html2canvas-ignore class="secondary--text">
      <v-btn text tile plain value="en" class="header-button" @click="setLocale('en')">English</v-btn>
      <v-divider vertical class="header-button-divider"></v-divider>
      <v-btn text tile plain value="es" class="header-button" @click="setLocale('es')">Español</v-btn>
    </v-btn-toggle>
  </v-app-bar>
</template>

<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'AppHeader',
  computed: {
    ...mapState(['locale']),
  },
  methods: {
    ...mapActions(['setLocale']),
  },
}
</script>

<style lang="scss" scoped>
.v-app-bar ::v-deep .v-toolbar__content {
  padding: 0;
}
.v-app-bar.v-app-bar--fixed {
  z-index: 1001;
}
.header-button {
  font-size: 14pt !important;
  font-weight: bold;
}
.header-button-divider {
  border-width: 0 2px 0 0;
  max-height: 26px;
  margin-top: 16px;
  background-color: $color-secondary;
}
</style>
