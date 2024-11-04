import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/app/views/HomeView.vue'
import CommunityView from '@/app/views/CommunityView.vue'
import ExploreView from '@/app/views/ExploreView.vue'
import TablesView from '@/app/views/TablesView.vue'
import CustomLocationsView from '@/app/views/CustomLocationsView.vue'
import AboutToolsView from '@/app/views/AboutToolsView.vue'
import AboutDataView from '@/app/views/AboutDataView.vue'
import FaqsView from '@/app/views/FaqsView.vue'
import DataCorrectionsView from '@/app/views/DataCorrectionsView.vue'
import AnnouncementsView from '@/app/views/AnnouncementsView.vue'
import UnauthorizedView from '@/app/views/UnauthorizedView.vue'
import HelpView from '@/app/views/HelpView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/community',
    name: 'community',
    component: CommunityView
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
    path: '/custom-locations',
    name: 'custom-locations',
    component: CustomLocationsView
  },
  { 
    path: '/help', 
    name: 'help', 
    component: HelpView
  },
  {
    path: '/about-tools',
    name: 'about-tools',
    component: AboutToolsView,
  },
  {
    path: '/about-data',
    name: 'about-data',
    component: AboutDataView,
  },
  {
    path: '/faqs',
    name: 'faqs',
    component: FaqsView,
  },
  {
    path: '/data-corrections',
    name: 'data-corrections',
    component: DataCorrectionsView,
  },
  {
    path: '/announcements',
    name: 'announcements',
    component: AnnouncementsView,
  },
  {
    path: '/unauthorized',
    name: 'unauthorized',
    component: UnauthorizedView
  }
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
