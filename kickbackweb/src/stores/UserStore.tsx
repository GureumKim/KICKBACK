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
      name: null,
      role: null,

      profileImage:
      "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
      
      // friendsList: [],

      setUser: (userInfo) => {
        set({
          name: userInfo.name,
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
