import { create } from "zustand";
import { persist } from "zustand/middleware";

const useBearStore = create(
  persist(
    (set) => ({
      isAuthenticated: Boolean(localStorage.getItem("accessToken")),
      nickname: localStorage.getItem("nickname") || null,

      login: (accessToken, nickname) =>
        set(() => {
          localStorage.setItem("accessToken", accessToken);

          localStorage.setItem("nickname", nickname);
          return { isAuthenticated: true, nickname };
        }),

      logout: () =>
        set(() => {
          localStorage.removeItem("accessToken");
          localStorage.removeItem("nickname");
          return { isAuthenticated: false, nickname: null };
        }),

      // 회원정보 변경 시 로직 추가
      modify: (nickname) =>
        set(() => {
          localStorage.setItem("nickname", nickname);
          return { isAuthenticated: true, nickname };
        }),
    }),
    {
      name: "isLogin",
      getStorage: () => localStorage,
    }
  )
);

export default useBearStore;
