import $axios from "axios";

const groupCreate = (payload) => $axios.post("/group/{user_id}", payload);

const requestGroupList = () => $axios.get("/group/{id}/list");

const requestGroup = () => $axios.get("/group/{group_id}");

const groupDelete = (payload) => $axios.delete("/group/{group_id}/delete", payload);

const groupUpdate = (payload) => $axios.put("/group/{group_id}/update", payload);

export { groupCreate, requestGroupList, requestGroup, groupDelete, groupUpdate };
