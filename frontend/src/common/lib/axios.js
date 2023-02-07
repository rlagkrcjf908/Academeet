import VueAxios from 'vue-axios'
import axios from 'axios'
// import config from '../config'

const BASE_URL = '/api/v1'
const DEFAULT_ACCEPT_TYPE = 'application/json'

axios.defaults.baseURL = BASE_URL
axios.defaults.headers['Content-Type'] = DEFAULT_ACCEPT_TYPE

const setAuthHeader = (token) => {
  if (token) {
      axios.defaults.headers = {
          Authorization: `Bearer ${token}`,
          'content-type': 'application/json'
      }
  } else {
      delete axios.defaults.headers.Authorization;
  }
}


export default { VueAxios, axios, setAuthHeader }