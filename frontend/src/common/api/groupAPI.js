import $axios from "axios";

const groupCreate = (payload) => $axios.post("/group//{user_id}", payload);

const requestGroupList = (payload) => $axios.get("/group/{id}/list", payload);

const requestGroup = (groupid) => $axios.get("/group/{group_id}", groupid);

const groupDelete = (payload) => $axios.delete("/group/{group_id}/delete", payload);

const groupUpdate = (payload) => $axios.put("/group/{group_id}/update", payload);

export { groupCreate, requestGroupList, requestGroup, groupDelete, groupUpdate };
