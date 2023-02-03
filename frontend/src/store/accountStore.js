import { requestLogin, profileUpdate, requestProfile  } from "../common/api/accountAPI";

const state = {
  token: null,
  userid: null,
  profile: {
    profileImg:null,
    username: null,
    email: null,
    nickname: null,
    phone: null,
    birthday: null
  },
};

const getters = {
  getToken: state => {
    return state.token;
  },
  getUserProfile: state => {
    return state.Profile;
  },

};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_USER_PROFILE: (state, profile) => {
    state.Profile = profile;
  },
};

const actions = {
  loginAction: async ({ commit }, loginData) => {
    const response = await requestLogin(loginData);
    commit("SET_TOKEN", response.data.accessToken);
  },
  profileUpdateAction: async ({ commit}, profileData) => {
    const response = await profileUpdate(profileData);
    commit("SET_USER_PROFILE", response.data.profile);
  },
  requestProfileAction: async ({ commit }) => {
    const response = await requestProfile();
    commit("SET_USER_PROFILE", response.data.profile);
  }
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
