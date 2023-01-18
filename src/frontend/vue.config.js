const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: ['vuetify'],
  pluginOptions: {
    i18n: {
      locale: 'en',
      fallbackLocale: 'en',
      localeDir: '@/locales',
      enableInSFC: false,
      enableBridge: false,
    },
  },
  pages: {
    index: {
      entry: 'src/app/app.js',
      template: 'public/index.html',
      filename: 'index.html',

    },
    admin: {
      entry: 'src/admin/admin.js',
      template: 'public/admin.html',
      filename: 'admin.html',

    }
  },
  devServer: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8082',
        ws: true,
        changeOrigin: true
      }
    }
  }
})
