<template>
  <v-app>
    <AppHeader></AppHeader>
    <NavigationDrawer></NavigationDrawer>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import AppHeader from '@/components/AppHeader'
import NavigationDrawer from '@/components/NavigationDrawer'

export default {
  name: 'App',
  components: { AppHeader, NavigationDrawer },

  data: () => ({
    //
  }),
}
</script>
