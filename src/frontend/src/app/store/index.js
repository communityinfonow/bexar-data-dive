import Vue from 'vue'
import Vuex from 'vuex'
import router from '@/app/router/index'
import i18n from '@/i18n'
import axios from 'axios'
import { format } from '@/formatter/formatter'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loading: false,
    locale: '',
    communityRoute: 'community',
    exploreRoute: 'explore',
    tablesRoute: 'tables',
    customLocationsRoute: 'custom-locations',
    locationMenu: null,
    indicatorMenu: null,
    featuredIndicators: null,
    community: null,
    indicator: null,
    source: null,
    filters: null,
    dockedTooltip: null,
    showMapLabels: false,
    showTrendLabels: true,
    showCompareLabels: true,
    exploreData: null,
    exploreTab: null,
    filterSelections: null,
    compareSelections: null,
    tablesData: null,
    aboutData: null,
    faqs: null,
    announcements: null,
    surveySubmitted: false,
    customLocations: []
  },
  getters: {
    tools: (state) => {
      return [
        {
          key: 'community',
          name: i18n.t('tools.community.name'),
          imagePath: "/img/tools_community_" + i18n.locale + ".png",
          route: state.communityRoute,
          icon: 'mdi-map',
          shortDescription: i18n.t('tools.community.short_description'),
          fullDescription: i18n.t('tools.community.long_description'),
          showOnHome: true
        },
        {
          key: 'explore',
          name: i18n.t('tools.explore.name'),
          imagePath: "/img/tools_explore_" + i18n.locale + ".png",
          route: state.exploreRoute,
          icon: 'mdi-view-dashboard',
          shortDescription: i18n.t('tools.explore.short_description'),
          fullDescription: i18n.t('tools.explore.long_description'),
          showOnHome: true,
          subTools: [
            {
              name: i18n.t('tools.explore.tabs.map.name'),
              fullDescription: i18n.t('tools.explore.tabs.map.description')
            },
            {
              name: i18n.t('tools.explore.tabs.trend.name'),
              fullDescription: i18n.t('tools.explore.tabs.trend.description')
            },
            {
              name: i18n.t('tools.explore.tabs.compare.name'),
              fullDescription: i18n.t('tools.explore.tabs.compare.description')
            }
          ]
        },
        {
          key: 'tables',
          name: i18n.t('tools.tables.name'),
          imagePath: "/img/tools_tables_" + i18n.locale + ".png",
          route: state.tablesRoute,
          icon: 'mdi-grid',
          shortDescription: i18n.t('tools.tables.short_description'),
          fullDescription: i18n.t('tools.tables.long_description'),
          showOnHome: true
        },
        {
          key: 'custom-locations',
          name: i18n.t('tools.custom_locations.name'),
          imagePath: "/img/custom_locations_tables_" + i18n.locale + ".png",
          route: state.customLocationsRoute,
          icon: 'mdi-map-plus',
          shortDescription: i18n.t('tools.custom_locations.short_description'),
          fullDescription: i18n.t('tools.custom_locations.long_description'),
          showOnHome: false
        }
      ]
    },
    about_views: () => {
      return [
        {
          name: i18n.t('about_tools_view.name'),
          route: 'about-tools',
          icon: 'mdi-information'
        },
        {
          name: i18n.t('about_data_view.name'),
          route: 'about-data',
          icon: 'mdi-database'
        },
        {
          name: i18n.t('announcements_view.name'),
          route: 'announcements',
          icon: 'mdi-bullhorn',
          icon_transform: 'rotate(-45deg)'
        },
        {
          name: i18n.t('faqs_view.name'),
          route: 'faqs',
          icon: 'mdi-help-circle' 
        }
      ]
    },
    locationMenu: (state) => {
      let menu = JSON.parse(JSON.stringify(state.locationMenu))
      if (menu) {
        menu.categories.find(c => c.id === '7').items = state.customLocations.map((location) => {
          return {
            categoryId: '7',
            description_en: null,
            description_es: null,
            hasData: true,
            id: location.id,
            name_en: location.name + ' (' + state.locationMenu.categories.find(c => c.id === location.typeId)['name_' + state.locale] + ')',
            name_es: location.name + ' (' + state.locationMenu.categories.find(c => c.id === location.typeId)['name_' + state.locale] + ')',
          }
        });
      }
      return menu
    },
    filters: (state) => {
      let filters = JSON.parse(JSON.stringify(state.filters))
      if (filters && state.customLocations?.length > 0) {
        filters.locationTypeFilter.options.push({
          display: false, 
          id: '7',
          name_en: i18n.t('tools.custom_locations.name'),
          name_es: i18n.t('tools.custom_locations.name'),
          disabled: !state.customLocations.some((location) => filters?.locationTypeFilter?.options?.some(locationType => locationType.id === location.typeId))
        });
        filters.locationFilter.options = filters.locationFilter.options.concat(state.customLocations
          .map((location) => {
            return {
              display: false,
              id: location.id,
              name_en: location.name + ' (' + state.locationMenu.categories.find(c => c.id === location.typeId)['name_en'] + ')',
              name_es: location.name + ' (' + state.locationMenu.categories.find(c => c.id === location.typeId)['name_es'] + ')',
              typeId: '7',
              disabled: !filters?.locationTypeFilter?.options?.some(locationType => locationType.id === location.typeId)
            };
          })
        );
        filters.locationTypeYears[7] = Array.from(new Set(Object.values(filters.locationTypeYears).flat()));
      }
      
      return filters
    }
  },
  mutations: {
    SET_LOADING(state, loading) {
      state.loading = loading
    },
    SET_LOCALE(state, locale) {
      state.locale = locale
    },
    SET_INDICATOR_MENU(state, indicatorMenu) {
      state.indicatorMenu = indicatorMenu
    },
    SET_LOCATION_MENU(state, locationMenu) {
      state.locationMenu = locationMenu
    },
    SET_FEATURED_INDICATORS(state, indicators) {
      state.featuredIndicators = indicators
    },
    SET_COMMUNITY(state, community) {
      state.community = community
    },
    SET_INDICATOR(state, indicator) {
      state.indicator = indicator
    },
    SET_SOURCE(state, source) {
      state.source = source
    },
    SET_FILTERS(state, filters) {
      state.filters = filters
    },
    SET_EXPLORE_DATA(state, exploreData) {
      state.exploreData = exploreData
    },
    SET_DOCKED_TOOLTIP(state, value) {
      state.dockedTooltip = value
    },
    SET_EXPLORE_TAB(state, exploreTab) {
      state.exploreTab = exploreTab
    },
    SET_FILTER_SELECTIONS(state, selections) { 
      state.filterSelections = selections
    },
    SET_COMPARE_SELECTIONS(state, selections) {
      state.compareSelections = selections
    },
    SET_TABLES_DATA(state, tablesData) {
      state.tablesData = tablesData
    },
    SET_ABOUT_DATA(state, data) {
      state.aboutData = data;
    },
    SET_FAQS(state, data) {
      state.faqs = data;
    },
    SET_ANNOUNCEMENTS(state, data) {
      state.announcements = data;
    },
    SET_TOOL_ROUTE(state, params) {
      state[params.key + 'Route'] = params.route
    },
    SET_SHOW_MAP_LABELS(state, showLabels) {
      state.showMapLabels = showLabels
    },
    SET_SHOW_TREND_LABELS(state, showLabels) {
      state.showTrendLabels = showLabels
    },
    SET_SHOW_COMPARE_LABELS(state, showLabels) {
      state.showCompareLabels = showLabels
    },
    SET_SURVEY_SUBMITTED(state, surveySubmitted) {
      state.surveySubmitted = surveySubmitted
    },
    SET_CUSTOM_LOCATIONS(state, locations) {
      state.customLocations = locations
    },
    ADD_CUSTOM_LOCATION(state, location) {
      if (state.customLocations.find(l => l.id === location.id)) {
        state.customLocations = state.customLocations.map(l => {
          if (l.id === location.id) {
            return location
          } else {
            return l
          }
        });
      } else {
        state.customLocations.push(location);
      }
    },
    DELETE_CUSTOM_LOCATION(state, location) {
      state.customLocations = state.customLocations.filter(l => l.id !== location);
      localStorage.setItem('cinow-custom-locations', JSON.stringify(state.customLocations));
    }
  },
  actions: {
    setLoading(context, loading) {
      context.commit('SET_LOADING', loading)
    },
    setLocale(context, locale) {
      if (locale !== this.state.locale) {
        context.commit('SET_LOCALE', locale)
        localStorage.setItem('cinow-locale', locale)
        i18n.locale = locale
        document.title = i18n.t('app.title')
        if (router.currentRoute.query.lang !== locale) {
          router.replace({
            query: {
              ...router.currentRoute.query,
              lang: locale,
            },
          })
        }
        context.dispatch('getIndicatorMenu')
        context.dispatch('getLocationMenu')
      }
    },
    getIndicatorMenu(context) {
      axios.get('/api/indicator-menu').then(response => {
        context.commit('SET_INDICATOR_MENU', response.data)
      })
    },
    getLocationMenu(context) {
      axios.get('/api/location-menu').then(response => {
        context.commit('SET_LOCATION_MENU', response.data)
      })
    },
    getFeaturedIndicators(context) {
      axios.get('/api/featured-indicators').then(response => {
        context.commit('SET_FEATURED_INDICATORS', response.data)
      })
    },
    setCommunity(context, community) {
      if (community !== null) {
        context.dispatch('getCommunityData', community)
      } else {
        context.commit('SET_COMMUNITY', community)
      }
    },
    getCommunityData(context, community) {
      axios.get('/api/community-data', { params: { 
          location: community.id, 
          locationType: community.categoryId 
        }
      }).then(response => {
        context.commit('SET_COMMUNITY', response.data)
      })
    },
    setIndicator(context, indicator) {
      context.commit('SET_INDICATOR', indicator)
      if (indicator == null) {
        context.commit('SET_SOURCE', null)
        context.commit('SET_FILTERS', null)
        context.commit('SET_FILTER_SELECTIONS', null)
        context.commit('SET_COMPARE_SELECTIONS', null)
        return Promise.resolve();
      } else {
        context.commit('SET_COMPARE_SELECTIONS', null)
        return context.dispatch('getSource', indicator).then(() => {
          return context.dispatch('getFilters', indicator)
        });
      }
    },
    getSource(context, indicator) {
      return axios.get('/api/indicator-source', { params: {
        indicator: indicator.id
      }}).then(response => {
        context.commit('SET_SOURCE', response.data)
      })
    },
    getFilters(context, indicator) {
      return axios.get('/api/filters', { params: {
        indicator: indicator.id
      }}).then(response => { 
        context.commit('SET_FILTERS', response.data)
      })
    },
    getExploreData(context) {
      if (!this.state.filterSelections) {
        return;
      }
      axios.post('/api/explore-data', {
        indicator: context.state.indicator.id, 
        filters: this.state.filterSelections,
        comparisons: this.state.compareSelections
      }).then(response => {
        context.commit('SET_EXPLORE_DATA', response.data)
      })
    },
    setExploreData(context, data) {
      context.commit('SET_EXPLORE_DATA', data)
    },
    setDockedTooltip(context, data) {
      context.commit('SET_DOCKED_TOOLTIP', data)
    },
    setExploreTab(context, tab) {
      context.commit('SET_EXPLORE_TAB', tab)
    },
    setFilterSelections(context, selections) {
      context.commit('SET_FILTER_SELECTIONS', selections);
      let filterQuery = {
        ...router.currentRoute.query,
        locationType: selections.locationType,
        location: selections.location,
        year: selections.year

      };
      Object.entries(selections.indicatorFilters).forEach(e => {
        filterQuery['filter_' + e[0]] = e[1].id;
      });
      if (JSON.stringify(filterQuery) !== JSON.stringify(router.currentRoute.query)) {
        router.replace({
          query: filterQuery
        });
      }
      context.dispatch('getExploreData');
    },
    setCompareSelections(context, selections) {
      context.commit('SET_COMPARE_SELECTIONS', selections);
      let compareWiths = selections.options.map(o => (o.typeId ? o.typeId + "_" : "") + o.id)
      if (compareWiths.length === 1) {
        compareWiths = compareWiths[0]
      }
      let compareQuery = {
        ...router.currentRoute.query,
        compareBy: selections.type.id,
        compareWith: compareWiths

      };
      if (JSON.stringify(compareQuery) !== JSON.stringify(router.currentRoute.query)) {
        router.replace({
          query: compareQuery
        });
      }
      context.dispatch('getExploreData');
    },
    getTablesData(context, request) {
      //TODO: need to manually add custom location filters here so we don't fetch all custom locations other users have created
      let filterQuery = {
        ...router.currentRoute.query
      }
      if (request.locationTypes) {
        filterQuery.locationTypes = Array.isArray(request.locationTypes) && request.locationTypes.length > 1 ? request.locationTypes : request.locationTypes[0]
      }
      if (request.locations) {
        filterQuery.locations = Array.isArray(request.locations) && request.locations.length > 1 ? request.locations : request.locations[0]
      }
      if (request.years) {
        filterQuery.years = Array.isArray(request.years) && request.years.length > 1 ? request.years : request.years[0]
      }
      if (request.races) {
        filterQuery.races = request.races.map(r => r === null ? '000' : r)
        filterQuery.races = Array.isArray(filterQuery.races) && filterQuery.races.length > 1 ? filterQuery.races : filterQuery.races[0]
      } else {
        delete filterQuery.races
      }
      if (request.ages) {
        filterQuery.ages = request.ages.map(a => a === null ? '000' : a)
        filterQuery.ages = Array.isArray(filterQuery.ages) && filterQuery.ages.length > 1 ? filterQuery.ages : filterQuery.ages[0]
      } else {
        delete filterQuery.ages
      }
      if (request.sexes) {
        filterQuery.sexes = request.sexes.map(s => s === null ? '000' : s)
        filterQuery.sexes = Array.isArray(filterQuery.sexes) && filterQuery.sexes.length > 1 ? filterQuery.sexes : filterQuery.sexes[0]
      } else {
        delete filterQuery.sexes
      }
      if (request.educations) {
        filterQuery.educations = request.educations.map(e => e === null ? '000' : e)
        filterQuery.educations = Array.isArray(filterQuery.educations) && filterQuery.educations.length > 1 ? filterQuery.educations : filterQuery.educations[0]
      } else {
        delete filterQuery.educations
      }
      if (request.incomes) {
        filterQuery.incomes = request.incomes.map(i => i === null ? '000' : i)
        filterQuery.incomes = Array.isArray(filterQuery.incomes) && filterQuery.incomes.length > 1 ? filterQuery.incomes : filterQuery.incomes[0]
      } else {
        delete filterQuery.incomes
      }
      if (JSON.stringify(filterQuery) !== JSON.stringify(router.currentRoute.query)) {
        router.replace({
          query: filterQuery
        });
      }
      axios.post('/api/tables-data', { 
        ...request 
      }).then(response => {
        response.data.items.forEach(i => {
          if (i.suppressed) {
            i.valueLabel = i18n.t('data.suppressed');
          } else if (i.value === null) { 
            i.valueLabel = i18n.t('data.no_data');
          } else  {
            i.valueLabel = format(response.data.indicator.typeId, i.value);
          }
          i.moeLowLabel = format(response.data.indicator.typeId, i.moeLow);
          i.moeHighLabel = format(response.data.indicator.typeId, i.moeHigh);
          i.universeValueLabel = format('1', i.universeValue);
        })
        context.commit('SET_TABLES_DATA', response.data);
      });
    },
    setTablesData(context, data) {
      context.commit('SET_TABLES_DATA', data);
    },
    getAboutData(context) {
      axios.get('/api/about-data').then(response => {
        context.commit('SET_ABOUT_DATA', response.data);
      });
    },
    getFaqs(context) {
      axios.get('/api/faqs').then(response => {
        context.commit('SET_FAQS', response.data.filter(f => !!f.display))
      });
    },
    getAnnouncements(context) {
      axios.get('/api/announcements').then(response => {
        context.commit('SET_ANNOUNCEMENTS', response.data)
      });
    },
    setToolRoute(context, params) {
      context.commit('SET_TOOL_ROUTE', params);
    },
    setShowMapLabels(context, showLabels) {
      context.commit('SET_SHOW_MAP_LABELS', showLabels)
    },
    setShowTrendLabels(context, showLabels) {
      context.commit('SET_SHOW_TREND_LABELS', showLabels)
    },
    setShowCompareLabels(context, showLabels) {
      context.commit('SET_SHOW_COMPARE_LABELS', showLabels)
    },
    setSurveySubmitted(context, surveySubmitted) {
      sessionStorage.setItem('cinow-survey-submitted', surveySubmitted)
      context.commit('SET_SURVEY_SUBMITTED', surveySubmitted)
    },
    setCustomLocations(context, locations) {
      context.commit('SET_CUSTOM_LOCATIONS', locations)
    },
    addCustomLocation(context, location) {
      let postLocation = JSON.parse(JSON.stringify(location));
      //postLocation.name_en = postLocation.name + ' (' + this.state.locationMenu.categories.find(c => c.id === location.geojson.features[0].properties.typeId).name_en + ')';
      //postLocation.name_es = postLocation.name + ' (' + this.state.locationMenu.categories.find(c => c.id === location.geojson.features[0].properties.typeId).name_es + ')';
      postLocation.geojson = JSON.stringify(postLocation.geojson);
      console.log(postLocation);
      return axios.post('/api/custom-locations', postLocation).then(() => {
        context.commit('ADD_CUSTOM_LOCATION', location);
        localStorage.setItem('cinow-custom-locations', JSON.stringify(this.state.customLocations));
        router.replace({
          query: {
            ...router.currentRoute.query,
            typeId: location.typeId,
            id: location.id
  
          }
        });
      });
    },
    deleteCustomLocation(context, location) {
      context.commit('DELETE_CUSTOM_LOCATION', location)
    }
  },
  modules: {},
})
