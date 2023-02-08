import { 
  groupCreate,
  requestGroupList, 
  requestGroup, 
  groupDelete, 
  groupUpdate, 
  requestUserList, 
  userSearch ,
  requestAttdUser,
  requestAttdList,
  attdUserUpdate,

} from "../common/api/groupAPI";

//변수들의 집합
const state = {
  groupInfo: null,
  groupList:[],
  groupUserList:[],
  attdList: [], //그룹의 전체 출석
  attdUser: [], //유저 출석
};

const getters = {
  getGroupList: state => {
    return state.groupList;
  },
  getGroupListLength: state => {
    return state.groupList.length;
  },
};

//변수를 조작하는 함수들을 선언
const mutations = {
  // 그룹 정보
  SET_GROUP_INFO: (state, payload) => {
    state.groupInfo = payload;
  },
  // 그룹 리스트
  SET_GROUP_LIST: (state, payload) => {
    state.groupList = payload;
  },
  // 그룹 유저 리스트
  SET_GROUP_USER_LIST: (state, payload) => {
    state.groupUserList = payload;
  },
  // 그룹 삭제
  DELETE_GROUP_INFO: (state) => {
    state.groupIpnfo = [];
  },
  // 그룹 업데이트
  UPDATE_GROUP_INFO: (state, payload) => {
    state.groupInfo = payload;
  },

  //출석관련
  _ATTD_USER: (state, payload) => {
    state.attdUser = payload; //유저 한명 수정
  },
  //출석관련
  SET_ATTD_LIST: (state, payload) => {
    console.log("출석리스트 페이로드",payload)
    state.attdList = payload; //그룹 전체 리스트
    state.groupInfo = 1;
    // console.log("groupList",state.groupList);
    console.log("state",state.attdList);
    console.log("attdList",state.attdList);
  },
  SET_ATTD_USER: (state, payload) => {
    state.attdUser = payload; //유저 한명의 출석 리스트
  },
  UPDATE_ATTD_USER: (state, payload) => {
    state.attdUser = payload; //유저 한명 수정
  },
};

//비동기 처리를 하는 함수들
const actions = {
  // 그룹생성
  groupCreateAction: async ({ commit }, groupData) => {
    const payload = {
      "name" : groupData.name,
      "users": groupData.users,
    } 
    const response = await groupCreate(groupData.userid, JSON.stringify(payload));
    commit("SET_GROUP_INFO", response.data);
  },
  // 그룹 리스트 요청
  requestGroupListAction: async ({ commit }, id) => {
    const response = await requestGroupList(id);
    commit("SET_GROUP_LIST", response.data);
  },
  // 그룹 정보 요청
  requestGroupAction: async ({ commit }, groupId) => {
    const response = await requestGroup(groupId);
    commit("SET_GROUP_INFO", response.data);
  },
  // 그룹 사용자 리스트 요청
  requestUserListAction: async ({ commit }, groupId) => {
    const response = await requestUserList(groupId);
    commit("SET_GROUP_USER_LIST", response.data);
  },
  // 그룹삭제
  groupDeleteAction: async ({ commit }) => {
    const response = await groupDelete(groupId)
    console.log(response);
    commit("DELETE_GROUP_INFO");
  },
  // 그룹 수정
  groupUpdateAction: async ({ commit }, groupData) => {
    const response = await groupUpdate(groupId, groupData);
    commit("UPDATE_GROUP_INFO", response.data);
  },
  // 회원 검색
  requestUserSearchAction: async ({ commit }, usrname) => {
    const response = await userSearch(usrname);
    commit("UPDATE_SEARCH_USER_LIST", response.data);
  },
  //출석관련
  requestAttdListAction: async ({ commit }, groupId) => {
    console.log("requestAttdListAction Test");
    const response = await requestAttdList(groupId);
    console.log(response.status);
    console.log("받은 데이터, response.data");
    console.log(response.data);
    // commit("SET_ATTD_LIST", response.data.groupData);
    commit("SET_ATTD_LIST", response.data);
  },
  requestAttdUser: async ({ commit }, data) => {
    const response = await requestAttdUser(data.groupId, data.userId);
    commit("SET_ATTD_USER", response.data.groupData);
  },
  attdUserUpdate: async ({ commit }, groupId) => {
    const response = await attdUserUpdate(groupId);
    commit("UPDATE_ATTD_USER", response.data.groupData);
  },

  // getAttdUser: async ({ commit }) => {
  //   axios.get(`http://192.168.129.112:8080/.json`).then((response)=>{
  //     console.log(response.data);//ajax로 받아온 데이터
  //     groupUserList = response.data.SET_ATTD_USER             
  //   }).then(groupUserList => {})
  // }
    

};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
