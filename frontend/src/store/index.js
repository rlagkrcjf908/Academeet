import { createStore } from 'vuex';
import createPersistedState from "vuex-persistedstate";
import modules from './modules';

const persistedState = createPersistedState({
  paths: ['token', 'id', 'name', 'nick']
})

const store = createStore({
  state:      modules.state,
  getters:    modules.getters,
  mutations:  modules.mutations,
  actions:    modules.actions,
  plugins:    [persistedState]
})

export default store;