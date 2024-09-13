<template>
  <v-container v-if="corrections" fluid class="pa-0">
    <div  v-for="(item, index) in corrections" :key="item.id" :class="'mx-8 mb-8 ' + (item.id === 1 ? 'mt-4' : '')" :id="'correction_' + item.id">
      <v-divider v-if="index !== 0" class="my-8"></v-divider>
      <v-row class="no-gutters" :class="index === 0 ? 'mt-8' : ''">
        <v-col cols="12" class="pa-4 col col-auto">
          <h2 class="mb-4 text-dive-h5 blue--text">
            <span v-if="item.correctionType === 'indicator'">{{ item.indicator['name_' + locale] }}</span>
            <span v-if="item.correctionType === 'source'">{{ item.source['name_' + locale] }}</span>
            ({{ new Date(item.dateCorrected).toLocaleDateString() }})
          </h2>
          <v-list dense>
            <v-list-item>
              <v-list-item-content>
                {{ $t('corrections_view.years') }}:
                {{ item.years.length ? item.years.join(', ') : 'All' }}
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                {{ $t('corrections_view.location_types') }}: 
                {{ item.locationTypes.length ? item.locationTypes.map(lt => lt['name_' + locale]).join(', ') : 'All' }}
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                {{ $t('corrections_view.filter_types') }}:
                {{ item.filterTypes.length ? item.filterTypes.map(ft => ft['name_' + locale]).join(', ') : 'All' }}
              </v-list-item-content>
            </v-list-item>
            <v-list-item v-if="item['note_' + locale]">
              <v-list-item-content>
                {{ $t('corrections_view.note') }}:
                {{ item['note_' + locale] }}
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<script>
import i18n from '@/i18n'
import goTo from 'vuetify/lib/services/goto'
import { mapActions, mapState } from 'vuex'
import router from '@/app/router/index'

export default {
  name: 'DataCorrectionsView',
  components: {
    
  },
  created() {
    if (!this.corrections) {
      this.getCorrections();
    }
  },
  props: {
    selectedCorrection: {
      type: Object
    }
  },
  data() {
    return {}
  },
  computed: {
    ...mapState(['corrections', 'locale']),
    breadcrumbs() {
      let crumbs = [
        {
          text: i18n.t('home_view.name'),
          disabled: false,
          href: '/home'
        },
        {
          text: i18n.t('corrections_view.name'),
          disabled: true
        }
      ];

      return crumbs;
    }
  },
  watch: {
    selectedCorrection(newValue) {
      if (newValue) {
        if (router.currentRoute.query.item) {
          this.$nextTick(() => {
            this.scrollToItem({ id: router.currentRoute.query.item })
          });
        };
      }
    }
  },
  mounted () {
    if (this.selectedCorrection && router.currentRoute.query.item) {
      this.scrollToItem({ id: router.currentRoute.query.item })
    };
  },
  methods: {
    ...mapActions(['getCorrections']),
    scrollToItem(item) {
      if (item.id === '1') {
        goTo('#page-header')
      } else {
        goTo("#correction_" + item.id)
      }
      if (router.currentRoute.query.item != item.id) {
        router.replace({
          query: {
            ...router.currentRoute.query,
            item: item.id
          },
        })
      }
    }
  },
}
</script>

<style lang="scss" scoped></style>
