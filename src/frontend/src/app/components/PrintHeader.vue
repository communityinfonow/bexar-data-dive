<template>
  <div class="d-flex print-header">
    <div>
      <img class="float-left" src="/img/logo__active.svg" style="height: 40px;" alt="Bexar Data Dive Logo" />
      <span class="float-left ml-4" style="line-height: 40px; font-size: 1.2em;">{{ $t('app.name') }}</span>
    </div>
    <v-spacer></v-spacer>
    <span><img src="/img/cinow_logo.png" style="height: 30px;" alt="CINow Logo" /></span>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'PrintHeader',
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
