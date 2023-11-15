import Vue from 'vue'
import Vuex from 'vuex'
import router from '@/app/router/index'
import i18n from '@/i18n'
import axios from 'axios'
import { format } from '@/services/formatter'

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
    filterTypes: null,
    filters: null,
    dockedTooltip: null,
    highlightFilteredLocation: true,
    showMapLabels: false,
    trendLabelsOrLines: 'labels',
    compareLabelsOrLines: 'labels',
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
          iconPath: "/img/icon_tool_community__active.svg",
          iconPathInactive: "/img/icon_tool_community__inactive.svg",
          imagePath: "/img/tools_community_" + i18n.locale + ".png",
          route: state.communityRoute,
          shortDescription: i18n.t('tools.community.short_description'),
          headline: i18n.t('tools.community.headline'),
          fullDescription: i18n.t('tools.community.long_description'),
          showOnHome: true
        },
        {
          key: 'explore',
          name: i18n.t('tools.explore.name'),
          iconPath: "/img/icon_tool_explore__active.svg",
          iconPathInactive: "/img/icon_tool_explore__inactive.svg",
          imagePath: "/img/tools_explore_" + i18n.locale + ".png",
          route: state.exploreRoute,
          shortDescription: i18n.t('tools.explore.short_description'),
          headline: i18n.t('tools.explore.headline'),
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
          iconPath: "/img/icon_tool_tables__active.svg",
          iconPathInactive: "/img/icon_tool_tables__inactive.svg",
          imagePath: "/img/tools_tables_" + i18n.locale + ".png",
          route: state.tablesRoute,
          shortDescription: i18n.t('tools.tables.short_description'),
          headline: i18n.t('tools.tables.headline'),
          fullDescription: i18n.t('tools.tables.long_description'),
          showOnHome: true
        },
        {
          key: 'custom-locations',
          name: i18n.t('tools.custom_locations.name'),
          iconPath: "/img/icon_tool_custom__active.svg",
          iconPathInactive: "/img/icon_tool_custom__inactive.svg",
          imagePath: "/img/tools_custom_" + i18n.locale + ".png",
          route: state.customLocationsRoute,
          shortDescription: i18n.t('tools.custom_locations.short_description'),
          headline: i18n.t('tools.custom_locations.headline'),
          fullDescription: i18n.t('tools.custom_locations.long_description'),
          showOnHome: false
        }
      ]
    },
    about_views: () => {
      return [
        {
          key: 'help',
          name: i18n.t('help_view.name'),
          icon: 'mdi-help-circle',
          route: 'help',
          shortDescription: i18n.t('help_view.short_description'),
          headline: i18n.t('help_view.headline'),
          fullDescription: i18n.t('help_view.long_description')
        },
        {
          key: 'about-data',
          name: i18n.t('about_data_view.name'),
          route: 'about-data',
          iconPath: '/img/icon_ux_info__active.svg',
          iconPathInactive: '/img/icon_ux_info__inactive.svg'
        },
        {
          name: i18n.t('announcements_view.name'),
          route: 'announcements',
          icon: 'mdi-bullhorn',
          icon_transform: 'rotate(-45deg)'
        }
      ]
    },
    locationMenu: (state) => {
      let menu = JSON.parse(JSON.stringify(state.locationMenu))
      if (menu && menu.categories.find(c => c.id === '7')) {
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
      if (!state.filters) {
        return null;
      }
      let filters = JSON.parse(JSON.stringify(state.filters))
      if (state.locationMenu && state.locationMenu.categories.find(c => c.id === '7')) {
        if (filters && state.customLocations?.length > 0) {
          filters.locationTypeFilter.options.push({
            display: false, 
            id: '7',
            name_en: state.locationMenu.categories.find(c => c.id === '7')['name_en'],
            name_es: state.locationMenu.categories.find(c => c.id === '7')['name_es'],
            disabled: !state.indicator.aggregable || !state.customLocations.some((location) => filters?.locationTypeFilter?.options?.some(locationType => locationType.id === location.typeId))
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
      }
      return filters
    },
    helpMenu: (state) => {
      return {
        categories: [
          {
            id: 'faqs',
            name_en: i18n.t('faqs_view.name'),
            name_es: i18n.t('faqs_view.name'),
            description_en: i18n.t('faqs_view.welcome'),
            description_es: i18n.t('faqs_view.welcome'),
            subcategories: null,
            items: state.faqs?.map(faq => {
              return {
                categoryId: 'faqs',
                id: faq.id,
                name_en: faq.question_en,
                name_es: faq.question_es,
                hasData: true
              }
            }) || []
          },
          {
            id: 'about-tools',
            name_en: i18n.t('about_tools_view.name'),
            name_es: i18n.t('about_tools_view.name'),
            description_en: i18n.t('about_tools_view.welcome'),
            description_es: i18n.t('about_tools_view.welcome'),
            subcategories: null,
            items: [{
                categoryId: 'about-tools',
                id: 'about-tools',
                name_en: i18n.t('about_tools_view.name'),
                name_es: i18n.t('about_tools_view.name'),
                hasData: true
            }]
          }
        ]
      }
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
    SET_FILTER_TYPES(state, filterTypes) {
      state.filterTypes = filterTypes
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
    SET_HIGHLIGHT_FILTERED_LOCATION(state, highlight) {
      state.highlightFilteredLocation = highlight
    },
    SET_SHOW_MAP_LABELS(state, showLabels) {
      state.showMapLabels = showLabels
    },
    SET_TREND_LABELS_OR_LINES(state, labelsOrLines) {
      state.trendLabelsOrLines = labelsOrLines
    },
    SET_COMPARE_LABELS_OR_LINES(state, labelsOrLines) {
      state.compareLabelsOrLines = labelsOrLines
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
        context.dispatch('getFilterTypes')
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
    setCommunity(context, data) {
      if (data !== null) {
        context.dispatch('getCommunityData', { community: data.community, filterType: data.filterType })
      } else {
        context.commit('SET_COMMUNITY', data)
      }
    },
    getCommunityData(context, data) {
      axios.get('/api/community-data', { params: { 
          location: data.community.id, 
          locationType: data.community.categoryId,
          filterType: data.filterType
        }
      }).then(response => {
        context.commit('SET_COMMUNITY', response.data)
      })
    },
    setIndicator(context, indicator) {
      context.commit('SET_INDICATOR', indicator)
      context.commit('SET_SOURCE', null)
        context.commit('SET_FILTERS', null)
        context.commit('SET_FILTER_SELECTIONS', null)
        context.commit('SET_COMPARE_SELECTIONS', null)
      if (indicator == null) {
        return Promise.resolve();
      } else {
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
    getFilterTypes(context) {
      return axios.get('/api/filter-types').then(response => {
        context.commit('SET_FILTER_TYPES', response.data)
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
      let compareWiths = selections.options.filter(o => !!o).map(o => (o.typeId ? o.typeId + "_" : "") + o.id)
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
      request.customLocationIds = this.state.customLocations.map(l => l.id);
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
      return axios.get('/api/faqs').then(response => {
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
    setHighlightFilteredLocation(context, highlightFilteredLocation) {
      context.commit('SET_HIGHLIGHT_FILTERED_LOCATION', highlightFilteredLocation);
    },
    setShowMapLabels(context, showLabels) {
      context.commit('SET_SHOW_MAP_LABELS', showLabels)
    },
    setTrendLabelsOrLines(context, labelsOrLines) {
      context.commit('SET_TREND_LABELS_OR_LINES', labelsOrLines)
    },
    setCompareLabelsOrLines(context, labelsOrLines) {
      context.commit('SET_COMPARE_LABELS_OR_LINES', labelsOrLines)
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
      postLocation.geojson = JSON.stringify(postLocation.geojson);
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
