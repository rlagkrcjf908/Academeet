import $axios from "axios";

// 그룹 생성 및 정보 관련 API
// 그룹생성
const groupCreate = (user_id,payload) => $axios.post(`/group/${user_id}`, payload);
// 현재 로그인 된 사용자가 속한 그룹의 리스트
const requestGroupList = (user_id) => $axios.get(`/group/${user_id}/list`);
// 선택한 그룹의 정보
const requestGroup = (group_id) => $axios.get(`/group/${group_id}`);//그룹호스트id 부르는 API
// 선택한 그룹의 유저들 리스트
const requestUserList = (group_id) => $axios.get(`/group/${group_id}/userList`);
// 유저 검색
const userSearch = (payload) => $axios.post(`/group/search`,payload);
// 그룹 삭제
const requestDeleteGroup = (group_id) => $axios.delete(`/group/${group_id}/delete`);

// 출석관련 API
//그룹 내 유저들의 모든 리스트
const requestAttdList = (group_id) => $axios.get(`/group/${group_id}/allattend`);
//그룹 내 회원 출석 상세정보, 여기에 meetId가 있음
const requestAttdUser = (group_id, select_user_id) => $axios.get(`/group/${group_id}/${select_user_id}/attend`);
//그룹 내 회원 출석 상세정보 수정
const attdUserUpdate = (group_id, select_user_id, payload) => $axios.put(`/group/${group_id}/${select_user_id}/update`, payload);

// 공지사항 관련 API
// 공지 글 작성
const artileCreate = (group_id, user_id, payload) => $axios.post(`/group/${group_id}/${user_id}/article`, payload);
// 선택 된 공지 글
const requestArtile = (article_id) => $axios.get(`/group/${article_id}/article/`);
// 공지 글 전체 리스트
const requestArtileList = (group_id, user_id) => $axios.get(`/group/${group_id}/${user_id}/artList`);
// 공지 글 삭제
const requestDeleteArtile = (group_id) => $axios.delete(`/group/${group_id}`);
// 공지 글 수정
const requestUpdateArtile = (article_id, payload) => $axios.put(`/group/${article_id}/articleupdate`, payload);

export { groupCreate, requestGroupList, requestGroup, requestUserList, userSearch, requestDeleteGroup, requestAttdList, requestAttdUser, attdUserUpdate, artileCreate, requestArtile, requestArtileList, requestDeleteArtile, requestUpdateArtile};
