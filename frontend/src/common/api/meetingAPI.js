import $axios from "axios";

const meetingCreate = (user_id, payload) => $axios.post(`/meet/${user_id}`, payload);

const hostGroup = (user_id) => $axios.get(`/meet/${user_id}/getGroup`)

const userSearch = (payload) => $axios.post(`/meet/search`, payload)

const requestMeetingList = (user_id) => $axios.get(`/user/${user_id}/meetList`);

export { meetingCreate, hostGroup, userSearch, requestMeetingList };
