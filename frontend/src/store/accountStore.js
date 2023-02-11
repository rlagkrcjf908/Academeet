import { profileUpdate } from "../common/api/accountAPI";
import axios from 'axios'
import router from '../router/index'
import setAuthHeader from 'axios'
import { ElMessage } from 'element-plus'

const DEFAULT_ACCEPT_TYPE = 'application/json; charset=utf-8; application/x-www-form-urlencoded;'
axios.defaults.headers.common['Content-Type'] = DEFAULT_ACCEPT_TYPE

const state = {
  token: null,
  userInfo: null,
  userId: null,
  isAuthenticated: false,
};

const getters = {
  token: state => state.token,
  userInfo: state => state.userInfo,
  userId: state => state.userId,
  isAuthenticated: state => state.isAuthenticated
}

const mutations = {
  SET_USERINFO: (state, item) => {
    state.userInfo = item
  },
  SET_TOKEN: (state, item) => {
    state.token = item.headers['accessToken']
  },
  SET_USERID: (state, item) => {
    state.userId = item
  },
  SET_AUTHENTICATED: (state) => {
    state.isAuthenticated = true
  },
  LOGOUT: (state) => {
    state.token = null
    state.userInfo = null
    state.userId = null
    state.isAuthenticated = false
  },
  SET_USERID: (state, item) => {
    state.userId = item
  }
};

const actions = {
  // 로그인
  login({ commit }, loginData) {
    const params = {
      "email": loginData.email,
      "password": loginData.password
    }
    // i8d108.p.ssafy.io
    axios.post("/api/v1/auth/login/", JSON.stringify(params), {
      headers: { 'content-type': 'application/json' }
    }).then(res => {
      commit('SET_USERINFO', res.data)
      commit('SET_TOKEN', res)
      commit('SET_USERID', res.data.userRes.id)
      commit('SET_AUTHENTICATED')
      console.log(res)
      // localStorage.setItem('token', res.data.accessToken)
      localStorage.setItem('userInfo', JSON.stringify(res.data.userRes));
      setAuthHeader(res.data.accessToken)
      ElMessage({
        showClose: true,
        message: '로그인 성공! 환영합니다.',
        type: 'success',
      })
      router.push("/")
    }).catch(e => {
      console.log(e)
      ElMessage({
        showClose: true,
        message: '존재하지 않는 회원 정보 입니다',
        type: 'error',
      })
    })
  },

  logout({ commit }) {
    axios.get("/api/v1/auth/logout/")
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    location.reload()
    commit('LOGOUT')
    ElMessage({
      showClose: true,
      message: '로그아웃 되었습니다.',
      type: 'success',
    })
    router.push("/login")
  },

  // 토큰 가져오기
  getToken() {
    axios.get("/api/v1/")
      .then((res) => console.log(res.data))
  },
  // 프로필 수정
  profileUpdateAction: async ({ commit }, payload) => {
    console.log(state.userId)
    const response = await profileUpdate(payload.id, payload.frm, payload.config);
    const profileData = response.data
    console.log(profileData)
    commit("SET_USER_PROFILE", profileData);
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};