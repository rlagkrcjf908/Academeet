const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer: {
    https: false,
    port: 8083,
    open: true,
    proxy: {
      '/api/v1': {
        // target: 'http://192.168.100.189:8080'
        target: 'http://localhost:8080'
      }
    },
    historyApiFallback: true,
    hot: true
  },
  // css: {
  //   requireModuleExtension: false // import 시에 경로에 .module 포함 안해도 됨.
  // },
  transpileDependencies: true,
  lintOnSave: false,
  outputDir: '../backend/src/main/resources/dist'
})
