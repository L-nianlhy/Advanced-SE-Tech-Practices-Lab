import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import { Expand } from "@element-plus/icons";
// import * as echarts from "echarts";
// createApp.prototype.$echarts = echarts;
createApp(App)
  .use(store)
  .use(router)
  .use(ElementPlus)
  .component("expand", Expand)
  .mount("#app");
