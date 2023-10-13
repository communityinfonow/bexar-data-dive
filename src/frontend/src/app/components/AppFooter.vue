<template>
  <v-footer app inset absolute outlined tile color="white" class="d-print-none px-0" style="z-index: 9998;">
    <hr style="width: 100%;" />
    <div class="my-4 px-16" style="width: 100%;">
      <span v-html="$t('home_view.acknowledgement')"></span>
    </div>
    <hr style="width: 100%;" />
    <div class="my-4 px-4 d-flex justify-end align-center" style="width: 100%;">
      <span>
        {{ $t('app.copyright') }} &copy; {{ new Date().getFullYear() }}&nbsp;<a href="https://cinow.info" class="link--text">{{ $t('app.cinow') }}</a>. {{ $t('app.all_rights_reserved') }}
      </span>
      <img src="/img/cinow_logo.png" class="ml-4" style="height: 30px;" alt="CI:Now Logo" />
    </div>
  </v-footer>
</template>

<script>
export default {
  name: 'AppFooter',
}
</script>

<style lang="scss" scoped>
</style>
