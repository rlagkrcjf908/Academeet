const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer: {
    // disableHostCheck: true,
    allowedHosts: "all",
    https: false,
    port: 8080,
    open: true,
    proxy: {
      '/api/v1': {
        //target: 'http://172.26.4.186:5000/'
        target: 'http://localhost:5000/'
      }
    },
    historyApiFallback: true,
    hot: true,
  },
  // css: {
  //   requireModuleExtension: false // import 시에 경로에 .module 포함 안해도 됨.
  // },
  transpileDependencies: [
    'element-plus'
  ],
  lintOnSave: false,
  // outputDir: '../backend/src/main/resources/dist'
})
