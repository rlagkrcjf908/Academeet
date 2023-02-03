import { requestLogin, profileUpdate, requestProfile  } from "../common/api/accountAPI";

const state = {
  token: null,
  id: 9,
  profile: {
    img:null,
    name: null,
    email: null,
    nick: null,
    phone: null,
    birth: null
  },
};

const getters = {
  getToken: state => {
    return state.token;
  },
  getUserProfile: state => {
    return state.profile;
  },

};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_USER_PROFILE: (state, profile) => {
    state.profile = profile;
    console.log(state.profile)
  },
};

const actions = {
  loginAction: async ({ commit }, loginData) => {
    const response = await requestLogin(loginData);
    commit("SET_TOKEN", response.data.accessToken);
  },
  profileUpdateAction: async ({ commit }, profileData) => {

    console.log('보낼데이터',profileData)
    console.log('보낼데이터2',JSON.stringify(profileData))

    const response = await profileUpdate(state.id, JSON.stringify(profileData));
    console.log('응답',response)
    console.log('응답2',response.data)
    // commit("SET_USER_PROFILE", response.data.profile);
  },
  requestProfileAction: async ({ commit }) => {
    const response = await requestProfile();
    console.log(response.data)

    commit("SET_USER_PROFILE", response.data);
  }
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
