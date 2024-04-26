import { create } from "zustand";
import { persist } from "zustand/middleware";

const useBearStore = create(
  persist(
    (set) => ({
      // 로그인 여부
      isAuthenticated: Boolean(localStorage.getItem("accessToken")),

      // 로그인 하면 코드 뺌.
      login: (isAuthenticated = true) =>
        set(() => {
          if (!isAuthenticated) {
            localStorage.removeItem("accessToken");
          }
          return { isAuthenticated };
        }),

      // 로그아웃 시 localstorage에서 토큰 제거
      logout: () =>
        set((state) => {
          localStorage.removeItem("accessToken");
          return { isAuthenticated: false };
        }),

      // 회원가입 진행하면 바로 로그인 상태로 변경함
      signup: (isAuthenticated = true) =>
        set(() => {
          if (!isAuthenticated) {
            localStorage.removeItem("accessToken");
          }
          return { isAuthenticated };
        }),
    }),
    {
      name: "isLogin", // localStorage에 저장될 때 사용될 이름
      getStorage: () => localStorage, // 사용할 저장소 지정, 여기서는 localStorage 사용
    }
  )
);

export default useBearStore;
