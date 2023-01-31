import { createStore } from 'vuex'
import accountStore from "./accountStore";
import groupStore from "./groupStore";
import meetingStore from "./meetingStore";
export default createStore({
    modules: {
      accountStore,
      groupStore,
      meetingStore,
    }
  });
  