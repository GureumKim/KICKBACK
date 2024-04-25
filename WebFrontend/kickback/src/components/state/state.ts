import { create } from "zustand";
import { persist } from "zustand/middleware";

const useBearStore = create(
  persist(
    (set) => ({
      isAuthenticated: false, // 초기 로그인 상태
      user: null, // 로그인한 사용자 정보
      login: (userInfo) => {
        set({ isAuthenticated: true, user: userInfo });
      },
      logout: () => {
        set({ isAuthenticated: false, user: null });
      },
      signUp: (userInfo) => {
        set({ isAuthenticated: true, user: userInfo });
      },
    }),
    {
      name: "auth", // localStorage에 저장될 때 사용될 key 이름
      getStorage: () => localStorage, // 사용할 스토리지 지정
    }
  )
);

export default useBearStore;
