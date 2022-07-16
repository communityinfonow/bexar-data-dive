import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import CommunityView from '@/views/CommunityView.vue'
import ExploreView from '@/views/ExploreView.vue'
import TablesView from '@/views/TablesView.vue'
import AboutTheToolsView from '@/views/AboutTheToolsView.vue'
import AboutTheDataView from '@/views/AboutTheDataView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/community',
    name: 'community',
    component: CommunityView,
  },
  {
    path: '/explore',
    name: 'explore',
    component: ExploreView,
  },
  {
    path: '/tables',
    name: 'tables',
    component: TablesView,
  },
  {
    path: '/about-the-tools',
    name: 'about-the-tools',
    component: AboutTheToolsView,
  },
  {
    path: '/about-the-data',
    name: 'about-the-data',
    component: AboutTheDataView,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

router.beforeEach((to, from, next) => {
  if (!to.query.lang && from.query.lang) {
    next({ name: to.name, query: { ...to.query, lang: from.query.lang } })
  } else {
    next()
  }
})

export default router
