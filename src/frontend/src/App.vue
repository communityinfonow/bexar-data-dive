<template>
  <v-app>
    <AppHeader></AppHeader>
    <NavigationDrawer></NavigationDrawer>
    <v-main>
      <router-view />
    </v-main>
    <AppFooter></AppFooter>
  </v-app>
</template>

<script>
import AppHeader from '@/components/AppHeader'
import AppFooter from '@/components/AppFooter'
import NavigationDrawer from '@/components/NavigationDrawer'

export default {
  name: 'App',
  components: { AppHeader, AppFooter, NavigationDrawer },
}
</script>

<style lang="scss" scoped>
::v-deep .v-menu__content {
  z-index: 1000 !important;
}
</style>