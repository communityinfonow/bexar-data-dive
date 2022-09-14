<template>
  <v-app-bar app color="accent" dark class="d-print-none">
    <v-toolbar-title class="">
      <img class="float-left" src="/img/bdd_logo_color.png" style="height: 40px;" alt="Bexar Data Dive Logo" />
      <span class="float-left ml-4" style="line-height: 40px; font-size: 1.2em;">{{ $t('app.name') }} <sup class="text-caption font-weight-bold">BETA</sup></span>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn-toggle :value="locale" mandatory group dark data-html2canvas-ignore>
      <v-btn text tile plain value="en" class="header-button" @click="setLocale('en')">English</v-btn>
      <div style="margin-top: 4px;" title="Coming soon"><v-btn text tile plain value="es" class="header-button" @click="setLocale('es')" disabled>Español</v-btn></div>
    </v-btn-toggle>
    <span><img src="/img/cinow_logo.png" style="height: 30px;" alt="CI:Now Logo" /></span>
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
.v-app-bar.v-app-bar--fixed {
  z-index: 1001;
}
.header-button {
  font-size: 14pt !important;
  font-weight: bold;
}
</style>
