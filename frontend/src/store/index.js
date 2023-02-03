import { createStore } from 'vuex'
import accountStore from "./accountStore";
import groupStore from "./groupStore";
import meetingStore from "./meetingStore";
import createPersistedState from "vuex-persistedstate";
import modules from './modules';

const persistedState = createPersistedState({
  paths: ['token', 'id', 'name', 'nick']
})

export default createStore({
  state:      modules.state,
  getters:    modules.getters,
  mutations:  modules.mutations,
  actions:    modules.actions,
  plugins:    [persistedState],
  modules: {
    accountStore,
    groupStore,
    meetingStore,
  }
});

// export default store;

export default store;