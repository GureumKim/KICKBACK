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
      PATH: "http://localhost:8080",
      // PATH: "http://192.168.100.158:8080",
      isLogin: false,
      tokenExpireTime: null,

      login: () => {
        set({ isLogin: true });
      },
      logout: () => {
        set({ isLogin: false });
        localStorage.clear();
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
