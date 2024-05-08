import { create } from "zustand";
import { UserInterface } from "../interface/UserInterface";

interface UserStore extends UserInterface {
  setUser: (userInfo: UserInterface) => void;
}

const useUserStore = create<UserStore>((set) => ({
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
}));

export default useUserStore;
