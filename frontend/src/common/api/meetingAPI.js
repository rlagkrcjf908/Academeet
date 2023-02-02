import $axios from "axios";

const meetingCreate = (payload) => $axios.post("/meet/{user_id}", payload);


export { meetingCreate };
