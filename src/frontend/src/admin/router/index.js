import Vue from 'vue'
import VueRouter from 'vue-router'
import AdminView from '@/admin/views/AdminView.vue'
import AnnouncementsView from '@/admin/views/AnnouncementsView.vue'
import DataCorrectionsView from '@/admin/views/DataCorrectionsView.vue'
import IndicatorsView from '@/admin/views/IndicatorsView.vue'
import FiltersView from '@/admin/views/FiltersView.vue'
import SourcesView from '@/admin/views/SourcesView.vue'
import FaqsView from '@/admin/views/FaqsView.vue'
import AuditLogView from '@/admin/views/AuditLogView.vue'
import MissedTranslationsView from '@/admin/views/MissedTranslationsView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/admin',
    redirect: '/admin/home'
  },
  {
    path: '/admin/home',
    name: 'admin',
    component: AdminView,
  },
  {
    path: '/admin/announcements',
    name: 'announcements',
    component: AnnouncementsView,
  },
  {
    path: '/admin/data-corrections',
    name: 'data-corrections',
    component: DataCorrectionsView,
  },
  {
    path: '/admin/indicators',
    name: 'indicators',
    component: IndicatorsView,
  },
  {
    path: '/admin/filters',
    name: 'filters',
    component: FiltersView,
  },
  {
    path: '/admin/sources',
    name: 'sources',
    component: SourcesView,
  },
  {
    path: '/admin/faqs',
    name: 'faqs',
    component: FaqsView,
  },
  {
    path: '/admin/audit-log',
    name: 'audit-log',
    component: AuditLogView,
  },
  {
    path: '/admin/missed-translations',
    name: 'missed-translations',
    component: MissedTranslationsView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

export default router
