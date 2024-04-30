import { create } from "zustand";
import { persist } from "zustand/middleware";
import { UserInterface } from "../interface/UserInterface";

interface UserStore extends UserInterface {
  setUser: (userInfo: UserInterface) => void;

  // friendsList: Array<any>;
  // setFriendsList: (friends: Array<any>) => void;
}

const useUserStore = create(
  persist<UserStore>(
    (set) => ({
      nickname: null,
      role: null,

      profileImage:null,
      
      // friendsList: [],

      setUser: (userInfo) => {
        set({
          nickname: userInfo.nickname,
          role: userInfo.role,
          profileImage: userInfo.profileImage
        });
      },

      // setFriendsList: (friends) => {
      //   set({ friendsList: friends })
      // }
    }),
    
    {
      name: "userStatus",
    }
  )
);

export default useUserStore;
