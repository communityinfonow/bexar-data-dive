<template>
  <v-app-bar app color="primary" dark>
    <v-toolbar-title>
      {{ $t('app.name') }} <sup class="text-caption">ALPHA</sup>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn-toggle :value="locale" mandatory group dark>
      <v-btn text tile plain value="en" @click="setLocale('en')">English</v-btn>
      <v-btn text tile plain value="es" @click="setLocale('es')">Español</v-btn>
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
.v-app-bar.v-app-bar--fixed {
  z-index: 1001;
}
</style>
