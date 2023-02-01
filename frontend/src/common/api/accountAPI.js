import $axios from "axios";

const requestLogin = (payload) => $axios.post("/auth", payload);

const checkPassword = (id, payload) => $axios.post(`/user/${id}/pwinput`, payload)

const changePassword = (id, payload) => $axios.put(`/user/${id}/pwchange`, payload);

const requestProfile = () => $axios.get("/user/me");

const profileUpdate = (id, payload) => $axios.put(`/user/${id}/update`, payload);

export { requestLogin, changePassword, checkPassword, profileUpdate, requestProfile };