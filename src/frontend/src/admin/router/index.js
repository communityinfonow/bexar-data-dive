import Vue from 'vue'
import VueRouter from 'vue-router'
import AdminView from '@/admin/views/AdminView.vue'
import AnnouncementsView from '@/admin/views/AnnouncementsView.vue'
import IndicatorsView from '@/admin/views/IndicatorsView.vue'
import FiltersView from '@/admin/views/FiltersView.vue'
import SourcesView from '@/admin/views/SourcesView.vue'
import AboutToolsView from '@/admin/views/AboutToolsView.vue'
import AboutDataView from '@/admin/views/AboutDataView.vue'
import FaqsView from '@/admin/views/FaqsView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/admin'
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminView,
  },
  {
    path: '/announcements',
    name: 'announcements',
    component: AnnouncementsView,
  },
  {
    path: '/indicators',
    name: 'indicators',
    component: IndicatorsView,
  },
  {
    path: '/filters',
    name: 'filters',
    component: FiltersView,
  },
  {
    path: '/sources',
    name: 'sources',
    component: SourcesView,
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
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

export default router
