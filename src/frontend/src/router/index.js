import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import MyCommunityView from '@/views/MyCommunityView.vue'
import DashboardView from '@/views/DashboardView.vue'
import ExploreDataView from '@/views/ExploreDataView.vue'
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
    path: '/my-community',
    name: 'my-community',
    component: MyCommunityView,
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: DashboardView,
  },
  {
    path: '/explore-data',
    name: 'explore-data',
    component: ExploreDataView,
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
