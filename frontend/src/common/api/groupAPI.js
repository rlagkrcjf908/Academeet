import $axios from "axios";
// `` 백틱으로 바꾸기
const groupCreate = (user_id, payload) => $axios.post(`/group/${user_id}`, payload);

const requestGroupList = (id) => $axios.get("/group/{id}/list");

const requestGroup = () => $axios.get("/group/{group_id}");

const groupDelete = (payload) => $axios.delete("/group/{group_id}/delete", payload);

const groupUpdate = (payload) => $axios.put("/group/{group_id}/update", payload);

// 출석관련 API
//그룹 내 유저들의 모든 리스트
const groupAttdList = () => $axios.get("/group/{group_id}/allattend");
//그룹 내 회원 출석 상세정보
const groupAttdUser = () => $axios.get("/group/{group_id}/{user_id}/attend");
//그룹 내 회원 출석 상세정보 수정
const groupAttdModify = (payload) => $axios.put("/group/{group_id)/{user_id}/update", payload);

export {
groupCreate,
requestGroupList,
requestGroup,
groupDelete,
groupUpdate,
groupAttdUser,
groupAttdList,
groupAttdModify,
};
