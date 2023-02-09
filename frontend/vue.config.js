const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer: {
    // 메인(E2C) 서버에서는 반드시 https:true로 할것
    // 그 이외에는 반드시 false로 할것
    https: true,
    port: 8083,
    open: true,
    proxy: {
      '/api/v1': {
        target: 'https://i8d108.p.ssafy.io/'
        // target: 'http://localhost:8080'
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
