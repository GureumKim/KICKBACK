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
      isLogin: false,
      tokenExpireTime: null,

      login: () => {
        set({ isLogin: true });
      },
      logout: () => {
        set({ isLogin: false });
        cookie.remove("")
      },
    }),
    {
      name: "userLoginStatus",
    }
  )
);

export default useAuthStore;
