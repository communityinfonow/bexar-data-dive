<template>
  <v-app-bar app color="green" dark class="d-print-none" clipped-left flat>
    <v-toolbar-title class="">
      <img src="/img/bexar-data-dive-logo.svg" :style="{ 'float': 'left', 'height': '30px' }" alt="Bexar Data Dive Logo" />
      <span class="float-left ml-4" style="line-height: 40px; font-size: 1.2em;">{{ $t('app.name') }} <sup class="text-caption font-weight-bold">ADMIN</sup></span>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <span><img src="/img/cinow_logo.png" style="height: 30px;" alt="CINow Logo" /></span>
  </v-app-bar>
</template>

<script>
export default {
  name: 'AppHeader'
}
</script>

<style lang="scss" scoped>
.v-app-bar.v-app-bar--fixed {
  z-index: 1001;
}
</style>
