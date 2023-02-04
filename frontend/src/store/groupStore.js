import { 
  groupCreate,
  requestGroupList, 
  requestGroup, 
  groupDelete, 
  groupUpdate, 
  requestUserList, 
} from "../common/api/groupAPI";

//변수들의 집합
const state = {
  id: 9,
  groupInfo: null,
  groupList:[],
  groupUserList:null,
  attdList: null, //그룹의 전체 출석
  attdUser: null, //유저 출석
};
const getters = {
  getGroupList: state => {
    return state.groupList;
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
    state.groupInfo = [];
  },
  // 그룹 업데이트
  UPDATE_GROUP_INFO: (state, payload) => {
    state.groupInfo = payload;
  },

    //출석관련
    SET_ATTD_LIST: (state, payload) => {
      state.attdList = payload; //그룹 전체 리스트
    },
    SET_ATTD_USER: (state, payload) => {
      state.attdUser = payload; //유저 한명의 출석 리스트
    },
    _ATTD_USER: (state, payload) => {
      state.attdUser = payload; //유저 한명 수정
    },
};

//비동기 처리를 하는 함수들
const actions = {
  // 그룹생성
  groupCreateAction: async ({ commit }, groupData) => {
    const response = await groupCreate(state.id,JSON.stringify(groupData));
    commit("SET_GROUP_INFO", response.data);
  },
  // 그룹 리스트 요청
  requestGroupListAction: async ({ commit }, id) => {
    const response = await requestGroupList(id);
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
    //출석관련
    requestAttdList: async ({ commit }, groupId) => {
      const response = await requestAttdList(groupId);
      commit("SET_ATTD_LIST", response.data.groupData);
    },
    //유저만 부르게 어떻게 하지..?
    requestAttdUser: async ({ commit }, groupId) => {
      const response = await requestAttdUser(groupId);
      commit("SET_ATTD_USER", response.data.groupData);
    },
    attdUserUpdate: async ({ commit }, groupId) => {
      const response = await attdUserUpdate(groupId);
      commit("UPDATE_ATTD_USER", response.data.groupData);
    },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
