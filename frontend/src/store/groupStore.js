import {
  groupCreate,
  requestGroupList,
  requestGroup,
  groupDelete,
  groupUpdate,
} from "../common/api/groupAPI";

//변수들의 집합
const state = {
  groupInfo: null,
  groupList: null,
  attdList: null, //그룹의 전체 출석
  attdUser: null, //유저 출석
};

const getters = {
  getToken: state => {
    return state.token;
  }
};

//변수를 조작하는 함수들을 선언
const mutations = {
  // 그룹 정보
  SET_GROUP_INFO: (state, payload) => {
    state.groupInfo = payload;
  },
  // 그룹 리스트
  SET_GROUP_LIST: (state, payload) => {
    console.log("페이로드",payload)
    state.groupList = payload;
    console.log("그룹리스트",state.groupList);
  },
  // 그룹 유저 리스트
  SET_GROUP_USER_LIST: (state, payload) => {
    state.groupUserList = payload;
  },
  // 그룹 삭제
  DELETE_GROUP_INFO: (state) => {
    state.groupInfo = [];
  },
  // 그룹 업데이트
  UPDATE_GROUP_INFO: (state, payload) => {
    state.groupInfo = payload;
  },
};

//비동기 처리를 하는 함수들
const actions = {
  // 그룹생성
  groupCreateAction: async ({ commit }, groupData) => {
    const response = await groupCreate(state.id,groupData);
    commit("SET_GROUP_INFO", response.data);
  },
  // 그룹 리스트 요청
  requestGroupListAction: async ({ commit }, id) => {
    const response = await requestGroupList(id);
    console.log("받은데이터",response.data)
    commit("SET_GROUP_LIST", response.data);
  },
  // 그룹 정보 요청
  requestGroupAction: async ({ commit }) => {
    const response = await requestGroup(groupid);
    commit("SET_GROUP_INFO", response.data);
  },
  // 그룹 사용자 리스트 요청
  requestUserListAction: async ({ commit }, group_id) => {
    const response = await requestUserList(group_id);
    commit("SET_GROUP_USER_LIST", response.data);
  },
  // 그룹삭제
  groupDeleteAction: async ({ commit }) => {
    const response = await groupDelete(groupid);
    console.log(response);
    commit("DELETE_GROUP_INFO");
  },
  // 그룹 수정
  groupUpdateAction: async ({ commit }, groupData) => {
    const response = await groupUpdate(groupid, groupData);
    commit("UPDATE_GROUP_INFO", response.data);
  },
  // 회원 검색
  requestUserSearchAction: async ({ commit }, usrname) => {
    const response = await userSearch(usrname);
    commit("UPDATE_SEARCH_USER_LIST", response.data);
  },

};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
