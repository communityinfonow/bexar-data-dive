<template>
  <v-navigation-drawer
    app
    expand-on-hover
    light
    style="z-index: 10000"
    :mini-variant="miniNavDrawer"
    class="d-print-none"
    width="320"
    permanent
  >
    <v-list nav>
      <v-list-item-group
        v-model="activeView"
        active-class="red--text"
        :aria-label="$t('tools.tools')"
      >
        <template v-for="(item, index) in navItems">
          <v-list-item v-if="index < 5" :key="index" :to="item.route" role="option">
            <v-list-item-icon>
              <v-icon v-if="item.icon">{{ item.icon }}</v-icon>
              <v-img :src="$route.name === item.key ? item.iconPath : item.iconPathInactive" v-if="item.iconPath" height="24" width="24"></v-img>
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
        <v-list-item-group
          v-model="activeView"
          active-class="red--text"
        >
          <template v-for="(item, index) in navItems">
            <v-list-item v-if="index >= 5" :key="index" :to="item.route">
              <v-list-item-icon>
                <v-icon v-if="item.icon" :style="{ transform: item.icon_transform || '' }">{{ item.icon }}</v-icon>
              <v-img :src="$route.name === item.key ? item.iconPath : item.iconPathInactive" v-if="item.iconPath" height="24" width="24"></v-img>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title>{{ item.name }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </template>
        </v-list-item-group>
        <v-list-item href="https://cinow.info/contact/#form" target="_blank">
          <v-list-item-icon><v-icon>mdi-bug</v-icon></v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title
              >{{ $t('app.report_bug') }}
              <sup
                ><v-icon x-small>mdi-open-in-new</v-icon></sup
              ></v-list-item-title
            >
          </v-list-item-content>
        </v-list-item>
        <v-list-item href="https://cinow.info/contact/#form" target="_blank">
          <v-list-item-icon><v-icon>mdi-email</v-icon></v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title
              >{{ $t('app.connect') }}
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
import i18n from '@/i18n'

export default {
  name: 'NavigationDrawer',
  data: () => ({
    miniNavDrawer: true,
    activeView: null,
  }),
  computed: {
    ...mapGetters(['tools', 'about_views']),
    navItems() {
      return [{ route: '/home', icon: 'mdi-home', name: i18n.t('home_view.name') }]
        .concat(this.tools)
        .concat(this.about_views)
    }
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
