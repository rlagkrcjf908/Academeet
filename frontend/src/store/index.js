import { createStore } from 'vuex'
import accountStore from "./accountStore";
import groupStore from "./groupStore";
import meetingStore from "./meetingStore";
import createPersistedState from "vuex-persistedstate";

export default createStore({
  plugins: [
    createPersistedState({
      paths: ['accountStore'],
    })
  ],
  modules: {
    modules,
    accountStore,
    groupStore,
    meetingStore,
  }
});
