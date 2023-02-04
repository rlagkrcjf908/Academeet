import $axios from "axios";

const groupCreate = (user_id,payload) => $axios.post(`/group/${user_id}`, payload);

const requestGroupList = (user_id) => $axios.get(`/group/${user_id}/list`);

const requestGroup = (group_id) => $axios.get(`/group/${group_id}`);

const requestUserList = (group_id) => $axios.get(`/group/${group_id}/userList`);

const groupDelete = (group_id, payload) => $axios.delete(`/group/${group_id}/delete`, payload);

const groupUpdate = (group_id, payload) => $axios.put(`/group/${group_id}/update`, payload);

const userSearch = (payload) => $axios.post(`/group/search`,payload);

export { groupCreate, requestGroupList, requestGroup, groupDelete, groupUpdate, requestUserList, userSearch };
