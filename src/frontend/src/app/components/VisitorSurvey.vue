<template>
    <v-speed-dial 
      v-if="showSurvey" 
      class="visitor-survey-fab" 
      :class="{ 'en': locale === 'en', 'es': locale === 'es' }" 
      v-model="surveyOpen" 
      direction="left" 
      fixed 
      transition="slide-x-reverse-transition"
      bottom
      right>
      <template v-slot:activator>
        <v-btn v-model="surveyOpen" fab color="yellow" class="wobbly">
          <img src="/img/icon_ux_survey__active.svg" height="56px" width="56px">
        </v-btn>
      </template>
      <v-card class="survey-card" light>
        <template v-if="!surveySubmitted">
          <v-form ref="survey_form">
            <v-card-text @click.stop="">
              {{ $t('survey.question') }}
              <v-btn-toggle group class="d-flex justify-space-between">
                <v-btn v-for="(option, index) in surveyOptions" :key="index" @click.stop="surveyResponse = option.value" class="survey-response-button" :style="{ 'color': option.color }">
                  <v-icon x-large :style="{ 'color': option.color }">{{ option.icon }}</v-icon>
                </v-btn>
              </v-btn-toggle>
              <div class="d-flex justify-space-between">
                <span>{{ surveyOptions[0].label}}</span>
                  <span>{{ surveyOptions[3].label}}</span>
              </div>
            </v-card-text>
            <v-card-actions @click.stop="">
              <v-btn text @click.stop="dismissSurvey">{{ $t('survey.close') }}</v-btn>
              <v-spacer></v-spacer>
              <v-btn color="primary" :disabled="!surveyResponse" @click.stop="submitSurvey">{{ $t('survey.submit') }}</v-btn>
            </v-card-actions>
          </v-form>
        </template>
        <template v-else>
          <v-card-text v-html="$t('survey.feedback')" @click.stop="">
          </v-card-text>
          <v-card-actions @click.stop=""> 
            <v-btn class="mx-auto" text color="primary" @click="closeSurvey">{{ $t('survey.close') }}</v-btn>
          </v-card-actions>
        </template>
      </v-card>
    </v-speed-dial>
</template>

<script>
import axios from 'axios'
import i18n from '@/i18n'
import { mapActions, mapState } from 'vuex'
export default {
  name: 'VisitorSurvey',
  computed: { 
    ...mapState(['locale', 'surveySubmitted']), 
    surveyOptions() {
      return [
        {
          icon: 'mdi-emoticon-sad',
          color: '#F4511E',
          label: i18n.t('survey.options')[0],
          value: 'red'
        },
        {
          icon: 'mdi-emoticon-neutral',
          color: '#FFB300',
          label: '&nbsp;',
          value: 'yellow'
        },
        {
          icon: 'mdi-emoticon-happy',
          color: '#039BE5',
          label: '&nbsp;',
          value: 'blue'
        },
        {
          icon: 'mdi-emoticon-excited',
          color: '#7CB342',
          label: i18n.t('survey.options')[1],
          value: 'green'
        }
      ]
    }
  },
  data() {
    return {
      showSurvey: false,
      surveyOpen: false,
      surveyResponse: null
    }
  },
  mounted() {
    if (!this.surveySubmitted) {
      setTimeout(() => { 
        this.showSurvey = true;
        this.surveyOpen = true;
      }, 60000);
    }
  },
  methods: {
    ...mapActions(['setSurveySubmitted']),
    submitSurvey() {
      axios.post('/api/survey-responses', { 
          question: i18n.t('survey.question'), 
          response: this.surveyResponse,
          lang: this.locale
        })
        .then(() => this.setSurveySubmitted(true))
      
    },
    dismissSurvey() {
      this.setSurveySubmitted(true);
      this.surveyOpen = false;
      this.showSurvey = false;
    },
    closeSurvey() {
      this.surveyOpen = false;
      this.showSurvey = false;
    }
  },
}
</script>

<style lang="scss" scoped>
  .visitor-survey-fab {
    z-index: 9999;
    bottom: 12em;
  }
  .visitor-survey-fab .v-btn {
    bottom: 36%;
  }
  .visitor-survey-fab.en ::v-deep .v-speed-dial__list {
    width: 418px;
  }
  .visitor-survey-fab.es ::v-deep .v-speed-dial__list {
    width: 466px;
  }
  ::v-deep .v-speed-dial__list {
    height: auto !important;
  }
  .survey-card {
    width: 100%;
  }

  .wobbly {
    -webkit-animation: wobble 4s both infinite;
    animation: wobble 4s both infinite;
    text-align: center;
  }

  @keyframes wobble {
    0% {
      -webkit-transform: translateX(0%);
      transform: translateX(0%);
      -webkit-transform-origin: 50% 50%;
      transform-origin: 50% 50%;
    }
    5% {
      -webkit-transform: translateX(-8px)rotate(-8deg);
      transform: translateX(-8px)rotate(-8deg);
    }
    10% {
      -webkit-transform: translateX(6px)rotate(6deg);
      transform: translateX(6px)rotate(6deg);
    }
    15% {
      -webkit-transform: translateX(-4px)rotate(-4deg);
      transform: translateX(-4px)rotate(-4deg);
    }
    20% {
      -webkit-transform: translateX(2px)rotate(2deg);
      transform: translateX(2px)rotate(2deg);
    }
    25% {
      -webkit-transform: translateX(-1px)rotate(-1deg);
      transform: translateX(-1px)rotate(-1deg);
    }
    100% {
      -webkit-transform: translateX(0px)rotate(0deg);
      transform: translateX(0px)rotate(0deg);
    }
  }
</style>
