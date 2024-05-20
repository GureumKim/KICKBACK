import { create } from "zustand";
import { persist } from "zustand/middleware";

interface NoticeStore {
    minId: number;
    maxId: number; 
    setMinId: (num:number) => void;
    setMaxId: (num:number) => void;
}

const useNoticeStore = create(
  persist<NoticeStore>((set, get) => ({
  minId:NaN,
  maxId:NaN,

  setMinId: (num) => {
    set({minId : num});
  },
  setMaxId: (num) => {
    set({maxId:num});
  }
}),
    {
        name: "noticeStatus"
    }
));

export default useNoticeStore;
