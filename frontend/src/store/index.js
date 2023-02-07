import { createStore } from 'vuex'
import accountStore from "./accountStore";
import groupStore from "./groupStore";
import listStore from "./listStore"
import meetingStore from "./meetingStore";
import createPersistedState from "vuex-persistedstate";

export default createStore({
  plugins: [
    createPersistedState({
      paths: ['accountStore'],
    })
  ],
  modules: {
    accountStore,
    groupStore,
    listStore,
    meetingStore,
  }
});
