import { createStore } from 'vuex'
import accountStore from "./accountStore";
import groupStore from "./groupStore";
import meetingStore from "./meetingStore";
import createPersistedState from "vuex-persistedstate";

const persistedState = createPersistedState({
  paths: ['token', 'userId', 'name', 'nick']
})

export default createStore({
  plugins:    [persistedState],
  modules: {
    accountStore,
    groupStore,
    meetingStore,
  }
});

// export default store;