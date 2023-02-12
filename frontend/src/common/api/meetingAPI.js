import $axios from "axios";
// 미팅 생성
const meetingCreate = (user_id, payload) => $axios.post(`/meet/${user_id}`, payload);
// 로그인 된 유저가 호스트인 그룹 리스트
const hostGroup = (user_id) => $axios.get(`/meet/${user_id}/getGroup`)
// 유저 검색
const userSearch = (payload) => $axios.post(`/meet/search`, payload)
// 미팅 리스트
const requestMeetingList = (user_id) => $axios.get(`/user/${user_id}/meetList`);

export { meetingCreate, hostGroup, userSearch, requestMeetingList };
