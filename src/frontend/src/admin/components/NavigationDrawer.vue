<template>
  <v-navigation-drawer
    app
    light
    style="z-index: 10000"
    class="d-print-none"
    width="320"
    permanent
    clipped
  >
    <v-list nav>
      <v-list-item-group
        v-model="activeView"
        active-class="primary--text text--accent-4"
        :aria-label="$t('tools.tools')"
      >
        <template v-for="(item, index) in navItems">
          <v-list-item :key="index" :to="item.route" role="option">
            <v-list-item-content>
              <v-list-item-title>{{ item.name }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list-item-group>
    </v-list>
    <template v-slot:append>
      <v-list nav>
        <v-list-item href="/home" target="_blank">
          <v-list-item-content>
            <v-list-item-title
              >Bexar Data Dive
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
    activeView: null,
  }),
  computed: {
    ...mapGetters(['tools']),
    navItems() {
      return [{ route: '/admin/home', name: 'Admin Home' }]
        .concat(this.tools)
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
</style>
