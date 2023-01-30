import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueAxios from './common/lib/axios'
import axios from './common/lib/axios'
import i18n from './common/lib/i18n'

// element-plus와 icon을사용하기 위한 import
import ElementPlus from "element-plus";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";

createApp(App)
  .use(store)
  .use(router)
  .use(VueAxios, axios)
  .use(i18n)
  .use(ElementPlus)
  .use(ElementPlusIconsVue)
  .mount("#app");
