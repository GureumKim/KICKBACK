import { create } from "zustand";
import { persist } from "zustand/middleware";
import { UserInterface } from "../interface/UserInterface";

interface UserStore extends UserInterface {
  setUser: (userInfo: UserInterface) => void;
}

const useUserStore = create(
  persist<UserStore>(
    (set, get) => ({
      email: null,
      nickname: null,
      role: null,
      profileImage: null,

      setUser: (userInfo) => {
        set({
          email: userInfo.email,
          nickname: userInfo.nickname,
          role: userInfo.role,
          profileImage: userInfo.profileImage,
        });
      },
    }),
    {
      name: "userStore",
    }
  ));

export default useUserStore;
