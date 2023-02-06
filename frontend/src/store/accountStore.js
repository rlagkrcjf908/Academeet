import { profileUpdate } from "../common/api/accountAPI";
import axios from 'axios'
import router from '../router/index'
import setAuthHeader from '../common/api/AxiosToken'

const state = {
  token : null,
  userInfo : null,
  userId : null,
  isAuthenticated : false,
};

const getters = {
  token: state => state.token,
  userInfo: state => state.userInfo,
  userId : state => state.userId,
  isAuthenticated : state => state.isAuthenticated
}

const mutations = {
  SET_USERINFO: (state, item) => {
    state.userInfo = item
  },
  SET_TOKEN : (state, item) => {
    state.token = item.headers['accessToken']
  },
  SET_USERID : (state, item) => {
    state.userId = item
  },
  SET_AUTHENTICATED : (state) => {
    state.isAuthenticated = true
  },
  LOGOUT : (state) => {
    state.token = null
    state.userInfo = null
    state.userId = null
    state.isAuthenticated = false
  },
  SET_USERID : (state, item) => {
    state.userId = item
  }
};

const actions = {
  // 로그인
  login ({commit}, loginData) {
    const params = {
        "email": loginData.email,
        "password": loginData.password
    }
    axios.post("http://192.168.100.191:8080/api/v1/auth/login/", JSON.stringify(params), {
      headers: { 'content-type': 'application/json' }
    }).then(res => {
      alert("정보가 확인되었습니다.\n환영합니다!")
      commit('SET_USERINFO', res.data)
      commit('SET_TOKEN', res)
      commit('SET_USERID', res.data.userRes.id)
      commit('SET_AUTHENTICATED')
      localStorage.setItem('token', res.data.accessToken)
      localStorage.setItem('userInfo', JSON.stringify(res.data.userRes));
      setAuthHeader(res.data.accessToken)
      router.push("/")
    }).catch(e => {
      console.log(e)
      alert("로그인 요청에 문제가 발생했습니다.")
    })
  },
  
  logout({commit}){
    axios.get("http://192.168.100.191:8080/api/v1/auth/logout/")
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    localStorage.removeItem('vuex')
    commit('LOGOUT')
    alert("로그아웃이 성공적으로 이루어졌습니다.")
    router.push("/login")
  },
  
  // 토큰 가져오기
  getToken(){
    axios.get("http://192.168.100.191:8080/api/v1/")
    .then((res)=>console.log(res.data))
  },
  // 프로필 수정
  // 프로필 수정
  profileUpdateAction: async ({ commit }, profileData) => {
    const response = await profileUpdate(state.id, JSON.stringify(profileData));
    commit("SET_USER_PROFILE", profileData);
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