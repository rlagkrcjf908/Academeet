import $axios from "axios";
// 로그인
const requestLogin = (payload) => $axios.post("/auth", payload);
// 비밀번호 일치 확인
const checkPassword = (id, payload) => $axios.post(`/user/${id}/pwinput`, payload);
// 비밀번호 변경
const changePassword = (id, payload) => $axios.put(`/user/${id}/pwchange`, payload);
// 프로필 정보 요청
const requestProfile = () => $axios.get("/user/me");
// 프로필 수정
const profileUpdate = (id, payload) => $axios.put(`/user/${id}/update`, payload);

export { requestLogin, changePassword, checkPassword, profileUpdate, requestProfile };