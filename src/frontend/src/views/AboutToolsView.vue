<template>
  <v-container fluid class="pa-0">
    <v-row class="no-gutters flex-column">
      <v-col cols="auto" class="pa-4 col col-auto">
        <h1 class="text-h3 mb-2">{{ $t('about_tools_view.name') }}</h1>
        <section v-for="(tool, index) in tools" :key="'tool_' + index">
          <h2 class="my-4">{{ tool.name }}</h2>
          <tool-item :text="tool.fullDescription" :img="tool.img"></tool-item>
          <section v-for="(subTool, index) in tool.subTools" :key="'subtool_' + index">
            <h3 class="my-2">{{ subTool.name }}</h3>
            <tool-item :text="subTool.fullDescription" :img="subTool.img"></tool-item>
          </section>
        </section>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import ToolItem from '@/components/ToolItem'

export default {
  name: 'AboutToolsView',
  components: {
    ToolItem
  },
  computed: {
  ...mapGetters(['tools'])
  }
}
</script>

<style lang="scss" scoped></style>
