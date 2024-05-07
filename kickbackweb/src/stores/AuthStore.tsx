import { create } from "zustand";
import { persist } from "zustand/middleware";
import {Cookies} from 'react-cookie';

const cookie = new Cookies();

interface AuthStore {
  PATH: String;
  isLogin: boolean;
  login: () => void;
  logout: () => void;
}

const useAuthStore = create(
  persist<AuthStore>(
    (set, get) => ({
      // PATH: "http://localhost:8080",
      // PATH: "https://k10:8080",
      PATH: "https://k10c209.p.ssafy.io",
      isLogin: false,
      tokenExpireTime: null,

      login: () => {
        set({ isLogin: true });
      },
      logout: () => {
        set({ isLogin: false });
        localStorage.removeItem("userStatus");
        localStorage.removeItem("userLoginStatus");
        cookie.remove("accessToken");
        cookie.remove("refreshToken");
      },
    }),
    {
      name: "userLoginStatus",
    }
  )
);

export default useAuthStore;
