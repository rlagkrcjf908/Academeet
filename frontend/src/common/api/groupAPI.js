import $axios from "axios";

const groupCreate = (user_id,payload) => $axios.post(`/group/${user_id}`, payload);

const requestGroupList = (user_id) => $axios.get(`/group/${user_id}/list`);

// 학철이형한테 requestGroup 요청을 하면 다시 groupId, name, usesrId(호스트)를 달라고 요청하기
const requestGroup = (group_id) => $axios.get(`/group/${group_id}`);//그룹호스트id 부르는 API

// const requestGroupHost = (group_id) => $axios.get(`/group/${group_id}`);

const requestUserList = (group_id) => $axios.get(`/group/${group_id}/userList`);

const groupDelete = (group_id, payload) => $axios.delete(`/group/${group_id}/delete`, payload);

const groupUpdate = (group_id, payload) => $axios.put(`/group/${group_id}/update`, payload);

const userSearch = (payload) => $axios.post(`/group/search`,payload);

const requestDeleteGroup = (group_id) => $axios.delete(`/group/${group_id}/delete`);

// 출석관련 API
//그룹 내 유저들의 모든 리스트
const requestAttdList = (group_id) => $axios.get(`/group/${group_id}/allattend`);
//그룹 내 회원 출석 상세정보, 여기에 meetId가 있음
const requestAttdUser = (group_id, select_user_id) => $axios.get(`/group/${group_id}/${select_user_id}/attend`);
//그룹 내 회원 출석 상세정보 수정
const attdUserUpdate = (group_id, select_user_id, payload) => $axios.put(`/group/${group_id}/${select_user_id}/update`, payload);

export { groupCreate, requestGroupList, requestGroup, groupDelete, groupUpdate, requestUserList, userSearch, requestDeleteGroup, requestAttdList, requestAttdUser, attdUserUpdate };


// import $axios from "axios";

// const groupCreate = (user_id,payload) => $axios.post(`/group/${user_id}`, payload);

// const requestGroupList = (user_id) => $axios.get(`/group/${user_id}/list`);

// const requestGroup = (group_id) => $axios.get(`/group/${group_id}`);

// const requestUserList = (group_id) => $axios.get(`/group/${group_id}/userList`);

// const groupDelete = (group_id, payload) => $axios.delete(`/group/${group_id}/delete`, payload);

// const groupUpdate = (group_id, payload) => $axios.put(`/group/${group_id}/update`, payload);

// const userSearch = (payload) => $axios.post(`/group/search`,payload);

// const requestDeleteGroup = (group_id) => $axios.delete(`/group/${{group_id}}/delete`,group_id);

// export {
// groupCreate,
// requestGroupList,
// requestGroup,
// requestUserList,
// groupDelete,
// groupUpdate,
// userSearch,
// requestAttdUser,
// requestAttdList,
// attdUserUpdate,
// requestDeleteGroup,
// };