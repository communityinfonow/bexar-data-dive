<template>
  <v-footer app absolute outlined tile class="text-body-2 d-flex justify-end" style="background-color: white;">
	{{ $t('app.copyright') }} &copy; 2022&nbsp;<a href="https://cinow.info">{{ $t('app.cinow') }}</a>. {{ $t('app.all_rights_reserved') }}
  </v-footer>
</template>

<script>
export default {
  name: 'AppFooter',
}
</script>

<style lang="scss" scoped></style>
