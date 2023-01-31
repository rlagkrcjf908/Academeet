import { requestLogin, profileUpdate, requestProfile  } from "../common/api/accountAPI";

const state = {
  token: null,
  userid: null,
  username: null,
  profile: {
    profileImg: '@/assets/images/user.png',
    username: '김하니',
    email: 'ssafy8@gmail.com',
    nickname: '뉴진스하니',
    phone: '010-1234-5678',
    birthday: '2004.10.06'
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
  requestProfileAction: async ({ commit }, profileData) => {
    const response = await requestProfile(profileData);
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
