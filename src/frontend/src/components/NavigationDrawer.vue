<template>
  <v-navigation-drawer
    app
    expand-on-hover
    light
    style="z-index: 10000"
    :mini-variant="miniNavDrawer"
  >
    <v-list nav>
      <v-list-item-group
        v-model="activeView"
        active-class="primary--text text--accent-4"
      >
        <template v-for="(item, index) in navItems">
          <v-list-item v-if="index < 4" :key="index" :to="item.route">
            <v-list-item-icon>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>{{ item.name }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list-item-group>
    </v-list>
    <template v-slot:append>
      <v-list nav>
        <v-item-group
          v-model="activeView"
          active-class="primary--text text-accent-4"
        >
          <template v-for="(item, index) in navItems">
            <v-list-item v-if="index >= 4" :key="index" :to="item.route">
              <v-list-item-icon>
                <v-icon>{{ item.icon }}</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title>{{ item.name }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </template>
        </v-item-group>
        <v-list-item href="https://cinow.info/contact/" target="_blank">
          <v-list-item-icon><v-icon>mdi-email</v-icon></v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title
              >Connect With Us
              <sup
                ><v-icon x-small>mdi-open-in-new</v-icon></sup
              ></v-list-item-title
            >
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </template>
  </v-navigation-drawer>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'NavigationDrawer',
  data: () => ({
    miniNavDrawer: true,
    activeView: null,
  }),
  computed: {
    ...mapGetters(['tools']),
    navItems() {
      return [{ route: '/', icon: 'mdi-home', name: 'Home' }]
        .concat(this.tools)
        .concat([
          {
            route: 'about-the-tools',
            icon: 'mdi-hammer-wrench',
            name: 'About the Tools',
          },
          {
            route: 'about-the-data',
            icon: 'mdi-database',
            name: 'About the Data',
          },
        ])
    },
  },
  watch: {
    activeView() {
      this.miniNavDrawer = true
    },
  },
  methods: {},
}
</script>

<style lang="scss" scoped>
/*::v-deep .v-list .v-list-item--active {
  color: #0065a4;
}
::v-deep .theme--light.v-list-item--active:hover::before,
.theme--light.v-list-item--active::before {
  color: #0065a4;
  opacity: 0.12;
}*/
</style>
