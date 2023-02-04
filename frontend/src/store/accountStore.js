import { requestLogin, profileUpdate, requestProfile  } from "../common/api/accountAPI";
import axios from 'axios'
import router from '../router/index'
import setAuthHeader from '../common/api/AxiosToken'

const state = {
  token: null,
  id: null,
  email: null,
  name: null,
  nick: null,
  phone: null,
  birth: null,
  profile: null
}

const getters = {
  'token': state => state.token,
  'id': state => state.id,
  'email': state => state.email,
  'name': state => state.name,
  'nick': state => state.nick,
  'phone': state => state.phone,
  'birth': state => state.birth,
  'profile': state => state.profile,

}

const mutations = {
  login (state, item) {
    state.token = item.headers['accessToken']
    state.id = item.data['id']
    state.email = item.data['email']
    state.name = item.data['name']
    state.nick = item.data['nick']
    state.phone = item.data['phone']
    state.birth = item.data['birth']
    state.profile = item.data['profile']
    // console.log(item.data.user.id)
  },

  // SET_USER_PROFILE: (state, profile) => {
  //   state.profile = profile;
  //   console.log(state.profile)
  // },
};

const actions = {
  login ({commit}, {email, password}) {
    const params = {
        "email": email,
        "password": password
    }
    axios.post("http://192.168.0.8:8080/api/v1/auth/login/", JSON.stringify(params), {
      headers: { 'content-type': 'application/json' }
    }).then(res => {
      alert("정보가 확인되었습니다.\n환영합니다!")
      commit('login', res)
      console.log(res)
      console.log(res.data)
      localStorage.setItem('token', res.data.accessToken)
      localStorage.setItem('userInfo', JSON.stringify(res.data.user));
      setAuthHeader(res.data.accessToken)
      
      router.push("/")
    }).catch(e => {
      console.log(e)
      alert("로그인 요청에 문제가 발생했습니다.")
    })
  },  
  getToken(){
    axios.get("http://192.168.0.8:8080/api/v1/")
    .then((res)=>console.log(res.data))
  },
  // profileUpdateAction: async ({ commit }, profileData) => {

  //   console.log('보낼데이터',profileData)
  //   console.log('보낼데이터2',JSON.stringify(profileData))

  //   const response = await profileUpdate(state.id, JSON.stringify(profileData));
  //   console.log('응답',response)
  //   console.log('응답2',response.data)
  //   // commit("SET_USER_PROFILE", response.data.profile);
  // },
  // requestProfileAction: async ({ commit }) => {
  //   const response = await requestProfile();
  //   console.log(response.data)

  //   commit("SET_USER_PROFILE", response.data);
  // }
};

export default {
  // namespaced: true,
  state,
  getters,
  mutations,
  actions
};
